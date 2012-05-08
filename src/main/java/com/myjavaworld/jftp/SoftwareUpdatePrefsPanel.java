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
import java.util.ResourceBundle;

import javax.swing.JPanel;

import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MCheckBox;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.util.ResourceLoader;

/**
 * @author Sai Pullabhotla
 */
public class SoftwareUpdatePrefsPanel extends JPanel implements ActionListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.SoftwareUpdatePrefsPanel");
	private MCheckBox cbCheckForUpdates = null;
	private MButton butCheckNow = null;

	public SoftwareUpdatePrefsPanel() {
		super();
		setLayout(new GridBagLayout());
		initComponents();
	}

	public void actionPerformed(ActionEvent evt) {
		AutoUpdater updater = new AutoUpdater(this, false, true);
		updater.start();
	}

	public boolean validateFields() {
		return true;
	}

	public void populateScreen() {
		populateScreen(JFTP.prefs);
	}

	public void populateScreen(JFTPPreferences prefs) {
		cbCheckForUpdates.setSelected(prefs.getCheckForUpdates());
	}

	public void saveChanges() {
		JFTP.prefs.setCheckForUpdates(cbCheckForUpdates.isSelected());
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		cbCheckForUpdates = new MCheckBox(
				resources.getString("text.checkForUpdates"));
		butCheckNow = new MButton(resources.getString("text.checkNow"));
		butCheckNow.addActionListener(this);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(12, 12, 12, 12);
		add(cbCheckForUpdates, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0, 12, 12, 12);
		add(butCheckNow, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		add(new MLabel(), c);

		populateScreen();
	}
}