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

import java.awt.Image;
import java.awt.Toolkit;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * An utility class
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class JFTPUtil {

	public static Icon getIcon(String name) {
		return new ImageIcon(JFTPUtil.class.getResource(name));
	}

	public static Image getImage(String name) {
		return Toolkit.getDefaultToolkit().getImage(
				JFTPUtil.class.getResource(name));
	}

	public static String getTimeString(int seconds) {
		int ss = seconds % 60;
		int mm = seconds / 60;
		int hh = 0;
		if (mm > 59) {
			hh = mm / 60;
			mm %= 60;
		}
		StringBuffer buffer = new StringBuffer(8);
		if (hh < 10) {
			buffer.append("0");
		}
		buffer.append(hh);
		buffer.append(":");
		if (mm < 10) {
			buffer.append("0");
		}
		buffer.append(mm);
		buffer.append(":");
		if (ss < 10) {
			buffer.append("0");
		}
		buffer.append(ss);
		return buffer.toString();
	}

	public static void updateProxySettings() {
		boolean useProxy = JFTP.prefs.isUseProxy();
		if (useProxy) {
			String proxyHost = JFTP.prefs.getProxyHost();
			int proxyPort = JFTP.prefs.getProxyPort();
			final String proxyUser = JFTP.prefs.getProxyUser();
			final char[] proxyPassword = JFTP.prefs.getProxyPassword();

			Properties props = System.getProperties();
			props.setProperty("socksProxyHost", proxyHost);
			props.setProperty("socksProxyPort", String.valueOf(proxyPort));
			Authenticator authenticator = new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(proxyUser, proxyPassword);
				}
			};
			Authenticator.setDefault(authenticator);
		} else {
			Properties props = System.getProperties();
			props.remove("socksProxyHost");
			props.remove("socksProxyPort");
			Authenticator.setDefault(null);
		}
	}
}