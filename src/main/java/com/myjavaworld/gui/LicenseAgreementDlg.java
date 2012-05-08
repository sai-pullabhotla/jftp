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
package com.myjavaworld.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import com.myjavaworld.util.ResourceLoader;

/**
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 1.0
 * 
 */
public class LicenseAgreementDlg extends MDialog implements ActionListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.gui.LicenseAgreementDlg");
	private URL url = null;
	private JEditorPane ta = null;
	private MButton butAgree = null;
	private MButton butDisagree = null;
	private boolean licenseAgreed = false;

	public LicenseAgreementDlg(Frame parent) {
		super(parent, resources.getString("title.dialog"), true);
		initComponents();
		pack();
	}

	public void setURL(URL url) {
		try {
			ta.setPage(url);
			this.url = url;
		} catch (IOException e) {
			ta.setText("Error loading the license agreement. Please contact the support. ");
			e.printStackTrace();
		}
		ta.setCaretPosition(0);
	}

	public URL getURL() {
		return url;
	}

	public boolean isLicenseAgreed() {
		return licenseAgreed;
	}

	public void actionPerformed(ActionEvent evt) {
		String command = evt.getActionCommand();
		if (command.equals("cmd.agree")) {
			licenseAgreed = true;
		}
		setVisible(false);
	}

	@Override
	protected void escape() {
		butDisagree.doClick();
	}

	private void initComponents() {
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		ta = new JEditorPane();
		ta.setEditable(false);
		ta.setContentType("text/html");
		JScrollPane scrollPane = new JScrollPane(ta);
		scrollPane.getViewport().setPreferredSize(new Dimension(500, 300));

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(12, 12, 12, 12);
		getContentPane().add(scrollPane, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(6, 12, 12, 12);
		c.anchor = GridBagConstraints.SOUTHEAST;
		getContentPane().add(getCommandButtons(), c);

	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butAgree = new MButton(resources.getString("text.agree"));
		butAgree.setActionCommand("cmd.agree");
		butAgree.addActionListener(this);
		butDisagree = new MButton(resources.getString("text.disagree"));
		butDisagree.setActionCommand("cmd.disagree");
		butDisagree.addActionListener(this);

		panel.add(butAgree);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butDisagree);

		return panel;
	}
}
