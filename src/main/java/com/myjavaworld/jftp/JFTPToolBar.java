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

import java.awt.Insets;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.myjavaworld.gui.MButton;
import com.myjavaworld.jftp.actions.AbortAction;
import com.myjavaworld.jftp.actions.ChangeLocalDirectoryAction;
import com.myjavaworld.jftp.actions.ChangeRemoteDirectoryAction;
import com.myjavaworld.jftp.actions.ConnectAction;
import com.myjavaworld.jftp.actions.DeleteLocalFileAction;
import com.myjavaworld.jftp.actions.DeleteRemoteFileAction;
import com.myjavaworld.jftp.actions.DisconnectAction;
import com.myjavaworld.jftp.actions.DownloadAction;
import com.myjavaworld.jftp.actions.ManageCertificatesAction;
import com.myjavaworld.jftp.actions.ManageFavoritesAction;
import com.myjavaworld.jftp.actions.NewLocalDirectoryAction;
import com.myjavaworld.jftp.actions.NewLocalFileAction;
import com.myjavaworld.jftp.actions.NewRemoteDirectoryAction;
import com.myjavaworld.jftp.actions.NewRemoteFileAction;
import com.myjavaworld.jftp.actions.NewSessionAction;
import com.myjavaworld.jftp.actions.ReconnectAction;
import com.myjavaworld.jftp.actions.RenameLocalFileAction;
import com.myjavaworld.jftp.actions.RenameRemoteFileAction;
import com.myjavaworld.jftp.actions.UploadAction;
import com.myjavaworld.util.ResourceLoader;

/**
 * Main tool bar for JFTP
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class JFTPToolBar extends JToolBar {

	private static final String HELP_ID_HOME = "index";
	private JFTP jftp = null;
	private MButton butNewSession = null;
	private MButton butConnect = null;
	private MButton butDisconnect = null;
	private MButton butReconnect = null;
	private MButton butAbort = null;
	private MButton butUpload = null;
	private MButton butDownload = null;
	private MButton butNewLocalFile = null;
	private MButton butNewLocalDirectory = null;
	private MButton butChangeLocalDirectory = null;
	private MButton butRenameLocalFile = null;
	private MButton butDeleteLocalFile = null;
	private MButton butNewRemoteFile = null;
	private MButton butNewRemoteDirectory = null;
	private MButton butChangeRemoteDirectory = null;
	private MButton butRenameRemoteFile = null;
	private MButton butDeleteRemoteFile = null;
	private MButton butManageFavorites = null;
	private MButton butManageCertificates = null;
	private MButton butHelp = null;

	public JFTPToolBar(JFTP jftp) {
		super(SwingConstants.HORIZONTAL);
		setBorder(BorderFactory.createEtchedBorder());
		this.jftp = jftp;
		setFloatable(false);
		this.setRollover(true);
		prepareToolBar();
	}

	@Override
	public Insets getInsets() {
		return new Insets(2, 2, 2, 2);
	}

	private void prepareToolBar() {

		ResourceBundle resources = ResourceLoader
				.getBundle("com.myjavaworld.jftp.JFTPToolBar");

		butNewSession = new MButton();
		butNewSession.setIcon(JFTPUtil.getIcon("newSession16.gif"));
		butNewSession.setToolTipText(resources.getString("tooltip.newSession"));
		butNewSession.addActionListener(NewSessionAction.getInstance(jftp));

		butConnect = new MButton();
		butConnect.setIcon(JFTPUtil.getIcon("connect16.gif"));
		butConnect.setToolTipText(resources.getString("tooltip.connect"));
		butConnect.addActionListener(ConnectAction.getInstance(jftp));

		butDisconnect = new MButton();
		butDisconnect.setIcon(JFTPUtil.getIcon("disconnect16.gif"));
		butDisconnect.setToolTipText(resources.getString("tooltip.disconnect"));
		butDisconnect.addActionListener(DisconnectAction.getInstance(jftp));

		butReconnect = new MButton();
		butReconnect.setIcon(JFTPUtil.getIcon("reconnect16.gif"));
		butReconnect.setToolTipText(resources.getString("tooltip.reconnect"));
		butReconnect.addActionListener(ReconnectAction.getInstance(jftp));

		butAbort = new MButton();
		butAbort.setIcon(JFTPUtil.getIcon("abort16.gif"));
		butAbort.setToolTipText(resources.getString("tooltip.abort"));
		butAbort.addActionListener(AbortAction.getInstance(jftp));

		butUpload = new MButton();
		butUpload.setIcon(JFTPUtil.getIcon("upload16.gif"));
		butUpload.setToolTipText(resources.getString("tooltip.upload"));
		butUpload.addActionListener(UploadAction.getInstance(jftp));

		butDownload = new MButton();
		butDownload.setIcon(JFTPUtil.getIcon("download16.gif"));
		butDownload.setToolTipText(resources.getString("tooltip.download"));
		butDownload.addActionListener(DownloadAction.getInstance(jftp));

		butNewLocalFile = new MButton();
		butNewLocalFile.setIcon(JFTPUtil.getIcon("newLocalFile16.gif"));
		butNewLocalFile.setToolTipText(resources
				.getString("tooltip.newLocalFile"));
		butNewLocalFile.addActionListener(NewLocalFileAction.getInstance(jftp));

		butNewLocalDirectory = new MButton();
		butNewLocalDirectory.setIcon(JFTPUtil
				.getIcon("newLocalDirectory16.gif"));
		butNewLocalDirectory.setToolTipText(resources
				.getString("tooltip.newLocalDirectory"));
		butNewLocalDirectory.addActionListener(NewLocalDirectoryAction
				.getInstance(jftp));

		butChangeLocalDirectory = new MButton();
		butChangeLocalDirectory.setIcon(JFTPUtil
				.getIcon("changeLocalDirectory16.gif"));
		butChangeLocalDirectory.setToolTipText(resources
				.getString("tooltip.changeLocalDirectory"));
		butChangeLocalDirectory.addActionListener(ChangeLocalDirectoryAction
				.getInstance(jftp));

		butRenameLocalFile = new MButton();
		butRenameLocalFile.setIcon(JFTPUtil.getIcon("renameLocalFile16.gif"));
		butRenameLocalFile.setToolTipText(resources
				.getString("tooltip.renameLocalFile"));
		butRenameLocalFile.addActionListener(RenameLocalFileAction
				.getInstance(jftp));

		butDeleteLocalFile = new MButton();
		butDeleteLocalFile.setIcon(JFTPUtil.getIcon("deleteLocalFile16.gif"));
		butDeleteLocalFile.setToolTipText(resources
				.getString("tooltip.deleteLocalFiles"));
		butDeleteLocalFile.addActionListener(DeleteLocalFileAction
				.getInstance(jftp));

		butNewRemoteFile = new MButton();
		butNewRemoteFile.setIcon(JFTPUtil.getIcon("newRemoteFile16.gif"));
		butNewRemoteFile.setToolTipText(resources
				.getString("tooltip.newRemoteFile"));
		butNewRemoteFile.addActionListener(NewRemoteFileAction
				.getInstance(jftp));

		butNewRemoteDirectory = new MButton();
		butNewRemoteDirectory.setIcon(JFTPUtil
				.getIcon("newRemoteDirectory16.gif"));
		butNewRemoteDirectory.setToolTipText(resources
				.getString("tooltip.newRemoteDirectory"));
		butNewRemoteDirectory.addActionListener(NewRemoteDirectoryAction
				.getInstance(jftp));

		butChangeRemoteDirectory = new MButton();
		butChangeRemoteDirectory.setIcon(JFTPUtil
				.getIcon("changeRemoteDirectory16.gif"));
		butChangeRemoteDirectory.setToolTipText(resources
				.getString("tooltip.changeRemoteDirectory"));
		butChangeRemoteDirectory.addActionListener(ChangeRemoteDirectoryAction
				.getInstance(jftp));

		butRenameRemoteFile = new MButton();
		butRenameRemoteFile.setIcon(JFTPUtil.getIcon("renameRemoteFile16.gif"));
		butRenameRemoteFile.setToolTipText(resources
				.getString("tooltip.renameRemoteFile"));
		butRenameRemoteFile.addActionListener(RenameRemoteFileAction
				.getInstance(jftp));

		butDeleteRemoteFile = new MButton();
		butDeleteRemoteFile.setIcon(JFTPUtil.getIcon("deleteRemoteFile16.gif"));
		butDeleteRemoteFile.setToolTipText(resources
				.getString("tooltip.deleteRemoteFiles"));
		butDeleteRemoteFile.addActionListener(DeleteRemoteFileAction
				.getInstance(jftp));

		butManageFavorites = new MButton();
		butManageFavorites.setIcon(JFTPUtil.getIcon("favorites16.gif"));
		butManageFavorites.setToolTipText(resources
				.getString("tooltip.manageFavorites"));
		butManageFavorites.addActionListener(ManageFavoritesAction
				.getInstance(jftp));

		butManageCertificates = new MButton();
		butManageCertificates.setIcon(JFTPUtil.getIcon("certificate16.gif"));
		butManageCertificates.setToolTipText(resources
				.getString("tooltip.manageCertificates"));
		butManageCertificates.addActionListener(ManageCertificatesAction
				.getInstance(jftp));

		butHelp = new MButton();
		butHelp.setIcon(JFTPUtil.getIcon("help16.gif"));
		butHelp.setToolTipText(resources.getString("tooltip.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID_HOME);

		add(butNewSession);
		add(Box.createHorizontalStrut(2));
		add(butConnect);
		add(Box.createHorizontalStrut(2));
		add(butDisconnect);
		add(Box.createHorizontalStrut(2));
		add(butReconnect);

		addSeparator();

		add(butDownload);
		add(Box.createHorizontalStrut(2));
		add(butUpload);
		add(Box.createHorizontalStrut(2));
		add(butAbort);

		addSeparator();

		add(butNewLocalFile);
		add(Box.createHorizontalStrut(2));
		add(butNewLocalDirectory);
		add(Box.createHorizontalStrut(2));
		add(butChangeLocalDirectory);
		add(Box.createHorizontalStrut(2));
		add(butRenameLocalFile);
		add(Box.createHorizontalStrut(2));
		add(butDeleteLocalFile);

		addSeparator();

		add(butNewRemoteFile);
		add(Box.createHorizontalStrut(2));
		add(butNewRemoteDirectory);
		add(Box.createHorizontalStrut(2));
		add(butChangeRemoteDirectory);
		add(Box.createHorizontalStrut(2));
		add(butRenameRemoteFile);
		add(Box.createHorizontalStrut(2));
		add(butDeleteRemoteFile);

		addSeparator();

		add(butManageFavorites);
		add(butManageCertificates);

		addSeparator();

		add(butHelp);
	}

	@Override
	public JButton add(Action action) {
		JButton b = super.add(action);
		b.setFocusPainted(false);
		return b;
	}

	@Override
	protected JButton createActionComponent(Action a) {
		JButton b = super.createActionComponent(a);
		return b;
	}

	public void updateButtons() {
		FTPSession session = jftp.getCurrentSession();
		boolean nullSession = session == null;
		boolean connected = !nullSession && session.isConnected();
		int localFileSelectionCount = 0;
		if (!nullSession) {
			localFileSelectionCount = session.getLocalFileSelectionCount();
		}
		int remoteFileSelectionCount = 0;
		if (connected) {
			remoteFileSelectionCount = session.getRemoteFileSelectionCount();
		}
		butConnect.setEnabled(!nullSession && !connected);
		butDisconnect.setEnabled(connected);
		butReconnect
				.setEnabled(!nullSession && session.getRemoteHost() != null);
		butAbort.setEnabled(connected);
		butDownload.setEnabled(connected && remoteFileSelectionCount > 0);
		butUpload.setEnabled(connected && localFileSelectionCount > 0);
		butNewLocalFile.setEnabled(!nullSession);
		butNewLocalDirectory.setEnabled(!nullSession);
		butChangeLocalDirectory.setEnabled(!nullSession);
		butRenameLocalFile.setEnabled(!nullSession
				&& localFileSelectionCount == 1);
		butDeleteLocalFile.setEnabled(!nullSession
				&& localFileSelectionCount > 0);
		butNewRemoteFile.setEnabled(connected);
		butNewRemoteDirectory.setEnabled(connected);
		butChangeRemoteDirectory.setEnabled(connected);
		butRenameRemoteFile.setEnabled(connected
				&& remoteFileSelectionCount == 1);
		butDeleteRemoteFile.setEnabled(connected
				&& remoteFileSelectionCount > 0);
	}
}