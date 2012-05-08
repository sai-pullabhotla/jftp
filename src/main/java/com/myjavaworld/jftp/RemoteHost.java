/**
 *
 * This software is the confidential and proprietary information of the author,
 * Sai Pullabhotla. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with the author.
 *
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 *
 */
package com.myjavaworld.jftp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.myjavaworld.ftp.FTPConstants;

/**
 * Objects of this class encapsulates information of an FTP server as required
 * by JFTP.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class RemoteHost implements Serializable, Comparable {

	private static final long serialVersionUID = -3213664402994404410L;
	public static final String DEFAULT_FTP_CLIENT_CLASS_NAME = "com.myjavaworld.ftp.DefaultFTPClient";
	public static final String DEFAULT_LIST_PARSER_CLASS_NAME = "com.myjavaworld.ftp.DefaultListParser";
	protected String name = null;
	protected String hostName = null;
	protected int port = 0;
	protected String user = null;
	protected String password = null;
	protected String account = null;
	protected String ftpClientClassName = null;
	protected String listParserClassName = null;
	protected boolean passive = true;
	protected String[] commands = null;
	protected String initialLocalDirectory = null;
	protected String initialRemoteDirectory = null;
	protected int sslUsage = 0;
	protected boolean dataChannelUnencrypted = true;
	protected Integer implicitSSLPort = null;

	public RemoteHost() {
		this("", "", FTPConstants.DEFAULT_PORT, "", "", "");
	}

	public RemoteHost(String hostName, String user, String password) {
		this(hostName, hostName, FTPConstants.DEFAULT_PORT, user, password, "");
	}

	public RemoteHost(String hostName, int port, String user, String password,
			String account) {
		this(hostName, hostName, port, user, password, account);
	}

	public RemoteHost(String name, String hostName, int port, String user,
			String password, String account) {
		setName(name);
		setHostName(hostName);
		setPort(port);
		setUser(user);
		setPassword(password);
		setAccount(account);
		setFTPClientClassName(DEFAULT_FTP_CLIENT_CLASS_NAME);
		setListParserClassName(DEFAULT_LIST_PARSER_CLASS_NAME);
		initialLocalDirectory = "";
		initialRemoteDirectory = "";
		setPassive(true);
		setCommands(new String[0]);
		setSSLUsage(FTPConstants.USE_NO_SSL);
		setDataChannelUnencrypted(false);
		setImplicitSSLPort(FTPConstants.DEFAULT_IMPLICIT_SSL_PORT);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName == null ? "" : hostName.trim();
	}

	public String getHostName() {
		return hostName;
	}

	public void setPort(int port) {
		this.port = port <= 0 ? FTPConstants.DEFAULT_PORT : port;
	}

	public int getPort() {
		return port;
	}

	public void setPassword(String password) {
		this.password = password == null ? "" : password;
	}

	public String getPassword() {
		return password;
	}

	public void setUser(String user) {
		this.user = user == null ? "" : user;
	}

	public String getUser() {
		return user;
	}

	public void setAccount(String account) {
		this.account = account == null ? "" : account;
	}

	public String getAccount() {
		return account;
	}

	public void setFTPClientClassName(String ftpClientClassName) {
		this.ftpClientClassName = ftpClientClassName;
	}

	public String getFTPClientClassName() {
		return ftpClientClassName;
	}

	public void setListParserClassName(String listParserClassName) {
		this.listParserClassName = listParserClassName;
	}

	public String getListParserClassName() {
		return listParserClassName;
	}

	public void setCommands(String[] commands) {
		this.commands = commands;
	}

	public void setCommands(String commands) {
		StringTokenizer tokenizer = new StringTokenizer(commands, "\r\n");
		List commandList = new ArrayList(5);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.trim().length() > 0) {
				commandList.add(token);
			}
		}
		String[] commandArray = new String[commandList.size()];
		commandArray = (String[]) (commandList.toArray(commandArray));
		setCommands(commandArray);
	}

	public String[] getCommands() {
		return commands;
	}

	public String getCommandsAsString() {
		StringBuffer buffer = new StringBuffer(256);
		for (int i = 0; i < commands.length; i++) {
			buffer.append(commands[i]);
			buffer.append("\n");
		}
		return buffer.toString();
	}

	public void setPassive(boolean passive) {
		this.passive = passive;
	}

	public boolean isPassive() {
		return passive;
	}

	public void setInitialLocalDirectory(String initialLocalDirectory) {
		this.initialLocalDirectory = initialLocalDirectory;
	}

	public String getInitialLocalDirectory() {
		return initialLocalDirectory;
	}

	public void setInitialRemoteDirectory(String initialRemoteDirectory) {
		this.initialRemoteDirectory = initialRemoteDirectory;
	}

	public String getInitialRemoteDirectory() {
		return initialRemoteDirectory;
	}

	public void setSSLUsage(int sslUsage) {
		this.sslUsage = sslUsage;
	}

	public int getSSLUsage() {
		return sslUsage;
	}

	public void setDataChannelUnencrypted(boolean dataChannelUnencrypted) {
		this.dataChannelUnencrypted = dataChannelUnencrypted;
	}

	public boolean isDataChannelUnencrypted() {
		return dataChannelUnencrypted;
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

	@Override
	public String toString() {
		if (name.trim().length() == 0) {
			return hostName;
		}
		return name + " [" + hostName + "]";
	}

	public int compareTo(Object obj) {
		RemoteHost that = (RemoteHost) obj;
		return this.name.toUpperCase().compareTo(that.name.toUpperCase());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof RemoteHost)) {
			return false;
		}
		RemoteHost that = (RemoteHost) obj;
		return name.equals(that.name) && hostName.equals(that.hostName)
				&& port == that.port && user.equals(that.user)
				&& password.equals(that.password);
	}
}