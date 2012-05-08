/**
 *
 * This software is the confidential and proprietary information of the author,
 * Sai Pullabhotla. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with the author.
 *
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 *
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
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MTextField;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * A dialog box that allows the user to type in a file name that is to be
 * created.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class NewLocalFileDlg extends MDialog implements ActionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.NewLocalFileDlg");
	private static final String HELP_ID = "local.newFile";
	private MTextField tfFile = null;
	private MButton butCreate = null;
	private MButton butCancel = null;
	private MButton butHelp = null;
	private boolean approved = false;

	/**
	 * Created an instance of <code>NewLocalFileDlg</code>.
	 * 
	 * @param frame
	 *            Parent frame.
	 * 
	 */
	public NewLocalFileDlg(Frame frame) {
		super(frame, resources.getString("title.dialog"), true);
		getContentPane().setLayout(new GridBagLayout());
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), HELP_ID);
		initComponents();
		pack();
	}

	/**
	 * Returns the name of the file as entred by the user
	 * 
	 * @return File name
	 * 
	 */
	public String getFile() {
		if (approved) {
			return tfFile.getText();
		}
		return null;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butCreate) {
			if (!validateInput()) {
				return;
			}
			approved = true;
			close();
		} else if (evt.getSource() == butCancel) {
			close();
		}
	}

	private void close() {
		setVisible(false);
	}

	@Override
	protected void escape() {
		butCancel.doClick();
	}

	private boolean validateInput() {
		String error = null;
		Component errorComponent = null;
		if (tfFile.getText().trim().length() == 0) {
			error = resources.getString("error.file.required");
			errorComponent = tfFile;
		}
		if (error != null) {
			GUIUtil.showError(this, error);
			if (errorComponent != null) {
				errorComponent.requestFocusInWindow();
			}
			return false;
		}
		return true;
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;

		MLabel labFile = new MLabel(resources.getString("text.file"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(12, 12, 11, 11);
		getContentPane().add(labFile, c);

		tfFile = new MTextField(20);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.insets = new Insets(12, 0, 11, 11);
		getContentPane().add(tfFile, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(6, 12, 11, 11);
		getContentPane().add(getCommandButtons(), c);
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butCreate = new MButton(resources.getString("text.create"));
		butCreate.addActionListener(this);
		getRootPane().setDefaultButton(butCreate);
		butCancel = new MButton(CommonResources.getString("text.cancel"));
		butCancel.addActionListener(this);
		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		panel.add(butCreate);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butCancel);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);

		return panel;
	}
}
