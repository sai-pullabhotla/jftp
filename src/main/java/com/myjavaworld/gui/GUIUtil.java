/**
 * This software is the confidential and proprietary information of the author,
 * Sai Pullabhotla. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with the author. THE AUTHOR MAKES NO REPRESENTATIONS OR
 * WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. THE AUTHOR SHALL NOT
 * BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
 * MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.myjavaworld.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.SystemUtil;

/**
 * An utility class for user interfaces related things.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class GUIUtil {

	public static final int ACCELERATOR_MASK = Toolkit.getDefaultToolkit()
			.getMenuShortcutKeyMask();

	/**
	 * Gives the center point relative to the user's screen, given the size of
	 * some component.
	 * 
	 * @param size
	 *            Size of the component that is to be centered.
	 * @return Location of the component which makes the component to display in
	 *         the center of the screen.
	 */
	public static Point getCenterPointRelativeToScreen(Dimension size) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Point((screenSize.width - size.width) / 2,
				(screenSize.height - size.height) / 2);
	}

	public static boolean isSystemLookAndFeel() {
		String systemLookAndFeelClassName = UIManager
				.getSystemLookAndFeelClassName();
		String currentLookAndFeelClassName = UIManager.getLookAndFeel()
				.getClass().getName();
		return systemLookAndFeelClassName.equals(currentLookAndFeelClassName);
	}

	public static KeyStroke getHelpKeyStroke() {
		if (SystemUtil.isMac() && isSystemLookAndFeel()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, ACCELERATOR_MASK
					+ InputEvent.SHIFT_MASK);
		}
		return KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
	}

	public static int getDeleteKey() {
		return SystemUtil.isMac() ? KeyEvent.VK_BACK_SPACE : KeyEvent.VK_DELETE;
	}

	public static void showInformation(Component parent, String info) {
		showInformation(parent, CommonResources.getString("title.info"), info,
				false);
	}

	public static void showInformation(Component parent, String info,
			boolean format) {
		showInformation(parent, CommonResources.getString("title.info"), info,
				format);
	}

	public static void showInformation(Component parent, String title,
			String info) {
		showInformation(parent, title, info, false);
	}

	public static void showInformation(Component parent, String title,
			String info, boolean format) {
		if (format) {
			info = htmlFormat(info);
		}
		JOptionPane.showMessageDialog(parent, info, title,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static int showConfirmation(Component parent, String message) {
		return showConfirmation(parent,
				CommonResources.getString("title.confirm"), message, false);
	}

	public static int showConfirmation(Component parent, String message,
			boolean format) {
		return showConfirmation(parent,
				CommonResources.getString("title.confirm"), message, format);
	}

	public static int showConfirmation(Component parent, String title,
			String message) {
		return showConfirmation(parent, title, message, false);
	}

	public static int showConfirmation(Component parent, String title,
			String message, boolean format) {
		if (format) {
			message = htmlFormat(message);
		}
		return JOptionPane.showConfirmDialog(parent, message, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	public static void showError(Component parent, String error) {
		showError(parent, CommonResources.getString("title.error"), error,
				false);
	}

	public static void showError(Component parent, String title, String error) {
		showError(parent, title, error, false);
	}

	public static void showError(Component parent, String title, String error,
			boolean format) {
		if (format) {
			error = htmlFormat(error);
		}
		JOptionPane.showMessageDialog(parent, error, title,
				JOptionPane.ERROR_MESSAGE);
	}

	public static void showError(Component parent, Throwable t) {
		showError(parent, CommonResources.getString("title.error"), t);
	}

	public static void showError(Component parent, String title, Throwable t) {
		MessageFormat mf = new MessageFormat(
				CommonResources.getString("error.exception"));
		showError(parent, title, mf.format(new Object[] { t }), true);
		t.printStackTrace();
	}

	public static String htmlFormat(String input) {
		input = input.replaceAll("\n", "<br>");
		input = input.replaceAll("\t",
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		return "<html><body><table width=\"500\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td>"
				+ input + "</td></tr></table></body></html>";
	}
}