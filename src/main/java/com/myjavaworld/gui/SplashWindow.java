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

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JWindow;

/**
 * A generic splash window.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class SplashWindow extends JWindow {

	/**
	 * Creates an instance of <code>SplashWindow</code>.
	 * 
	 * @param icon
	 *            to show in the splash window
	 * 
	 */
	public SplashWindow(Icon icon) {
		super();
		getContentPane().setLayout(new BorderLayout());
		initComponents(icon);
		pack();
	}

	private void initComponents(Icon icon) {
		MLabel labSplash = new MLabel(icon);
		labSplash.setBorder(BorderFactory.createLineBorder(Color.black));
		getContentPane().add(labSplash, BorderLayout.CENTER);
	}

	/**
	 * Makes this splash window visible/invisible on the screen. This method
	 * blocks the current thread for <code>displayTime</code> number of milli
	 * seconds.
	 * 
	 * @param visible
	 *            if <code>true</code>, displays this window. <code>false</code>
	 *            , otherwise.
	 * 
	 */
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			setLocation(GUIUtil.getCenterPointRelativeToScreen(getSize()));
		}
		super.setVisible(visible);
	}
}
