/*
 * Created on Nov 30, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.myjavaworld.jftp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import com.myjavaworld.ftp.FTPConstants;
import com.myjavaworld.gui.MCheckBox;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MRadioButton;
import com.myjavaworld.util.ResourceLoader;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SecurityPrefsPanel extends JPanel implements ActionListener,
		ItemListener {

	private MCheckBox cbDataChannelEncryption;
	// private IntegerField tfImplicitSSLPort;
	private MRadioButton radioImplicitSSL;
	private MRadioButton radioExplicitSSL;
	private MRadioButton radioUseSSLIfAvailable;
	private MRadioButton radioNoSSL;
	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.SecurityPrefsPanel");

	public SecurityPrefsPanel() {
		super();
		initComponents();
		populateScreen();
	}

	public void actionPerformed(ActionEvent evt) {
	}

	public void itemStateChanged(ItemEvent evt) {
		if (evt.getSource() == radioNoSSL) {
			cbDataChannelEncryption.setEnabled(!radioNoSSL.isSelected());
			// tfImplicitSSLPort.setEnabled(radioImplicitSSL.isSelected());
		} else if (evt.getSource() == radioImplicitSSL) {
			// tfImplicitSSLPort.setEnabled(radioImplicitSSL.isSelected());
		}

	}

	public void populateScreen() {
		populateScreen(JFTP.prefs);
	}

	public void populateScreen(JFTPPreferences prefs) {
		int sslUsage = prefs.getSSLUsage();
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
		cbDataChannelEncryption.setSelected(prefs.isDataChannelUnencrypted());
	}

	private void initComponents() {
		setLayout(new GridBagLayout());
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
		add(radioNoSSL, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		add(radioUseSSLIfAvailable, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		add(radioExplicitSSL, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		add(radioImplicitSSL, c);

		// c.gridx = 0;
		// c.gridy = 4;
		// c.gridwidth = 1;
		// c.gridheight = 1;
		// c.weightx = 0.0;
		// c.weighty = 0.0;
		// c.fill = GridBagConstraints.HORIZONTAL;
		// c.insets = new Insets(0, 60, 12, 12);
		// add(labImplicitSSLPort, c);
		//
		// c.gridx = 1;
		// c.gridy = 4;
		// c.gridwidth = 1;
		// c.gridheight = 1;
		// c.weightx = 1.0;
		// c.weighty = 0.0;
		// c.fill = GridBagConstraints.HORIZONTAL;
		// c.insets = new Insets(0, 0, 12, 12);
		// add(tfImplicitSSLPort, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 12, 12, 12);
		add(cbDataChannelEncryption, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 0, 0, 0);
		add(new MLabel(), c);

		radioNoSSL.addItemListener(this);
		radioImplicitSSL.addItemListener(this);

		radioNoSSL.setSelected(true);
	}

	public boolean validateFields() {
		return true;
	}

	public void saveChanges() {
		int sslUsage = FTPConstants.USE_NO_SSL;
		if (radioUseSSLIfAvailable.isSelected()) {
			sslUsage = FTPConstants.USE_SSL_IF_AVAILABLE;
		} else if (radioExplicitSSL.isSelected()) {
			sslUsage = FTPConstants.USE_EXPLICIT_SSL;
		} else if (radioImplicitSSL.isSelected()) {
			sslUsage = FTPConstants.USE_IMPLICIT_SSL;
		}
		JFTP.prefs.setSSLUsage(sslUsage);
		// if(sslUsage == FTPConstants.USE_IMPLICIT_SSL) {
		// JFTP.prefs.setImplicitSSLPort(tfImplicitSSLPort.getValue());
		// }
		JFTP.prefs.setDataChannelUnencrypted(cbDataChannelEncryption
				.isSelected());
	}
}
