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
package com.myjavaworld.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 * An extension of <code>PlainDocument</code> that allows only one line of text
 * in the document at any given time. The purpose of this class is to use it
 * with <code>JTextField</code> and its extensions so that they will not allow
 * multiple lines of text. For E.g. The <code>javax.swing.JTextField
 * </code> does not work quite well when multiple lines of text is pasted in to
 * it.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 1.0
 * 
 */
public class SingleLineDocument extends MPlainDocument {

	/**
	 * Default Constructor.
	 * 
	 */
	public SingleLineDocument() {
		super();
	}

	/**
	 * Inserts the given string, <code>str</code> at the given position,
	 * <code> offset</code>. The implementation of this method ignores all the
	 * text that appears after a new line character ( <code>\r</code> or
	 * <code>\n</code>).
	 * 
	 */
	@Override
	public void insertString(int offset, String str, AttributeSet a)
			throws BadLocationException {
		if (str == null) {
			return;
		}
		int length = str.length();
		for (int i = 0; i < length; i++) {
			char ch = str.charAt(i);
			if (ch == '\r' || ch == '\n') {
				str = str.substring(0, i);
				break;
			}
		}
		super.insertString(offset, str, a);
	}
}
