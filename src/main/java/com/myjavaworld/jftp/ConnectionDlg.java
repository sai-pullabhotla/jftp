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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.myjavaworld.ftp.FTPConstants;
import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.IntegerField;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MCheckBox;
import com.myjavaworld.gui.MComboBox;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MPasswordField;
import com.myjavaworld.gui.MRadioButton;
import com.myjavaworld.gui.MScrollPane;
import com.myjavaworld.gui.MTextArea;
import com.myjavaworld.gui.MTextField;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * A dialog box that takes connection parameters from the user.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class ConnectionDlg extends MDialog implements ActionListener,
		ComponentListener, ItemListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.ConnectionDlg");
	private static final String HELP_ID = "connect.connectionDialog";
	private JTabbedPane tabs = null;
	private MTextField tfHost = null;
	private IntegerField tfPort = null;
	private MTextField tfUser = null;
	private MPasswordField tfPassword = null;
	private MTextField tfAccount = null;
	private MButton butConnect = null;
	private MButton butCancel = null;
	private MButton butHelp = null;
	private MComboBox comboFTPClient = null;
	private MComboBox comboListParser = null;
	private MTextField tfInitialLocalDirectory = null;
	private MTextField tfInitialRemoteDirectory = null;
	private MCheckBox cbPassive = null;
	private MTextArea taCommands = null;
	private MButton butBrowseLocalDirectory = null;
	private MRadioButton radioNoSSL = null;
	private MRadioButton radioUseSSLIfAvailable = null;
	private MRadioButton radioExplicitSSL = null;
	private MRadioButton radioImplicitSSL = null;
	private IntegerField tfImplicitSSLPort = null;
	private MCheckBox cbDataChannelEncryption = null;
	private RemoteHost remoteHost = null;
	private boolean approved = false;

	public ConnectionDlg(Frame frame) {
		super(frame, resources.getString("title.dialog"), true);
		getContentPane().setLayout(new GridBagLayout());
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), HELP_ID);
		initComponents();
		pack();
	}

	public void setRemoteHost(RemoteHost remoteHost) {
		this.remoteHost = remoteHost == null ? new RemoteHost() : remoteHost;
		populateScreen();
	}

	public RemoteHost getRemoteHost() {
		if (approved) {
			return remoteHost;
		}
		return null;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource().equals(butConnect)) {
			if (!validateInput()) {
				return;
			}
			approved = true;
			connectButtonPressed();
			close();
		} else if (evt.getSource().equals(butCancel)) {
			cancelButtonPressed();
			close();
		} else if (evt.getSource().equals(butBrowseLocalDirectory)) {
			browseButtonPressed();
		}
	}

	public void itemStateChanged(ItemEvent evt) {
		if (evt.getSource() == radioNoSSL) {
			// radioUseSSLIfAvailable.setEnabled(! radioNoSSL.isSelected());
			// radioExplicitSSL.setEnabled(! radioNoSSL.isSelected());
			// radioImplicitSSL.setEnabled(! radioNoSSL.isSelected());
			// radioUseSSLIfAvailable.setEnabled(! radioNoSSL.isSelected());
			// tfImplicitSSLPort.setEnabled(! radioNoSSL.isSelected());
			cbDataChannelEncryption.setEnabled(!radioNoSSL.isSelected());
			tfImplicitSSLPort.setEnabled(radioImplicitSSL.isSelected());
		} else if (evt.getSource() == radioImplicitSSL) {
			tfImplicitSSLPort.setEnabled(radioImplicitSSL.isSelected());
		}
	}

	public void componentHidden(ComponentEvent evt) {
	}

	public void componentShown(ComponentEvent evt) {
		tabs.setSelectedIndex(0);
		tfHost.requestFocus();
	}

	public void componentMoved(ComponentEvent evt) {
	}

	public void componentResized(ComponentEvent evt) {
	}

	private void close() {
		setVisible(false);
	}

	@Override
	protected void escape() {
		butCancel.doClick();
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		tabs = new JTabbedPane();
		tabs.addTab(resources.getString("text.general"), getGeneralTab());
		tabs.addTab(resources.getString("text.security"), getSSLTab());
		tabs.addTab(resources.getString("text.advanced"), getAdvancedTab());
		tabs.addTab(resources.getString("text.commands"), getCommandsTab());
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 5;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		getContentPane().add(tabs, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(6, 12, 12, 12);
		getContentPane().add(getCommandButtons(), c);

		addComponentListener(this);
	}

	private Component getGeneralTab() {
		GridBagLayout layout = new GridBagLayout();

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		JPanel panel = new JPanel(layout);

		MLabel labHost = new MLabel(resources.getString("text.host"));
		tfHost = new MTextField(20);
		MLabel labUser = new MLabel(resources.getString("text.user"));
		tfUser = new MTextField(10);
		MLabel labPassword = new MLabel(resources.getString("text.password"));
		tfPassword = new MPasswordField(10);
		MLabel labPort = new MLabel(resources.getString("text.port"));
		tfPort = new IntegerField(6);
		tfPort.setValue(FTPConstants.DEFAULT_PORT);
		MLabel labAccount = new MLabel(resources.getString("text.account"));
		tfAccount = new MTextField(20);

		c.insets = new Insets(12, 12, 12, 12);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0.0;
		panel.add(labHost, c);

		c.insets = new Insets(12, 0, 12, 12);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1.0;
		panel.add(tfHost, c);

		c.insets = new Insets(0, 12, 12, 12);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.0;
		panel.add(labUser, c);

		c.insets = new Insets(0, 0, 12, 12);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 1.0;
		panel.add(tfUser, c);

		c.insets = new Insets(0, 12, 12, 12);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.0;
		panel.add(labPassword, c);

		c.insets = new Insets(0, 0, 12, 12);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 1.0;
		panel.add(tfPassword, c);

		c.insets = new Insets(0, 12, 12, 12);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 0.0;
		panel.add(labPort, c);

		c.insets = new Insets(0, 0, 12, 12);
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 2;
		c.weightx = 1.0;
		panel.add(tfPort, c);

		c.insets = new Insets(0, 12, 12, 12);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.weightx = 0.0;
		panel.add(labAccount, c);

		c.insets = new Insets(0, 0, 12, 12);
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 2;
		c.weightx = 0.5;
		panel.add(tfAccount, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		panel.add(new MLabel(), c);

		return panel;
	}

	private Component getSSLTab() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		radioNoSSL = new MRadioButton(resources.getString("text.noSSL"));
		radioUseSSLIfAvailable = new MRadioButton(
				resources.getString("text.useSSLIfAvailable"));
		radioExplicitSSL = new MRadioButton(
				resources.getString("text.explicitSSL"));
		radioImplicitSSL = new MRadioButton(
				resources.getString("text.implicitSSL"));
		MLabel labImplicitSSLPort = new MLabel(
				resources.getString("text.implicitSSLPort"));
		tfImplicitSSLPort = new IntegerField(5);
		tfImplicitSSLPort.setValue(FTPConstants.DEFAULT_IMPLICIT_SSL_PORT);
		cbDataChannelEncryption = new MCheckBox(
				resources.getString("text.dataChannelEncryption"));

		ButtonGroup bg = new ButtonGroup();
		bg.add(radioNoSSL);
		bg.add(radioUseSSLIfAvailable);
		bg.add(radioExplicitSSL);
		bg.add(radioImplicitSSL);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 12, 12, 12);
		panel.add(radioNoSSL, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(radioUseSSLIfAvailable, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(radioExplicitSSL, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 6, 12);
		panel.add(radioImplicitSSL, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 60, 12, 12);
		panel.add(labImplicitSSLPort, c);

		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		panel.add(tfImplicitSSLPort, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(cbDataChannelEncryption, c);

		radioNoSSL.addItemListener(this);
		radioImplicitSSL.addItemListener(this);

		radioNoSSL.setSelected(true);
		// cbEncryptDataChannel.setSelected(true);

		return panel;
	}

	private Component getAdvancedTab() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		MLabel labFTPClient = new MLabel(resources.getString("text.ftpClient"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(12, 12, 12, 12);
		panel.add(labFTPClient, c);

		comboFTPClient = new MComboBox(JFTP.getInstalledClients());
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.insets = new Insets(12, 0, 12, 12);
		panel.add(comboFTPClient, c);

		MLabel labListParser = new MLabel(
				resources.getString("text.listParser"));
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(labListParser, c);

		comboListParser = new MComboBox(JFTP.getInstalledParsers());
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.insets = new Insets(0, 0, 12, 12);
		panel.add(comboListParser, c);

		MLabel labInitialLocalDirectory = new MLabel(
				resources.getString("text.initialLocalDirectory"));
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(labInitialLocalDirectory, c);

		tfInitialLocalDirectory = new MTextField(20);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.insets = new Insets(0, 0, 12, 12);
		panel.add(tfInitialLocalDirectory, c);

		butBrowseLocalDirectory = new MButton(
				CommonResources.getString("text.browse"));
		butBrowseLocalDirectory.addActionListener(this);
		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 12, 12);
		panel.add(butBrowseLocalDirectory, c);

		MLabel labInitialRemoteDirectory = new MLabel(
				resources.getString("text.initialRemoteDirectory"));
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(labInitialRemoteDirectory, c);

		tfInitialRemoteDirectory = new MTextField(20);
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.insets = new Insets(0, 0, 12, 12);
		panel.add(tfInitialRemoteDirectory, c);

		cbPassive = new MCheckBox(resources.getString("text.passive"),
				JFTP.prefs.isPassive());
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(cbPassive, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		panel.add(new MLabel(), c);

		return panel;
	}

	private Component getCommandsTab() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		MLabel labCommands = new MLabel(
				resources.getString("text.executeCommands"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.weighty = 0;
		c.insets = new Insets(12, 12, 12, 12);
		panel.add(labCommands, c);

		taCommands = new MTextArea(4, 30);
		MScrollPane scroller = new MScrollPane(taCommands);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(scroller, c);

		return panel;
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butConnect = new MButton(resources.getString("text.connect"));
		butConnect.addActionListener(this);
		getRootPane().setDefaultButton(butConnect);
		butCancel = new MButton(CommonResources.getString("text.cancel"));
		butCancel.addActionListener(this);
		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		panel.add(butConnect);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butCancel);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);

		return panel;
	}

	private void populateScreen() {
		String temp = null;
		temp = remoteHost.getHostName();
		tfHost.setText(temp);
		temp = remoteHost.getUser();
		tfUser.setText(temp.trim().length() == 0 ? "anonymous" : temp);
		temp = remoteHost.getPassword();
		tfPassword.setText(temp.trim().length() == 0 ? JFTP.prefs.getEmail()
				: temp);
		tfPort.setValue(remoteHost.getPort());
		temp = remoteHost.getAccount();
		tfAccount.setText(temp);
		temp = remoteHost.getFTPClientClassName();
		comboFTPClient.setSelectedItem(JFTP.getClientName(temp));
		temp = remoteHost.getListParserClassName();
		comboListParser.setSelectedItem(JFTP.getParserName(temp));
		tfInitialLocalDirectory.setText(remoteHost.getInitialLocalDirectory());
		tfInitialRemoteDirectory
				.setText(remoteHost.getInitialRemoteDirectory());
		cbPassive.setSelected(remoteHost.isPassive());
		taCommands.setText(remoteHost.getCommandsAsString());

		int sslUsage = remoteHost.getSSLUsage();
		switch (sslUsage) {
		case FTPConstants.USE_NO_SSL:
			radioNoSSL.setSelected(true);
			break;
		case FTPConstants.USE_SSL_IF_AVAILABLE:
			radioUseSSLIfAvailable.setSelected(true);
			break;
		case FTPConstants.USE_EXPLICIT_SSL:
			radioExplicitSSL.setSelected(true);
			break;
		case FTPConstants.USE_IMPLICIT_SSL:
			radioImplicitSSL.setSelected(true);
			break;
		}
		tfImplicitSSLPort.setValue(remoteHost.getImplicitSSLPort());
		cbDataChannelEncryption.setSelected(remoteHost
				.isDataChannelUnencrypted());
	}

	private void connectButtonPressed() {

		remoteHost = new RemoteHost();
		// remoteHost.setName(tfHost.getText());
		remoteHost.setHostName(tfHost.getText());
		remoteHost.setPort(tfPort.getValue());
		remoteHost.setUser(tfUser.getText());
		remoteHost.setPassword(new String(tfPassword.getPassword()));
		remoteHost.setAccount(tfAccount.getText());
		remoteHost.setFTPClientClassName(JFTP
				.getClientClassName((String) comboFTPClient.getSelectedItem()));
		remoteHost
				.setListParserClassName(JFTP
						.getParserClassName((String) comboListParser
								.getSelectedItem()));
		remoteHost.setInitialLocalDirectory(tfInitialLocalDirectory.getText());
		remoteHost
				.setInitialRemoteDirectory(tfInitialRemoteDirectory.getText());
		remoteHost.setPassive(cbPassive.isSelected());
		remoteHost.setCommands(taCommands.getText());
		int sslUsage = FTPConstants.USE_NO_SSL;
		if (radioUseSSLIfAvailable.isSelected()) {
			sslUsage = FTPConstants.USE_SSL_IF_AVAILABLE;
		} else if (radioExplicitSSL.isSelected()) {
			sslUsage = FTPConstants.USE_EXPLICIT_SSL;
		} else if (radioImplicitSSL.isSelected()) {
			sslUsage = FTPConstants.USE_IMPLICIT_SSL;
			remoteHost.setImplicitSSLPort(tfImplicitSSLPort.getValue());
		}
		remoteHost.setSSLUsage(sslUsage);
		remoteHost.setDataChannelUnencrypted(cbDataChannelEncryption
				.isSelected());
	}

	private void cancelButtonPressed() {
		remoteHost = null;
	}

	private void browseButtonPressed() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogTitle(CommonResources
				.getString("title.selectDirectory"));
		fileChooser.setApproveButtonText(CommonResources
				.getString("text.select"));

		String dir = tfInitialLocalDirectory.getText();
		if (dir.trim().length() == 0) {
			dir = JFTP.prefs.getLocalDirectory();
		}
		File currentDirectory = null;
		if (dir.trim().length() > 0) {
			currentDirectory = new File(dir);
		}
		if (currentDirectory.exists() && currentDirectory.isDirectory()) {
			fileChooser.setSelectedFile(currentDirectory);
		}
		int option = fileChooser.showOpenDialog(this);
		if (option != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selectedFile = fileChooser.getSelectedFile();
		tfInitialLocalDirectory.setText(selectedFile.getAbsolutePath());
	}

	private boolean validateInput() {
		String message = null;
		int errorTab = 0;
		Component errorComponent = null;
		int port = -1;
		int implicitSSLPort = -1;
		try {
			port = tfPort.getValue();
			implicitSSLPort = tfImplicitSSLPort.getValue();
		} catch (NumberFormatException exp) {
		}
		if (tfHost.getText().trim().length() == 0) {
			message = resources.getString("error.host.required");
			errorComponent = tfHost;
		} else if (tfUser.getText().trim().length() == 0) {
			message = resources.getString("error.user.required");
			errorComponent = tfUser;
		} else if (new String(tfPassword.getPassword()).trim().length() == 0) {
			message = resources.getString("error.password.required");
			errorComponent = tfPassword;
		} else if (port < 0 || port > 0xFFFF) {
			message = resources.getString("error.port.invalid");
			errorComponent = tfPort;
		} else if (radioImplicitSSL.isSelected()) {
			if (implicitSSLPort < 0 || implicitSSLPort > 0xFFFF) {
				message = resources.getString("error.implicitSSLPort.invalid");
				errorTab = 1;
				errorComponent = tfImplicitSSLPort;
			}
		}

		if (message == null) {
			return true;
		}
		GUIUtil.showError(this, message);
		tabs.setSelectedIndex(errorTab);
		if (errorComponent != null) {
			errorComponent.requestFocus();
		}
		return false;
	}
}