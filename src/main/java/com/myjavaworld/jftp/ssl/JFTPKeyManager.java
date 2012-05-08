/*
 * Created on Sep 28, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.myjavaworld.jftp.ssl;

import java.net.Socket;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.X509KeyManager;

import com.myjavaworld.jftp.JFTP;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JFTPKeyManager implements X509KeyManager {

	private X509KeyManager km = null;

	public JFTPKeyManager() throws KeyStoreException, NoSuchAlgorithmException,
			UnrecoverableKeyException {
		super();
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(KeyStoreManager.getClientCertificateStore(),
				JFTP.prefs.getClientCertificateStorePassword());
		km = (X509KeyManager) kmf.getKeyManagers()[0];
	}

	public PrivateKey getPrivateKey(String alias) {
		return km.getPrivateKey(alias);
	}

	public X509Certificate[] getCertificateChain(String alias) {
		return km.getCertificateChain(alias);
	}

	public String[] getClientAliases(String keyType, Principal[] issuers) {
		return km.getClientAliases(keyType, issuers);
	}

	public String[] getServerAliases(String keyType, Principal[] issuers) {
		return km.getServerAliases(keyType, issuers);
	}

	public String chooseServerAlias(String keyType, Principal[] issuers,
			Socket socket) {
		return km.chooseServerAlias(keyType, issuers, socket);
	}

	public String chooseClientAlias(String[] keyType, Principal[] issuers,
			Socket socket) {
		return km.chooseClientAlias(keyType, issuers, socket);
	}
}
