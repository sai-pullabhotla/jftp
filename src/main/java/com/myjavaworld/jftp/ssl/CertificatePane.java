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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.myjavaworld.gui.MLabel;
import com.myjavaworld.jftp.JFTP;
import com.myjavaworld.util.ResourceLoader;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CertificatePane extends JPanel {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.ssl.CertificatePane");
	private MLabel tfVersion = null;
	private MLabel tfSerialNumber = null;
	private MLabel tfValidity = null;
	private MLabel tfSignatureAlgorithm = null;
	private MLabel labIssuerOrganization = null;
	private MLabel labIssuerOrganizationUnit = null;
	private MLabel labIssuerCommonName = null;
	private MLabel labIssuerCountry = null;
	private MLabel labSubjectOrganization = null;
	private MLabel labSubjectOrganizationUnit = null;
	private MLabel labSubjectCommonName = null;
	private MLabel labSubjectCountry = null;
	private Certificate[] chain = null;

	public CertificatePane() {
		this(null);
	}

	public CertificatePane(Certificate[] chain) {
		super();
		setLayout(new GridBagLayout());
		initComponents();
		setCertificateChain(chain);
	}

	public void setCertificateChain(Certificate[] chain) {
		this.chain = chain;
		if (chain != null) {
			populateScreen();
		}
	}

	public Certificate[] getCertificateChain() {
		return chain;
	}

	private void populateScreen() {
		populateGeneralPanel();
		populateIssuerPanel();
		populateSubjectPanel();
	}

	private void populateGeneralPanel() {
		X509Certificate certificate = (X509Certificate) chain[0];
		tfVersion.setText("V" + certificate.getVersion());
		tfSerialNumber.setText(certificate.getSerialNumber().toString());
		DateFormat formatter = DateFormat.getDateTimeInstance(
				JFTP.prefs.getDateFormat(), JFTP.prefs.getTimeFormat());
		String validFrom = formatter.format(certificate.getNotBefore());
		String validTo = formatter.format(certificate.getNotAfter());
		MessageFormat mf = new MessageFormat(
				resources.getString("value.validity"));
		Object[] args = new String[] { validFrom, validTo };
		tfValidity.setText(mf.format(args));
		tfSignatureAlgorithm.setText(certificate.getSigAlgName());
	}

	private void populateIssuerPanel() {
		X509Certificate certificate = (X509Certificate) chain[0];
		String dn = certificate.getIssuerDN().getName();
		DNParser parser = new DNParser(dn);
		labIssuerOrganization.setText(parser.getParameter("O"));
		labIssuerOrganizationUnit.setText(parser.getParameter("OU"));
		labIssuerCommonName.setText(parser.getParameter("CN"));
		labIssuerCountry.setText(parser.getParameter("C"));
	}

	private void populateSubjectPanel() {
		X509Certificate certificate = (X509Certificate) chain[0];
		String dn = certificate.getSubjectDN().getName();
		DNParser parser = new DNParser(dn);
		labSubjectOrganization.setText(parser.getParameter("O"));
		labSubjectOrganizationUnit.setText(parser.getParameter("OU"));
		labSubjectCommonName.setText(parser.getParameter("CN"));
		labSubjectCountry.setText(parser.getParameter("C"));
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 12, 0);
		add(getGeneralPanel(), c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 12, 0);
		add(getIssuerPanel(), c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		add(getSubjectPanel(), c);
	}

	private Component getGeneralPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder(resources
				.getString("title.general")));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(6, 6, 6, 12);
		MLabel labVersion = new MLabel(resources.getString("text.version"));
		panel.add(labVersion, c);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(6, 0, 6, 6);
		tfVersion = new MLabel();
		panel.add(tfVersion, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 6, 6, 12);
		MLabel labSerialNumber = new MLabel(
				resources.getString("text.serialNumber"));
		panel.add(labSerialNumber, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 6, 6);
		tfSerialNumber = new MLabel();
		panel.add(tfSerialNumber, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 6, 6, 12);
		MLabel labValidity = new MLabel(resources.getString("text.validity"));
		panel.add(labValidity, c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 6, 6);
		tfValidity = new MLabel();
		panel.add(tfValidity, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 6, 6, 12);
		MLabel labSignatureAlgorithm = new MLabel(
				resources.getString("text.signatureAlgorithm"));
		panel.add(labSignatureAlgorithm, c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 6, 6);
		tfSignatureAlgorithm = new MLabel();
		panel.add(tfSignatureAlgorithm, c);

		return panel;
	}

	private Component getIssuerPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder(resources
				.getString("title.issuer")));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(6, 6, 6, 12);
		MLabel labOrganization = new MLabel(
				resources.getString("text.organization"));
		panel.add(labOrganization, c);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(6, 0, 6, 6);
		labIssuerOrganization = new MLabel();
		panel.add(labIssuerOrganization, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 6, 6, 12);
		MLabel labOrganizationUnit = new MLabel(
				resources.getString("text.organizationUnit"));
		panel.add(labOrganizationUnit, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 6, 6);
		labIssuerOrganizationUnit = new MLabel();
		panel.add(labIssuerOrganizationUnit, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 6, 6, 12);
		MLabel labCommonName = new MLabel(
				resources.getString("text.commonName"));
		panel.add(labCommonName, c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 6, 6);
		labIssuerCommonName = new MLabel();
		panel.add(labIssuerCommonName, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 6, 6, 12);
		MLabel labCountry = new MLabel(resources.getString("text.country"));
		panel.add(labCountry, c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 6, 6);
		labIssuerCountry = new MLabel();
		panel.add(labIssuerCountry, c);

		return panel;
	}

	private Component getSubjectPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder(resources
				.getString("title.subject")));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(6, 6, 6, 12);
		MLabel labOrganization = new MLabel(
				resources.getString("text.organization"));
		panel.add(labOrganization, c);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(6, 0, 6, 6);
		labSubjectOrganization = new MLabel();
		panel.add(labSubjectOrganization, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 6, 6, 12);
		MLabel labOrganizationUnit = new MLabel(
				resources.getString("text.organizationUnit"));
		panel.add(labOrganizationUnit, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 6, 6);
		labSubjectOrganizationUnit = new MLabel();
		panel.add(labSubjectOrganizationUnit, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 6, 6, 12);
		MLabel labCommonName = new MLabel(
				resources.getString("text.commonName"));
		panel.add(labCommonName, c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 6, 6);
		labSubjectCommonName = new MLabel();
		panel.add(labSubjectCommonName, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 6, 6, 12);
		MLabel labCountry = new MLabel(resources.getString("text.country"));
		panel.add(labCountry, c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 6, 6);
		labSubjectCountry = new MLabel();
		panel.add(labSubjectCountry, c);

		return panel;
	}
}
