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

import javax.swing.Icon;
import javax.swing.JLabel;

import com.myjavaworld.util.SystemUtil;

/**
 * An extension of <code>javax,swing.JLabel</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MLabel extends JLabel {

	/**
	 * Constructs an object of <code>MLabel</code>.
	 * 
	 */
	public MLabel() {
		super();
	}

	/**
	 * Constructs an object of <code>MLabel</code>.
	 * 
	 * @param text
	 *            Text/label
	 * @param alignment
	 *            text alignment.
	 * 
	 */
	public MLabel(String text, int alignment) {
		super(text, alignment);
	}

	/**
	 * Constructs an object of <code>MLabel</code>.
	 * 
	 * @param text
	 *            Text/label.
	 * 
	 */
	public MLabel(String text) {
		super(text);
	}

	public MLabel(Icon icon) {
		super(icon);
	}

	public MLabel(Icon icon, int alignment) {
		super(icon, alignment);
	}

	public void setDisplayedMnemonic(String mnemonic) {
		if (mnemonic != null && mnemonic.trim().length() > 0) {
			super.setDisplayedMnemonic(mnemonic.charAt(0));
		}
	}

	public void setDisplayedMnemonicIndex(String mnemonicIndex) {
		if (mnemonicIndex != null && mnemonicIndex.trim().length() > 0) {
			try {
				super.setDisplayedMnemonicIndex(Integer.parseInt(mnemonicIndex));
			} catch (NumberFormatException exp) {
				throw exp;
			}
		}
	}

	public void setMnemonic(String mnemonic, String mnemonicIndex) {
		if (!SystemUtil.isMac()) {
			super.setDisplayedMnemonic(mnemonic.charAt(0));
			super.setDisplayedMnemonicIndex(Integer.parseInt(mnemonicIndex));
		}
	}
}