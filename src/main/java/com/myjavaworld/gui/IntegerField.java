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
import javax.swing.text.Document;

/**
 * An extension of <code>javax.swing.JTextField</code> that allows only integer
 * values.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class IntegerField extends MTextField {

	/**
	 * Constructs an <code>IntegerField</code>.
	 */
	public IntegerField() {
		// super("0");
		super();
		// setText("0");
	}

	/**
	 * Constructs an <code>IntegerField</code> whose display size is set based
	 * on the given parameter <code>cols</code>.
	 * 
	 * @param cols
	 *            Display size
	 * 
	 */
	public IntegerField(int cols) {
		// super("0", cols);
		super(cols);
		// setText("0");
	}

	/**
	 * Constructs an <code>IntegerField</code> with the given initial value,
	 * <code>value</code> and display size, <code>cols</code>.
	 * 
	 * @param value
	 *            Initial value
	 * @param cols
	 *            Display size
	 * 
	 */
	// public IntegerField(int value, int cols) {
	// //super("" + value, cols);
	// super(cols);
	// setText(String.valueOf(value));
	// }
	/**
	 * Sets the value in this field to the given <code>value</code>.
	 * 
	 * @param value
	 *            New value to set.
	 * 
	 */
	public void setValue(int value) {
		// super.setText("" + value);
		setText(String.valueOf(value));
	}

	/**
	 * Returns the current value in this field.
	 * 
	 * @return Current value.
	 * 
	 */
	public int getValue() {
		return Integer.parseInt(getText());
	}

	@Override
	protected Document createDefaultModel() {
		return new IntegerDocument();
	}

	/**
	 * An implementation of Document that allows only integers.
	 * 
	 */
	static class IntegerDocument extends SingleLineDocument {

		@Override
		public void insertString(int offset, String str, AttributeSet a)
				throws BadLocationException {
			StringBuffer sb = new StringBuffer(getText(0, getLength()));
			sb.insert(offset, str);
			try {
				Integer.parseInt(sb.toString());
				super.insertString(offset, str, a);
			} catch (NumberFormatException exp) {
				if (sb.toString().equals("-")) {
					super.insertString(offset, str, a);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		}
	}
}