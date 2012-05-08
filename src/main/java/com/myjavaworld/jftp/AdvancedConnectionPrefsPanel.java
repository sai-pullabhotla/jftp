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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.IntegerField;
import com.myjavaworld.gui.MCheckBox;
import com.myjavaworld.gui.MComboBox;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.util.ResourceLoader;

/**
 * Supporting panel for the preferences dialog.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class AdvancedConnectionPrefsPanel extends JPanel {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.AdvancedConnectionPrefsPanel");
	private MComboBox comboFTPClient = null;
	private MComboBox comboListParser = null;
	private MCheckBox cbPassive = null;
	private IntegerField tfBufferSize = null;
	private IntegerField tfTimeout = null;

	public AdvancedConnectionPrefsPanel() {
		super();
		setLayout(new GridBagLayout());
		initComponents();
	}

	public boolean validateFields() {
		String error = null;
		Component errorComponent = null;
		int bufferSize = -1;
		int timeout = -1;

		try {
			bufferSize = tfBufferSize.getValue();
			timeout = tfTimeout.getValue();
		} catch (NumberFormatException exp) {
		}
		if (bufferSize <= 0) {
			error = resources.getString("error.bufferSize.invalid");
			errorComponent = tfBufferSize;
		} else if (timeout < 0) {
			error = resources.getString("error.timeout.invalid");
			errorComponent = tfTimeout;
		}
		if (error == null) {
			return true;
		}
		GUIUtil.showError(this, error);
		errorComponent.requestFocusInWindow();
		return false;
	}

	public void populateScreen() {
		populateScreen(JFTP.prefs);
	}

	public void populateScreen(JFTPPreferences prefs) {
		String ftpClient = JFTP.getClientName(prefs.getClient());
		comboFTPClient.setSelectedItem(ftpClient);
		String listParser = JFTP.getParserName(prefs.getListParser());
		comboListParser.setSelectedItem(listParser);
		cbPassive.setSelected(prefs.isPassive());
		tfBufferSize.setValue(prefs.getBufferSize() / 1024);
		tfTimeout.setValue(prefs.getTimeout() / 60000);
	}

	public void saveChanges() {
		String ftpClientClassName = JFTP
				.getClientClassName((String) comboFTPClient.getSelectedItem());
		JFTP.prefs.setClient(ftpClientClassName);
		String listParserClassName = JFTP
				.getParserClassName((String) comboListParser.getSelectedItem());
		JFTP.prefs.setListParser(listParserClassName);
		JFTP.prefs.setPassive(cbPassive.isSelected());
		JFTP.prefs.setBufferSize(tfBufferSize.getValue() * 1024);
		JFTP.prefs.setTimeout(tfTimeout.getValue() * 60000);
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		MLabel labFTPClient = new MLabel(resources.getString("text.ftpClient"));
		comboFTPClient = new MComboBox(JFTP.getInstalledClients());
		MLabel labListParser = new MLabel(
				resources.getString("text.listParser"));
		comboListParser = new MComboBox(JFTP.getInstalledParsers());
		cbPassive = new MCheckBox(resources.getString("text.passive"));
		MLabel labBufferSize = new MLabel(
				resources.getString("text.bufferSize"));
		tfBufferSize = new IntegerField(4);
		MLabel labBufferSizeUnit = new MLabel(resources.getString("text.kb"));
		MLabel labTimeout = new MLabel(resources.getString("text.timeout"));
		tfTimeout = new IntegerField(5);
		MLabel labTimeoutUnit = new MLabel(resources.getString("text.minutes"));

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(12, 12, 12, 12);
		add(labFTPClient, c);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.insets = new Insets(12, 0, 12, 12);
		add(comboFTPClient, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		add(labListParser, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.insets = new Insets(0, 0, 12, 12);
		add(comboListParser, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		add(cbPassive, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		add(labBufferSize, c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 0, 12, 12);
		add(tfBufferSize, c);

		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.insets = new Insets(0, 0, 12, 12);
		add(labBufferSizeUnit, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		add(labTimeout, c);

		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 0, 12, 12);
		add(tfTimeout, c);

		c.gridx = 2;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.insets = new Insets(0, 0, 12, 12);
		add(labTimeoutUnit, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(new MLabel(), c);

		populateScreen();
	}
}