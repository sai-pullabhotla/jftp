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

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * A component for rendering table headers of <code>MTable</code>. MTable sets
 * this component as the default renderer.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MTableHeaderRenderer extends JLabel implements TableCellRenderer {

	private Icon icon = null;

	/**
	 * Creates an instance of <code>MTableHeaderRenderer</code>.
	 * 
	 */
	public MTableHeaderRenderer() {
		this(null);
	}

	/**
	 * Creates an instance of <code>MTableHeaderRenderer</code>.
	 * 
	 * @param icon
	 *            Icon to paint in the header.
	 * 
	 */
	public MTableHeaderRenderer(Icon icon) {
		super();
		this.icon = icon;
		setHorizontalAlignment(CENTER);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setBackground((Color) UIManager.get("TableHeader.background"));
		setForeground((Color) UIManager.get("TableHeader.foreground"));
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		setText(String.valueOf(value));
		if (icon != null) {
			setIcon(icon);
		}
		return this;
	}

	/**
	 * Sets the icon for this Header renderer.
	 * 
	 * @param icon
	 *            Icon to set.
	 * 
	 */
	@Override
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	/**
	 * Returns the current icon set on this renderer.
	 * 
	 * @return Current icon. May return null if no icon was set.
	 * 
	 */
	@Override
	public Icon getIcon() {
		return icon;
	}
}