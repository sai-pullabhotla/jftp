/*
 * Created on Sep 26, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.myjavaworld.jftp.ssl;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.myjavaworld.jftp.JFTP;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JFTPSSLContext {

	public static SSLContext getSSLContext(JFTP jftp, String hostName)
			throws KeyManagementException, KeyStoreException,
			NoSuchAlgorithmException, UnrecoverableKeyException {
		SSLContext ctx = SSLContext.getInstance("SSL");
		ctx.init(new KeyManager[] { new JFTPKeyManager() },
				new TrustManager[] { new JFTPTrustManager(jftp, hostName) },
				null);
		return ctx;
	}
}
