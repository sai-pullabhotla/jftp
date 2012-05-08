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

import com.myjavaworld.gui.SwingWorker;
import com.myjavaworld.jftp.FTPSession;
import com.myjavaworld.jftp.JFTP;
import com.myjavaworld.jftp.LocalFile;
import com.myjavaworld.jftp.UploadAsDlg;

/**
 * An Action Implementation for uploading a signle file with a different name.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class UploadAsAction implements ActionListener {

	private JFTP jftp = null;
	private static UploadAsAction instance = null;

	private UploadAsAction(JFTP jftp) {
		this.jftp = jftp;
	}

	public static synchronized UploadAsAction getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new UploadAsAction(jftp);
			}
		}
		return instance;
	}

	public void actionPerformed(ActionEvent evt) {
		final FTPSession session = jftp.getCurrentSession();
		if (session == null || !session.isConnected()) {
			return;
		}
		final LocalFile selectedFile = session.getSelectedLocalFile();
		if (selectedFile == null) {
			return;
		}
		UploadAsDlg dlg = new UploadAsDlg(jftp, selectedFile);
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
				session.upload(session.getLocalWorkingDirectory(),
						session.getRemoteWorkingDirectory(), selectedFile,
						fileName);
				return null;
			}

			@Override
			public void finished() {
				session.setBusy(false);
				session.refreshRemotePane();
			}
		}.start();
	}
}
