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
package com.myjavaworld.gui;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * @author Sai Pullabhotla
 */
public class ProgressDialog extends MDialog {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.gui.ProgressDialog");
	private MButton butCancel = null;
	private MLabel labMessage = null;
	private JProgressBar progressBar = null;

	public ProgressDialog(Frame parent) {
		this(parent, resources.getString("title.dialog"), true);
	}

	public ProgressDialog(Dialog parent) {
		this(parent, resources.getString("title.dialog"), true);
	}

	public ProgressDialog(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public ProgressDialog(Dialog parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public void setText(String text) {
		labMessage.setText(text);
	}

	public void setIndeterminate(boolean indterminate) {
		progressBar.setIndeterminate(indterminate);
	}

	public void setMinimum(int minimum) {
		progressBar.setMinimum(minimum);
	}

	public void setMaximum(int maximum) {
		progressBar.setMaximum(maximum);
	}

	public void setProgress(int value) {
		progressBar.setValue(value);
	}

	public void setCancelButtonEnabled(boolean enable) {
		butCancel.setEnabled(enable);
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			pack();
		}
		super.setVisible(visible);
	}

	public void addActionListener(ActionListener al) {
		butCancel.addActionListener(al);
	}

	public void removeActionListener(ActionListener al) {
		butCancel.removeActionListener(al);
	}

	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		labMessage = new MLabel(resources.getString("message.wait"));
		butCancel = new MButton(CommonResources.getString("text.cancel"));
		butCancel.setEnabled(false);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 12, 12, 12);
		getContentPane().add(labMessage, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(progressBar, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(6, 12, 12, 12);
		c.anchor = GridBagConstraints.SOUTHEAST;
		getContentPane().add(butCancel, c);
	}
}