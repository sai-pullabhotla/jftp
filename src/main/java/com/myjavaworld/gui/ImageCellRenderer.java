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

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 * A cell renderer (Table and List) used to render a given image along with the
 * given object (value).
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class ImageCellRenderer extends JLabel implements TableCellRenderer,
		ListCellRenderer, TreeCellRenderer {

	private static final Color TREE_SELECTION_FOREGROUND = UIManager
			.getColor("Tree.selectionForeground");
	private static final Color TREE_SELECTION_BACKGROUND = UIManager
			.getColor("Tree.selectionBackground");
	private static final Color TREE_BACKGROUND = UIManager
			.getColor("Tree.textBackground");
	private static final Color TREE_FOREGROUND = UIManager
			.getColor("Tree.textForeground");
	private Icon icon = null;

	/**
	 * Constructs an <code>ImageCellRenderer</code> object with no image.
	 */
	public ImageCellRenderer() {
		this(null);
	}

	/**
	 * Constructs an <code>ImageCellRenderer</code> object with no image.
	 * 
	 * @param icon
	 *            Icon to render
	 * 
	 */
	public ImageCellRenderer(Icon icon) {
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

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		setText(String.valueOf(value));
		if (icon != null) {
			setIcon(icon);
		}
		setBackground(isSelected ? TREE_SELECTION_BACKGROUND : TREE_BACKGROUND);
		setForeground(isSelected ? TREE_SELECTION_FOREGROUND : TREE_FOREGROUND);
		setFont(tree.getFont());
		return this;
	}
}