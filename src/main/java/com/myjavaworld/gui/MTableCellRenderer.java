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
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Default table cell renderer for use with <code>MTable</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MTableCellRenderer extends JLabel implements TableCellRenderer {

	/**
	 * Creates an instance of <code>MTableCellRenderer</code>.
	 * 
	 */
	public MTableCellRenderer() {
		super();
		setOpaque(true);
		setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		setText(value == null ? "null" : value.toString());
		setBackground(isSelected ? table.getSelectionBackground() : table
				.getBackground());
		setForeground(isSelected ? table.getSelectionForeground() : table
				.getForeground());
		setFont(table.getFont());
		return this;
	}
}