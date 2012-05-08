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
package com.myjavaworld.jftp;

import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

import com.myjavaworld.util.ResourceLoader;

/**
 * Table model used to display local files in a JTable.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class LocalFileTableModel extends AbstractTableModel {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.LocalFileTableModel");
	private static final String[] COLUMN_NAMES = {
			resources.getString("text.name"), resources.getString("text.type"),
			resources.getString("text.size"),
			resources.getString("text.lastModified") };
	private LocalFile[] data = null;

	public LocalFileTableModel() {
		this(new LocalFile[0]);
	}

	public LocalFileTableModel(LocalFile[] data) {
		setData(data);
	}

	public void setData(LocalFile[] data) {
		if (data == null) {
			data = new LocalFile[0];
		}
		this.data = data;
		fireTableDataChanged();
	}

	public LocalFile[] getData() {
		return data;
	}

	public LocalFile getFileAt(int row) {
		return data[row];
	}

	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int col) {
		return COLUMN_NAMES[col];
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getValueAt(int row, int col) {
		if (col == 0) {
			return data[row];
		}
		if (col == 1) {
			return data[row].getType();
		}
		if (col == 2) {
			return new Long(data[row].getSize());
		}
		return new Date(data[row].getLastModified());
	}

	@Override
	public Class getColumnClass(int col) {
		if (col == 0) {
			return LocalFile.class;
		}
		if (col == 2) {
			return Long.class;
		}
		if (col == 3) {
			return Date.class;
		}
		return Object.class;
	}
}