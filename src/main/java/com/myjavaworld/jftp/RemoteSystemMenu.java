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

import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.KeyStroke;
import javax.swing.event.MenuListener;

import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MMenu;
import com.myjavaworld.gui.MMenuItem;
import com.myjavaworld.jftp.actions.ChangeRemoteDirectoryAction;
import com.myjavaworld.jftp.actions.DeleteRemoteFileAction;
import com.myjavaworld.jftp.actions.EditRemoteFileAction;
import com.myjavaworld.jftp.actions.NewRemoteDirectoryAction;
import com.myjavaworld.jftp.actions.NewRemoteFileAction;
import com.myjavaworld.jftp.actions.OpenRemoteFileAction;
import com.myjavaworld.jftp.actions.PrintRemoteFileAction;
import com.myjavaworld.jftp.actions.RenameRemoteFileAction;
import com.myjavaworld.util.ResourceLoader;

/**
 * Remote system menu
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class RemoteSystemMenu extends MMenu implements MenuListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.RemoteSystemMenu");
	private static final ResourceBundle ftpMenuResources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.FTPMenu");
	private JFTP jftp = null;
	private MMenuItem miFile = null;
	private MMenuItem miDirectory = null;
	private MMenuItem miChangeDirectory = null;
	private MMenuItem miRename = null;
	private MMenuItem miDelete = null;
	private MMenuItem miOpenFile;
	private MMenuItem miEditFile;
	private MMenuItem miPrintFile;
	private MMenu menuNew;
	private MMenu menuExecute;
	private MMenuItem miHelp;
	private MMenuItem miStat;
	private MMenuItem miSyst;
	private MMenuItem miNoop;
	private MMenuItem miOther;
	private MMenuItem miApplyFilter;
	private MMenuItem miClearFilter;
	private MMenuItem miRefresh;
	private MMenuItem miProperties;
	private MMenuItem miSelectAll = null;
	private MMenuItem miInvertSelection = null;

	public RemoteSystemMenu(JFTP jftp) {
		super();
		this.jftp = jftp;
		setText(resources.getString("text.remoteSystem"));
		setMnemonic(resources.getString("mnemonic.remoteSystem"),
				resources.getString("mnemonicIndex.remoteSystem"));
		prepareMenuItems();
		addMenuListener(this);
	}

	private void prepareMenuItems() {

		miOpenFile = new MMenuItem(resources.getString("text.open"));
		miOpenFile.setActionCommand("cmd.openRemoteFile");
		miOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				GUIUtil.ACCELERATOR_MASK));
		miOpenFile.setMnemonic(resources.getString("mnemonic.open"),
				resources.getString("mnemonicIndex.open"));
		miOpenFile.addActionListener(OpenRemoteFileAction.getInstance(jftp));
		add(miOpenFile);

		miEditFile = new MMenuItem(resources.getString("text.edit"));
		miEditFile.setActionCommand("cmd.editRemoteFile");
		miEditFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				GUIUtil.ACCELERATOR_MASK));
		miEditFile.setMnemonic(resources.getString("mnemonic.edit"),
				resources.getString("mnemonicIndex.edit"));
		miEditFile.addActionListener(EditRemoteFileAction.getInstance(jftp));
		add(miEditFile);

		miPrintFile = new MMenuItem(resources.getString("text.print"));
		miPrintFile.setActionCommand("cmd.printRemoteFile");
		miPrintFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				GUIUtil.ACCELERATOR_MASK));
		miPrintFile.setMnemonic(resources.getString("mnemonic.print"),
				resources.getString("mnemonicIndex.print"));
		miPrintFile.addActionListener(PrintRemoteFileAction.getInstance(jftp));
		add(miPrintFile);

		addSeparator();

		menuNew = new MMenu(resources.getString("text.new"), true);
		menuNew.setMnemonic(resources.getString("mnemonic.new"),
				resources.getString("mnemonicIndex.new"));
		add(menuNew);

		miFile = new MMenuItem(resources.getString("text.file"));
		miFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				GUIUtil.ACCELERATOR_MASK));
		miFile.setMnemonic(resources.getString("mnemonic.file"),
				resources.getString("mnemonicIndex.file"));
		miFile.addActionListener(NewRemoteFileAction.getInstance(jftp));
		menuNew.add(miFile);

		miDirectory = new MMenuItem(resources.getString("text.directory"));
		miDirectory.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				GUIUtil.ACCELERATOR_MASK));
		miDirectory.setMnemonic(resources.getString("mnemonic.directory"),
				resources.getString("mnemonicIndex.directory"));
		miDirectory.addActionListener(NewRemoteDirectoryAction
				.getInstance(jftp));
		menuNew.add(miDirectory);

		miChangeDirectory = new MMenuItem(
				resources.getString("text.changeDirectory"));
		miChangeDirectory.setMnemonic(
				resources.getString("mnemonic.changeDirectory"),
				resources.getString("mnemonicIndex.changeDirectory"));
		miChangeDirectory.addActionListener(ChangeRemoteDirectoryAction
				.getInstance(jftp));
		add(miChangeDirectory);

		miRename = new MMenuItem(resources.getString("text.rename"));
		miRename.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				GUIUtil.ACCELERATOR_MASK));
		miRename.setMnemonic(resources.getString("mnemonic.rename"),
				resources.getString("mnemonicIndex.rename"));
		miRename.addActionListener(RenameRemoteFileAction.getInstance(jftp));
		add(miRename);

		miDelete = new MMenuItem(resources.getString("text.delete"));
		miDelete.setAccelerator(KeyStroke.getKeyStroke(GUIUtil.getDeleteKey(),
				GUIUtil.ACCELERATOR_MASK));
		miDelete.setMnemonic(resources.getString("mnemonic.delete"),
				resources.getString("mnemonicIndex.delete"));
		miDelete.addActionListener(DeleteRemoteFileAction.getInstance(jftp));
		add(miDelete);

		addSeparator();

		menuExecute = new MMenu(resources.getString("text.execute"), true);
		menuExecute.setMnemonic(resources.getString("mnemonic.execute"),
				resources.getString("mnemonicIndex.execute"));
		add(menuExecute);

		miHelp = new MMenuItem(resources.getString("text.help"));
		miHelp.setMnemonic(resources.getString("mnemonic.help"),
				resources.getString("mnemonicIndex.help"));
		miHelp.setActionCommand("cmd.help");
		miHelp.addActionListener(jftp);
		menuExecute.add(miHelp);

		miStat = new MMenuItem(resources.getString("text.stat"));
		miStat.setMnemonic(resources.getString("mnemonic.stat"),
				resources.getString("mnemonicIndex.stat"));
		miStat.setActionCommand("cmd.stat");
		miStat.addActionListener(jftp);
		menuExecute.add(miStat);

		miSyst = new MMenuItem(resources.getString("text.syst"));
		miSyst.setMnemonic(resources.getString("mnemonic.syst"),
				resources.getString("mnemonicIndex.syst"));
		miSyst.setActionCommand("cmd.syst");
		miSyst.addActionListener(jftp);
		menuExecute.add(miSyst);

		miNoop = new MMenuItem(resources.getString("text.noop"));
		miNoop.setMnemonic(resources.getString("mnemonic.noop"),
				resources.getString("mnemonicIndex.noop"));
		miNoop.setActionCommand("cmd.noop");
		miNoop.addActionListener(jftp);
		menuExecute.add(miNoop);

		miOther = new MMenuItem(resources.getString("text.other"));
		miOther.setMnemonic(resources.getString("mnemonic.other"),
				resources.getString("mnemonicIndex.other"));
		miOther.setActionCommand("cmd.other");
		miOther.addActionListener(jftp);
		menuExecute.add(miOther);

		addSeparator();

		miApplyFilter = new MMenuItem(resources.getString("text.applyFilter"));
		miApplyFilter.setMnemonic(resources.getString("mnemonic.applyFilter"),
				resources.getString("mnemonicIndex.applyFilter"));
		miApplyFilter.setActionCommand("cmd.applyRemoteFileFilter");
		miApplyFilter.addActionListener(jftp);
		add(miApplyFilter);

		miClearFilter = new MMenuItem(resources.getString("text.clearFilter"));
		miClearFilter.setMnemonic(resources.getString("mnemonic.clearFilter"),
				resources.getString("mnemonicIndex.clearFilter"));
		miApplyFilter.setActionCommand("cmd.applyRemoteFileFilter");
		miClearFilter.setActionCommand("cmd.clearRemoteFileFilter");
		miClearFilter.addActionListener(jftp);
		add(miClearFilter);

		addSeparator();

		miRefresh = new MMenuItem(resources.getString("text.refresh"));
		miRefresh.setMnemonic(resources.getString("mnemonic.refresh"),
				resources.getString("mnemonicIndex.refresh"));
		miRefresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,
				GUIUtil.ACCELERATOR_MASK));
		miRefresh.setActionCommand("cmd.refreshRemotePane");
		miRefresh.addActionListener(jftp);
		add(miRefresh);

		addSeparator();

		miProperties = new MMenuItem(resources.getString("text.properties"));
		miProperties.setMnemonic(resources.getString("mnemonic.properties"),
				resources.getString("mnemonicIndex.properties"));
		miProperties.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				GUIUtil.ACCELERATOR_MASK));
		miProperties.setActionCommand("cmd.remoteFileProperties");
		miProperties.addActionListener(jftp);
		add(miProperties);

		addSeparator();

		miSelectAll = new MMenuItem();
		miSelectAll.setText(resources.getString("text.selectAll"));
		miSelectAll.setMnemonic(resources.getString("mnemonic.selectAll"),
				resources.getString("mnemonicIndex.selectAll"));
		miSelectAll.setActionCommand("cmd.remoteSelectAll");
		miSelectAll.addActionListener(jftp);
		add(miSelectAll);

		miInvertSelection = new MMenuItem();
		miInvertSelection.setText(resources.getString("text.invertSelection"));
		miInvertSelection.setMnemonic(
				resources.getString("mnemonic.invertSelection"),
				resources.getString("mnemonicIndex.invertSelection"));
		miInvertSelection.setActionCommand("cmd.remoteInvertSelection");
		miInvertSelection.addActionListener(jftp);
		add(miInvertSelection);
	}

	public void menuCanceled(javax.swing.event.MenuEvent menuEvent) {
		miOpenFile.setEnabled(true);
		miEditFile.setEnabled(true);
		miPrintFile.setEnabled(true);
		menuNew.setEnabled(true);
		miChangeDirectory.setEnabled(true);
		miRename.setEnabled(true);
		miDelete.setEnabled(true);
		menuExecute.setEnabled(true);
		miApplyFilter.setEnabled(true);
		miClearFilter.setEnabled(true);
		miRefresh.setEnabled(true);
		miProperties.setEnabled(true);
		miSelectAll.setEnabled(true);
		miInvertSelection.setEnabled(true);
	}

	public void menuDeselected(javax.swing.event.MenuEvent menuEvent) {
		miOpenFile.setEnabled(true);
		miEditFile.setEnabled(true);
		miPrintFile.setEnabled(true);
		menuNew.setEnabled(true);
		miChangeDirectory.setEnabled(true);
		miRename.setEnabled(true);
		miDelete.setEnabled(true);
		menuExecute.setEnabled(true);
		miApplyFilter.setEnabled(true);
		miClearFilter.setEnabled(true);
		miRefresh.setEnabled(true);
		miProperties.setEnabled(true);
		miSelectAll.setEnabled(true);
		miInvertSelection.setEnabled(true);
	}

	public void menuSelected(javax.swing.event.MenuEvent menuEvent) {
		FTPSession session = jftp.getCurrentSession();
		boolean nullSession = session == null;
		boolean connected = !nullSession && session.isConnected();
		int selectionCount = 0;
		if (connected) {
			selectionCount = session.getRemoteFileSelectionCount();
		}
		boolean isFile = false;
		RemoteFile selectedFile = null;
		if (selectionCount == 1) {
			selectedFile = session.getSelectedRemoteFile();
			isFile = selectedFile.isFile();
		}
		miOpenFile.setEnabled(connected && selectionCount == 1);
		miEditFile.setEnabled(connected && selectionCount == 1 && isFile);
		miPrintFile.setEnabled(connected && selectionCount == 1 && isFile);
		menuNew.setEnabled(connected);
		miChangeDirectory.setEnabled(connected);
		miRename.setEnabled(connected && selectionCount == 1);
		miDelete.setEnabled(connected && selectionCount > 0);
		menuExecute.setEnabled(connected);
		miApplyFilter.setEnabled(connected);
		miClearFilter.setEnabled(connected
				&& session.getRemoteFileFilter() != null);
		miRefresh.setEnabled(connected);
		miProperties.setEnabled(connected && selectionCount == 1);
		miSelectAll.setEnabled(connected);
		miInvertSelection.setEnabled(connected);
	}

	private boolean isZipFile(RemoteFile file) {
		String ext = file.getExtension();
		return "ZIP".equalsIgnoreCase(ext);
	}
}