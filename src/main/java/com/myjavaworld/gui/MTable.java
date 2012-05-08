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

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.TableModel;

import com.myjavaworld.util.SystemUtil;

/**
 * An extension of <code>javax.swing.JTable</code> with some additional/modified
 * behaviours and/or functionality.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MTable extends JTable {

	/**
	 * Creates an instance of <code>MTable</code>.
	 * 
	 */
	public MTable() {
		super();
		getTableHeader().setDefaultRenderer(new MTableHeaderRenderer());
	}

	/**
	 * Creates an instance of <code>MTable</code>.
	 * 
	 * @param rows
	 *            Number of rows
	 * @param cols
	 *            Number of columns
	 * 
	 */
	public MTable(int rows, int cols) {
		super(rows, cols);
		getTableHeader().setDefaultRenderer(new MTableHeaderRenderer());
	}

	/**
	 * Creates an instance of <code>MTable</code>.
	 * 
	 * @param data
	 *            Table data
	 * @param columnNames
	 *            Column names
	 * 
	 */
	public MTable(Vector data, Vector columnNames) {
		super(data, columnNames);
		getTableHeader().setDefaultRenderer(new MTableHeaderRenderer());
	}

	/**
	 * Creates an instance of <code>MTable</code>.
	 * 
	 * @param data
	 *            Table data
	 * @param columnNames
	 *            Column names
	 * 
	 */
	public MTable(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		getTableHeader().setDefaultRenderer(new MTableHeaderRenderer());
	}

	/**
	 * Creates an instance of <code>MTable</code>.
	 * 
	 * @param model
	 *            Table model
	 * 
	 */
	public MTable(TableModel model) {
		super(model);
		getTableHeader().setDefaultRenderer(new MTableHeaderRenderer());
	}

	/**
	 * Returns the height of the row. This method calculates the row height
	 * based on the current font sent on the table where as,
	 * <code>javax.swing.JTable</code> always returns a fixed row height, which
	 * does not work well, especially, when using a Theme or look and feel other
	 * than the defaults.
	 * 
	 * @return Row height
	 * 
	 */
	@Override
	public int getRowHeight() {
		Font font = getFont();
		if (font == null) {
			font = (Font) UIManager.get("Table.font");
		}
		FontMetrics metrics = getFontMetrics(font);
		return metrics.getLeading() + metrics.getMaxAscent()
				+ metrics.getMaxDescent() + 2;
	}

	@Override
	protected void processMouseEvent(MouseEvent evt) {
		if (SystemUtil.isMac()) {
			workaroundMacPopupIssue(evt);
		} else if (evt.isPopupTrigger()) {
			windowsWorkAround(evt);
		} else {
			super.processMouseEvent(evt);
		}
	}

	private void windowsWorkAround(MouseEvent evt) {
		int row = rowAtPoint(evt.getPoint());
		if (row >= 0) {
			if (!isRowSelected(row)) {
				clearSelection();
			}
			addRowSelectionInterval(row, row);
		} else {
			clearSelection();
		}
		super.processMouseEvent(evt);
	}

	private void workaroundMacPopupIssue(MouseEvent evt) {
		int[] selectedRows = getSelectedRows();
		int row = rowAtPoint(evt.getPoint());
		ListSelectionModel model = getSelectionModel();
		if (model.isSelectedIndex(row)) {
			super.processMouseEvent(evt);
			model.clearSelection();
			for (int i = 0; i < selectedRows.length; i++) {
				model.addSelectionInterval(selectedRows[i], selectedRows[i]);
			}
		} else {
			super.processMouseEvent(evt);
		}
	}
}