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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MTextField;
import com.myjavaworld.util.ResourceLoader;

/**
 * Supporting panel for the preferences dialog.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class CertificatePrefsPanel extends JPanel implements ActionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.CertificatePrefsPanel");
	private MTextField tfServerCertificates = null;
	private MTextField tfClientCertificates = null;
	private MButton butBrowseClientCertificates = null;
	private MButton butBrowseServerCertificates = null;

	public CertificatePrefsPanel() {
		super();
		setLayout(new GridBagLayout());
		initComponents();
	}

	public void saveChanges() {
		JFTP.prefs.setServerCertificateStore(tfServerCertificates.getText());
		JFTP.prefs.setClientCertificateStore(tfClientCertificates.getText());
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butBrowseServerCertificates) {
			browseServerCertificateStore();
		} else if (evt.getSource() == butBrowseClientCertificates) {
			browseClientCertificateStore();
		}
	}

	private void browseServerCertificateStore() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);

		String fileName = tfServerCertificates.getText();
		if (fileName.trim().length() > 0) {
			File file = new File(fileName);
			if (file.exists() && file.isFile()) {
				fileChooser.setSelectedFile(file);
			}
		}
		int option = fileChooser.showDialog(this, "Select");
		if (option != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selectedFile = fileChooser.getSelectedFile();
		try {
			fileName = selectedFile.getCanonicalPath();
		} catch (IOException exp) {
			fileName = selectedFile.getAbsolutePath();
		}
		tfServerCertificates.setText(fileName);
	}

	private void browseClientCertificateStore() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);

		String fileName = tfClientCertificates.getText();
		if (fileName.trim().length() > 0) {
			File file = new File(fileName);
			if (file.exists() && file.isFile()) {
				fileChooser.setSelectedFile(file);
			}
		}
		int option = fileChooser.showDialog(this, "Select");
		if (option != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selectedFile = fileChooser.getSelectedFile();
		try {
			fileName = selectedFile.getCanonicalPath();
		} catch (IOException exp) {
			fileName = selectedFile.getAbsolutePath();
		}
		tfClientCertificates.setText(fileName);
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		MLabel labServerCertificates = new MLabel(
				resources.getString("labServerCertificates.text"));

		MLabel labClientCertificates = new MLabel(
				resources.getString("labClientCertificates.text"));

		tfServerCertificates = new MTextField(20);
		tfServerCertificates.setToolTipText(resources
				.getString("tfServerCertificates.tooltip"));
		tfClientCertificates = new MTextField(20);
		tfClientCertificates.setToolTipText(resources
				.getString("tfClientCertificates.tooltip"));

		butBrowseServerCertificates = new MButton(
				resources.getString("butBrowseServerCertificates.text"));
		butBrowseClientCertificates = new MButton(
				resources.getString("butBrowseClientCertificates.text"));

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 12, 12, 12);
		add(labServerCertificates, c);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 0, 12, 12);
		add(tfServerCertificates, c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 0, 12, 12);
		add(butBrowseServerCertificates, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		add(labClientCertificates, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		add(tfClientCertificates, c);

		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		add(butBrowseClientCertificates, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 0, 0, 0);
		add(new MLabel(), c);

		tfServerCertificates.setText(JFTP.prefs.getServerCertificateStore());
		tfClientCertificates.setText(JFTP.prefs.getClientCertificateStore());

		butBrowseServerCertificates.addActionListener(this);
		butBrowseClientCertificates.addActionListener(this);
	}
}
