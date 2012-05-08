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

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;

import com.myjavaworld.gui.MTableCellRenderer;

/**
 * A table cell renderer used to render Local files.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class LocalFileCellRenderer extends MTableCellRenderer {

	private Icon directoryIcon = null;
	private Icon fileIcon = null;

	public LocalFileCellRenderer() {
		super();
		directoryIcon = JFTPUtil.getIcon("directory16.gif");
		fileIcon = JFTPUtil.getIcon("file16.gif");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		if (value == null) {
			setText("");
			setIcon(null);
		} else {
			LocalFile file = (LocalFile) value;
			setText(file.getDisplayName());
			Icon icon = file.getIcon();
			if (icon != null) {
				setIcon(file.getIcon());
			} else {
				setIcon(file.isDirectory() ? directoryIcon : fileIcon);
			}
		}
		setBackground(isSelected ? table.getSelectionBackground() : table
				.getBackground());
		setForeground(isSelected ? table.getSelectionForeground() : table
				.getForeground());
		setFont(table.getFont());
		return this;
	}
}