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