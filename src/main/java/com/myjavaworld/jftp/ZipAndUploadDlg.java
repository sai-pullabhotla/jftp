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
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MRadioButton;
import com.myjavaworld.gui.MTextField;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * 
 * @author Sai Pullabhotla
 * 
 */
public class ZipAndUploadDlg extends MDialog implements ActionListener,
		ItemListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.ZipAndUploadDlg");
	private static final String HELP_ID = "transfer.zipAndUpload";
	private MTextField tfFileName = null;
	private MRadioButton radioTempDir = null;
	private MRadioButton radioCurrentDir = null;
	private MRadioButton radioOtherDir = null;
	private MTextField tfOtherDir = null;
	private MButton butBrowse = null;
	private MButton butUpload = null;
	private MButton butCancel = null;
	private MButton butHelp = null;
	private boolean approved = false;
	private JFTP jftp = null;

	public ZipAndUploadDlg(JFTP jftp) {
		super(jftp);
		this.jftp = jftp;
		setTitle(resources.getString("title.dialog"));
		setModal(true);
		setResizable(false);
		initComponents();
		pack();
	}

	public boolean isApproved() {
		return approved;
	}

	public String getFileName() {
		if (approved) {
			return tfFileName.getText();
		}
		return null;
	}

	public boolean getDeleteOption() {
		return radioTempDir.isSelected();
	}

	public File getZipFile() {
		File zipFile = null;
		if (approved) {
			if (radioTempDir.isSelected()) {
				try {
					zipFile = File.createTempFile("jftp", ".zip");
				} catch (Exception exp) {
					GUIUtil.showError(this, exp);
				}
			} else if (radioCurrentDir.isSelected()) {
				zipFile = new File(jftp.getCurrentSession()
						.getLocalWorkingDirectory().getFile(), tfFileName
						.getText().trim());
			} else if (radioOtherDir.isSelected()) {
				zipFile = new File(tfOtherDir.getText().trim(), tfFileName
						.getText().trim());
			}
		}
		return zipFile;
	}

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if (source == butUpload) {
			if (!validateInput()) {
				return;
			}
			approved = true;
			setVisible(false);
		} else if (source == butCancel) {
			setVisible(false);
		} else if (source == radioOtherDir) {
			if (tfOtherDir.getText().trim().length() == 0) {
				browse();
			}
		} else if (source == butBrowse) {
			browse();
		}
	}

	public void itemStateChanged(ItemEvent evt) {
		boolean enable = radioOtherDir.isSelected();
		tfOtherDir.setEnabled(enable);
		butBrowse.setEnabled(enable);
	}

	@Override
	protected void escape() {
		butCancel.doClick();
	}

	private boolean validateInput() {
		String error = null;
		Component errorComponent = null;

		if (tfFileName.getText().trim().length() < 1) {
			error = resources.getString("error.fileName.required");
			errorComponent = tfFileName;
		} else if (radioOtherDir.isSelected()) {
			if (tfOtherDir.getText().trim().length() == 0) {
				error = resources.getString("error.otherDir.required");
				errorComponent = tfOtherDir;
			} else {
				File dir = new File(tfOtherDir.getText().trim());
				if (!dir.exists() || !dir.isDirectory()) {
					error = resources.getString("error.otherDir.doesNotExists");
					errorComponent = tfOtherDir;
				}
			}
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

	private void browse() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle(CommonResources
				.getString("title.selectDirectory"));
		chooser.setApproveButtonText(CommonResources.getString("text.select"));
		int option = chooser.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			File selectedDir = chooser.getSelectedFile();
			if (selectedDir != null) {
				tfOtherDir.setText(selectedDir.getAbsolutePath());
			}
		}
	}

	private void initComponents() {
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		MLabel labFileName = new MLabel(resources.getString("text.fileName"));
		tfFileName = new MTextField(30);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 12, 12, 12);
		getContentPane().add(labFileName, c);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 0, 12, 12);
		getContentPane().add(tfFileName, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(getOptionsPanel(), c);

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

	private Component getOptionsPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(resources
				.getString("title.options")));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		radioTempDir = new MRadioButton(
				resources.getString("text.zipToTempDir"), true);
		radioCurrentDir = new MRadioButton(
				resources.getString("text.zipToCurrentDir"), false);
		radioOtherDir = new MRadioButton(
				resources.getString("text.zipToOtherDir"), false);
		tfOtherDir = new MTextField(30);
		tfOtherDir.setEnabled(false);
		butBrowse = new MButton(CommonResources.getString("text.browse"));
		butBrowse.setEnabled(false);
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioTempDir);
		bg.add(radioCurrentDir);
		bg.add(radioOtherDir);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		panel.add(radioTempDir, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 3, 3, 3);
		panel.add(radioCurrentDir, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 3, 3, 3);
		panel.add(radioOtherDir, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 3, 3, 3);
		panel.add(tfOtherDir, c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0, 0, 3, 3);
		panel.add(butBrowse, c);

		radioOtherDir.addActionListener(this);
		radioOtherDir.addItemListener(this);
		butBrowse.addActionListener(this);

		return panel;
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butUpload = new MButton(resources.getString("text.upload"));
		butUpload.addActionListener(this);
		getRootPane().setDefaultButton(butUpload);
		butCancel = new MButton(CommonResources.getString("text.cancel"));
		butCancel.addActionListener(this);
		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		panel.add(butUpload);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butCancel);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);

		return panel;
	}
}
