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