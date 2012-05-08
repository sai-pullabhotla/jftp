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
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MTextField;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * A dialog box that allows the user to change to a specified remote directory.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class ChangeRemoteDirectoryDlg extends MDialog implements ActionListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.ChangeRemoteDirectoryDlg");
	private static final String HELP_ID = "remote.changeDirectory";
	private JTextField tfDirectory = null;
	private MButton butChange = null;
	private MButton butCancel = null;
	private MButton butHelp = null;
	// private String directory = null;
	private boolean approved = false;

	/**
	 * Creates an instance of <code>ChangeRemoteDirectoryDlg</code> dialog.
	 * 
	 * @param parent
	 *            Parent frame.
	 * 
	 */
	public ChangeRemoteDirectoryDlg(Frame parent) {
		super(parent, resources.getString("title.dialog"), true);
		getContentPane().setLayout(new GridBagLayout());
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), HELP_ID);
		initComponents();
		pack();
	}

	/**
	 * Returns the directory entered by the user.
	 * 
	 * @return Directory typed by the user.
	 * 
	 */
	public String getDirectory() {
		if (approved) {
			return tfDirectory.getText();
		}
		return null;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butChange) {
			if (!validateInput()) {
				return;
			}
			approved = true;
			close();
		} else if (evt.getSource() == butCancel) {
			close();
		}
	}

	@Override
	protected void escape() {
		butCancel.doClick();
	}

	private void close() {
		setVisible(false);
	}

	private boolean validateInput() {
		String message = null;
		Component errorComponent = null;
		if (tfDirectory.getText().trim().length() == 0) {
			message = resources.getString("error.directory.required");
			errorComponent = tfDirectory;
		}
		if (message != null) {
			GUIUtil.showError(this, message);
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
		c.anchor = GridBagConstraints.WEST;

		MLabel labDirectory = new MLabel(resources.getString("text.directory"));
		tfDirectory = new MTextField(20);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 12, 12, 12);
		getContentPane().add(labDirectory, c);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 0, 12, 12);
		getContentPane().add(tfDirectory, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(new MLabel(), c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(6, 12, 12, 12);
		getContentPane().add(getCommandButtons(), c);
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butChange = new MButton(resources.getString("text.change"));
		butCancel = new MButton(CommonResources.getString("text.cancel"));
		butHelp = new MButton(CommonResources.getString("text.help"));

		butChange.addActionListener(this);
		getRootPane().setDefaultButton(butChange);
		butCancel.addActionListener(this);
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		panel.add(butChange);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butCancel);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);

		return panel;
	}
}