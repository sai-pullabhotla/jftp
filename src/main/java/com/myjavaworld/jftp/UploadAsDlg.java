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
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MTextField;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * 
 * @author Sai Pullabhotla
 * 
 */
public class UploadAsDlg extends MDialog implements ActionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.UploadAsDlg");
	private static final String HELP_ID = "transfer.uploadAs";
	private MTextField tfFileName = null;
	private MButton butUpload = null;
	private MButton butCancel = null;
	private MButton butHelp = null;
	private LocalFile localFile = null;
	private boolean approved = false;

	public UploadAsDlg(JFTP jftp, LocalFile localFile) {
		super(jftp);
		this.localFile = localFile;
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

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butUpload) {
			if (!validateInput()) {
				return;
			}
			approved = true;
			setVisible(false);
		}
		if (evt.getSource() == butCancel) {
			setVisible(false);
		}
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

		MessageFormat formatter = new MessageFormat(
				resources.getString("text.fileName"));
		String label = formatter.format(new Object[] { localFile.getName() });
		MLabel labFileName = new MLabel(label);
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
