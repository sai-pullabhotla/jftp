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

import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.jftp.FTPSession;
import com.myjavaworld.jftp.JFTP;
import com.myjavaworld.jftp.RenameRemoteFileDlg;

/**
 * An action used by the Rename Local File menu item and tool bar button.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class RenameRemoteFileAction implements ActionListener {

	private JFTP jftp = null;
	private static RenameRemoteFileAction instance = null;

	private RenameRemoteFileAction(JFTP jftp) {
		this.jftp = jftp;
	}

	public static synchronized RenameRemoteFileAction getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new RenameRemoteFileAction(jftp);
			}
		}
		return instance;
	}

	public void actionPerformed(ActionEvent evt) {
		FTPSession session = jftp.getCurrentSession();
		if (session == null || !session.isConnected()) {
			return;
		}
		String fromName = "";
		String toName = "";
		RemoteFile from = session.getSelectedRemoteFile();
		if (from != null) {
			fromName = from.getName();
		}
		RenameRemoteFileDlg dlg = new RenameRemoteFileDlg(jftp, fromName);
		dlg.setLocationRelativeTo(jftp);
		dlg.setVisible(true);
		fromName = dlg.getFromFile();
		toName = dlg.getToFile();
		dlg.dispose();
		if (!(fromName == null && toName == null)) {
			session.renameRemoteFile(fromName, toName);
		}
	}
}
