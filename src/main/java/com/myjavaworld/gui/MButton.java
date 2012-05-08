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
import javax.swing.JButton;

import com.myjavaworld.util.SystemUtil;

/**
 * An extension of <code>javax.swing.JButton</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MButton extends JButton {

	/**
	 * Constructs an object of <code>MButton</code>.
	 * 
	 */
	public MButton() {
		super();
	}

	/**
	 * Constructs an object of <code>MButton</code>.
	 * 
	 * @param text
	 *            Text/label
	 * 
	 */
	public MButton(String text) {
		super(text);
	}

	/**
	 * Constructs an object of <code>MButton</code>.
	 * 
	 * @param icon
	 *            an Icon for this button
	 * 
	 */
	public MButton(Icon icon) {
		super(icon);
	}

	/**
	 * Constructs an object of <code>MButton</code>.
	 * 
	 * @param text
	 *            Text/label
	 * @param icon
	 *            Icon
	 * 
	 */
	public MButton(String text, Icon icon) {
		super(text, icon);
	}

	public void setMnemonic(String str) {
		if (str != null && str.trim().length() > 0) {
			super.setMnemonic(str.charAt(0));
		}
	}

	public void setDisplayedMnemonicIndex(String str) {
		if (str != null && str.trim().length() > 0) {
			try {
				super.setDisplayedMnemonicIndex(Integer.parseInt(str));
			} catch (NumberFormatException exp) {
				throw exp;
			}
		}
	}

	public void setMnemonic(String mnemonic, String mnemonicIndex) {
		if (!SystemUtil.isMac()) {
			super.setMnemonic(mnemonic.charAt(0));
			super.setDisplayedMnemonicIndex(Integer.parseInt(mnemonicIndex));
		}
	}
}