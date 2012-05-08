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
public class EmailLocalFileAction implements ActionListener {

	private JFTP jftp = null;
	private static EmailLocalFileAction instance = null;

	private EmailLocalFileAction(JFTP jftp) {
		this.jftp = jftp;
	}

	public static synchronized EmailLocalFileAction getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new EmailLocalFileAction(jftp);
			}
		}
		return instance;
	}

	public void actionPerformed(ActionEvent evt) {
		// final FTPSession session = jftp.getCurrentSession();
		// if (session == null) {
		// return;
		// }
		// final LocalFile selectedFile = session.getSelectedLocalFile();
		// if(selectedFile == null || !selectedFile.isFile()) {
		// return;
		// }
		// try {
		// Message msg = new Message();
		// List attachments = new ArrayList();
		// attachments.add(selectedFile.getAbsolutePath());
		// msg.setAttachments(attachments);
		// Desktop.mail(msg);
		// }
		// catch(Throwable t) {
		// GUIUtil.showError(jftp, t);
		// }
	}
}
