/*
 * Created on Apr 14, 2003
 * Created by Sai Pullabhotla 
 *
 * This software is the confidential and proprietary information of the 
 * author, Sai Pullabhotla. You shall not disclose such Confidential 
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with the author.
 *
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF 
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */
package com.myjavaworld.jftp;

import java.util.Locale;

import javax.swing.UIManager;

import com.myjavaworld.gui.SplashWindow;
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

		SplashWindow splash = new SplashWindow(JFTPUtil.getIcon("splash.gif"));
		splash.setVisible(true);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		splash.setVisible(false);
		jftp = new JFTP();

		if (SystemUtil.isMac()) {
			OSXAdapter2.enableFullScreenMode(jftp);
		}

		jftp.setVisible(true);
		jftp.newSession();
	}

	public static void main(String args[]) {
		new JFTPApplication();
	}

	public void showAboutDialog() {
		jftp.showAboutDialog();
	}

	public void showPreferencesDialog() {
		jftp.showPreferencesDialog();
	}

	public void quit() {
		jftp.exit();
	}

	private void registerForMacOSXEvents() {
		OSXAdapter2.init(this);
	}

}