/*
 * Created on Jan 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.myjavaworld.gui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JRadioButtonMenuItem;

import com.myjavaworld.util.SystemUtil;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class MRadioButtonMenuItem extends JRadioButtonMenuItem {

	public MRadioButtonMenuItem() {
		super();
	}

	public MRadioButtonMenuItem(Icon icon) {
		super(icon);
	}

	public MRadioButtonMenuItem(String text) {
		super(text);
	}

	public MRadioButtonMenuItem(Action action) {
		super(action);
	}

	public MRadioButtonMenuItem(String text, Icon icon) {
		super(text, icon);
	}

	public MRadioButtonMenuItem(String text, boolean selected) {
		super(text, selected);
	}

	public MRadioButtonMenuItem(Icon icon, boolean selected) {
		super(icon, selected);
	}

	public MRadioButtonMenuItem(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
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
