/*
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

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 1.0
 * 
 */
public class IndentIcon implements Icon {

	private static final int space = 10;
	private Icon icon = null;
	private int depth = 0;

	public IndentIcon() {
		super();
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public Icon getIcon() {
		return icon;
	}

	/**
	 * @return Returns the depth.
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 *            The depth to set.
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (c.getComponentOrientation().isLeftToRight()) {
			icon.paintIcon(c, g, x + depth * space, y);
		} else {
			icon.paintIcon(c, g, x, y);
		}
	}

	public int getIconWidth() {
		return icon.getIconWidth() + depth * space;
	}

	public int getIconHeight() {
		return icon.getIconHeight();
	}
}