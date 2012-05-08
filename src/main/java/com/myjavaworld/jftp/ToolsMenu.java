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

import java.util.ResourceBundle;

import javax.swing.event.MenuListener;

import com.myjavaworld.gui.MMenu;
import com.myjavaworld.gui.MMenuItem;
import com.myjavaworld.jftp.actions.ManageCertificatesAction;
import com.myjavaworld.jftp.actions.ManageFavoritesAction;
import com.myjavaworld.util.ResourceLoader;
import com.myjavaworld.util.SystemUtil;

/**
 * Tools menu
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class ToolsMenu extends MMenu implements MenuListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.ToolsMenu");
	private JFTP jftp = null;
	private MMenuItem miAddToFavorites = null;
	private MMenuItem miManageFavorites = null;
	private MMenuItem miManageCertificates = null;
	private MMenuItem miPreferences = null;

	public ToolsMenu(JFTP jftp) {
		super();
		this.jftp = jftp;
		setText(resources.getString("text.tools"));
		setMnemonic(resources.getString("mnemonic.tools"),
				resources.getString("mnemonicIndex.tools"));
		prepareMenuItems();
		addMenuListener(this);
	}

	private void prepareMenuItems() {

		miAddToFavorites = new MMenuItem();
		miAddToFavorites.setText(resources.getString("text.addToFavorites"));
		miAddToFavorites.setActionCommand("cmd.addToFavorites");
		miAddToFavorites.addActionListener(jftp);
		miAddToFavorites.setMnemonic(
				resources.getString("mnemonic.addToFavorites"),
				resources.getString("mnemonicIndex.addToFavorites"));
		add(miAddToFavorites);

		miManageFavorites = new MMenuItem();
		miManageFavorites.setText(resources.getString("text.manageFavorites"));
		miManageFavorites.addActionListener(ManageFavoritesAction
				.getInstance(jftp));
		miManageFavorites.setMnemonic(
				resources.getString("mnemonic.manageFavorites"),
				resources.getString("mnemonicIndex.manageFavorites"));
		add(miManageFavorites);

		addSeparator();

		miManageCertificates = new MMenuItem();
		miManageCertificates.setText(resources
				.getString("text.manageCertificates"));
		miManageCertificates.addActionListener(ManageCertificatesAction
				.getInstance(jftp));
		miManageCertificates.setMnemonic(
				resources.getString("mnemonic.manageCertificates"),
				resources.getString("mnemonicIndex.manageCertificates"));
		add(miManageCertificates);

		miPreferences = new MMenuItem(resources.getString("text.preferences"));
		miPreferences.setActionCommand("cmd.preferences");
		miPreferences.setMnemonic(resources.getString("mnemonic.preferences"),
				resources.getString("mnemonicIndex.preferences"));
		miPreferences.addActionListener(jftp);

		if (!SystemUtil.isMac()) {
			addSeparator();

			add(miPreferences);
		}
	}

	public void menuCanceled(javax.swing.event.MenuEvent menuEvent) {
	}

	public void menuDeselected(javax.swing.event.MenuEvent menuEvent) {
	}

	public void menuSelected(javax.swing.event.MenuEvent menuEvent) {
		FTPSession currentSession = jftp.getCurrentSession();
		getMenuComponent(0).setEnabled(
				(currentSession != null && currentSession.isConnected()));
	}
}