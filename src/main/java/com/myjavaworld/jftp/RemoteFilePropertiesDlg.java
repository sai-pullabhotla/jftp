/*
 * Copyright 2012 jMethods, Inc. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.myjavaworld.jftp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.myjavaworld.ftp.FTPClient;
import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MCheckBox;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MLabelTextField;
import com.myjavaworld.gui.SwingWorker;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.Filter;
import com.myjavaworld.util.ResourceLoader;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RemoteFilePropertiesDlg extends MDialog implements ActionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.RemoteFilePropertiesDlg");
	private static final String HELP_ID = "remote.properties";
	private FTPClient client = null;
	private RemoteFile file = null;
	private RemoteFile newFile = null;
	private MLabelTextField tfFileName = null;
	private MLabelTextField tfSite = null;
	private MLabelTextField tfFullPath = null;
	private MLabelTextField tfType = null;
	private MLabelTextField tfSize = null;
	private MLabelTextField tfContents = null;
	private MLabelTextField tfLastModified = null;
	private MLabelTextField tfLinkCount = null;
	private MLabelTextField tfOwner = null;
	private MLabelTextField tfGroup = null;
	private MCheckBox cbOwnerRead = null;
	private MCheckBox cbOwnerWrite = null;
	private MCheckBox cbOwnerExecute = null;
	private MCheckBox cbGroupRead = null;
	private MCheckBox cbGroupWrite = null;
	private MCheckBox cbGroupExecute = null;
	private MCheckBox cbPublicRead = null;
	private MCheckBox cbPublicWrite = null;
	private MCheckBox cbPublicExecute = null;
	private MCheckBox cbRecursive = null;
	private MButton butOK = null;
	private MButton butCancel = null;
	private MButton butHelp = null;
	private MessageFormat sizeFormatter = null;
	private MessageFormat contentsFormatter = null;
	private int dirCount = 0;
	private int fileCount = 0;
	private long totalSize = 0L;
	private boolean closing = false;
	private boolean approved = false;
	private Filter filter = null;

	public RemoteFilePropertiesDlg(Frame parent, FTPClient client,
			RemoteFile file, Filter filter) {
		super(parent, "", true);
		this.client = client;
		this.file = file;
		this.filter = filter;
		sizeFormatter = new MessageFormat(resources.getString("value.size"));
		contentsFormatter = new MessageFormat(
				resources.getString("value.contents"));
		getContentPane().setLayout(new GridBagLayout());
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), HELP_ID);
		initComponents();
		updateTitle();
		populateScreen();
		pack();
	}

	public RemoteFile getFile() {
		if (approved) {
			return newFile;
		}
		return null;
	}

	public boolean isRecursive() {
		return cbRecursive.isSelected();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butOK) {
			okButtonPressed();
		} else if (evt.getSource() == butCancel) {
			newFile = null;
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

	private void start() {
		dirCount = -1;
		fileCount = 0;
		totalSize = 0L;
		closing = false;
		SwingWorker worker = new SwingWorker() {

			@Override
			public Object construct() {
				try {
					RemoteFile workingDir = client.getWorkingDirectory();
					computeSizeAndContents(file);
					client.setWorkingDirectory(workingDir);
				} catch (Exception exp) {
					System.err.println(exp);
				}
				return null;
			}

			@Override
			public void finished() {
				updateSizeAndContents();
			}
		};
		worker.start();
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

	private void computeSizeAndContents(RemoteFile file) {
		if (closing) {
			return;
		}
		if (file.getName().equals(".") || file.getName().equals("..")) {
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
			try {
				client.setWorkingDirectory(file);
				RemoteFile[] children = client.list(filter);
				if (children == null) {
					return;
				}
				for (int i = 0; i < children.length; i++) {
					computeSizeAndContents(children[i]);
				}
				updateSizeAndContents();
			} catch (Exception exp) {
				System.err.println(exp);
			}
		}
	}

	private void okButtonPressed() {
		approved = true;
		String newFileName = tfFileName.getText().trim();
		// boolean dir = file.isDirectory();
		String attributes = "";
		String oldAttributes = file.getAttributes();
		if (oldAttributes.length() == 10) {
			attributes += oldAttributes.charAt(0);
			attributes += cbOwnerRead.isSelected() ? 'r' : '-';
			attributes += cbOwnerWrite.isSelected() ? 'w' : '-';
			attributes += cbOwnerExecute.isSelected() ? 'x' : '-';
			attributes += cbGroupRead.isSelected() ? 'r' : '-';
			attributes += cbGroupWrite.isSelected() ? 'w' : '-';
			attributes += cbGroupExecute.isSelected() ? 'x' : '-';
			attributes += cbPublicRead.isSelected() ? 'r' : '-';
			attributes += cbPublicWrite.isSelected() ? 'w' : '-';
			attributes += cbPublicExecute.isSelected() ? 'x' : '-';
		}
		newFile = client.getListParser().createRemoteFile(newFileName);
		newFile.setAttributes(attributes);
		close();
	}

	private void populateScreen() {
		tfFileName.setText(file.getName());
		tfSite.setText(client.getRemoteHost());
		tfFullPath.setText(file.getPath());
		tfType.setText(file.getType());
		String size = sizeFormatter
				.format(new Long[] { new Long(file.getSize()) });
		tfSize.setText(size);
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL,
				DateFormat.MEDIUM);
		Date lastModified = new Date(file.getLastModified());
		tfLastModified.setText(df.format(lastModified));
		String linkCount = NumberFormat.getInstance().format(
				file.getLinkCount());
		tfLinkCount.setText(linkCount);
		tfOwner.setText(file.getOwner());
		tfGroup.setText(file.getGroup());
	}

	@Override
	protected void escape() {
		close();
	}

	private void updateTitle() {
		MessageFormat titleFormatter = new MessageFormat(
				resources.getString("title.dialog"));
		String title = titleFormatter.format(new Object[] { file.getName() });
		setTitle(title);
	}

	private void close() {
		closing = true;
		setVisible(false);
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
		if (file.isDirectory()) {
			labIcon.setIcon(JFTPUtil.getIcon("directory16.gif"));
		} else if (file.isFile()) {
			labIcon.setIcon(JFTPUtil.getIcon("file16.gif"));
		}
		getContentPane().add(labIcon, c);
		c.gridx = 1;
		c.gridy = row++;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 0, 12, 12);
		// tfFileName = new MTextField(30);
		tfFileName = new MLabelTextField(30);
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
		getContentPane().add(new MLabel(resources.getString("text.site")), c);
		c.gridx = 1;
		c.gridy = row++;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		tfSite = new MLabelTextField(20);
		getContentPane().add(tfSite, c);
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
			getContentPane().add(this.tfContents, c);
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
		c.gridy = row;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new MLabel(resources.getString("text.links")), c);
		c.gridx = 1;
		c.gridy = row++;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		tfLinkCount = new MLabelTextField(20);
		getContentPane().add(tfLinkCount, c);
		c.gridx = 0;
		c.gridy = row;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new MLabel(resources.getString("text.owner")), c);
		c.gridx = 1;
		c.gridy = row++;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		tfOwner = new MLabelTextField(20);
		getContentPane().add(tfOwner, c);
		c.gridx = 0;
		c.gridy = row;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new MLabel(resources.getString("text.group")), c);
		c.gridx = 1;
		c.gridy = row++;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		tfGroup = new MLabelTextField(20);
		getContentPane().add(tfGroup, c);
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
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				resources.getString("title.attributes")));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		String attributes = file.getAttributes();
		boolean ownerRead = false;
		boolean ownerWrite = false;
		boolean ownerExecute = false;
		boolean groupRead = false;
		boolean groupWrite = false;
		boolean groupExecute = false;
		boolean publicRead = false;
		boolean publicWrite = false;
		boolean publicExecute = false;
		if (attributes.length() == 10) {
			ownerRead = attributes.charAt(1) == 'r' ? true : false;
			ownerWrite = attributes.charAt(2) == 'w' ? true : false;
			ownerExecute = attributes.charAt(3) == 'x' ? true : false;
			groupRead = attributes.charAt(4) == 'r' ? true : false;
			groupWrite = attributes.charAt(5) == 'w' ? true : false;
			groupExecute = attributes.charAt(6) == 'x' ? true : false;
			publicRead = attributes.charAt(7) == 'r' ? true : false;
			publicWrite = attributes.charAt(8) == 'w' ? true : false;
			publicExecute = attributes.charAt(9) == 'x' ? true : false;
		}
		cbOwnerRead = new MCheckBox("", ownerRead);
		cbOwnerWrite = new MCheckBox("", ownerWrite);
		cbOwnerExecute = new MCheckBox("", ownerExecute);
		cbGroupRead = new MCheckBox("", groupRead);
		cbGroupWrite = new MCheckBox("", groupWrite);
		cbGroupExecute = new MCheckBox("", groupExecute);
		cbPublicRead = new MCheckBox("", publicRead);
		cbPublicWrite = new MCheckBox("", publicWrite);
		cbPublicExecute = new MCheckBox("", publicExecute);
		cbRecursive = new MCheckBox(resources.getString("text.recursive"),
				false);
		if (!file.isDirectory()) {
			cbRecursive.setEnabled(false);
		}

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		panel.add(new MLabel(), c);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 0, 3, 3);
		panel.add(new MLabel(resources.getString("text.read")), c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 0, 3, 3);
		panel.add(new MLabel(resources.getString("text.write")), c);

		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 0, 3, 3);
		panel.add(new MLabel(resources.getString("text.execute")), c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 3, 3, 3);
		panel.add(new MLabel(resources.getString("text.owner")), c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 3, 3);
		panel.add(cbOwnerRead, c);

		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 3, 3);
		panel.add(cbOwnerWrite, c);

		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 3, 3);
		panel.add(cbOwnerExecute, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 3, 3, 3);
		panel.add(new MLabel(resources.getString("text.group")), c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 3, 3);
		panel.add(cbGroupRead, c);

		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 3, 3);
		panel.add(cbGroupWrite, c);

		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 3, 3);
		panel.add(cbGroupExecute, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 3, 3, 3);
		panel.add(new MLabel(resources.getString("text.public")), c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 3, 3);
		panel.add(cbPublicRead, c);

		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 3, 3);
		panel.add(cbPublicWrite, c);

		c.gridx = 3;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 3, 3);
		panel.add(cbPublicExecute, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0, 3, 3, 3);
		panel.add(cbRecursive, c);

		return panel;
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butOK = new MButton(CommonResources.getString("text.ok"));
		butOK.addActionListener(this);
		getRootPane().setDefaultButton(butOK);
		butCancel = new MButton(CommonResources.getString("text.cancel"));
		butCancel.addActionListener(this);
		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);
		panel.add(butOK);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butCancel);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);
		return panel;
	}
}