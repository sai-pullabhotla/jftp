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

import java.awt.Color;
import java.awt.Insets;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.myjavaworld.util.ResourceLoader;

/**
 * Status window used by FTP Sessions to display the commands and responses.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class StatusWindow extends JTextPane {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.StatusWindow");
	public static final int MAX_SIZE = 16 * 1024;
	private Document document = null;
	private Style normalStyle = null;
	private Style commandStyle = null;
	private Style replyStyle = null;
	private Style errorStyle = null;
	private Style statusStyle = null;
	private Style infoStyle = null;

	public StatusWindow() {
		super();
		setMargin(new Insets(6, 6, 6, 6));
		document = getDocument();
		this.setEditable(false);
		initStyles();
		MessageFormat format = new MessageFormat(
				resources.getString("text.productInfo"));
		Object[] args = { JFTPConstants.PRODUCT_NAME,
				JFTPConstants.PRODUCT_VERSION, JFTPConstants.PRODUCT_BUILD };
		addInfo(format.format(args));
	}

	public void addNormal(String str) {
		append(str, normalStyle);
	}

	public void addCommand(String str) {
		append(str, commandStyle);
	}

	public void addReply(String str) {
		append(str, replyStyle);
	}

	public void addError(String str) {
		append(str, errorStyle);
	}

	public void addStatus(String str) {
		append(str, statusStyle);
	}

	public void addInfo(String str) {
		append(str, infoStyle);
	}

	private synchronized void append(String str, Style style) {
		try {
			if (document.getLength() >= MAX_SIZE) {
				document.remove(0, document.getLength());
			}
			document.insertString(document.getLength(), str, style);
			document.insertString(document.getLength(), "\n", style);
			this.setCaretPosition(document.getLength());
		} catch (BadLocationException exp) {
		}
	}

	private void initStyles() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(
				StyleContext.DEFAULT_STYLE);

		normalStyle = addStyle("normalStyle", def);
		StyleConstants.setFontFamily(def, "Dailog");

		commandStyle = addStyle("commandStyle", normalStyle);
		StyleConstants.setForeground(commandStyle, new Color(0, 0, 255));

		replyStyle = addStyle("replyStyle", normalStyle);
		StyleConstants.setForeground(replyStyle, new Color(0, 102, 51));

		errorStyle = addStyle("errorStyle", normalStyle);
		StyleConstants.setForeground(errorStyle, new Color(255, 0, 0));

		statusStyle = addStyle("statusStyle", normalStyle);
		StyleConstants.setForeground(statusStyle, new Color(255, 128, 0));

		infoStyle = addStyle("infoStyle", normalStyle);
	}
}