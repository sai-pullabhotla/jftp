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