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

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;

import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.gui.MTableCellRenderer;

/**
 * A table cell renderer used to render <code>RemoteFile</code> objects.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class RemoteFileCellRenderer extends MTableCellRenderer {

	private Icon directoryIcon = null;
	private Icon fileIcon = null;

	public RemoteFileCellRenderer() {
		super();
		directoryIcon = JFTPUtil.getIcon("directory16.gif");
		fileIcon = JFTPUtil.getIcon("file16.gif");
		// directoryIcon = UIManager.getIcon("Tree.closedIcon");
		// fileIcon = UIManager.getIcon("Tree.leafIcon");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		if (value == null) {
			setText("");
			setIcon(null);
		} else {
			RemoteFile file = (RemoteFile) value;
			setText(file.getName());
			setIcon(file.isDirectory() ? directoryIcon : fileIcon);
		}
		setBackground(isSelected ? table.getSelectionBackground() : table
				.getBackground());
		setForeground(isSelected ? table.getSelectionForeground() : table
				.getForeground());
		setFont(table.getFont());
		return this;
	}
}