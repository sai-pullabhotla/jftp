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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.myjavaworld.ftp.FTPConstants;
import com.myjavaworld.gui.MComboBox;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MScrollPane;
import com.myjavaworld.gui.MTable;
import com.myjavaworld.gui.MTableCellRenderer;
import com.myjavaworld.gui.MTextField;
import com.myjavaworld.util.ResourceLoader;

/**
 * A supporting panel used by the preferences dialog.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class TransferModesPrefsPanel extends JPanel {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.TransferModesPrefsPanel");
	private static final String[] COLUMN_NAMES = {
			resources.getString("text.fileType"),
			resources.getString("text.transferMode") };
	private static final String[] TYPE_NAMES = { "ASCII", "Binary" };
	private TypesTableModel model = null;
	private MTable table = null;
	private MComboBox comboDefaultTransferMode = null;

	public TransferModesPrefsPanel() {
		super();
		setLayout(new GridBagLayout());
		initComponents();
		configureTable();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 300);
	}

	public boolean validateFields() {
		return true;
	}

	public void populateScreen() {
		populateScreen(JFTP.prefs);
	}

	public void populateScreen(JFTPPreferences prefs) {
		comboDefaultTransferMode.setSelectedIndex(prefs
				.getDefaultTransferType() - 1);
		model.setData(prefs.getTransferTypes());
	}

	public void saveChanges() {
		int rowCount = model.getRowCount();
		Map map = new TreeMap();
		for (int i = 0; i < rowCount; i++) {
			String extension = (String) model.getValueAt(i, 0);
			if (extension.trim().length() > 0) {
				map.put(extension, model.getValueAt(i, 1));
			}
		}
		JFTP.prefs.setTransferTypes(map);
		JFTP.prefs.setDefaultTransferType(comboDefaultTransferMode
				.getSelectedIndex() + 1);
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;

		// model = new TypesTableModel(JFTP.prefs.getTransferTypes());
		model = new TypesTableModel();
		table = new MTable(model);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(12, 12, 12, 12);
		MScrollPane scroller = new MScrollPane(table);
		add(scroller, c);

		MLabel labDefaultTransferMode = new MLabel(
				resources.getString("text.defaultTransferMode"));
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0, 12, 12, 12);
		add(labDefaultTransferMode, c);

		comboDefaultTransferMode = new MComboBox(TYPE_NAMES);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		add(comboDefaultTransferMode, c);

		populateScreen();
	}

	private void configureTable() {
		table.setDefaultRenderer(Object.class, new MTableCellRenderer());
		table.setDefaultRenderer(Integer.class, new TransferTypeCellRenderer());
		MComboBox comboTypes = new MComboBox(TYPE_NAMES);
		table.setDefaultEditor(Integer.class, new DefaultCellEditor(comboTypes));
		DefaultCellEditor fileTypeEditor = new DefaultCellEditor(
				new MTextField());
		fileTypeEditor.setClickCountToStart(1);
		table.setDefaultEditor(Object.class, fileTypeEditor);
	}

	private class TypesTableModel extends AbstractTableModel {

		private Vector extensions = null;
		private Vector types = null;

		public TypesTableModel() {
			this(new TreeMap());
		}

		public TypesTableModel(Map map) {
			setData(map);
		}

		public void setData(Map map) {
			if (map == null) {
				map = new TreeMap();
			}
			extensions = new Vector(map.size());
			types = new Vector(map.size());
			for (Iterator i = map.keySet().iterator(); i.hasNext();) {
				String key = (String) (i.next());
				extensions.add(key);
				types.add(map.get(key));
			}
			addEmptyRow();
			fireTableDataChanged();
		}

		public int getRowCount() {
			return extensions.size();
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
				return extensions.get(row);
			} else {
				return types.get(row);
			}
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			String str = value.toString();
			if (col == 0) {
				extensions.set(row, str.toUpperCase());
				if (row == extensions.size() - 1 && str.trim().length() != 0) {
					addEmptyRow();
				}
			} else {
				int type = str.equals(TYPE_NAMES[0]) ? FTPConstants.TYPE_ASCII
						: FTPConstants.TYPE_BINARY;
				types.set(row, new Integer(type));
			}
			fireTableDataChanged();
		}

		@Override
		public Class getColumnClass(int col) {
			if (col == 1) {
				return Integer.class;
			}
			return Object.class;
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return true;
		}

		private void addEmptyRow() {
			extensions.add("");
			types.add(new Integer(FTPConstants.TYPE_ASCII));
		}
	}

	private static class TransferTypeCellRenderer extends MTableCellRenderer {

		public TransferTypeCellRenderer() {
			super();
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int col) {
			int type = ((Integer) value).intValue();
			setText(TYPE_NAMES[type - 1]);
			setBackground(isSelected ? table.getSelectionBackground() : table
					.getBackground());
			setForeground(isSelected ? table.getSelectionForeground() : table
					.getForeground());
			setFont(table.getFont());
			return this;
		}
	}
}