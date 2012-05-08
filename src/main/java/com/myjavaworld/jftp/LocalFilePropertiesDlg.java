/*
 * Created on Jul 10, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.myjavaworld.jftp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MLabelTextField;
import com.myjavaworld.gui.SwingWorker;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.Filter;
import com.myjavaworld.util.ResourceLoader;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LocalFilePropertiesDlg extends MDialog implements ActionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.LocalFilePropertiesDlg");
	private static final String HELP_ID = "local.properties";
	private LocalFile file = null;
	private MLabelTextField tfFileName = null;
	private MLabelTextField tfFullPath = null;
	private MLabelTextField tfType = null;
	private MLabelTextField tfSize = null;
	private MLabelTextField tfLastModified = null;
	private MLabelTextField tfContents = null;
	private JCheckBox cbRead = null;
	private JCheckBox cbWrite = null;
	private JCheckBox cbHidden = null;
	private MButton butClose = null;
	private MButton butHelp = null;
	private int dirCount = -1;
	private int fileCount = 0;
	private long totalSize = 0L;
	private MessageFormat sizeFormatter = null;
	private MessageFormat contentsFormatter = null;
	private boolean closing = false;
	private Filter filter = null;

	public LocalFilePropertiesDlg(Frame parent, LocalFile file, Filter filter) {
		super(parent, "", true);
		this.file = file;
		this.filter = filter;
		this.totalSize = file.getSize();
		getContentPane().setLayout(new GridBagLayout());
		sizeFormatter = new MessageFormat(resources.getString("value.size"));
		contentsFormatter = new MessageFormat(
				resources.getString("value.contents"));
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), HELP_ID);
		initComponents();
		updateTitle();
		populateScreen();
		pack();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butClose) {
			close();
		}
	}

	@Override
	public void windowClosing(WindowEvent evt) {
		close();
	}

	@Override
	public void windowOpened(WindowEvent evt) {
		if (file.isDirectory()) {
			start();
		}
	}

	@Override
	protected void escape() {
		close();
	}

	private void start() {
		dirCount = -1;
		fileCount = 0;
		totalSize = 0L;
		closing = false;
		SwingWorker worker = new SwingWorker() {

			@Override
			public Object construct() {
				computeSizeAndContents(file);
				return null;
			}

			@Override
			public void finished() {
				updateSizeAndContents();
			}
		};
		worker.start();
	}

	private void updateTitle() {
		MessageFormat titleFormatter = new MessageFormat(
				resources.getString("title.dialog"));
		String title = titleFormatter.format(new Object[] { file
				.getDisplayName() });
		setTitle(title);
	}

	private void close() {
		closing = true;
		setVisible(false);
	}

	private void populateScreen() {
		tfFileName.setText(file.getDisplayName());
		try {
			tfFullPath.setText(file.getFile().getCanonicalPath());
		} catch (IOException exp) {
			tfFullPath.setText(file.getFile().getAbsolutePath());
		}
		tfType.setText(file.getType());
		String size = sizeFormatter.format(new Long[] { new Long(totalSize) });
		tfSize.setText(size);
		// updateSizeAndContents();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL,
				DateFormat.MEDIUM);
		Date lastModified = new Date(file.getLastModified());
		tfLastModified.setText(df.format(lastModified));
	}

	private void updateSizeAndContents() {
		String size = sizeFormatter.format(new Long[] { new Long(totalSize) });
		tfSize.setText(size);
		if (tfContents != null) {
			String contents = contentsFormatter.format(new Long[] {
					new Long(dirCount), new Long(fileCount) });
			tfContents.setText(contents);
		}
	}

	private void computeSizeAndContents(LocalFile file) {
		if (closing) {
			return;
		}
		if (file.isFile()) {
			fileCount++;
			totalSize += file.getSize();
		} else if (file.isDirectory()) {
			if (closing) {
				return;
			}
			dirCount++;
			totalSize += file.getSize();
			LocalFile[] children = file.list(filter);
			for (int i = 0; i < children.length; i++) {
				computeSizeAndContents(children[i]);
			}
		}
		updateSizeAndContents();
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		int row = 0;

		c.gridx = 0;
		c.gridy = row;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 12, 12, 12);
		MLabel labIcon = new MLabel();
		labIcon.setHorizontalAlignment(SwingConstants.LEFT);
		FileSystemView view = FileSystemView.getFileSystemView();
		labIcon.setIcon(view.getSystemIcon(file.getFile()));
		getContentPane().add(labIcon, c);

		c.gridx = 1;
		c.gridy = row++;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 0, 12, 12);
		tfFileName = new MLabelTextField(30);
		tfFileName.setText(file.getDisplayName());
		getContentPane().add(tfFileName, c);

		c.gridx = 0;
		c.gridy = row++;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new JSeparator(SwingConstants.HORIZONTAL), c);

		c.gridx = 0;
		c.gridy = row;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new MLabel(resources.getString("text.fullPath")),
				c);

		c.gridx = 1;
		c.gridy = row++;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		tfFullPath = new MLabelTextField(20);
		getContentPane().add(tfFullPath, c);

		c.gridx = 0;
		c.gridy = row;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new MLabel(resources.getString("text.type")), c);

		c.gridx = 1;
		c.gridy = row++;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		tfType = new MLabelTextField(20);
		getContentPane().add(tfType, c);

		c.gridx = 0;
		c.gridy = row;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new MLabel(resources.getString("text.size")), c);

		c.gridx = 1;
		c.gridy = row++;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		tfSize = new MLabelTextField(20);
		getContentPane().add(tfSize, c);

		if (file.isDirectory()) {
			c.gridx = 0;
			c.gridy = row;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.weightx = 0.0;
			c.weighty = 0.0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(0, 12, 12, 12);
			getContentPane().add(
					new MLabel(resources.getString("text.contents")), c);

			c.gridx = 1;
			c.gridy = row++;
			c.gridwidth = 2;
			c.gridheight = 1;
			c.weightx = 1.0;
			c.weighty = 0.0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(0, 0, 12, 12);
			tfContents = new MLabelTextField(20);
			getContentPane().add(tfContents, c);
		}

		c.gridx = 0;
		c.gridy = row;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(
				new MLabel(resources.getString("text.lastModified")), c);

		c.gridx = 1;
		c.gridy = row++;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		tfLastModified = new MLabelTextField(20);
		getContentPane().add(tfLastModified, c);

		c.gridx = 0;
		c.gridy = row++;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(getAttributesPanel(), c);

		c.gridx = 0;
		c.gridy = row++;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new MLabel(), c);

		c.gridx = 0;
		c.gridy = row++;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(6, 12, 11, 11);
		getContentPane().add(getCommandButtons(), c);
	}

	private Component getAttributesPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 3));
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				resources.getString("title.attributes")));

		cbRead = new JCheckBox(resources.getString("text.read"), file.canRead());
		cbWrite = new JCheckBox(resources.getString("text.write"),
				file.canWrite());
		cbHidden = new JCheckBox(resources.getString("text.hidden"),
				file.isHidden());

		cbRead.setEnabled(false);
		cbWrite.setEnabled(false);
		cbHidden.setEnabled(false);

		panel.add(cbRead);
		panel.add(cbWrite);
		panel.add(cbHidden);

		return panel;
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butClose = new MButton(CommonResources.getString("text.close"));
		butClose.addActionListener(this);
		getRootPane().setDefaultButton(butClose);
		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		panel.add(butClose);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);

		return panel;
	}
}
