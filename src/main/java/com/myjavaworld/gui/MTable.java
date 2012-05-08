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