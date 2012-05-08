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
import java.io.File;

import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.SwingWorker;
import com.myjavaworld.jftp.DownloadAndUnzipDlg;
import com.myjavaworld.jftp.FTPSession;
import com.myjavaworld.jftp.JFTP;
import com.myjavaworld.zip.Unzip;

/**
 * An Action Implementation for uploading a signle file with a different name.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class DownloadAndUnzipAction implements ActionListener {

	private JFTP jftp = null;
	private static DownloadAndUnzipAction instance = null;

	private DownloadAndUnzipAction(JFTP jftp) {
		this.jftp = jftp;
	}

	public static synchronized DownloadAndUnzipAction getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new DownloadAndUnzipAction(jftp);
			}
		}
		return instance;
	}

	public void actionPerformed(ActionEvent evt) {
		downloadAndUnzip();
	}

	private void downloadAndUnzip() {
		final FTPSession session = jftp.getCurrentSession();
		if (session == null || !session.isConnected()) {
			return;
		}
		final RemoteFile selectedFile = session.getSelectedRemoteFile();
		if (selectedFile == null) {
			return;
		}
		if (!"ZIP".equalsIgnoreCase(selectedFile.getExtension())) {
			return;
		}
		DownloadAndUnzipDlg dlg = new DownloadAndUnzipDlg(jftp, selectedFile);
		dlg.setLocationRelativeTo(jftp);
		dlg.setVisible(true);
		if (!dlg.isApproved()) {
			return;
		}
		final File localFile = dlg.getDownloadTo();
		final File unzipTo = dlg.getUnzipDirectory();
		final boolean deleteOption = dlg.getDeleteOption();
		dlg.dispose();
		System.gc();
		session.clearAbortFlag();
		session.setBusy(true);
		new SwingWorker() {

			private Throwable error = null;

			@Override
			public Object construct() {
				session.downloadDataFile(selectedFile, localFile);
				try {
					Unzip unzip = new Unzip(localFile);
					unzip.setTargetDirectory(unzipTo);
					unzip.addZipListener(session);
					unzip.addProgressListener(session);
					unzip.open();
					unzip.unzip();
					unzip.close();
					if (deleteOption) {
						localFile.delete();
					}
				} catch (Throwable t) {
					error = t;
				}
				return null;
			}

			@Override
			public void finished() {
				session.setBusy(false);
				session.resetStatusBar();
				if (error != null) {
					GUIUtil.showError(jftp, error);
				}
				session.refreshLocalPane();
			}
		}.start();
	}
}