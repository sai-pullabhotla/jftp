/*
 * Created on Feb 8, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.myjavaworld.gui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

import com.myjavaworld.util.SystemUtil;

public class MCheckBox extends JCheckBox {

	public MCheckBox() {
		super();
	}

	public MCheckBox(Icon icon) {
		super(icon);
	}

	public MCheckBox(Icon icon, boolean selected) {
		super(icon, selected);
	}

	public MCheckBox(String text) {
		super(text);
	}

	public MCheckBox(Action action) {
		super(action);
	}

	public MCheckBox(String text, boolean selected) {
		super(text, selected);
	}

	public MCheckBox(String text, Icon icon) {
		super(text, icon);
	}

	public MCheckBox(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
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
