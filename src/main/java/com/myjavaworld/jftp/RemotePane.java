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
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.gui.DateCellRenderer;
import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MComboBox;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MScrollPane;
import com.myjavaworld.gui.MTable;
import com.myjavaworld.gui.MTableCellRenderer;
import com.myjavaworld.gui.MTableHeaderRenderer;
import com.myjavaworld.gui.NumericCellRenderer;
import com.myjavaworld.jftp.actions.DeleteRemoteFileAction;
import com.myjavaworld.jftp.actions.OpenRemoteFileAction;
import com.myjavaworld.util.ResourceLoader;

/**
 * Remote file system view.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class RemotePane extends JPanel implements ActionListener,
		MouseListener, ListSelectionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.RemotePane");
	private static final Icon UP_ARROW_ICON = JFTPUtil.getIcon("upArrow16.gif");
	private static final Icon DOWN_ARROW_ICON = JFTPUtil
			.getIcon("downArrow16.gif");
	private FTPSession session = null;
	private MComboBox comboWorkingDirectory = null;
	private MButton butUp = null;
	private RemoteFileTableModel model = null;
	private MTable table = null;
	private MScrollPane scroller = null;
	private MLabel labStatus = null;
	private RemoteFileComparator comparator = null;
	private boolean updateComboWorkingDirectory = true;
	private MessageFormat statusFormat1 = null;
	private MessageFormat statusFormat2 = null;
	private Object[] statusArgs = null;

	public RemotePane(FTPSession session) {
		super();
		this.session = session;
		comparator = new RemoteFileComparator();
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

	public void setData(RemoteFile dir, RemoteFile[] data) {
		if (data == null) {
			data = new RemoteFile[0];
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

	public RemoteFile[] getData() {
		return model.getData();
	}

	public int getSelectionCount() {
		return table.getSelectedRowCount();
	}

	public RemoteFile getSelectedFile() {
		int row = table.getSelectedRow();
		if (row < 0) {
			return null;
		}
		return model.getFileAt(row);
	}

	public RemoteFile[] getSelectedFiles() {
		int[] selectedRows = table.getSelectedRows();
		if (selectedRows == null) {
			return new RemoteFile[0];
		}
		RemoteFile[] selectedFiles = new RemoteFile[selectedRows.length];
		for (int i = 0; i < selectedRows.length; i++) {
			selectedFiles[i] = model.getFileAt(selectedRows[i]);
		}
		return selectedFiles;
	}

	public void refresh() {
		session.setRemoteWorkingDirectory(session.getRemoteWorkingDirectory());
	}

	public void clearAll() {
		comboWorkingDirectory.setModel(new DefaultComboBoxModel());
		model.setData(new RemoteFile[0]);
	}

	public void valueChanged(ListSelectionEvent evt) {
		updateStatus();
		session.updateToolBar();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butUp) {
			session.upRemoteWorkingDirectory();
		} else if (evt.getSource() == comboWorkingDirectory) {
			RemoteFile selectedFile = (RemoteFile) comboWorkingDirectory
					.getSelectedItem();
			updateComboWorkingDirectory = false;
			session.setRemoteWorkingDirectory(selectedFile);
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
					if (column < 4) {
						sort(column);
					}
				}
			}
		}
	}

	public void mouseClicked(MouseEvent evt) {
	}

	private void tableRightClicked(MouseEvent evt) {
		JPopupMenu popup = RemoteSystemPopupMenu.getInstance(session.getJFTP());
		popup.show((Component) evt.getSource(), evt.getX(), evt.getY());
	}

	private void scrollerRightClicked(MouseEvent evt) {
		table.clearSelection();
		JPopupMenu popup = RemoteSystemPopupMenu.getInstance(session.getJFTP());
		popup.show((Component) evt.getSource(), evt.getX(), evt.getY());
	}

	private void doubleClicked(MouseEvent evt) {
		int row = table.rowAtPoint(evt.getPoint());
		if (row < 0) {
			return;
		}
		RemoteFile file = model.getFileAt(row);
		if (file.isDirectory() || file.isLink()) {
			session.setRemoteWorkingDirectory(file);
		} else {
			OpenRemoteFileAction.getInstance(session.getJFTP())
					.actionPerformed(null);
		}
	}

	private void updateComboWorkingDirectory(RemoteFile dir) {
		comboWorkingDirectory.removeActionListener(this);
		int count = comboWorkingDirectory.getItemCount();
		for (int i = 0; i < count; i++) {
			if (dir.equals(comboWorkingDirectory.getItemAt(i))) {
				comboWorkingDirectory.setSelectedIndex(i);
				comboWorkingDirectory.addActionListener(this);
				return;
			}
		}
		comboWorkingDirectory.insertItemAt(dir, 0);
		comboWorkingDirectory.setSelectedIndex(0);
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
			labStatus.setText(statusFormat2.format(statusArgs));
			return;
		}
		count = model.getRowCount();
		for (int i = 0; i < count; i++) {
			size += model.getFileAt(i).getSize();
		}
		statusArgs[0] = new Integer(count);
		statusArgs[1] = new Long(size);
		labStatus.setText(statusFormat1.format(statusArgs));
	}

	private void sort(int col) {
		int compareBy = comparator.getCompareBy();
		int order = comparator.getOrder();
		if (compareBy == col + 1) {
			order = order == RemoteFileComparator.ASC_ORDER ? RemoteFileComparator.DESC_ORDER
					: RemoteFileComparator.ASC_ORDER;
		} else {
			compareBy = col + 1;
			order = RemoteFileComparator.ASC_ORDER;
		}
		comparator.setCompareBy(compareBy);
		comparator.setOrder(order);
		RemoteFile[] data = model.getData();
		Arrays.sort(model.getData(), comparator);
		model.setData(data);
		updateTableHeader();
	}

	private void updateTableHeader() {
		int compareBy = comparator.getCompareBy();
		int order = comparator.getOrder();
		Icon icon = order == RemoteFileComparator.ASC_ORDER ? UP_ARROW_ICON
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
		c.fill = GridBagConstraints.HORIZONTAL;

		comboWorkingDirectory = new MComboBox();
		comboWorkingDirectory.setToolTipText(resources
				.getString("tooltip.workingDirectory"));
		comboWorkingDirectory.addActionListener(this);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(2, 0, 2, 2);
		add(comboWorkingDirectory, c);

		// butUp = new MButton(UIManager.getIcon("FileChooser.upFolderIcon"));
		butUp = new MButton(JFTPUtil.getIcon("upDirectory16.gif"));
		butUp.setToolTipText(resources.getString("tooltip.up"));
		butUp.addActionListener(this);
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(2, 0, 2, 0);
		add(butUp, c);

		model = new RemoteFileTableModel();
		table = new MTable(model) {

			@Override
			public boolean getScrollableTracksViewportWidth() {
				return getPreferredSize().width < getParent().getWidth();
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
		c.gridwidth = 4;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 0, 2, 0);
		add(scroller, c);

		labStatus = new MLabel(" ");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 4;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 5, 2, 0);
		add(labStatus, c);
	}

	private void configureTable() {
		table.setName("remotePane");
		scroller.setName("remotePane");
		TransferHandler th = new DnDTransferHandler(session.getJFTP());
		table.setDragEnabled(true);
		scroller.setTransferHandler(th);
		table.setTransferHandler(th);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(75);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);

		int dateFormat = JFTP.prefs.getDateFormat();
		int timeFormat = JFTP.prefs.getTimeFormat();
		table.setDefaultRenderer(RemoteFile.class, new RemoteFileCellRenderer());
		table.setDefaultRenderer(Long.class, new NumericCellRenderer());
		table.setDefaultRenderer(Date.class, new DateCellRenderer(dateFormat,
				timeFormat));
		table.setDefaultRenderer(Object.class, new MTableCellRenderer());

		table.addMouseListener(this);
		scroller.addMouseListener(this);
		table.getSelectionModel().addListSelectionListener(this);

		table.getTableHeader().setDefaultRenderer(new MTableHeaderRenderer());
		table.getTableHeader().addMouseListener(this);
		table.registerKeyboardAction(
				DeleteRemoteFileAction.getInstance(session.getJFTP()),
				"cmd.deleteRemoteFile",
				KeyStroke.getKeyStroke(GUIUtil.getDeleteKey(), 0),
				JComponent.WHEN_FOCUSED);
		table.registerKeyboardAction(
				OpenRemoteFileAction.getInstance(session.getJFTP()),
				"cmd.openRemoteFile",
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_FOCUSED);
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
