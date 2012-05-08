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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

/**
 * An extension of <code>javax.swing.JPopupMenu</code> with the additional
 * functionality of displaying the popup with in the screen limits by adjusting
 * the popup position if required.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 1.0
 * 
 */
public class MPopupMenu extends JPopupMenu {

	/**
	 * Displays this popup menu at the given position x and y on the component,
	 * <code>invoker</code>. This method always makes sure that the popup will
	 * fit in the screen. If the popup is going beyond the screen size, the
	 * orientation of the popup will be changed to fit in the screen area.
	 * 
	 * @param invoker
	 *            Component that invoked this popup menu.
	 * @param x
	 *            X position
	 * @param y
	 *            Y position.
	 * 
	 */
	@Override
	public void show(Component invoker, int x, int y) {
		Dimension size = getSize();
		if (size.width == 0 || size.height == 0) {
			size = getPreferredSize();
		}
		Point p = new Point(x + size.width, y + size.height);
		SwingUtilities.convertPointToScreen(p, invoker);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x1 = 0, y1 = 0;
		if (p.y > screenSize.height - 25) {
			y1 = y - size.height;
		}
		if (p.x > screenSize.width) {
			x1 = x - size.width;
		}
		super.show(invoker, x1 == 0 ? x : x1, y1 == 0 ? y : y1);
	}

	@Override
	public JMenuItem add(Action action) {
		MMenuItem mi = new MMenuItem(action);
		mi.setIcon(null);
		add(mi);
		return mi;
	}
}
