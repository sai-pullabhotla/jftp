/*
 * Created on Feb 9, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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
