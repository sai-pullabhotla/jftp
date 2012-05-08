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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MMenu;
import com.myjavaworld.gui.MMenuItem;
import com.myjavaworld.jftp.actions.ChangeLocalDirectoryAction;
import com.myjavaworld.jftp.actions.DeleteLocalFileAction;
import com.myjavaworld.jftp.actions.EditLocalFileAction;
import com.myjavaworld.jftp.actions.NewLocalDirectoryAction;
import com.myjavaworld.jftp.actions.NewLocalFileAction;
import com.myjavaworld.jftp.actions.OpenLocalFileAction;
import com.myjavaworld.jftp.actions.PrintLocalFileAction;
import com.myjavaworld.jftp.actions.RenameLocalFileAction;
import com.myjavaworld.util.ResourceLoader;

/**
 * Local System Menu.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class LocalSystemMenu extends MMenu implements MenuListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.LocalSystemMenu");
	private static final ResourceBundle ftpMenuResources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.FTPMenu");
	private JFTP jftp = null;
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
	private boolean openSupported = false;
	private boolean editSupported = false;
	private boolean printSupported = false;

	public LocalSystemMenu(JFTP jftp) {
		super();
		this.jftp = jftp;
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			openSupported = desktop.isSupported(Desktop.Action.OPEN);
			editSupported = desktop.isSupported(Desktop.Action.EDIT);
			printSupported = desktop.isSupported(Desktop.Action.PRINT);
		}
		setText(resources.getString("text.localSystem"));
		setMnemonic(resources.getString("mnemonic.localSystem"),
				resources.getString("mnemonicIndex.localSystem"));
		prepareMenuItems();
		addMenuListener(this);
	}

	private void prepareMenuItems() {

		miOpenFile = new MMenuItem();
		miOpenFile.setText(resources.getString("text.open"));
		miOpenFile.setActionCommand("cmd.openLocalFile");
		miOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				GUIUtil.ACCELERATOR_MASK + InputEvent.SHIFT_MASK));
		miOpenFile.setMnemonic(resources.getString("mnemonic.open"),
				resources.getString("mnemonicIndex.open"));
		miOpenFile.addActionListener(OpenLocalFileAction.getInstance(jftp));
		add(miOpenFile);

		miEditFile = new MMenuItem();
		miEditFile.setText(resources.getString("text.edit"));
		miEditFile.setActionCommand("cmd.editLocalFile");
		miEditFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				GUIUtil.ACCELERATOR_MASK + InputEvent.SHIFT_MASK));
		miEditFile.setMnemonic(resources.getString("mnemonic.edit"),
				resources.getString("mnemonicIndex.edit"));
		miEditFile.addActionListener(EditLocalFileAction.getInstance(jftp));
		add(miEditFile);

		miPrintFile = new MMenuItem();
		miPrintFile.setText(resources.getString("text.print"));
		miPrintFile.setActionCommand("cmd.printLocalFile");
		miPrintFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				GUIUtil.ACCELERATOR_MASK + InputEvent.SHIFT_MASK));
		miPrintFile.setMnemonic(resources.getString("mnemonic.print"),
				resources.getString("mnemonicIndex.print"));
		miPrintFile.addActionListener(PrintLocalFileAction.getInstance(jftp));
		add(miPrintFile);

		addSeparator();

		menuNew = new MMenu(resources.getString("text.new"), true);
		menuNew.setMnemonic(resources.getString("mnemonic.new"),
				resources.getString("mnemonicIndex.new"));
		add(menuNew);

		miFile = new MMenuItem();
		miFile.setText(resources.getString("text.file"));
		miFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				GUIUtil.ACCELERATOR_MASK + InputEvent.SHIFT_MASK));
		miFile.setMnemonic(resources.getString("mnemonic.file"),
				resources.getString("mnemonicIndex.file"));
		miFile.addActionListener(NewLocalFileAction.getInstance(jftp));
		menuNew.add(miFile);

		miDirectory = new MMenuItem();
		miDirectory.setText(resources.getString("text.directory"));
		miDirectory.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				GUIUtil.ACCELERATOR_MASK + InputEvent.SHIFT_MASK));
		miDirectory.setMnemonic(resources.getString("mnemonic.directory"),
				resources.getString("mnemonicIndex.directory"));
		miDirectory
				.addActionListener(NewLocalDirectoryAction.getInstance(jftp));
		menuNew.add(miDirectory);

		miChangeDirectory = new MMenuItem();
		miChangeDirectory.setText(resources.getString("text.changeDirectory"));
		miChangeDirectory.setMnemonic(
				resources.getString("mnemonic.changeDirectory"),
				resources.getString("mnemonicIndex.changeDirectory"));
		miChangeDirectory.addActionListener(ChangeLocalDirectoryAction
				.getInstance(jftp));
		add(miChangeDirectory);

		miRename = new MMenuItem();
		miRename.setText(resources.getString("text.rename"));
		miRename.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				GUIUtil.ACCELERATOR_MASK + InputEvent.SHIFT_MASK));
		miRename.setMnemonic(resources.getString("mnemonic.rename"),
				resources.getString("mnemonicIndex.rename"));
		miRename.addActionListener(RenameLocalFileAction.getInstance(jftp));
		add(miRename);

		miDelete = new MMenuItem();
		miDelete.setText(resources.getString("text.delete"));
		miDelete.setAccelerator(KeyStroke.getKeyStroke(GUIUtil.getDeleteKey(),
				GUIUtil.ACCELERATOR_MASK + InputEvent.SHIFT_MASK));
		miDelete.setMnemonic(resources.getString("mnemonic.delete"),
				resources.getString("mnemonicIndex.delete"));
		miDelete.addActionListener(DeleteLocalFileAction.getInstance(jftp));
		add(miDelete);

		addSeparator();

		miApplyFilter = new MMenuItem();
		miApplyFilter.setText(resources.getString("text.applyFilter"));
		miApplyFilter.setMnemonic(resources.getString("mnemonic.applyFilter"),
				resources.getString("mnemonicIndex.applyFilter"));
		miApplyFilter.setActionCommand("cmd.applyLocalFileFilter");
		miApplyFilter.addActionListener(jftp);
		add(miApplyFilter);

		miClearFilter = new MMenuItem();
		miClearFilter.setText(resources.getString("text.clearFilter"));
		miClearFilter.setMnemonic(resources.getString("mnemonic.clearFilter"),
				resources.getString("mnemonicIndex.clearFilter"));
		miClearFilter.setActionCommand("cmd.clearLocalFileFilter");
		miClearFilter.addActionListener(jftp);
		add(miClearFilter);

		addSeparator();

		miRefresh = new MMenuItem();
		miRefresh.setText(resources.getString("text.refresh"));
		miRefresh.setMnemonic(resources.getString("mnemonic.refresh"),
				resources.getString("mnemonicIndex.refresh"));
		miRefresh.setActionCommand("cmd.refreshLocalPane");
		miRefresh.addActionListener(jftp);
		miRefresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,
				GUIUtil.ACCELERATOR_MASK + InputEvent.SHIFT_MASK));
		add(miRefresh);

		addSeparator();

		miProperties = new MMenuItem();
		miProperties.setText(resources.getString("text.properties"));
		miProperties.setMnemonic(resources.getString("mnemonic.properties"),
				resources.getString("mnemonicIndex.properties"));
		miProperties.setActionCommand("cmd.localFileProperties");
		miProperties.addActionListener(jftp);
		miProperties.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				GUIUtil.ACCELERATOR_MASK + InputEvent.SHIFT_MASK));
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
	}

	public void menuCanceled(MenuEvent menuEvent) {
		miOpenFile.setEnabled(true);
		miEditFile.setEnabled(true);
		miPrintFile.setEnabled(true);
		menuNew.setEnabled(true);
		miChangeDirectory.setEnabled(true);
		miRename.setEnabled(true);
		miDelete.setEnabled(true);
		miApplyFilter.setEnabled(true);
		miClearFilter.setEnabled(true);
		miRefresh.setEnabled(true);
		miProperties.setEnabled(true);
		miSelectAll.setEnabled(true);
		miInvertSelection.setEnabled(true);
	}

	public void menuDeselected(MenuEvent menuEvent) {
		miOpenFile.setEnabled(true);
		miEditFile.setEnabled(true);
		miPrintFile.setEnabled(true);
		menuNew.setEnabled(true);
		miChangeDirectory.setEnabled(true);
		miRename.setEnabled(true);
		miDelete.setEnabled(true);
		miApplyFilter.setEnabled(true);
		miClearFilter.setEnabled(true);
		miRefresh.setEnabled(true);
		miProperties.setEnabled(true);
		miSelectAll.setEnabled(true);
		miInvertSelection.setEnabled(true);
	}

	public void menuSelected(MenuEvent menuEvent) {
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

		if (isFile) {
			miOpenFile.setEnabled(!nullSession && selectionCount == 1
					&& openSupported);
		} else {
			miOpenFile.setEnabled(!nullSession && selectionCount == 1);
		}
		miEditFile.setEnabled(!nullSession && selectionCount == 1 && isFile
				&& editSupported);
		miPrintFile.setEnabled(!nullSession && selectionCount == 1 && isFile
				&& printSupported);
		menuNew.setEnabled(!nullSession);
		miChangeDirectory.setEnabled(!nullSession);
		miRename.setEnabled(!nullSession && selectionCount == 1);
		miDelete.setEnabled(!nullSession && selectionCount > 0);
		miApplyFilter.setEnabled(!nullSession);
		miClearFilter.setEnabled(!nullSession
				&& session.getLocalFileFilter() != null);
		miRefresh.setEnabled(!nullSession);
		miProperties.setEnabled(!nullSession && selectionCount == 1);
		miSelectAll.setEnabled(!nullSession);
		miInvertSelection.setEnabled(!nullSession);
	}
}