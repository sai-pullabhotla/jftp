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
