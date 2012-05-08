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

import javax.swing.AbstractAction;

import com.myjavaworld.jftp.JFTP;
import com.myjavaworld.jftp.ssl.CertificateManagerDlg;

/**
 * An action implementation for Connecting to an FTP server.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class ManageCertificatesAction extends AbstractAction {

	private JFTP jftp = null;
	private static ManageCertificatesAction instance = null;

	private ManageCertificatesAction(JFTP jftp) {
		this.jftp = jftp;
	}

	public static synchronized ManageCertificatesAction getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new ManageCertificatesAction(jftp);
			}
		}
		return instance;
	}

	public void actionPerformed(ActionEvent evt) {
		CertificateManagerDlg dlg = new CertificateManagerDlg(jftp);
		dlg.setLocationRelativeTo(jftp);
		dlg.setVisible(true);
		dlg.dispose();
	}
}
