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
import javax.swing.JRadioButton;

import com.myjavaworld.util.SystemUtil;

public class MRadioButton extends JRadioButton {

	public MRadioButton() {
		super();
	}

	public MRadioButton(String text) {
		super(text);
	}

	public MRadioButton(String text, boolean selected) {
		super(text, selected);
	}

	public MRadioButton(String text, Icon icon) {
		super(text, icon);
	}

	public MRadioButton(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
	}

	public MRadioButton(Action action) {
		super(action);
	}

	public MRadioButton(Icon icon) {
		super(icon);
	}

	public MRadioButton(Icon icon, boolean selected) {
		super(icon, selected);
	}

	public void setMnemonic(String mnemonic) {
		if (mnemonic != null && mnemonic.trim().length() != 0) {
			super.setMnemonic(mnemonic.charAt(0));
		}
	}

	public void setDisplayedMnemonicIndex(String mnemonicIndex) {
		if (mnemonicIndex != null && mnemonicIndex.trim().length() != 0) {
			try {
				super.setDisplayedMnemonicIndex(Integer.parseInt(mnemonicIndex));
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
