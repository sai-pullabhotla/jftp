/*
 * Created on Oct 5, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
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

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.jftp.JFTPHelp2;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CertificateDlg extends MDialog implements ActionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.ssl.CertificateDlg");
	private static final String HELP_ID = "security.viewCertificate";
	private MButton butInstall = null;
	private MButton butClose = null;
	private MButton butHelp = null;
	private boolean installable = false;
	private Certificate[] chain = null;

	public CertificateDlg(Frame parent, Certificate certificate) {
		this(parent, new Certificate[] { certificate });
	}

	public CertificateDlg(Frame parent, Certificate[] chain) {
		this(parent, chain, false);
	}

	public CertificateDlg(Frame parent, Certificate[] chain, boolean installable) {
		super(parent);
		this.chain = chain;
		this.installable = installable;
		setTitle(resources.getString("title.dialog"));
		setModal(true);
		getContentPane().setLayout(new GridBagLayout());
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), HELP_ID);
		initComponents();
		pack();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butClose) {
			setVisible(false);
		} else if (evt.getSource() == butInstall) {
			try {
				KeyStoreManager.addServerCertificate(chain);
				setVisible(false);
			} catch (Exception exp) {
				GUIUtil.showError(this, exp);
			}
		}
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(12, 12, 12, 12);
		c.fill = GridBagConstraints.BOTH;
		getContentPane().add(new CertificatePane(chain), c);

		c.gridx = 0;
		c.gridy = 1;
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

		butInstall = new MButton(resources.getString("text.install"));
		butInstall.addActionListener(this);

		butClose = new MButton(CommonResources.getString("text.close"));
		butClose.addActionListener(this);
		getRootPane().setDefaultButton(butClose);

		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		if (installable) {
			panel.add(butInstall);
			panel.add(Box.createRigidArea(new Dimension(5, 0)));
		}
		panel.add(butClose);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);
		return panel;
	}
}