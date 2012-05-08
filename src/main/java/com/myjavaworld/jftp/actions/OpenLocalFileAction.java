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

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.jftp.FTPSession;
import com.myjavaworld.jftp.JFTP;
import com.myjavaworld.jftp.LocalFile;

/**
 * An Action Implementation for uploading a signle file with a different name.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class OpenLocalFileAction implements ActionListener {

	private JFTP jftp = null;
	private static OpenLocalFileAction instance = null;

	private OpenLocalFileAction(JFTP jftp) {
		this.jftp = jftp;
	}

	public static synchronized OpenLocalFileAction getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new OpenLocalFileAction(jftp);
			}
		}
		return instance;
	}

	public void actionPerformed(ActionEvent evt) {
		final FTPSession session = jftp.getCurrentSession();
		if (session == null) {
			return;
		}
		final LocalFile selectedFile = session.getSelectedLocalFile();
		if (selectedFile == null) {
			return;
		}
		if (selectedFile.isDirectory()) {
			session.setLocalWorkingDirectory(selectedFile);
		} else {
			try {
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(selectedFile.getFile());
				}
			} catch (Throwable t) {
				GUIUtil.showError(jftp, t);
			}
		}
	}
}
