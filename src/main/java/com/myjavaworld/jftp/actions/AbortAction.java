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

import com.myjavaworld.ftp.ConnectionException;
import com.myjavaworld.ftp.FTPClient;
import com.myjavaworld.ftp.FTPException;
import com.myjavaworld.gui.SwingWorker;
import com.myjavaworld.jftp.FTPSession;
import com.myjavaworld.jftp.JFTP;

/**
 * An Action Implementation for aborting data transfers in FTP.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class AbortAction implements ActionListener {

	private JFTP jftp = null;
	private static AbortAction instance = null;

	private AbortAction(JFTP jftp) {
		this.jftp = jftp;
	}

	public static synchronized AbortAction getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new AbortAction(jftp);
			}
		}
		return instance;
	}

	public void actionPerformed(ActionEvent evt) {
		final FTPSession session = jftp.getCurrentSession();
		if (session == null || !session.isConnected()) {
			return;
		}
		final FTPClient client = session.getFTPClient();
		session.setAbortFlag(true);
		new SwingWorker() {

			@Override
			public Object construct() {
				try {
					client.abort();
				} catch (FTPException exp) {
					session.ftpException(exp);
				} catch (ConnectionException exp) {
					session.connectionException(exp);
				} catch (Throwable t) {
					session.exception(t);
				}
				return null;
			}

			@Override
			public void finished() {
			}
		}.start();

	}
}
