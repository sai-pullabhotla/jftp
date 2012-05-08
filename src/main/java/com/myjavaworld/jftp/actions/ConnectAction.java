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
