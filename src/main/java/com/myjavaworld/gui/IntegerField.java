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