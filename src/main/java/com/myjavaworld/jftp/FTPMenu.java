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
package com.myjavaworld.jftp;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MMenu;
import com.myjavaworld.gui.MMenuItem;
import com.myjavaworld.jftp.actions.AbortAction;
import com.myjavaworld.jftp.actions.ConnectAction;
import com.myjavaworld.jftp.actions.DisconnectAction;
import com.myjavaworld.jftp.actions.DownloadAction;
import com.myjavaworld.jftp.actions.DownloadAndUnzipAction;
import com.myjavaworld.jftp.actions.DownloadAsAction;
import com.myjavaworld.jftp.actions.NewSessionAction;
import com.myjavaworld.jftp.actions.ReconnectAction;
import com.myjavaworld.jftp.actions.UploadAction;
import com.myjavaworld.jftp.actions.UploadAsAction;
import com.myjavaworld.jftp.actions.ZipAndUploadAction;
import com.myjavaworld.util.ResourceLoader;
import com.myjavaworld.util.SystemUtil;

/**
 * FTP Menu
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class FTPMenu extends MMenu implements MenuListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.FTPMenu");
	private JFTP jftp = null;
	private MMenuItem miNewSession = null;
	private MMenuItem miCloseSession = null;
	private MMenuItem miConnect = null;
	private MMenuItem miDisconnect = null;
	private MMenuItem miReconnect = null;
	private MMenuItem miDownload1 = null;
	private MMenuItem miDownloadAs1 = null;
	private MMenuItem miDownloadAndUnzip1 = null;
	private MMenuItem miUpload1 = null;
	private MMenuItem miUploadAs1 = null;
	private MMenuItem miZipAndUpload1 = null;
	private MMenuItem miAbort = null;
	private MMenuItem miExit = null;

	public FTPMenu(JFTP jftp) {
		super();
		this.jftp = jftp;
		setText(resources.getString("text.ftp"));
		setMnemonic(resources.getString("mnemonic.ftp"),
				resources.getString("mnemonicIndex.ftp"));
		prepareMenuItems();
		addMenuListener(this);
	}

	private void prepareMenuItems() {

		miNewSession = new MMenuItem();
		miNewSession.setText(resources.getString("text.newSession"));
		miNewSession.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				GUIUtil.ACCELERATOR_MASK));
		miNewSession.setMnemonic(resources.getString("mnemonic.newSession"),
				resources.getString("mnemonicIndex.newSession"));
		miNewSession.addActionListener(NewSessionAction.getInstance(jftp));
		add(miNewSession);

		miCloseSession = new MMenuItem(resources.getString("text.closeSession"));
		miCloseSession.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				GUIUtil.ACCELERATOR_MASK + InputEvent.SHIFT_MASK));
		miCloseSession.setMnemonic(
				resources.getString("mnemonic.closeSession"),
				resources.getString("mnemonicIndex.closeSession"));
		miCloseSession.setActionCommand("cmd.closeSession");
		miCloseSession.addActionListener(jftp);
		add(miCloseSession);

		addSeparator();

		miConnect = new MMenuItem();
		miConnect.setText(resources.getString("text.connect"));
		miConnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		miConnect.setMnemonic(resources.getString("mnemonic.connect"),
				resources.getString("mnemonicIndex.connect"));
		miConnect.addActionListener(ConnectAction.getInstance(jftp));
		add(miConnect);

		miDisconnect = new MMenuItem();
		miDisconnect.setText(resources.getString("text.disconnect"));
		miDisconnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		miDisconnect.setMnemonic(resources.getString("mnemonic.disconnect"),
				resources.getString("mnemonicIndex.disconnect"));
		miDisconnect.addActionListener(DisconnectAction.getInstance(jftp));
		add(miDisconnect);

		miReconnect = new MMenuItem();
		miReconnect.setText(resources.getString("text.reconnect"));
		miReconnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,
				GUIUtil.ACCELERATOR_MASK));
		miReconnect.setMnemonic(resources.getString("mnemonic.reconnect"),
				resources.getString("mnemonicIndex.reconnect"));
		miReconnect.addActionListener(ReconnectAction.getInstance(jftp));
		add(miReconnect);

		addSeparator();

		miDownload1 = new MMenuItem();
		miDownload1.setText(resources.getString("text.download"));
		miDownload1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
		miDownload1.setMnemonic(resources.getString("mnemonic.download"),
				resources.getString("mnemonicIndex.download"));
		miDownload1.addActionListener(DownloadAction.getInstance(jftp));
		add(miDownload1);

		miDownloadAs1 = new MMenuItem(resources.getString("text.downloadAs"));
		miDownloadAs1.setActionCommand("cmd.downloadAs");
		miDownloadAs1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7,
				GUIUtil.ACCELERATOR_MASK));
		miDownloadAs1.setMnemonic(resources.getString("mnemonic.downloadAs"),
				resources.getString("mnemonicIndex.downloadAs"));
		miDownloadAs1.addActionListener(DownloadAsAction.getInstance(jftp));
		add(miDownloadAs1);

		miDownloadAndUnzip1 = new MMenuItem(
				resources.getString("text.downloadAndUnzip"));
		miDownloadAndUnzip1.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F7, GUIUtil.ACCELERATOR_MASK
						+ InputEvent.SHIFT_MASK));
		miDownloadAndUnzip1.setMnemonic(
				resources.getString("mnemonic.downloadAndUnzip"),
				resources.getString("mnemonicIndex.downloadAndUnzip"));
		miDownloadAndUnzip1.addActionListener(DownloadAndUnzipAction
				.getInstance(jftp));
		add(miDownloadAndUnzip1);

		addSeparator();

		miUpload1 = new MMenuItem();
		miUpload1.setText(resources.getString("text.upload"));
		miUpload1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
		miUpload1.setMnemonic(resources.getString("mnemonic.upload"),
				resources.getString("mnemonicIndex.upload"));
		miUpload1.addActionListener(UploadAction.getInstance(jftp));
		add(miUpload1);

		miUploadAs1 = new MMenuItem();
		miUploadAs1.setText(resources.getString("text.uploadAs"));
		miUploadAs1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9,
				GUIUtil.ACCELERATOR_MASK));
		miUploadAs1.setMnemonic(resources.getString("mnemonic.uploadAs"),
				resources.getString("mnemonicIndex.uploadAs"));
		miUploadAs1.addActionListener(UploadAsAction.getInstance(jftp));
		add(miUploadAs1);

		miZipAndUpload1 = new MMenuItem();
		miZipAndUpload1.setText(resources.getString("text.zipAndUpload"));
		miZipAndUpload1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9,
				GUIUtil.ACCELERATOR_MASK + InputEvent.SHIFT_MASK));
		miZipAndUpload1.setMnemonic(
				resources.getString("mnemonic.zipAndUpload"),
				resources.getString("mnemonicIndex.zipAndUpload"));
		miZipAndUpload1.addActionListener(ZipAndUploadAction.getInstance(jftp));
		add(miZipAndUpload1);

		addSeparator();

		miAbort = new MMenuItem();
		miAbort.setText(resources.getString("text.abort"));
		miAbort.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
		miAbort.setMnemonic(resources.getString("mnemonic.abort"),
				resources.getString("mnemonicIndex.abort"));
		miAbort.addActionListener(AbortAction.getInstance(jftp));
		add(miAbort);

		miExit = new MMenuItem(resources.getString("text.exit"));
		miExit.setActionCommand("cmd.exit");
		miExit.setMnemonic(resources.getString("mnemonic.exit"),
				resources.getString("mnemonicIndex.exit"));
		miExit.addActionListener(jftp);

		if (!SystemUtil.isMac()) {
			addSeparator();
			add(miExit);
		}
	}

	public void menuSelected(MenuEvent e) {
		FTPSession session = jftp.getCurrentSession();
		boolean nullSession = session == null;
		boolean connected = !nullSession && session.isConnected();

		int selectionCount1 = 0;
		boolean isFile1 = false;
		if (!nullSession) {
			selectionCount1 = session.getLocalFileSelectionCount();
			if (selectionCount1 == 1) {
				LocalFile selectedFile = session.getSelectedLocalFile();
				isFile1 = selectedFile.isFile();
			}
		}

		int selectionCount2 = 0;
		if (connected) {
			selectionCount2 = session.getRemoteFileSelectionCount();
		}
		boolean isFile2 = false;
		RemoteFile selectedFile2 = null;
		if (selectionCount2 == 1) {
			selectedFile2 = session.getSelectedRemoteFile();
			isFile2 = selectedFile2.isFile();
		}
		miCloseSession.setEnabled(!nullSession);
		miConnect.setEnabled(!nullSession);
		miDisconnect.setEnabled(connected);
		miReconnect.setEnabled(!nullSession && session.getRemoteHost() != null);
		miDownload1.setEnabled(connected && selectionCount2 > 0);
		miDownloadAs1.setEnabled(connected && selectionCount2 == 1);
		miDownloadAndUnzip1.setEnabled(connected && selectionCount2 == 1
				&& isFile2 && isZipFile(selectedFile2));
		miUpload1.setEnabled(connected && selectionCount1 > 0);
		miUploadAs1.setEnabled(connected && selectionCount1 == 1);
		miZipAndUpload1.setEnabled(connected && selectionCount1 > 0);
		miAbort.setEnabled(connected);
	}

	public void menuDeselected(MenuEvent e) {
		miCloseSession.setEnabled(true);
		miConnect.setEnabled(true);
		miDisconnect.setEnabled(true);
		miReconnect.setEnabled(true);
		miDownload1.setEnabled(true);
		miDownloadAs1.setEnabled(true);
		miDownloadAndUnzip1.setEnabled(true);
		miUpload1.setEnabled(true);
		miUploadAs1.setEnabled(true);
		miZipAndUpload1.setEnabled(true);
		miAbort.setEnabled(true);
	}

	public void menuCanceled(MenuEvent e) {
		miCloseSession.setEnabled(true);
		miConnect.setEnabled(true);
		miDisconnect.setEnabled(true);
		miReconnect.setEnabled(true);
		miDownload1.setEnabled(true);
		miDownloadAs1.setEnabled(true);
		miDownloadAndUnzip1.setEnabled(true);
		miUpload1.setEnabled(true);
		miUploadAs1.setEnabled(true);
		miZipAndUpload1.setEnabled(true);
		miAbort.setEnabled(true);
	}

	private boolean isZipFile(RemoteFile file) {
		String ext = file.getExtension();
		return "ZIP".equalsIgnoreCase(ext);
	}
}