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

import com.myjavaworld.jftp.JFTP;

/**
 * An Action Implementation for uploading a signle file with a different name.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class EmailRemoteFileAction implements ActionListener {

	private JFTP jftp = null;
	private static EmailRemoteFileAction instance = null;

	private EmailRemoteFileAction(JFTP jftp) {
		this.jftp = jftp;
	}

	public static synchronized EmailRemoteFileAction getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new EmailRemoteFileAction(jftp);
			}
		}
		return instance;
	}

	public void actionPerformed(ActionEvent evt) {
		// final FTPSession session = jftp.getCurrentSession();
		// if (session == null || !session.isConnected()) {
		// return;
		// }
		//
		// final RemoteFile selectedFile = session.getSelectedRemoteFile();
		// if(selectedFile == null || selectedFile.isDirectory()) {
		// return;
		// }
		// session.clearAbortFlag();
		// session.setBusy(true);
		// new SwingWorker() {
		// private File tempFile = null;
		// public Object construct() {
		// tempFile = session.downloadToTempFile(selectedFile, true);
		// return null;
		// }
		//
		// public void finished() {
		// session.setBusy(false);
		// if(tempFile == null) {
		// return;
		// }
		// try {
		// Message msg = new Message();
		// List attachments = new ArrayList();
		// attachments.add(tempFile.getAbsolutePath());
		// msg.setAttachments(attachments);
		// Desktop.mail(msg);
		// }
		// catch(Throwable t) {
		// GUIUtil.showError(jftp, t);
		// }
		// }
		// }.start();
	}
}
