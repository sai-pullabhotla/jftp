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