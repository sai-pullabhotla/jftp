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

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

import com.myjavaworld.util.SystemUtil;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class MMenuItem extends JMenuItem {

	public MMenuItem() {
		super();
	}

	public MMenuItem(String text) {
		super(text);
	}

	public MMenuItem(String text, int mnemonic) {
		super(text, mnemonic);
	}

	public MMenuItem(String text, Icon icon) {
		super(text, icon);
	}

	public MMenuItem(Action action) {
		super(action);
	}

	public MMenuItem(Icon icon) {
		super(icon);
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
