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
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.myjavaworld.gui.DateCellRenderer;
import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.IndentIcon;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MComboBox;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MScrollPane;
import com.myjavaworld.gui.MTable;
import com.myjavaworld.gui.MTableCellRenderer;
import com.myjavaworld.gui.MTableHeaderRenderer;
import com.myjavaworld.gui.NumericCellRenderer;
import com.myjavaworld.jftp.actions.DeleteLocalFileAction;
import com.myjavaworld.jftp.actions.OpenLocalFileAction;
import com.myjavaworld.util.ResourceLoader;

/**
 * Local File System view.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class LocalPane extends JPanel implements ActionListener, MouseListener,
		ListSelectionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.LocalPane");
	private static final Icon UP_ARROW_ICON = JFTPUtil.getIcon("upArrow16.gif");
	private static final Icon DOWN_ARROW_ICON = JFTPUtil
			.getIcon("downArrow16.gif");
	private FTPSession session = null;
	private MComboBox comboRoots = null;
	private MComboBox comboWorkingDirectory = null;
	private MButton butUp = null;
	private LocalFileTableModel model = null;
	private MTable table = null;
	private MScrollPane scroller = null;
	private MLabel labStatus = null;
	private LocalFileComparator comparator = null;
	private boolean updateComboWorkingDirectory = true;
	private MessageFormat statusFormat1 = null;
	private MessageFormat statusFormat2 = null;
	private Object[] statusArgs = null;
	private DirectoryComboBoxModel dcm = null;

	public LocalPane(FTPSession session) {
		super();
		this.session = session;
		comparator = new LocalFileComparator();
		statusFormat1 = new MessageFormat(
				resources.getString("status.objectCount"));
		statusFormat2 = new MessageFormat(
				resources.getString("status.selectedObjectCount"));
		statusFormat1.setLocale(Locale.getDefault());
		statusFormat2.setLocale(Locale.getDefault());
		statusArgs = new Object[2];
		setLayout(new GridBagLayout());
		initComponents();
		configureTable();
		updateTableHeader();
	}

	public void setData(LocalFile dir, LocalFile[] data) {
		if (data == null) {
			return;
		}
		if (dcm == null) {
			dcm = new DirectoryComboBoxModel(dir);
			comboWorkingDirectory.setModel(dcm);
			comboWorkingDirectory.setRenderer(new DirectoryCellRenderer());
		}
		Arrays.sort(data, comparator);
		model.setData(data);
		if (updateComboWorkingDirectory) {
			updateComboWorkingDirectory(dir);
		}
		if (data.length > 0) {
			table.scrollRectToVisible(table.getCellRect(0, 0, true));
		}
		updateStatus();
	}

	public LocalFile[] getData() {
		return model.getData();
	}

	public int getSelectionCount() {
		return table.getSelectedRowCount();
	}

	public LocalFile getSelectedFile() {
		int row = table.getSelectedRow();
		if (row < 0) {
			return null;
		}
		return model.getFileAt(row);
	}

	public LocalFile[] getSelectedFiles() {
		int[] selectedRows = table.getSelectedRows();
		if (selectedRows == null) {
			return new LocalFile[0];
		}
		LocalFile[] selectedFiles = new LocalFile[selectedRows.length];
		for (int i = 0; i < selectedRows.length; i++) {
			selectedFiles[i] = model.getFileAt(selectedRows[i]);
		}
		return selectedFiles;
	}

	public void refresh() {
		session.setLocalWorkingDirectory(session.getLocalWorkingDirectory());
		comboRoots.setModel(new DefaultComboBoxModel(getRoots()));
	}

	public void valueChanged(ListSelectionEvent evt) {
		updateStatus();
		session.updateToolBar();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butUp) {
			session.upLocalWorkingDirectory();
		} else if (evt.getSource() == comboRoots) {
			LocalFile root = (LocalFile) comboRoots.getSelectedItem();
			if (root == null) {
				return;
			}
			session.setLocalWorkingDirectory(root);
		} else if (evt.getSource() == comboWorkingDirectory) {
			LocalFile selectedFile = (LocalFile) comboWorkingDirectory
					.getSelectedItem();
			updateComboWorkingDirectory = false;
			session.setLocalWorkingDirectory(selectedFile);
			updateComboWorkingDirectory = true;
		}
	}

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseExited(MouseEvent evt) {
	}

	public void mousePressed(MouseEvent evt) {
		if (evt.isPopupTrigger()) {
			if (evt.getSource() == table) {
				tableRightClicked(evt);
			} else if (evt.getSource() == scroller) {
				scrollerRightClicked(evt);
			}
		}
	}

	public void mouseReleased(MouseEvent evt) {
		if (evt.isPopupTrigger()) {
			if (evt.getSource() == table) {
				tableRightClicked(evt);
			} else if (evt.getSource() == scroller) {
				scrollerRightClicked(evt);
			}
		} else if (evt.getSource() == table) {
			if (SwingUtilities.isLeftMouseButton(evt)
					&& evt.getClickCount() >= 2) {
				doubleClicked(evt);
			}
		} else if (evt.getSource() == table.getTableHeader()) {
			if (SwingUtilities.isLeftMouseButton(evt)) {
				Cursor cursor = table.getTableHeader().getCursor();
				if (cursor.equals(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR))) {
					int column = table.columnAtPoint(evt.getPoint());
					column = table.convertColumnIndexToModel(column);
					sort(column);
				}
			}
		}
	}

	public void mouseClicked(MouseEvent evt) {
	}

	private void tableRightClicked(MouseEvent evt) {
		JPopupMenu popup = LocalSystemPopupMenu.getInstance(session.getJFTP());
		popup.show((Component) evt.getSource(), evt.getX(), evt.getY());
	}

	private void scrollerRightClicked(MouseEvent evt) {
		table.clearSelection();
		JPopupMenu popup = LocalSystemPopupMenu.getInstance(session.getJFTP());
		popup.show((Component) evt.getSource(), evt.getX(), evt.getY());
	}

	private void doubleClicked(MouseEvent evt) {
		int row = table.rowAtPoint(evt.getPoint());
		if (row < 0) {
			return;
		}
		LocalFile file = model.getFileAt(row);
		OpenLocalFileAction.getInstance(session.getJFTP())
				.actionPerformed(null);
	}

	private LocalFile[] getRoots() {
		LocalFile[] roots = LocalFile.listRoots();
		if (roots == null) {
			roots = new LocalFile[0];
		}
		return roots;
	}

	private void updateComboWorkingDirectory(LocalFile dir) {
		comboWorkingDirectory.removeActionListener(this);
		dcm.addItem(dir);
		dcm.setSelectedItem(dir);
		comboWorkingDirectory.addActionListener(this);
	}

	private void updateStatus() {
		long size = 0L;
		int count = table.getSelectedRowCount();
		if (count > 0) {
			int[] selectedRows = table.getSelectedRows();
			for (int i = 0; i < selectedRows.length; i++) {
				size += model.getFileAt(selectedRows[i]).getSize();
			}
			statusArgs[0] = new Integer(count);
			statusArgs[1] = new Long(size);
			// statusArgs[2] = new Integer(count);
			labStatus.setText(statusFormat2.format(statusArgs));
			return;
		}
		count = model.getRowCount();
		for (int i = 0; i < count; i++) {
			size += model.getFileAt(i).getSize();
		}
		statusArgs[0] = new Integer(count);
		statusArgs[1] = new Long(size);
		// statusArgs[2] = new Integer(count);
		labStatus.setText(statusFormat1.format(statusArgs));
	}

	private void sort(int col) {
		int compareBy = comparator.getCompareBy();
		int order = comparator.getOrder();
		if (compareBy == col + 1) {
			order = order == LocalFileComparator.ASC_ORDER ? LocalFileComparator.DESC_ORDER
					: LocalFileComparator.ASC_ORDER;
		} else {
			compareBy = col + 1;
			order = LocalFileComparator.ASC_ORDER;
		}
		comparator.setCompareBy(compareBy);
		comparator.setOrder(order);
		LocalFile[] data = model.getData();
		Arrays.sort(model.getData(), comparator);
		model.setData(data);
		updateTableHeader();
	}

	private void updateTableHeader() {
		int compareBy = comparator.getCompareBy();
		int order = comparator.getOrder();
		Icon icon = order == LocalFileComparator.ASC_ORDER ? UP_ARROW_ICON
				: DOWN_ARROW_ICON;
		for (int i = 0; i < 4; i++) {
			MTableHeaderRenderer r = new MTableHeaderRenderer();
			if (compareBy - 1 == i) {
				r.setIcon(icon);
			} else {
				r.setIcon(null);
			}
			table.getColumnModel().getColumn(table.convertColumnIndexToView(i))
					.setHeaderRenderer(r);
		}
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		comboRoots = new MComboBox(getRoots());
		comboRoots.setRenderer(new DriveCellRenderer());
		// comboRoots.setData(getRoots());
		comboRoots.setToolTipText(resources.getString("tooltip.roots"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(2, 0, 2, 2);
		add(comboRoots, c);

		comboWorkingDirectory = new MComboBox();
		comboWorkingDirectory.setToolTipText(resources
				.getString("tooltip.workingDirectory"));
		comboWorkingDirectory.addActionListener(this);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(2, 0, 2, 2);
		add(comboWorkingDirectory, c);

		// butUp = new MButton(UIManager.getIcon("FileChooser.upFolderIcon"));
		butUp = new MButton(JFTPUtil.getIcon("upDirectory16.gif"));
		butUp.setToolTipText(resources.getString("tooltip.up"));
		butUp.addActionListener(this);
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(2, 0, 2, 0);
		add(butUp, c);

		model = new LocalFileTableModel();
		table = new MTable(model) {

			@Override
			public boolean getScrollableTracksViewportWidth() {
				return (getPreferredSize().width < getParent().getWidth());
			}
		};
		table.setShowGrid(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scroller = new MScrollPane(table);
		if (table.isBackgroundSet()) {
			scroller.getViewport().setBackground(table.getBackground());
		}
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 0, 2, 0);
		add(scroller, c);

		labStatus = new MLabel("Status Bar");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 5, 2, 0);
		add(labStatus, c);
		comboRoots.addActionListener(this);
	}

	private void configureTable() {
		table.setName("localPane");
		scroller.setName("localPane");
		TransferHandler th = new DnDTransferHandler(session.getJFTP());
		table.setDragEnabled(true);
		scroller.setTransferHandler(th);
		table.setTransferHandler(th);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(75);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.setDefaultRenderer(LocalFile.class, new LocalFileCellRenderer());
		table.setDefaultRenderer(Long.class, new NumericCellRenderer());
		int dateFormat = JFTP.prefs.getDateFormat();
		int timeFormat = JFTP.prefs.getTimeFormat();
		table.setDefaultRenderer(Date.class, new DateCellRenderer(dateFormat,
				timeFormat));
		table.setDefaultRenderer(Object.class, new MTableCellRenderer());
		table.addMouseListener(this);
		scroller.addMouseListener(this);
		table.getSelectionModel().addListSelectionListener(this);
		table.getTableHeader().setDefaultRenderer(new MTableHeaderRenderer());
		table.getTableHeader().addMouseListener(this);
		table.registerKeyboardAction(
				DeleteLocalFileAction.getInstance(session.getJFTP()),
				"cmd.deleteLocalFile",
				KeyStroke.getKeyStroke(GUIUtil.getDeleteKey(), 0),
				JComponent.WHEN_FOCUSED);
		table.registerKeyboardAction(
				OpenLocalFileAction.getInstance(session.getJFTP()),
				"cmd.openLocalFile",
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_FOCUSED);
	}

	class DirectoryComboBoxModel extends AbstractListModel implements
			ComboBoxModel {

		private LocalFile selectedDir = null;
		private Vector dirs = null;

		public DirectoryComboBoxModel(LocalFile dir) {
			dirs = new Vector(10);
			this.selectedDir = dir;
			// addI
		}

		public Object getSelectedItem() {
			return selectedDir;
		}

		public void setSelectedItem(Object selectedDir) {
			this.selectedDir = (LocalFile) selectedDir;
			fireContentsChanged(this, -1, -1);
		}

		public int getSize() {
			return dirs.size();
		}

		public Object getElementAt(int index) {
			return dirs.get(index);
		}

		private void addItem(LocalFile dir) {
			if (dir == null) {
				return;
			}
			LocalFile cdir = dir;
			try {
				cdir = dir.getCanonicalFile();
			} catch (IOException exp) {
			}
			dirs.clear();
			do {
				dirs.add(0, cdir);
			} while ((cdir = cdir.getParent()) != null);
		}
	}

	class DirectoryCellRenderer extends MLabel implements ListCellRenderer {

		private IndentIcon indentIcon = null;

		public DirectoryCellRenderer() {
			super();
			setOpaque(true);
			indentIcon = new IndentIcon();
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			LocalFile lf = (LocalFile) value;
			// if(lf == null) {
			// setText("null");
			// return this;
			// }
			setText(lf.getDisplayName());
			indentIcon.setIcon(lf.getIcon());
			indentIcon.setDepth(index + 1);
			setIcon(indentIcon);
			setBackground(isSelected ? list.getSelectionBackground() : list
					.getBackground());
			setForeground(isSelected ? list.getSelectionForeground() : list
					.getForeground());
			setFont(list.getFont());
			return this;
		}
	}

	public void selectAll() {
		table.selectAll();
	}

	public void invertSelection() {
		int rowCount = model.getRowCount();
		ListSelectionModel selectionModel = table.getSelectionModel();
		for (int i = 0; i < rowCount; i++) {
			if (selectionModel.isSelectedIndex(i)) {
				selectionModel.removeSelectionInterval(i, i);
			} else {
				selectionModel.addSelectionInterval(i, i);
			}
		}
	}
}