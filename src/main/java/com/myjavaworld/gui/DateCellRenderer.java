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
import java.text.DateFormat;
import java.util.Locale;

import javax.swing.JTable;

/**
 * A table cell renderer for rendering Dates in a variety of formats.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class DateCellRenderer extends MTableCellRenderer {

	private DateFormat formatter = null;

	/**
	 * Constructs a <code>DateCellRenderer</code> object with the default
	 * formatting suitable for the current Locale. Both Date and Time styles are
	 * set to <code>java.text.DateFormat.SHORT</code>.
	 * 
	 */
	public DateCellRenderer() {
		this(DateFormat.SHORT, DateFormat.SHORT);
	}

	/**
	 * Constructs a <code>DateCellRenderer</code> object with the given date and
	 * time styles.
	 * 
	 * @param dateStyle
	 *            Date style to use when rendering
	 * @param timeStyle
	 *            Time style to use when rendering
	 * 
	 */
	public DateCellRenderer(int dateStyle, int timeStyle) {
		super();
		formatter = DateFormat.getDateTimeInstance(dateStyle, timeStyle,
				Locale.getDefault());
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		setText(value == null ? "null" : formatter.format(value));
		setBackground(isSelected ? table.getSelectionBackground() : table
				.getBackground());
		setForeground(isSelected ? table.getSelectionForeground() : table
				.getForeground());
		setFont(table.getFont());
		return this;
	}
}