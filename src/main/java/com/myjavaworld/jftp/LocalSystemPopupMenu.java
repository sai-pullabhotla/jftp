/*
 * Copyright 2012 jMethods, Inc. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.myjavaworld.jftp;

import java.awt.Desktop;
import java.util.ResourceBundle;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.myjavaworld.gui.MMenu;
import com.myjavaworld.gui.MMenuItem;
import com.myjavaworld.gui.MPopupMenu;
import com.myjavaworld.jftp.actions.ChangeLocalDirectoryAction;
import com.myjavaworld.jftp.actions.DeleteLocalFileAction;
import com.myjavaworld.jftp.actions.EditLocalFileAction;
import com.myjavaworld.jftp.actions.NewLocalDirectoryAction;
import com.myjavaworld.jftp.actions.NewLocalFileAction;
import com.myjavaworld.jftp.actions.OpenLocalFileAction;
import com.myjavaworld.jftp.actions.PrintLocalFileAction;
import com.myjavaworld.jftp.actions.RenameLocalFileAction;
import com.myjavaworld.jftp.actions.UploadAction;
import com.myjavaworld.jftp.actions.UploadAsAction;
import com.myjavaworld.jftp.actions.ZipAndUploadAction;
import com.myjavaworld.util.ResourceLoader;

/**
 * Local System Menu.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class LocalSystemPopupMenu extends MPopupMenu implements
		PopupMenuListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.LocalSystemMenu");
	private static final ResourceBundle ftpMenuResources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.FTPMenu");
	private JFTP jftp = null;
	private MMenuItem miUpload = null;
	private MMenuItem miUploadAs = null;
	private MMenuItem miZipAndUpload = null;
	private MMenuItem miOpenFile = null;
	private MMenuItem miEditFile = null;
	private MMenuItem miPrintFile = null;
	private MMenu menuNew = null;
	private MMenuItem miFile = null;
	private MMenuItem miDirectory = null;
	private MMenuItem miChangeDirectory = null;
	private MMenuItem miRename = null;
	private MMenuItem miDelete = null;
	private MMenuItem miApplyFilter = null;
	private MMenuItem miClearFilter = null;
	private MMenuItem miRefresh = null;
	private MMenuItem miProperties = null;
	private MMenuItem miSelectAll = null;
	private MMenuItem miInvertSelection = null;
	private static LocalSystemPopupMenu instance = null;
	private boolean openSupported = false;
	private boolean editSupported = false;
	private boolean printSupported = false;

	private LocalSystemPopupMenu(JFTP jftp) {
		super();
		this.jftp = jftp;
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			openSupported = desktop.isSupported(Desktop.Action.OPEN);
			editSupported = desktop.isSupported(Desktop.Action.EDIT);
			printSupported = desktop.isSupported(Desktop.Action.PRINT);
		}
		preparePopupMenu();
		addPopupMenuListener(this);
	}

	public static LocalSystemPopupMenu getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new LocalSystemPopupMenu(jftp);
			}
		}
		return instance;
	}

	private void preparePopupMenu() {
		miUpload = new MMenuItem();
		miUpload.setText(ftpMenuResources.getString("text.upload"));
		miUpload.addActionListener(UploadAction.getInstance(jftp));
		add(miUpload);

		miUploadAs = new MMenuItem(ftpMenuResources.getString("text.uploadAs"));
		miUploadAs.addActionListener(UploadAsAction.getInstance(jftp));
		add(miUploadAs);

		miZipAndUpload = new MMenuItem();
		miZipAndUpload.setText(ftpMenuResources.getString("text.zipAndUpload"));
		miZipAndUpload.setMnemonic(
				ftpMenuResources.getString("mnemonic.zipAndUpload"),
				ftpMenuResources.getString("mnemonicIndex.zipAndUpload"));
		miZipAndUpload.addActionListener(ZipAndUploadAction.getInstance(jftp));
		add(miZipAndUpload);

		addSeparator();

		miOpenFile = new MMenuItem(resources.getString("text.open"));
		miOpenFile.setActionCommand("cmd.openLocalFile");
		miOpenFile.addActionListener(OpenLocalFileAction.getInstance(jftp));
		add(miOpenFile);

		miEditFile = new MMenuItem(resources.getString("text.edit"));
		miEditFile.setActionCommand("cmd.editLocalFile");
		miEditFile.addActionListener(EditLocalFileAction.getInstance(jftp));
		add(miEditFile);

		miPrintFile = new MMenuItem(resources.getString("text.print"));
		miPrintFile.setActionCommand("cmd.printLocalFile");
		miPrintFile.addActionListener(PrintLocalFileAction.getInstance(jftp));
		add(miPrintFile);

		addSeparator();

		menuNew = new MMenu(resources.getString("text.new"), true);
		add(menuNew);

		miFile = new MMenuItem();
		miFile.setText(resources.getString("text.file"));
		miFile.addActionListener(NewLocalFileAction.getInstance(jftp));
		menuNew.add(miFile);

		miDirectory = new MMenuItem();
		miDirectory.setText(resources.getString("text.directory"));
		miDirectory
				.addActionListener(NewLocalDirectoryAction.getInstance(jftp));
		menuNew.add(miDirectory);

		miChangeDirectory = new MMenuItem();
		miChangeDirectory.setText(resources.getString("text.changeDirectory"));
		miChangeDirectory.addActionListener(ChangeLocalDirectoryAction
				.getInstance(jftp));
		add(miChangeDirectory);

		miRename = new MMenuItem();
		miRename.setText(resources.getString("text.rename"));
		miRename.addActionListener(RenameLocalFileAction.getInstance(jftp));
		add(miRename);

		miDelete = new MMenuItem();
		miDelete.setText(resources.getString("text.delete"));
		miDelete.addActionListener(DeleteLocalFileAction.getInstance(jftp));
		add(miDelete);

		addSeparator();

		miApplyFilter = new MMenuItem(resources.getString("text.applyFilter"));
		miApplyFilter.setActionCommand("cmd.applyLocalFileFilter");
		miApplyFilter.addActionListener(jftp);
		add(miApplyFilter);

		miClearFilter = new MMenuItem(resources.getString("text.clearFilter"));
		miClearFilter.setActionCommand("cmd.clearLocalFileFilter");
		miClearFilter.addActionListener(jftp);
		add(miClearFilter);

		addSeparator();

		miRefresh = new MMenuItem(resources.getString("text.refresh"));
		miRefresh.setActionCommand("cmd.refreshLocalPane");
		miRefresh.addActionListener(jftp);
		add(miRefresh);

		addSeparator();

		miProperties = new MMenuItem(resources.getString("text.properties"));
		miProperties.setActionCommand("cmd.localFileProperties");
		miProperties.addActionListener(jftp);
		add(miProperties);

		addSeparator();

		miSelectAll = new MMenuItem();
		miSelectAll.setText(resources.getString("text.selectAll"));
		miSelectAll.setMnemonic(resources.getString("mnemonic.selectAll"),
				resources.getString("mnemonicIndex.selectAll"));
		miSelectAll.setActionCommand("cmd.localSelectAll");
		miSelectAll.addActionListener(jftp);
		add(miSelectAll);

		miInvertSelection = new MMenuItem();
		miInvertSelection.setText(resources.getString("text.invertSelection"));
		miInvertSelection.setMnemonic(
				resources.getString("mnemonic.invertSelection"),
				resources.getString("mnemonicIndex.invertSelection"));
		miInvertSelection.setActionCommand("cmd.localInvertSelection");
		miInvertSelection.addActionListener(jftp);
		add(miInvertSelection);

		miUpload.setMnemonic(ftpMenuResources.getString("mnemonic.upload"),
				ftpMenuResources.getString("mnemonicIndex.upload"));
		miUploadAs.setMnemonic(ftpMenuResources.getString("mnemonic.uploadAs"),
				ftpMenuResources.getString("mnemonicIndex.uploadAs"));
		miOpenFile.setMnemonic(resources.getString("mnemonic.open"),
				resources.getString("mnemonicIndex.open"));
		miEditFile.setMnemonic(resources.getString("mnemonic.edit"),
				resources.getString("mnemonicIndex.edit"));
		miPrintFile.setMnemonic(resources.getString("mnemonic.print"),
				resources.getString("mnemonicIndex.print"));
		menuNew.setMnemonic(resources.getString("mnemonic.new"),
				resources.getString("mnemonicIndex.new"));
		miFile.setMnemonic(resources.getString("mnemonic.file"),
				resources.getString("mnemonicIndex.file"));
		miDirectory.setMnemonic(resources.getString("mnemonic.directory"),
				resources.getString("mnemonicIndex.directory"));
		miChangeDirectory.setMnemonic(
				resources.getString("mnemonic.changeDirectory"),
				resources.getString("mnemonicIndex.changeDirectory"));
		miRename.setMnemonic(resources.getString("mnemonic.rename"),
				resources.getString("mnemonicIndex.rename"));
		miDelete.setMnemonic(resources.getString("mnemonic.delete"),
				resources.getString("mnemonicIndex.delete"));
		miApplyFilter.setMnemonic(resources.getString("mnemonic.applyFilter"),
				resources.getString("mnemonicIndex.applyFilter"));
		miClearFilter.setMnemonic(resources.getString("mnemonic.clearFilter"),
				resources.getString("mnemonicIndex.clearFilter"));
		miRefresh.setMnemonic(resources.getString("mnemonic.refresh"),
				resources.getString("mnemonicIndex.refresh"));
		miProperties.setMnemonic(resources.getString("mnemonic.properties"),
				resources.getString("mnemonicIndex.properties"));
	}

	public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {
	}

	public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {
	}

	public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
		FTPSession session = jftp.getCurrentSession();
		boolean nullSession = session == null;
		boolean connected = !nullSession && session.isConnected();
		int selectionCount = 0;
		boolean isFile = false;
		if (!nullSession) {
			selectionCount = session.getLocalFileSelectionCount();
			if (selectionCount == 1) {
				LocalFile selectedFile = session.getSelectedLocalFile();
				isFile = selectedFile.isFile();
			}
		}

		miUpload.setEnabled(connected && selectionCount > 0);
		miUploadAs.setEnabled(connected && selectionCount == 1);
		miZipAndUpload.setEnabled(connected && selectionCount > 0);
		if (isFile) {
			miOpenFile.setEnabled(selectionCount == 1 && openSupported);
		} else {
			miOpenFile.setEnabled(selectionCount == 1);
		}
		miEditFile.setEnabled(selectionCount == 1 && isFile && editSupported);
		miPrintFile.setEnabled(selectionCount == 1 && isFile && printSupported);
		miRename.setEnabled(selectionCount == 1);
		miDelete.setEnabled(selectionCount > 0);
		miClearFilter.setEnabled(session.getLocalFileFilter() != null);
		miProperties.setEnabled(selectionCount == 1);
	}
}