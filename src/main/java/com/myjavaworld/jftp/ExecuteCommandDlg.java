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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.Box;
import javax.swing.BoxLayout;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MScrollPane;
import com.myjavaworld.gui.MTextArea;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * A dialog box that allows users for entering FTP commands.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class ExecuteCommandDlg extends MDialog implements ActionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.ExecuteCommandDlg");
	private static final String HELP_ID = "remote.executeCommands";
	private MTextArea taCommand = null;
	private MButton butExecute = null;
	private MButton butCancel = null;
	private MButton butHelp = null;
	// private String command = null;
	private boolean approved = false;

	public ExecuteCommandDlg(Frame parent) {
		super(parent, resources.getString("title.dialog"), true);
		getContentPane().setLayout(new GridBagLayout());
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), HELP_ID);
		initComponents();
		pack();
	}

	public String[] getCommands() {
		if (!approved) {
			return null;
		}
		String command = taCommand.getText();
		if (command == null) {
			return null;
		}
		StringTokenizer tokenizer = new StringTokenizer(command, "\r\n");
		ArrayList commandList = new ArrayList(5);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.trim().length() > 0) {
				commandList.add(token);
			}
		}
		String[] commandArray = new String[commandList.size()];
		commandArray = (String[]) (commandList.toArray(commandArray));
		return commandArray;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butExecute) {
			if (!validateInput()) {
				return;
			}
			approved = true;
			// command = taCommand.getText();
			close();
		} else if (evt.getSource() == butCancel) {
			// command = null;
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
		if (taCommand.getText().trim().length() == 0) {
			message = resources.getString("error.commands.required");
			errorComponent = taCommand;
		}
		if (message != null) {
			GUIUtil.showError(this, message);
			if (errorComponent != null) {
				taCommand.requestFocusInWindow();
			}
			return false;
		}
		return true;
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		MLabel labCommand = new MLabel(resources.getString("text.commands"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 12, 12, 12);
		getContentPane().add(labCommand, c);

		taCommand = new MTextArea(5, 30);
		MScrollPane commandScroller = new MScrollPane(taCommand);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(commandScroller, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(6, 12, 12, 12);
		getContentPane().add(getCommandButtons(), c);
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butExecute = new MButton(resources.getString("text.execute"));
		butExecute.addActionListener(this);
		getRootPane().setDefaultButton(butExecute);
		butCancel = new MButton(CommonResources.getString("text.cancel"));
		butCancel.addActionListener(this);
		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		panel.add(butExecute);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butCancel);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);

		return panel;
	}
}
