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

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * An extension of <code>javax.swing.text.PlainDocument</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 1.0
 * 
 */
public class MPlainDocument extends PlainDocument {

	private int maximumLength = 0;
	private int characterCase = 0;

	/**
	 * Created an instance of <code>MPlainDocument</code>.
	 * 
	 */
	public MPlainDocument() {
		super();
		this.maximumLength = Integer.MAX_VALUE;
		this.characterCase = MTextComponent.MIXED_CASE;
	}

	/**
	 * Sets the maximum length of this document to the given
	 * <code>maximumLength</code>.
	 * 
	 * @param maximumLength
	 *            Maximum number of characters to allow
	 * 
	 */
	public void setMaximumLength(int maximumLength) {
		this.maximumLength = maximumLength;
	}

	/**
	 * Returns the maximum length allowed by this document.
	 * 
	 * @return maximum length
	 * 
	 */
	public int getMaximumLength() {
		return maximumLength;
	}

	/**
	 * Sets the character case of this doocument to the given case.
	 * 
	 * @param characterCase
	 *            Character case to force on this document
	 * 
	 */
	public void setCharacterCase(int characterCase) {
		this.characterCase = characterCase;
	}

	/**
	 * Returns the current character case in use by this document.
	 * 
	 * @return character case
	 * 
	 */
	public int getCharacterCase() {
		return characterCase;
	}

	@Override
	public void insertString(int offset, String str, AttributeSet a)
			throws BadLocationException {
		boolean beep = false;
		if (str.length() + getLength() > maximumLength) {
			str = str.substring(0, maximumLength - getLength());
			beep = true;
		}

		switch (characterCase) {
		case MTextComponent.UPPER_CASE:
			str = str.toUpperCase();
			break;

		case MTextComponent.LOWER_CASE:
			str = str.toLowerCase();
			break;
		}

		super.insertString(offset, str, a);
		if (beep) {
			Toolkit.getDefaultToolkit().beep();
		}
	}
}
