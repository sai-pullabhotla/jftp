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

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * A component for rendering table headers of <code>MTable</code>. MTable sets
 * this component as the default renderer.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MTableHeaderRenderer extends JLabel implements TableCellRenderer {

	private Icon icon = null;

	/**
	 * Creates an instance of <code>MTableHeaderRenderer</code>.
	 * 
	 */
	public MTableHeaderRenderer() {
		this(null);
	}

	/**
	 * Creates an instance of <code>MTableHeaderRenderer</code>.
	 * 
	 * @param icon
	 *            Icon to paint in the header.
	 * 
	 */
	public MTableHeaderRenderer(Icon icon) {
		super();
		this.icon = icon;
		setHorizontalAlignment(CENTER);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setBackground((Color) UIManager.get("TableHeader.background"));
		setForeground((Color) UIManager.get("TableHeader.foreground"));
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		setText(String.valueOf(value));
		if (icon != null) {
			setIcon(icon);
		}
		return this;
	}

	/**
	 * Sets the icon for this Header renderer.
	 * 
	 * @param icon
	 *            Icon to set.
	 * 
	 */
	@Override
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	/**
	 * Returns the current icon set on this renderer.
	 * 
	 * @return Current icon. May return null if no icon was set.
	 * 
	 */
	@Override
	public Icon getIcon() {
		return icon;
	}
}