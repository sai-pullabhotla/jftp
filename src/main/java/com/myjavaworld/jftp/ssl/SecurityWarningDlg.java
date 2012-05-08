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
package com.myjavaworld.jftp.ssl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.cert.Certificate;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.jftp.JFTPHelp2;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SecurityWarningDlg extends MDialog implements ActionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.ssl.SecurityWarningDlg");
	private static final String HELP_ID = "security.installCertificate";
	public static final int NO_OPTION = 0;
	public static final int YES_OPTION = 1;
	private MLabel labMainMessage = null;
	private MLabel labDateMessage = null;
	private MLabel labHostMessage = null;
	private MLabel labTrustMessage = null;
	private MLabel labQuestion = null;
	private MButton butViewCertificate = null;
	private MButton butYes = null;
	private MButton butNo = null;
	private MButton butHelp = null;
	private Frame parent = null;
	private Certificate[] chain = null;
	private boolean validDate = false;
	private boolean validHost = false;
	private boolean trusted = false;
	private int option = 0;

	public SecurityWarningDlg(Frame parent, Certificate[] chain,
			boolean validDate, boolean validHost, boolean trusted) {
		super(parent);
		this.parent = parent;
		this.chain = chain;
		this.validDate = validDate;
		this.validHost = validHost;
		this.trusted = trusted;
		option = NO_OPTION;
		setTitle(resources.getString("title.dialog"));
		getAccessibleContext().setAccessibleDescription("dialog.description");
		setModal(true);
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), HELP_ID);
		initComponents();
		pack();
	}

	public static int showDialog(Component invoker, Certificate[] chain,
			boolean validDate, boolean validHost, boolean trusted) {
		Frame parent = null;
		if (invoker instanceof Frame) {
			parent = (Frame) invoker;
		} else {
			parent = (Frame) SwingUtilities.getAncestorOfClass(Frame.class,
					invoker);
		}
		SecurityWarningDlg dlg = new SecurityWarningDlg(parent, chain,
				validDate, validHost, trusted);
		dlg.setLocationRelativeTo(parent);
		dlg.setVisible(true);
		return dlg.option;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butViewCertificate) {
			CertificateDlg dlg = new CertificateDlg(parent, chain, true);
			dlg.setLocationRelativeTo(this);
			dlg.setVisible(true);
			dlg.dispose();
			return;
		}

		if (evt.getSource() == butYes) {
			option = YES_OPTION;
		}
		setVisible(false);
		dispose();
	}

	@Override
	protected void escape() {
		butNo.doClick();
	}

	private void initComponents() {
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;

		String message = null;
		Icon warningIcon = UIManager.getIcon("OptionPane.warningIcon");
		Icon infoIcon = UIManager.getIcon("OptionPane.informationIcon");

		message = resources.getString("text.main");
		labMainMessage = new MLabel(message);
		labMainMessage.setIcon(warningIcon);

		message = validDate ? resources.getString("text.validDate") : resources
				.getString("text.invalidDate");
		labDateMessage = new MLabel(message);
		labDateMessage.setIcon(validDate ? infoIcon : warningIcon);

		message = validHost ? resources.getString("text.validHost") : resources
				.getString("text.invalidHost");
		labHostMessage = new MLabel(message);
		labHostMessage.setIcon(validHost ? infoIcon : warningIcon);

		message = trusted ? resources.getString("text.trustedCertificate")
				: resources.getString("text.untrustedCertificate");
		labTrustMessage = new MLabel(message);
		labTrustMessage.setIcon(trusted ? infoIcon : warningIcon);

		labQuestion = new MLabel(resources.getString("text.question"));

		butViewCertificate = new MButton(
				resources.getString("text.viewCertificate"));
		butViewCertificate.addActionListener(this);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 12, 12, 12);
		getContentPane().add(labMainMessage, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(labDateMessage, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(labHostMessage, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(labTrustMessage, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(labQuestion, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.insets = new Insets(0, 12, 12, 12);
		c.fill = GridBagConstraints.NONE;
		getContentPane().add(butViewCertificate, c);

		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.insets = new Insets(6, 12, 12, 12);
		c.fill = GridBagConstraints.NONE;
		getContentPane().add(getCommandButtons(), c);
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);

		butYes = new MButton(CommonResources.getString("text.yes"));
		butYes.addActionListener(this);
		getRootPane().setDefaultButton(butYes);

		butNo = new MButton(CommonResources.getString("text.no"));
		butNo.addActionListener(this);

		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		panel.add(butYes);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butNo);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);
		return panel;
	}
}
