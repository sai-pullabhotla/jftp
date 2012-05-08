/*
 * Created on Jan 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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
