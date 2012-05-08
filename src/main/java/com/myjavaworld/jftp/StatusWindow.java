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