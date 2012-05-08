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

import java.awt.Rectangle;
import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import com.myjavaworld.ftp.FTPConstants;
import com.myjavaworld.util.SystemUtil;

/**
 * A serializable class used to store various user preferences
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class JFTPPreferences implements Serializable {

	private static final long serialVersionUID = 4316033315068041786L;
	private static final String DEFAULT_SERVER_CERTIFICATE_STORE = JFTP.DATA_HOME
			+ File.separator + "serverCertificates.jks";
	private static final String DEFAULT_CLIENT_CERTIFICATE_STORE = JFTP.DATA_HOME
			+ File.separator + "clientCertificates.jks";
	private static final char[] DEFAULT_CERTIFICATE_STORE_PASSWORD = "changeit"
			.toCharArray();
	private static final int DEFAULT_SOCKS_PROXY_PORT = 1080;
	private Locale locale = null;
	private String laf = null;
	private String theme = null;
	private String localDirectory = null;
	private String email = null;
	private String client = null;
	private String listParser = null;
	private int timeout = 0;
	private int bufferSize = 0;
	private int dateFormat = 0;
	private int timeFormat = 0;
	private int defaultTransferType = 0;
	private Map transferTypes = null;
	private boolean passive = true;
	private boolean useJavaWindows = false;
	private int toolBarType = 0;
	private String serverCertificateStore = null;
	private String clientCertificateStore = null;
	private char[] serverCertificateStorePassword = null;
	private char[] clientCertificateStorePassword = null;
	private Boolean useProxy = null;
	private String proxyHost = null;
	private Integer proxyPort = null;
	private String proxyUser = null;
	private char[] proxyPassword = null;
	private Integer sslUsage = null;
	private Integer implicitSSLPort = null;
	private Boolean dataChannelUnencrypted = null;
	private Rectangle windowBounds = null;
	private Boolean licenseAgreed = null;
	private String licenseAgreedForVersion = null;
	private Boolean checkForUpdates = null;

	public JFTPPreferences() {
		locale = Locale.getDefault();
		if (SystemUtil.isMac()
				|| SystemUtil.getOSName().toUpperCase().startsWith("WIN")) {
			laf = UIManager.getSystemLookAndFeelClassName();
		} else {
			laf = UIManager.getCrossPlatformLookAndFeelClassName();
		}
		theme = "Default";
		localDirectory = FileSystemView.getFileSystemView()
				.getDefaultDirectory().getAbsolutePath();
		email = "you@yourcompany.com";
		client = RemoteHost.DEFAULT_FTP_CLIENT_CLASS_NAME;
		listParser = RemoteHost.DEFAULT_LIST_PARSER_CLASS_NAME;
		timeout = FTPConstants.DEFAULT_TIMEOUT;
		bufferSize = FTPConstants.DEFAULT_BUFFER_SIZE;
		dateFormat = DateFormat.SHORT;
		timeFormat = DateFormat.SHORT;
		defaultTransferType = FTPConstants.TYPE_BINARY;
		transferTypes = new TreeMap();

		transferTypes.put("ASP", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("BAT", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("C", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("CONF", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("CGI", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("CPP", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("CSS", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("DHTML", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("DTD", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("H", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("HTM", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("HTML", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("INI", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("JAVA", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("JHTML", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("JS", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("JSP", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("LOG", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("MV", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("PHP", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("PHTML", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("PL", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("SH", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("SHTML", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("TXT", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("VBS", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("XML", new Integer(FTPConstants.TYPE_ASCII));
		transferTypes.put("XSD", new Integer(FTPConstants.TYPE_ASCII));

		passive = true;
		useJavaWindows = false;
		toolBarType = 0;

		serverCertificateStore = DEFAULT_SERVER_CERTIFICATE_STORE;
		clientCertificateStore = DEFAULT_CLIENT_CERTIFICATE_STORE;
		serverCertificateStorePassword = DEFAULT_CERTIFICATE_STORE_PASSWORD;
		clientCertificateStorePassword = DEFAULT_CERTIFICATE_STORE_PASSWORD;

		useProxy = Boolean.FALSE;
		proxyHost = "";
		proxyPort = new Integer(DEFAULT_SOCKS_PROXY_PORT);
		proxyUser = "";
		proxyPassword = new char[0];

		sslUsage = new Integer(FTPConstants.USE_NO_SSL);
		implicitSSLPort = new Integer(FTPConstants.DEFAULT_IMPLICIT_SSL_PORT);
		dataChannelUnencrypted = Boolean.FALSE;

		checkForUpdates = Boolean.TRUE;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLookAndFeelClassName(String laf) {
		this.laf = laf;
	}

	public String getLookAndFeelClassName() {
		return laf;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getTheme() {
		return theme;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getClient() {
		return client;
	}

	public void setListParser(String listParser) {
		this.listParser = listParser;
	}

	public String getListParser() {
		return listParser;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setLocalDirectory(String localDirectory) {
		this.localDirectory = localDirectory;
	}

	public String getLocalDirectory() {
		return localDirectory;
	}

	public void setDateFormat(int dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getDateFormat() {
		return dateFormat;
	}

	public void setTimeFormat(int timeFormat) {
		this.timeFormat = timeFormat;
	}

	public int getTimeFormat() {
		return timeFormat;
	}

	public void setDefaultTransferType(int defaultTransferType) {
		this.defaultTransferType = defaultTransferType;
	}

	public int getDefaultTransferType() {
		return defaultTransferType;
	}

	public void setTransferTypes(Map transferTypes) {
		this.transferTypes = transferTypes;
	}

	public Map getTransferTypes() {
		return transferTypes;
	}

	public void setPassive(boolean passive) {
		this.passive = passive;
	}

	public boolean isPassive() {
		return passive;
	}

	public void setUseJavaWindows(boolean useJavaWindows) {
		this.useJavaWindows = useJavaWindows;
	}

	public boolean getUseJavaWindows() {
		return useJavaWindows;
	}

	public void setToolBarType(int toolBarType) {
		this.toolBarType = toolBarType;
	}

	public int getToolBarType() {
		return toolBarType;
	}

	public void setServerCertificateStore(String fileName) {
		this.serverCertificateStore = fileName;
	}

	public String getServerCertificateStore() {
		if (serverCertificateStore == null) {
			return DEFAULT_SERVER_CERTIFICATE_STORE;
		}
		return serverCertificateStore;
	}

	public void setClientCertificateStore(String fileName) {
		this.clientCertificateStore = fileName;
	}

	public String getClientCertificateStore() {
		if (clientCertificateStore == null) {
			return DEFAULT_CLIENT_CERTIFICATE_STORE;
		}
		return clientCertificateStore;
	}

	public void setServerCertificateStorePassword(char[] password) {
		this.serverCertificateStorePassword = password;
	}

	public char[] getServerCertificateStorePassword() {
		if (serverCertificateStorePassword == null) {
			return DEFAULT_CERTIFICATE_STORE_PASSWORD;
		}
		return serverCertificateStorePassword;
	}

	public void setClientCertificateStorePassword(char[] password) {
		this.clientCertificateStorePassword = password;
	}

	public char[] getClientCertificateStorePassword() {
		if (clientCertificateStorePassword == null) {
			return DEFAULT_CERTIFICATE_STORE_PASSWORD;
		}
		return clientCertificateStorePassword;
	}

	public void setUseProxy(boolean useProxy) {
		this.useProxy = new Boolean(useProxy);
	}

	public boolean isUseProxy() {
		if (useProxy != null) {
			return useProxy.booleanValue();
		}
		return false;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyHost() {
		if (proxyHost != null) {
			return proxyHost;
		}
		return "";
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = new Integer(proxyPort);
	}

	public int getProxyPort() {
		if (proxyPort != null) {
			return proxyPort.intValue();
		}
		return DEFAULT_SOCKS_PROXY_PORT;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getProxyUser() {
		if (proxyUser != null) {
			return proxyUser;
		}
		return "";
	}

	public void setProxyPassword(char[] proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public char[] getProxyPassword() {
		if (proxyPassword != null) {
			return proxyPassword;
		}
		return new char[0];
	}

	public void setSSLUsage(int sslUsage) {
		this.sslUsage = new Integer(sslUsage);
	}

	public int getSSLUsage() {
		if (sslUsage != null) {
			return sslUsage.intValue();
		}
		return FTPConstants.USE_NO_SSL;
	}

	public void setImplicitSSLPort(int implicitSSLPort) {
		this.implicitSSLPort = new Integer(implicitSSLPort);
	}

	public int getImplicitSSLPort() {
		if (implicitSSLPort != null) {
			return implicitSSLPort.intValue();
		}
		return FTPConstants.DEFAULT_IMPLICIT_SSL_PORT;
	}

	public void setDataChannelUnencrypted(boolean dataChannelUnencrypted) {
		this.dataChannelUnencrypted = new Boolean(dataChannelUnencrypted);
	}

	public boolean isDataChannelUnencrypted() {
		if (dataChannelUnencrypted != null) {
			return dataChannelUnencrypted.booleanValue();
		}
		return false;
	}

	public void setWindowBounds(Rectangle windowBounds) {
		this.windowBounds = windowBounds;
	}

	public Rectangle getWindowBounds() {
		return windowBounds;
	}

	public void setLicenseAgreed(boolean licenseAgreed) {
		this.licenseAgreed = new Boolean(licenseAgreed);
	}

	public boolean isLicenseAgreed() {
		if (licenseAgreed == null) {
			return false;
		}
		return licenseAgreed.booleanValue();
	}

	/**
	 * @return Returns the licenseAgreedForVersion.
	 */
	public String getLicenseAgreedForVersion() {
		if (licenseAgreedForVersion == null) {
			return "";
		}
		return licenseAgreedForVersion;
	}

	/**
	 * @param licenseAgreedForVersion
	 *            The licenseAgreedForVersion to set.
	 */
	public void setLicenseAgreedForVersion(String licenseAgreedForVersion) {
		this.licenseAgreedForVersion = licenseAgreedForVersion;
	}

	public boolean getCheckForUpdates() {
		if (checkForUpdates == null) {
			return true;
		}
		return checkForUpdates.booleanValue();
	}

	public void setCheckForUpdates(boolean checkForUpdates) {
		this.checkForUpdates = new Boolean(checkForUpdates);
	}
}