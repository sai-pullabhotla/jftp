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