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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.IntegerField;
import com.myjavaworld.gui.MCheckBox;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MPasswordField;
import com.myjavaworld.gui.MTextField;
import com.myjavaworld.util.ResourceLoader;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProxyPrefsPanel extends JPanel implements ActionListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.ProxyPrefsPanel");
	private MCheckBox cbUseProxy = null;
	private MTextField tfHost = null;
	private IntegerField tfPort = null;
	private MTextField tfUser = null;
	private MPasswordField tfPassword = null;

	public ProxyPrefsPanel() {
		super();
		setLayout(new GridBagLayout());
		initComponents();
	}

	public void actionPerformed(ActionEvent evt) {
		setProxySettingsEnabled(cbUseProxy.isSelected());
	}

	private void setProxySettingsEnabled(boolean enabled) {
		tfHost.setEnabled(enabled);
		tfPort.setEnabled(enabled);
		tfUser.setEnabled(enabled);
		tfPassword.setEnabled(enabled);
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		MLabel labHost = new MLabel(resources.getString("text.host"));

		MLabel labPort = new MLabel(resources.getString("text.port"));

		MLabel labUser = new MLabel(resources.getString("text.user"));

		MLabel labPassword = new MLabel(resources.getString("text.password"));

		cbUseProxy = new MCheckBox(resources.getString("text.useProxy"));

		tfHost = new MTextField(30);

		tfPort = new IntegerField(5);

		tfUser = new MTextField(20);

		tfPassword = new MPasswordField(20);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 12, 12, 12);
		add(cbUseProxy, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		add(labHost, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		add(tfHost, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		add(labPort, c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		add(tfPort, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		add(labUser, c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		add(tfUser, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		add(labPassword, c);

		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		add(tfPassword, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		// c.insets = new Insets(0, 12, 12, 12);
		add(new MLabel(), c);

		cbUseProxy.addActionListener(this);
		populateScreen();
		// setProxySettingsEnabled(JFTP.prefs.isUseProxy());
		//
		// cbUseProxy.addActionListener(this);
		//
		// cbUseProxy.setSelected(JFTP.prefs.isUseProxy());
		// tfHost.setText(JFTP.prefs.getProxyHost());
		// tfPort.setValue(JFTP.prefs.getProxyPort());
		// tfUser.setText(JFTP.prefs.getProxyUser());
		// tfPassword.setText(new String(JFTP.prefs.getProxyPassword()));

	}

	public void populateScreen() {
		populateScreen(JFTP.prefs);
	}

	public void populateScreen(JFTPPreferences prefs) {
		setProxySettingsEnabled(prefs.isUseProxy());
		cbUseProxy.setSelected(prefs.isUseProxy());
		tfHost.setText(prefs.getProxyHost());
		tfPort.setValue(prefs.getProxyPort());
		tfUser.setText(prefs.getProxyUser());
		tfPassword.setText(new String(prefs.getProxyPassword()));
	}

	public boolean validateFields() {
		String error = null;
		Component errorComponent = null;

		if (cbUseProxy.isSelected()) {
			int port = -1;
			try {
				port = tfPort.getValue();
			} catch (NumberFormatException exp) {
			}
			if (tfHost.getText().trim().length() == 0) {
				error = resources.getString("error.host.required");
				errorComponent = tfHost;
			} else if (port < 0 || port > 0xffff) {
				error = resources.getString("error.port.invalid");
				errorComponent = tfPort;
			}
		}
		if (error == null) {
			return true;
		}
		GUIUtil.showError(this, error);
		errorComponent.requestFocusInWindow();
		return false;
	}

	public void saveChanges() {
		JFTP.prefs.setUseProxy(cbUseProxy.isSelected());
		JFTP.prefs.setProxyHost(tfHost.getText());
		int port = 1080;
		try {
			port = tfPort.getValue();
		} catch (NumberFormatException exp) {
		}
		JFTP.prefs.setProxyPort(port);
		JFTP.prefs.setProxyUser(tfUser.getText());
		JFTP.prefs.setProxyPassword(tfPassword.getPassword());
		JFTPUtil.updateProxySettings();
	}
}
