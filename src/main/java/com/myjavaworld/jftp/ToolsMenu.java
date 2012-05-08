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