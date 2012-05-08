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

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * A List cell renderer to render system roots.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class DriveCellRenderer extends JLabel implements ListCellRenderer {

	/**
	 * Created an instance of <code>LocaleCellRenderer</code>.
	 * 
	 */
	public DriveCellRenderer() {
		super();
		setOpaque(true);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {
		if (value == null) {
			setText("");
			setIcon(null);
		} else {
			LocalFile file = (LocalFile) value;
			setText(file.getDisplayName());
			try {
				setIcon(file.getIcon());
			} catch (Exception exp) {
				// Do nothing.
			}
		}
		setBackground(isSelected ? list.getSelectionBackground() : list
				.getBackground());
		setForeground(isSelected ? list.getSelectionForeground() : list
				.getForeground());
		setFont(list.getFont());
		return this;
	}
}