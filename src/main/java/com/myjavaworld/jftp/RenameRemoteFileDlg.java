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
import java.awt.event.WindowEvent;
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
 * A dialog box used to rename a remote file/directory
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class RenameRemoteFileDlg extends MDialog implements ActionListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.RenameRemoteFileDlg");
	private static final String HELP_ID = "remote.rename";
	private MTextField tfFrom = null;
	private MTextField tfTo = null;
	private MButton butRename = null;
	private MButton butCancel = null;
	private MButton butHelp = null;
	private String fromFile = null;
	// private String toFile = null;
	private boolean approved = false;

	public RenameRemoteFileDlg(Frame parent, String fromFile) {
		super(parent, resources.getString("title.dialog"), true);
		this.fromFile = fromFile;
		getContentPane().setLayout(new GridBagLayout());
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), HELP_ID);
		initComponents();
		pack();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butRename) {
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

	public String getFromFile() {
		if (approved) {
			return tfFrom.getText();
		}
		return null;
	}

	public String getToFile() {
		if (approved) {
			return tfTo.getText();
		}
		return null;
	}

	@Override
	public void windowOpened(WindowEvent evt) {
		if (tfFrom.getText().trim().length() > 0) {
			tfTo.requestFocus();
		}
	}

	private boolean validateInput() {
		String message = null;
		Component errorComponent = null;
		if (tfFrom.getText().trim().length() == 0) {
			message = resources.getString("error.from.required");
			errorComponent = tfFrom;
		} else if (tfTo.getText().trim().length() == 0) {
			message = resources.getString("error.to.required");
			errorComponent = tfTo;
		}
		if (message == null) {
			return true;
		}
		GUIUtil.showError(this, message);
		if (errorComponent != null) {
			errorComponent.requestFocusInWindow();
		}
		return false;
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;

		MLabel labFrom = new MLabel(resources.getString("text.from"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(12, 12, 12, 12);
		getContentPane().add(labFrom, c);

		tfFrom = new MTextField(20);
		tfFrom.setText(fromFile);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.insets = new Insets(12, 0, 11, 11);
		getContentPane().add(tfFrom, c);

		MLabel labTo = new MLabel(resources.getString("text.to"));
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(labTo, c);

		tfTo = new MTextField(20);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.insets = new Insets(0, 0, 11, 11);
		getContentPane().add(tfTo, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(6, 12, 11, 11);
		getContentPane().add(getCommandButtons(), c);
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butRename = new MButton(resources.getString("text.rename"));
		butRename.addActionListener(this);
		getRootPane().setDefaultButton(butRename);
		butCancel = new MButton(CommonResources.getString("text.cancel"));
		butCancel.addActionListener(this);
		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		panel.add(butRename);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butCancel);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);

		return panel;
	}
}