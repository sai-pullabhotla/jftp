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

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * A default List and Table cell renderer component.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MDefaultRenderer extends JLabel implements TableCellRenderer,
		ListCellRenderer {

	private Icon icon = null;

	/**
	 * Constructs an object of <code>MDefaultRenderer</code>.
	 * 
	 */
	public MDefaultRenderer() {
		this(null);
	}

	/**
	 * Constructs an object of <code>MDefaultRenderer</code>.
	 * 
	 * @param icon
	 *            Icon to render when rendering this component.
	 * 
	 */
	public MDefaultRenderer(Icon icon) {
		super();
		this.icon = icon;
		setOpaque(true);
		setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {

		setText(value == null ? "null" : value.toString());
		if (icon != null) {
			setIcon(icon);
		}
		setBackground(isSelected ? list.getSelectionBackground() : list
				.getBackground());
		setForeground(isSelected ? list.getSelectionForeground() : list
				.getForeground());
		setFont(list.getFont());
		return this;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		setText(value == null ? "null" : value.toString());
		if (icon != null) {
			setIcon(icon);
		}
		setBackground(isSelected ? table.getSelectionForeground() : table
				.getBackground());
		setForeground(isSelected ? table.getSelectionForeground() : table
				.getForeground());
		setFont(table.getFont());
		return this;
	}
}