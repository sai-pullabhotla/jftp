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
package com.myjavaworld.jftp;

import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.util.ResourceLoader;

/**
 * A table model used to display remote file system.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class RemoteFileTableModel extends AbstractTableModel {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.RemoteFileTableModel");
	private static final String[] COLUMN_NAMES = {
			resources.getString("text.name"), resources.getString("text.type"),
			resources.getString("text.size"),
			resources.getString("text.lastModified"),
			resources.getString("text.attributes") };
	private RemoteFile[] data = null;

	public RemoteFileTableModel() {
		this(new RemoteFile[0]);
	}

	public RemoteFileTableModel(RemoteFile[] data) {
		setData(data);
	}

	public void setData(RemoteFile[] data) {
		if (data == null) {
			data = new RemoteFile[0];
		}
		this.data = data;
		fireTableDataChanged();
	}

	public RemoteFile[] getData() {
		return data;
	}

	public RemoteFile getFileAt(int row) {
		return data[row];
	}

	public int getRowCount() {
		return data.length;
	}

	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int col) {
		return COLUMN_NAMES[col];
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
		if (col == 3) {
			return new Date(data[row].getLastModified());
		}
		return data[row].getAttributes();
	}

	@Override
	public Class getColumnClass(int col) {
		if (col == 0) {
			return RemoteFile.class;
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