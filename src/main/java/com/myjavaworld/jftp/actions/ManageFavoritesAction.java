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
package com.myjavaworld.jftp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.myjavaworld.jftp.FavoritesDlg;
import com.myjavaworld.jftp.JFTP;

/**
 * An action implementation for Connecting to an FTP server.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class ManageFavoritesAction extends AbstractAction {

	private JFTP jftp = null;
	private static ManageFavoritesAction instance = null;

	private ManageFavoritesAction(JFTP jftp) {
		this.jftp = jftp;
	}

	public static synchronized ManageFavoritesAction getInstance(JFTP jftp) {
		if (instance == null) {
			if (instance == null) {
				instance = new ManageFavoritesAction(jftp);
			}
		}
		return instance;
	}

	public void actionPerformed(ActionEvent evt) {
		FavoritesDlg dlg = new FavoritesDlg(jftp);
		dlg.setLocationRelativeTo(jftp);
		dlg.setVisible(true);
		dlg.dispose();
	}
}
