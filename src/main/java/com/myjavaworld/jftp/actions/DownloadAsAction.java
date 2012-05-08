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

import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.gui.SwingWorker;
import com.myjavaworld.jftp.DownloadAsDlg;
import com.myjavaworld.jftp.FTPSession;
import com.myjavaworld.jftp.JFTP;

/**
 * An Action Implementation for uploading a signle file with a different name.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class DownloadAsAction implements ActionListener {

	private JFTP jftp = null;
	private static DownloadAsAction instance = null;

	private DownloadAsAction(JFTP jftp) {
		this.jftp = jftp;
	}

	public static synchronized DownloadAsAction getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new DownloadAsAction(jftp);
			}
		}
		return instance;
	}

	public void actionPerformed(ActionEvent evt) {
		final FTPSession session = jftp.getCurrentSession();
		if (session == null || !session.isConnected()) {
			return;
		}
		final RemoteFile selectedFile = session.getSelectedRemoteFile();
		if (selectedFile == null) {
			return;
		}
		DownloadAsDlg dlg = new DownloadAsDlg(jftp, selectedFile);
		dlg.setLocationRelativeTo(jftp);
		dlg.setVisible(true);
		if (!dlg.isApproved()) {
			return;
		}
		final String fileName = dlg.getFileName();
		dlg.dispose();
		System.gc();
		session.clearAbortFlag();
		session.setBusy(true);
		new SwingWorker() {

			@Override
			public Object construct() {
				session.download(session.getRemoteWorkingDirectory(), session
						.getLocalWorkingDirectory().getFile(), selectedFile,
						fileName);
				return null;
			}

			@Override
			public void finished() {
				session.setBusy(false);
				session.refreshLocalPane();
			}
		}.start();
	}
}
