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
package com.myjavaworld.jftp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.myjavaworld.jftp.ConnectionDlg;
import com.myjavaworld.jftp.FTPSession;
import com.myjavaworld.jftp.JFTP;
import com.myjavaworld.jftp.RemoteHost;

/**
 * An action implementation for Connecting to an FTP server.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class ConnectAction implements ActionListener {

	private JFTP jftp = null;
	private static ConnectAction instance = null;

	private ConnectAction(JFTP jftp) {
		this.jftp = jftp;
	}

	public static synchronized ConnectAction getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new ConnectAction(jftp);
			}
		}
		return instance;
	}

	public void actionPerformed(ActionEvent evt) {
		FTPSession session = jftp.getCurrentSession();
		if (session == null) {
			return;
		}
		ConnectionDlg connectionDlg = new ConnectionDlg(jftp);
		RemoteHost host = session.getRemoteHost();
		if (host == null) {
			host = new RemoteHost();
			host.setFTPClientClassName(JFTP.prefs.getClient());
			host.setListParserClassName(JFTP.prefs.getListParser());
			host.setPassive(JFTP.prefs.isPassive());
			host.setSSLUsage(JFTP.prefs.getSSLUsage());
			host.setDataChannelUnencrypted(JFTP.prefs
					.isDataChannelUnencrypted());
		}
		connectionDlg.setRemoteHost(host);
		connectionDlg.setLocationRelativeTo(jftp);
		connectionDlg.setVisible(true);
		RemoteHost remoteHost = connectionDlg.getRemoteHost();
		connectionDlg.dispose();
		if (remoteHost != null) {
			session.connect(remoteHost);
		}
	}
}
