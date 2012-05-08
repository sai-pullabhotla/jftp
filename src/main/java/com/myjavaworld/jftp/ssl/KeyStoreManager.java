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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.util.Enumeration;

import com.myjavaworld.jftp.JFTP;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KeyStoreManager {

	private static final String ALIAS_PREFIX = "JFTP";
	private static KeyStore serverCertificateStore = null;
	private static KeyStore clientCertificateStore = null;
	private static boolean inited = false;

	private static synchronized void init() throws KeyStoreException {
		File file = new File(JFTP.prefs.getServerCertificateStore());
		char[] password = JFTP.prefs.getServerCertificateStorePassword();
		serverCertificateStore = getKeyStore(file, password);

		file = new File(JFTP.prefs.getClientCertificateStore());
		password = JFTP.prefs.getClientCertificateStorePassword();
		clientCertificateStore = getKeyStore(file, password);

		inited = true;
	}

	/**
	 * Returns the key store that has all trusted server certificates.
	 * 
	 * @return trusted server certificates store.
	 * @throws KeyStoreException
	 * 
	 */
	public static synchronized KeyStore getServerCertificateStore()
			throws KeyStoreException {
		if (!inited) {
			init();
		}
		return serverCertificateStore;
	}

	/**
	 * Returns the key store that has all client certificates.
	 * 
	 * @return client certificates store.
	 * @throws KeyStoreException
	 * 
	 */
	public static synchronized KeyStore getClientCertificateStore()
			throws KeyStoreException {
		if (!inited) {
			init();
		}
		return clientCertificateStore;
	}

	/**
	 * Adds the given certificate chain to the server certificates store.
	 * 
	 * @param chain
	 *            certificate chain
	 * @throws KeyStoreException
	 * 
	 */
	public static synchronized void addServerCertificate(Certificate[] chain)
			throws KeyStoreException {
		if (!inited) {
			init();
		}
		addCertificate(serverCertificateStore, chain);
		saveServerCertificateStore();
	}

	/**
	 * Adds the given certificate chain to the client certificates store.
	 * 
	 * @param chain
	 *            certificate chain
	 * @throws KeyStoreException
	 * 
	 */
	public static synchronized void addClientCertificate(Certificate[] chain)
			throws KeyStoreException {
		if (!inited) {
			init();
		}
		addCertificate(clientCertificateStore, chain);
		saveClientCertificateStore();
	}

	public static synchronized void deleteServerCertificate(String alias)
			throws KeyStoreException {
		if (!inited) {
			init();
		}
		deleteCertificate(serverCertificateStore, alias);
		saveServerCertificateStore();
	}

	public static synchronized void deleteClientCertificate(String alias)
			throws KeyStoreException {
		if (!inited) {
			init();
		}
		deleteCertificate(clientCertificateStore, alias);
		saveClientCertificateStore();
	}

	/**
	 * Returns a <code>KeyStore</code> object given the file name and password.
	 * If the given file does not exists, a file is created and formatted to
	 * store keys and certificates.
	 * 
	 * @param fileName
	 *            File name that has keys and/or certificates.
	 * @param password
	 *            Password for the key srore.
	 * 
	 * @return KeyStore.
	 * 
	 * @throws KeyStoreException
	 * 
	 */
	private static synchronized KeyStore getKeyStore(String fileName,
			char[] password) throws KeyStoreException {
		return getKeyStore(new File(fileName), password);
	}

	/**
	 * Returns a <code>KeyStore</code> object given the key store file and
	 * password. If the given file does not exists, a file is created and
	 * formatted to store keys and certificates.
	 * 
	 * @param file
	 *            File
	 * @param password
	 *            Password for the key store
	 * 
	 * @return KyStore object
	 * 
	 * @throws KeyStoreException
	 * 
	 */
	private static synchronized KeyStore getKeyStore(File file, char[] password)
			throws KeyStoreException {
		KeyStore keyStore = KeyStore.getInstance("JKS");
		FileInputStream fin = null;
		try {
			if (!file.exists()) {
				keyStore.load(null, password);
				saveCertificateStore(file, password, keyStore);
			}
			fin = new FileInputStream(file);
			keyStore.load(fin, password);
		} catch (Exception exp) {
			throw new KeyStoreException(exp.toString());
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (Exception exp) {
				}
				fin = null;
			}
		}
		return keyStore;
	}

	/**
	 * Saves the server certificate store.
	 * 
	 * @throws KeyStoreException
	 * 
	 */
	private static synchronized void saveServerCertificateStore()
			throws KeyStoreException {
		if (!inited) {
			init();
		}
		File file = new File(JFTP.prefs.getServerCertificateStore());
		char[] password = JFTP.prefs.getServerCertificateStorePassword();
		saveCertificateStore(file, password, serverCertificateStore);
	}

	/**
	 * Saves the client certificate store.
	 * 
	 * @throws KeyStoreException
	 * 
	 */
	private static synchronized void saveClientCertificateStore()
			throws KeyStoreException {
		if (!inited) {
			init();
		}
		File file = new File(JFTP.prefs.getClientCertificateStore());
		char[] password = JFTP.prefs.getClientCertificateStorePassword();
		saveCertificateStore(file, password, clientCertificateStore);
	}

	/**
	 * Adds the given certificate chain to the given key store. This method
	 * automatically assigns a unique alias to each of the certificate in the
	 * chain. If any of the certificates in the chain already exists in the key
	 * store, the ols entry will be replaced with the new entry.
	 * 
	 * @param keyStore
	 *            Key store
	 * @param chain
	 *            Certificate chain to add
	 * 
	 * @throws KeyStoreException
	 */
	private static synchronized void addCertificate(KeyStore keyStore,
			Certificate[] chain) throws KeyStoreException {
		if (chain == null || chain.length == 0) {
			return;
		}
		for (int i = 0; i < chain.length; i++) {
			String alias = keyStore.getCertificateAlias(chain[i]);
			if (alias == null) {
				alias = getNextAlias(keyStore);
			}
			keyStore.setCertificateEntry(alias, chain[i]);
		}
	}

	/**
	 * Returns unique alias name that can be used for adding a new entry to the
	 * given key store.
	 * 
	 * @param keyStore
	 *            key store
	 * 
	 * @return alias
	 * 
	 * @throws KeyStoreException
	 */
	private static synchronized String getNextAlias(KeyStore keyStore)
			throws KeyStoreException {
		Enumeration aliases = keyStore.aliases();
		int maxAliasID = 1000;
		while (aliases.hasMoreElements()) {
			String alias = (String) aliases.nextElement();
			if (alias.toUpperCase().startsWith(ALIAS_PREFIX.toUpperCase())
					&& alias.length() > ALIAS_PREFIX.length()) {
				try {
					int aliasID = Integer.parseInt(alias.substring(ALIAS_PREFIX
							.length()));
					maxAliasID = Math.max(maxAliasID, aliasID);
				} catch (NumberFormatException exp) {
					// That's OK.
				}
			}
		}
		maxAliasID++;
		return ALIAS_PREFIX + (maxAliasID);
	}

	private static synchronized void deleteCertificate(KeyStore keyStore,
			String alias) throws KeyStoreException {
		keyStore.deleteEntry(alias);
	}

	/**
	 * Saves the given key store to the given file using the given password.
	 * 
	 * @param file
	 *            file
	 * @param password
	 *            new password for the key store.
	 * @param keyStore
	 *            key store
	 * 
	 * @throws KeyStoreException
	 */
	private static synchronized void saveCertificateStore(File file,
			char[] password, KeyStore keyStore) throws KeyStoreException {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file);
			keyStore.store(fout, password);
		} catch (Exception exp) {
			throw new KeyStoreException("Failed to save the certificate "
					+ "store: " + file);
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (Exception exp) {
				}
				fout = null;
			}
		}
	}
}
