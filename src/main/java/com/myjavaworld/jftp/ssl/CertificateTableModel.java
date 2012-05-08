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

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

import com.myjavaworld.util.ResourceLoader;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CertificateTableModel extends AbstractTableModel {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.ssl.CertificateTableModel");
	private static final String[] COLUMN_NAMES = {
			resources.getString("text.subject"),
			resources.getString("text.issuer"),
			resources.getString("text.validFrom"),
			resources.getString("text.validTo") };
	private KeyStore keyStore = null;
	private java.util.List aliases = null;

	public CertificateTableModel(KeyStore keyStore) throws KeyStoreException {
		super();
		setKeyStore(keyStore);
	}

	public void setKeyStore(KeyStore keyStore) throws KeyStoreException {
		this.keyStore = keyStore;
		aliases = new ArrayList();
		Enumeration e = keyStore.aliases();
		while (e.hasMoreElements()) {
			String alias = (String) e.nextElement();
			if (keyStore.isCertificateEntry(alias)) {
				aliases.add(alias);
			}
		}
		fireTableDataChanged();
	}

	public KeyStore getKeyStore() {
		return keyStore;
	}

	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	public int getRowCount() {
		return aliases.size();
	}

	@Override
	public String getColumnName(int col) {
		return COLUMN_NAMES[col];
	}

	public Object getValueAt(int row, int col) {
		String alias = (String) aliases.get(row);
		try {
			X509Certificate certificate = (X509Certificate) keyStore
					.getCertificate(alias);
			if (col == 0) {
				String subjectDN = certificate.getSubjectDN().getName();
				String returnValue = DNParser.getParameter(subjectDN, "O");
				return returnValue == null ? "" : returnValue;
			} else if (col == 1) {
				String issuerDN = certificate.getIssuerDN().getName();
				String returnValue = DNParser.getParameter(issuerDN, "O");
				return returnValue == null ? "" : returnValue;
			} else if (col == 2) {
				return certificate.getNotBefore();
			} else if (col == 3) {
				return certificate.getNotAfter();
			}
			return "";
		} catch (KeyStoreException exp) {
			return "Error reading certificate";
		}
	}

	@Override
	public Class getColumnClass(int col) {
		if (col == 2 || col == 3) {
			return Date.class;
		}
		return Object.class;
	}

	public Certificate getCertificateAt(int row) {
		String alias = (String) aliases.get(row);
		try {
			return keyStore.getCertificate(alias);
		} catch (Exception exp) {
			return null;
		}
	}

	public String getAliasAt(int row) {
		return (String) aliases.get(row);
	}
}
