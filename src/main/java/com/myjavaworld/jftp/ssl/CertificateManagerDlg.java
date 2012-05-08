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
package com.myjavaworld.jftp.ssl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.myjavaworld.gui.DateCellRenderer;
import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MScrollPane;
import com.myjavaworld.gui.MTable;
import com.myjavaworld.jftp.JFTP;
import com.myjavaworld.jftp.JFTPHelp2;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CertificateManagerDlg extends MDialog implements ActionListener,
		ListSelectionListener, ChangeListener, MouseListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.ssl.CertificateManagerDlg");
	private static final String HELP_ID = "security.certificatesManager";
	JTabbedPane tabs = null;
	private MTable serverCertificatesTable = null;
	private CertificateTableModel serverCertificatesTableModel = null;
	private MTable clientCertificatesTable = null;
	private CertificateTableModel clientCertificatesTableModel = null;
	private MButton butView = null;
	private MButton butDelete = null;
	private MButton butImport = null;
	private MButton butClose = null;
	private MButton butHelp = null;
	private Frame parent = null;
	private Action deleteCertificatesAction = null;

	public CertificateManagerDlg(Frame parent) {
		super(parent);
		this.parent = parent;
		setTitle(resources.getString("title.dialog"));
		setModal(true);
		setResizable(true);
		deleteCertificatesAction = new DeleteCertificateAction();
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), HELP_ID);
		initComponents();
		pack();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butView) {
			viewCertificate();
		} else if (evt.getSource() == butClose) {
			setVisible(false);
		} // else if(evt.getSource() == butDelete) {
			// deleteCertificates();
			// }
		else if (evt.getSource() == butImport) {
			importCertificates();
		}
	}

	public void valueChanged(ListSelectionEvent evt) {
		if (evt.getSource() == serverCertificatesTable.getSelectionModel()) {
			int selectionCount = serverCertificatesTable.getSelectedRowCount();
			butView.setEnabled(selectionCount == 1);
			butDelete.setEnabled(selectionCount > 0);
		} else if (evt.getSource() == clientCertificatesTable
				.getSelectionModel()) {
			int selectionCount = clientCertificatesTable.getSelectedRowCount();
			butView.setEnabled(selectionCount == 1);
			butDelete.setEnabled(selectionCount > 0);
		}
	}

	public void stateChanged(ChangeEvent evt) {
		if (tabs.getSelectedIndex() == 0) {
			int selectionCount = serverCertificatesTable.getSelectedRowCount();
			butView.setEnabled(selectionCount == 1);
			butDelete.setEnabled(selectionCount > 0);
		} else if (tabs.getSelectedIndex() == 1) {
			int selectionCount = clientCertificatesTable.getSelectedRowCount();
			butView.setEnabled(selectionCount == 1);
			butDelete.setEnabled(selectionCount > 0);
		}
	}

	@Override
	public void windowOpened(WindowEvent evt) {
		butView.setEnabled(false);
		butDelete.setEnabled(false);
	}

	public void mousePressed(MouseEvent evt) {
	}

	public void mouseReleased(MouseEvent evt) {
	}

	public void mouseClicked(MouseEvent evt) {
		if (evt.getClickCount() >= 2) {
			if (evt.getSource() == serverCertificatesTable) {
				int row = serverCertificatesTable.rowAtPoint(evt.getPoint());
				if (row >= 0) {
					viewServerCertificate();
				}
			} else if (evt.getSource() == clientCertificatesTable) {
				int row = serverCertificatesTable.rowAtPoint(evt.getPoint());
				if (row >= 0) {
					viewClientCertificate();
				}
			}
		}
	}

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseExited(MouseEvent evt) {
	}

	@Override
	protected void escape() {
		butClose.doClick();
	}

	private void viewCertificate() {
		if (tabs.getSelectedIndex() == 0) {
			viewServerCertificate();
		} else {
			viewClientCertificate();
		}
	}

	private void viewServerCertificate() {
		int selectedRow = serverCertificatesTable.getSelectedRow();
		if (selectedRow >= 0) {
			Certificate certificate = serverCertificatesTableModel
					.getCertificateAt(selectedRow);
			CertificateDlg dlg = new CertificateDlg(parent, certificate);
			dlg.setLocationRelativeTo(this);
			dlg.setVisible(true);
			dlg.dispose();
		}
	}

	private void viewClientCertificate() {
		int selectedRow = clientCertificatesTable.getSelectedRow();
		if (selectedRow >= 0) {
			Certificate certificate = clientCertificatesTableModel
					.getCertificateAt(selectedRow);
			CertificateDlg dlg = new CertificateDlg(parent, certificate);
			dlg.setLocationRelativeTo(this);
			dlg.setVisible(true);
			dlg.dispose();
		}
	}

	private void deleteCertificates() {
		if (tabs.getSelectedIndex() == 0) {
			deleteServerCertificates();
		} else {
			deleteClientCertificates();
		}
	}

	private void deleteServerCertificates() {
		int[] selectedRows = serverCertificatesTable.getSelectedRows();
		if (selectedRows == null || selectedRows.length == 0) {
			return;
		}
		int option = GUIUtil.showConfirmation(this,
				resources.getString("confirm.deleteServerCertificates"));
		if (option != JOptionPane.YES_OPTION) {
			return;
		}
		for (int i = 0; i < selectedRows.length; i++) {
			String alias = serverCertificatesTableModel
					.getAliasAt(selectedRows[i]);
			try {
				KeyStoreManager.deleteServerCertificate(alias);
			} catch (Exception exp) {
				System.err.println(exp);
			}
		}
		try {
			serverCertificatesTableModel.setKeyStore(KeyStoreManager
					.getServerCertificateStore());
		} catch (Exception exp) {
			System.err.println(exp);
		}
	}

	private void deleteClientCertificates() {
		int[] selectedRows = clientCertificatesTable.getSelectedRows();
		if (selectedRows == null || selectedRows.length == 0) {
			return;
		}
		int option = GUIUtil.showConfirmation(this,
				resources.getString("confirm.deleteClientCertificates"));
		if (option != JOptionPane.YES_OPTION) {
			return;
		}
		for (int i = 0; i < selectedRows.length; i++) {
			String alias = clientCertificatesTableModel
					.getAliasAt(selectedRows[i]);
			try {
				KeyStoreManager.deleteClientCertificate(alias);
			} catch (Exception exp) {
				System.err.println(exp);
			}
		}
		try {
			clientCertificatesTableModel.setKeyStore(KeyStoreManager
					.getClientCertificateStore());
		} catch (Exception exp) {
			System.err.println(exp);
		}
	}

	private void importCertificates() {
		if (tabs.getSelectedIndex() == 0) {
			importServerCertificates();
		} else {
			importClientCertificates();
		}
	}

	private void importServerCertificates() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogTitle(CommonResources.getString("title.selectFile"));
		// chooser.setApproveButtonText(resources.getString("butImport.text"));
		// chooser.setApproveButtonMnemonic(
		// resources.getString("butImport.mnemonic").charAt(0));
		int option = chooser.showOpenDialog(this);
		if (option != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selectedFile = chooser.getSelectedFile();
		if (selectedFile == null) {
			return;
		}
		FileInputStream fin = null;

		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			fin = new FileInputStream(selectedFile);
			Collection certificates = cf.generateCertificates(fin);
			Iterator iterator = certificates.iterator();
			while (iterator.hasNext()) {
				Certificate cert = (Certificate) iterator.next();
				KeyStoreManager
						.addServerCertificate(new Certificate[] { cert });
			}
			serverCertificatesTableModel.setKeyStore(KeyStoreManager
					.getServerCertificateStore());
		} catch (Exception exp) {
			GUIUtil.showError(this, exp);
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
			} catch (Exception exp) {
			}
		}
	}

	private void importClientCertificates() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogTitle(CommonResources.getString("title.selectFile"));
		chooser.setApproveButtonText(resources.getString("text.import"));
		int option = chooser.showOpenDialog(this);
		if (option != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selectedFile = chooser.getSelectedFile();
		if (selectedFile == null) {
			return;
		}
		FileInputStream fin = null;

		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			fin = new FileInputStream(selectedFile);
			Collection certificates = cf.generateCertificates(fin);
			Iterator iterator = certificates.iterator();
			while (iterator.hasNext()) {
				Certificate cert = (Certificate) iterator.next();
				KeyStoreManager
						.addClientCertificate(new Certificate[] { cert });
			}
			clientCertificatesTableModel.setKeyStore(KeyStoreManager
					.getClientCertificateStore());
		} catch (Exception exp) {
			GUIUtil.showError(this, exp);
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
			} catch (Exception exp) {
			}
		}
	}

	private void initComponents() {
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		tabs = new JTabbedPane();
		tabs.add(resources.getString("text.serverCertificates"),
				getServerCertificatesTab());
		// tabs.add(resources.getString("labClientCertificates.text"),
		// getClientCertificatesTab());

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(0, 0, 0, 0);
		c.fill = GridBagConstraints.BOTH;
		getContentPane().add(tabs, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.insets = new Insets(6, 12, 12, 12);
		c.fill = GridBagConstraints.NONE;
		getContentPane().add(getCommandButtons(), c);

		tabs.addChangeListener(this);

		InputMap inputMap = serverCertificatesTable.getInputMap();
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),
				"deleteCertificate");
		ActionMap actionMap = serverCertificatesTable.getActionMap();
		actionMap.put("deleteCertificate", deleteCertificatesAction);

		// InputMap inputMap2 = clientCertificatesTable.getInputMap();
		// inputMap2.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),
		// "deleteCertificate");
		// ActionMap actionMap2 = clientCertificatesTable.getActionMap();
		// actionMap2.put("deleteCertificate", deleteCertificatesAction);
	}

	private Component getServerCertificatesTab() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		try {
			serverCertificatesTableModel = new CertificateTableModel(
					KeyStoreManager.getServerCertificateStore());
			serverCertificatesTable = new MTable(serverCertificatesTableModel);
			serverCertificatesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			serverCertificatesTable.getColumnModel().getColumn(0)
					.setPreferredWidth(150);
			serverCertificatesTable.getColumnModel().getColumn(1)
					.setPreferredWidth(150);
			serverCertificatesTable.getColumnModel().getColumn(2)
					.setPreferredWidth(150);
			serverCertificatesTable.getColumnModel().getColumn(3)
					.setPreferredWidth(150);
			serverCertificatesTable.setDefaultRenderer(
					Date.class,
					new DateCellRenderer(JFTP.prefs.getDateFormat(), JFTP.prefs
							.getTimeFormat()));
		} catch (Exception exp) {
			System.err.println(exp);
		}

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(12, 12, 12, 12);
		c.fill = GridBagConstraints.BOTH;
		MScrollPane scrollPane = new MScrollPane(serverCertificatesTable);
		scrollPane.getViewport().setPreferredSize(new Dimension(600, 300));
		panel.add(scrollPane, c);

		serverCertificatesTable.getSelectionModel().addListSelectionListener(
				this);
		serverCertificatesTable.addMouseListener(this);

		return panel;
	}

	// private Component getClientCertificatesTab() {
	// JPanel panel = new JPanel(new GridBagLayout());
	// GridBagConstraints c = new GridBagConstraints();
	// c.anchor = GridBagConstraints.WEST;
	//
	// try {
	// clientCertificatesTableModel = new CertificateTableModel(
	// KeyStoreManager.getClientCertificateStore());
	// clientCertificatesTable = new MTable(clientCertificatesTableModel);
	// clientCertificatesTable.setAutoResizeMode(MTable.AUTO_RESIZE_OFF);
	// clientCertificatesTable.getColumnModel().
	// getColumn(0).setPreferredWidth(150);
	// clientCertificatesTable.getColumnModel().
	// getColumn(1).setPreferredWidth(150);
	// clientCertificatesTable.getColumnModel().
	// getColumn(2).setPreferredWidth(150);
	// clientCertificatesTable.getColumnModel().
	// getColumn(3).setPreferredWidth(150);
	// clientCertificatesTable.setDefaultRenderer(Date.class,
	// new DateCellRenderer(JFTP.prefs.getDateFormat(),
	// JFTP.prefs.getTimeFormat()));
	// }
	// catch(Exception exp) {
	// System.err.println(exp);
	// }
	//
	// c.gridx = 0;
	// c.gridy = 0;
	// c.gridwidth = 1;
	// c.gridheight = 1;
	// c.weightx = 1.0;
	// c.weighty = 1.0;
	// c.insets = new Insets(12, 12, 12, 12);
	// c.fill = GridBagConstraints.BOTH;
	// MScrollPane scrollPane = new MScrollPane(clientCertificatesTable);
	// scrollPane.getViewport().setPreferredSize(new Dimension(600, 300));
	// panel.add(scrollPane, c);
	//
	// clientCertificatesTable.getSelectionModel().addListSelectionListener(this);
	// clientCertificatesTable.addMouseListener(this);
	//
	// return panel;
	// }
	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butView = new MButton(CommonResources.getString("text.view"));
		butView.addActionListener(this);
		getRootPane().setDefaultButton(butView);

		butDelete = new MButton(CommonResources.getString("text.delete"));
		butDelete.addActionListener(deleteCertificatesAction);

		butImport = new MButton(resources.getString("text.import"));
		butImport.addActionListener(this);

		butClose = new MButton(CommonResources.getString("text.close"));
		butClose.addActionListener(this);

		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		panel.add(butView);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butDelete);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butImport);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butClose);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);

		return panel;
	}

	class DeleteCertificateAction extends AbstractAction {

		public void actionPerformed(ActionEvent evt) {
			deleteCertificates();
		}
	}
}
