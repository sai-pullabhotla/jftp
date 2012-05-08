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

import java.util.Locale;

import javax.swing.UIManager;

import com.myjavaworld.util.SystemUtil;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 1.0
 */
public class JFTPApplication {

	private JFTP jftp = null;

	public JFTPApplication() {
		if (SystemUtil.isMac()) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty(
					"com.apple.mrj.application.apple.menu.about.name", "JFTP");
			registerForMacOSXEvents();
		}
		Locale.setDefault(JFTP.prefs.getLocale());
		try {
			UIManager.setLookAndFeel(JFTP.prefs.getLookAndFeelClassName());
		} catch (Exception exp) {
		}

		// SplashWindow splash = new
		// SplashWindow(JFTPUtil.getIcon("splash.gif"));
		// splash.setVisible(true);
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		//
		// splash.setVisible(false);
		jftp = new JFTP();

		if (SystemUtil.isMac()) {
			OSXAdapter.enableFullScreenMode(jftp);
		}

		jftp.setVisible(true);
		jftp.newSession();
	}

	public static void main(String args[]) {
		new JFTPApplication();
	}

	public void showAboutDialog() {
		if (jftp != null) {
			jftp.showAboutDialog();
		}
	}

	public void showPreferencesDialog() {
		if (jftp != null) {
			jftp.showPreferencesDialog();
		}
	}

	public void quit() {
		if (jftp != null) {
			jftp.exit();
		}
	}

	private void registerForMacOSXEvents() {
		OSXAdapter.init(this);
	}

}