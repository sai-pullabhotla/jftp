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
