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
import java.io.File;
import java.io.IOException;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.SwingWorker;
import com.myjavaworld.jftp.FTPSession;
import com.myjavaworld.jftp.JFTP;
import com.myjavaworld.jftp.LocalFile;
import com.myjavaworld.jftp.ZipAndUploadDlg;
import com.myjavaworld.zip.Zip;

/**
 * An Action Implementation for uploading a signle file with a different name.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class ZipAndUploadAction implements ActionListener {

	private JFTP jftp = null;
	private static ZipAndUploadAction instance = null;

	private ZipAndUploadAction(JFTP jftp) {
		this.jftp = jftp;
	}

	public static synchronized ZipAndUploadAction getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new ZipAndUploadAction(jftp);
			}
		}
		return instance;
	}

	public void actionPerformed(ActionEvent evt) {
		zipAndUpload();
	}

	private void zipAndUpload() {
		final FTPSession session = jftp.getCurrentSession();
		if (session == null || !session.isConnected()) {
			return;
		}
		final LocalFile[] selectedFiles = session.getSelectedLocalFiles();
		if (selectedFiles == null || selectedFiles.length == 0) {
			return;
		}
		ZipAndUploadDlg dlg = new ZipAndUploadDlg(jftp);
		dlg.setLocationRelativeTo(jftp);
		dlg.setVisible(true);
		if (!dlg.isApproved()) {
			return;
		}
		final String fileName = dlg.getFileName();
		final boolean deleteAfterUpload = dlg.getDeleteOption();
		final File file = dlg.getZipFile();
		dlg.dispose();
		System.gc();
		if (file == null) {
			return;
		}
		session.clearAbortFlag();
		session.setBusy(true);
		new SwingWorker() {

			@Override
			public Object construct() {
				Zip zip = null;
				try {
					zip = new Zip(file);
					zip.addZipListener(session);
					zip.setFilter(session.getLocalFileFilter());
					zip.addProgressListener(session);
					zip.setRelativeTo(session.getLocalWorkingDirectory()
							.getFile());
					zip.open();
					for (int i = 0; i < selectedFiles.length; i++) {
						zip.addEntry(selectedFiles[i].getFile());
					}
					zip.close();
					session.upload(new LocalFile(zip.getFile()), fileName);
					if (deleteAfterUpload) {
						zip.getFile().delete();
					}
				} catch (Throwable t) {
					GUIUtil.showError(jftp, t);
				} finally {
					if (zip != null) {
						try {
							zip.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				return null;
			}

			@Override
			public void finished() {
				session.setBusy(false);
				session.resetStatusBar();
				session.refreshRemotePane();
				session.refreshLocalPane();
			}
		}.start();
	}
}