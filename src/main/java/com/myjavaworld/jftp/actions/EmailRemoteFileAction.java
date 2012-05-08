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
