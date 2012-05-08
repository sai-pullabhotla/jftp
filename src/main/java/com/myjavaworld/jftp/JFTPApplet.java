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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JApplet;
import javax.swing.UIManager;

import com.myjavaworld.gui.MButton;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com To change the template
 *         for this generated type comment go to Window>Preferences>Java>Code
 *         Generation>Code and Comments
 */
public class JFTPApplet extends JApplet implements ActionListener {

	private MButton butStart = null;
	// private MLabel labStatus = null;

	static {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name",
				"JFTP");
		Locale.setDefault(JFTP.prefs.getLocale());
		try {
			UIManager.setLookAndFeel(JFTP.prefs.getLookAndFeelClassName());
		} catch (Exception exp) {
		}
	}

	@Override
	public void init() {
		super.init();
		initComponents();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butStart) {
			JFTP jftp = new JFTP(true);
			jftp.setVisible(true);
			jftp.newSession();
		}
	}

	private void initComponents() {
		getContentPane().setLayout(new BorderLayout(0, 0));

		Icon launchIcon = JFTPUtil.getIcon("launch.gif");
		butStart = new MButton(launchIcon);
		butStart.setBackground(Color.WHITE);
		butStart.setBorder(BorderFactory.createEmptyBorder());
		butStart.setPreferredSize(new Dimension(launchIcon.getIconWidth(),
				launchIcon.getIconHeight()));
		butStart.setMaximumSize(new Dimension(launchIcon.getIconWidth(),
				launchIcon.getIconHeight()));
		butStart.addActionListener(this);

		getContentPane().add(butStart, BorderLayout.CENTER);
	}
}