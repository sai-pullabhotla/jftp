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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MRadioButton;
import com.myjavaworld.gui.MTextField;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * 
 * @author Sai Pullabhotla
 * 
 */
public class DownloadAndUnzipDlg extends MDialog implements ActionListener,
		ItemListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.DownloadAndUnzipDlg");
	private static final String HELP_ID = "transfer.downloadAndUnzip";
	private JFTP jftp = null;
	private MRadioButton radioDownloadToTempDir = null;
	private MRadioButton radioDownloadToCurrentDir = null;
	private MRadioButton radioDownloadToOtherDir = null;
	private MTextField tfDownloadDir = null;
	private MButton butBrowseDownloadDir = null;
	private MRadioButton radioUnzipToCurrentDir = null;
	private MRadioButton radioUnzipToOtherDir = null;
	private MTextField tfUnzipDir = null;
	private MButton butBrowseUnzipDir = null;
	private MButton butDownload = null;
	private MButton butCancel = null;
	private MButton butHelp = null;
	private RemoteFile remoteFile = null;
	private boolean approved = false;

	public DownloadAndUnzipDlg(JFTP jftp, RemoteFile remoteFile) {
		super(jftp);
		this.jftp = jftp;
		this.remoteFile = remoteFile;
		setTitle(resources.getString("title.dialog"));
		setModal(true);
		setResizable(false);
		initComponents();
		pack();
	}

	public boolean isApproved() {
		return approved;
	}

	public File getUnzipDirectory() {
		File unzipDir = null;
		if (approved) {
			if (radioUnzipToCurrentDir.isSelected()) {
				return jftp.getCurrentSession().getLocalWorkingDirectory()
						.getFile();
			} else if (radioUnzipToOtherDir.isSelected()) {
				return new File(tfUnzipDir.getText().trim());
			}
		}
		return unzipDir;
	}

	public File getDownloadTo() {
		File downloadFile = null;
		if (approved) {
			if (radioDownloadToTempDir.isSelected()) {
				try {
					downloadFile = File.createTempFile("jftp", ".zip");
				} catch (IOException exp) {
					GUIUtil.showError(this, exp);
				}
			} else if (radioDownloadToCurrentDir.isSelected()) {
				downloadFile = new File(jftp.getCurrentSession()
						.getLocalWorkingDirectory().getFile(),
						remoteFile.getNormalizedName());
			} else if (radioDownloadToOtherDir.isSelected()) {
				downloadFile = new File(tfDownloadDir.getText().trim(),
						remoteFile.getNormalizedName());
			}
		}
		return downloadFile;
	}

	public boolean getDeleteOption() {
		return radioDownloadToTempDir.isSelected();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butDownload) {
			if (!validateInput()) {
				return;
			}
			approved = true;
			setVisible(false);
		} else if (evt.getSource() == butCancel) {
			setVisible(false);
		} else if (evt.getSource() == butBrowseDownloadDir
				|| (evt.getSource() == radioDownloadToOtherDir && tfDownloadDir
						.getText().trim().length() == 0)) {
			browseDownloadDir();
		} else if (evt.getSource() == butBrowseUnzipDir
				|| (evt.getSource() == radioUnzipToOtherDir && tfUnzipDir
						.getText().trim().length() == 0)) {
			browseUnzipDir();
		}
	}

	public void itemStateChanged(ItemEvent evt) {
		if (evt.getSource() == radioUnzipToOtherDir) {
			tfUnzipDir.setEnabled(radioUnzipToOtherDir.isSelected());
			butBrowseUnzipDir.setEnabled(radioUnzipToOtherDir.isSelected());
		} else if (evt.getSource() == radioDownloadToOtherDir) {
			tfDownloadDir.setEnabled(radioDownloadToOtherDir.isSelected());
			butBrowseDownloadDir.setEnabled(radioDownloadToOtherDir
					.isSelected());
		}
	}

	@Override
	protected void escape() {
		butCancel.doClick();
	}

	private void browseUnzipDir() {
		File dir = browse();
		if (dir != null) {
			tfUnzipDir.setText(dir.getAbsolutePath());
		}
	}

	private void browseDownloadDir() {
		File dir = browse();
		if (dir != null) {
			tfDownloadDir.setText(dir.getAbsolutePath());
		}
	}

	private File browse() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle(CommonResources
				.getString("title.selectDirectory"));
		chooser.setApproveButtonText(CommonResources.getString("text.select"));
		int option = chooser.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			File selectedDir = chooser.getSelectedFile();
			return selectedDir;
		}
		return null;
	}

	private boolean validateInput() {
		String error = null;
		Component errorComponent = null;

		if (radioUnzipToOtherDir.isSelected()
				&& tfUnzipDir.getText().trim().length() == 0) {
			error = resources.getString("error.unzipDir.required");
			errorComponent = tfUnzipDir;
		} else if (radioDownloadToOtherDir.isSelected()
				&& tfDownloadDir.getText().trim().length() == 0) {
			error = resources.getString("error.downloadDir.required");
			errorComponent = tfDownloadDir;
		}
		if (error != null) {
			GUIUtil.showError(this, error);
			if (errorComponent != null) {
				errorComponent.requestFocusInWindow();
			}
			return false;
		}
		return true;
	}

	private void initComponents() {
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 12, 12, 12);
		getContentPane().add(getUnzipOptions(), c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(getDownloadOptions(), c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(6, 12, 12, 12);
		c.anchor = GridBagConstraints.SOUTHEAST;
		getContentPane().add(getCommandButtons(), c);
	}

	private Component getUnzipOptions() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder(resources
				.getString("text.unzipOptions")));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		radioUnzipToCurrentDir = new MRadioButton(
				resources.getString("text.unzipToCurrentDir"), true);
		radioUnzipToOtherDir = new MRadioButton(
				resources.getString("text.unzipToOtherDir"), false);
		tfUnzipDir = new MTextField(30);
		butBrowseUnzipDir = new MButton(
				CommonResources.getString("text.browse"));
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioUnzipToCurrentDir);
		bg.add(radioUnzipToOtherDir);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		panel.add(radioUnzipToCurrentDir, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 3, 3, 3);
		panel.add(radioUnzipToOtherDir, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 24, 3, 3);
		panel.add(tfUnzipDir, c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0, 0, 3, 3);
		panel.add(butBrowseUnzipDir, c);

		radioUnzipToOtherDir.addItemListener(this);
		radioUnzipToOtherDir.addActionListener(this);
		butBrowseUnzipDir.addActionListener(this);
		tfUnzipDir.setEnabled(false);
		butBrowseUnzipDir.setEnabled(false);

		return panel;
	}

	private Component getDownloadOptions() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder(resources
				.getString("text.downloadOptions")));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		radioDownloadToTempDir = new MRadioButton(
				resources.getString("text.downloadToTempDir"), true);
		radioDownloadToCurrentDir = new MRadioButton(
				resources.getString("text.downloadToCurrentDir"), false);
		radioDownloadToOtherDir = new MRadioButton(
				resources.getString("text.downloadToOtherDir"), false);
		tfDownloadDir = new MTextField(30);
		butBrowseDownloadDir = new MButton(
				CommonResources.getString("text.browse"));
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioDownloadToTempDir);
		bg.add(radioDownloadToCurrentDir);
		bg.add(radioDownloadToOtherDir);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		panel.add(radioDownloadToTempDir, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 3, 3, 3);
		panel.add(radioDownloadToCurrentDir, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 3, 3, 3);
		panel.add(radioDownloadToOtherDir, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 24, 3, 3);
		panel.add(tfDownloadDir, c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0, 0, 3, 3);
		panel.add(butBrowseDownloadDir, c);

		radioDownloadToOtherDir.addItemListener(this);
		radioDownloadToOtherDir.addActionListener(this);
		butBrowseDownloadDir.addActionListener(this);
		tfDownloadDir.setEnabled(false);
		butBrowseDownloadDir.setEnabled(false);
		return panel;
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butDownload = new MButton(resources.getString("text.download"));
		butDownload.addActionListener(this);
		getRootPane().setDefaultButton(butDownload);
		butCancel = new MButton(CommonResources.getString("text.cancel"));
		butCancel.addActionListener(this);
		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		panel.add(butDownload);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butCancel);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);

		return panel;
	}
}
