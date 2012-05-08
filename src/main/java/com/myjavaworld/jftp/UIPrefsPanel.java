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
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.UIManager;

import com.myjavaworld.gui.MComboBox;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.util.ResourceLoader;

/**
 * A supporting panel for the preferences dialog. This panel gives the user the
 * option to change the appearance of the user interface.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class UIPrefsPanel extends JPanel {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.UIPrefsPanel");
	private MComboBox comboLookAndFeels = null;

	public UIPrefsPanel() {
		super();
		setLayout(new GridBagLayout());
		initComponents();
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		MLabel labLookAndFeel = new MLabel(
				resources.getString("text.lookAndFeel"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(12, 12, 12, 12);
		add(labLookAndFeel, c);

		comboLookAndFeels = new MComboBox(getInstalledLookAndFeels());
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.weighty = 0.0;
		c.insets = new Insets(12, 0, 12, 12);
		add(comboLookAndFeels, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		add(new MLabel(), c);

		populateScreen();
	}

	public boolean validateFields() {
		return true;
	}

	public void populateScreen() {
		populateScreen(JFTP.prefs);
	}

	public void populateScreen(JFTPPreferences prefs) {
		comboLookAndFeels.setSelectedItem(getLookAndFeelName(prefs
				.getLookAndFeelClassName()));
		// comboThemes.setSelectedItem(prefs.getTheme());
		// if (prefs.getUseJavaWindows()) {
		// radioJavaWindows.setSelected(true);
		// }
		// else {
		// radioStandardWindows.setSelected(true);
		// }
	}

	public void saveChanges() {
		String selecteLookAndFeel = (String) comboLookAndFeels
				.getSelectedItem();
		JFTP.prefs
				.setLookAndFeelClassName(getLookAndFeelClassName(selecteLookAndFeel));
		// JFTP.prefs.setTheme((String) comboThemes.getSelectedItem());
		// JFTP.prefs.setUseJavaWindows(radioJavaWindows.isSelected());

	}

	private String[] getInstalledLookAndFeels() {
		UIManager.LookAndFeelInfo[] lnfs = UIManager.getInstalledLookAndFeels();
		String[] lnfNames = new String[lnfs.length];
		for (int i = 0; i < lnfs.length; i++) {
			lnfNames[i] = lnfs[i].getName();
		}
		return lnfNames;
	}

	private String getLookAndFeelClassName(String lookAndFeelName) {
		UIManager.LookAndFeelInfo[] lnfs = UIManager.getInstalledLookAndFeels();
		for (int i = 0; i < lnfs.length; i++) {
			if (lookAndFeelName.equals(lnfs[i].getName())) {
				return lnfs[i].getClassName();
			}
		}
		return UIManager.getSystemLookAndFeelClassName();
	}

	private String getLookAndFeelName(String lookAndFeelClassName) {
		if (lookAndFeelClassName == null) {
			return null;
		}
		UIManager.LookAndFeelInfo[] lnfs = UIManager.getInstalledLookAndFeels();
		for (int i = 0; i < lnfs.length; i++) {
			if (lookAndFeelClassName.equals(lnfs[i].getClassName())) {
				return lnfs[i].getName();
			}
		}
		return null;
	}
}