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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import com.myjavaworld.util.ResourceLoader;

/**
 * Standard Popup menu for text components.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 1.0
 * 
 */
public class EditPopupMenu extends MPopupMenu implements ActionListener {

	private static EditPopupMenu instance = null;
	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.gui.EditPopupMenu");
	private MTextComponent textComponent = null;
	// Menu Items.
	private MMenuItem miUndo = null;
	private MMenuItem miRedo = null;
	private MMenuItem miCut = null;
	private MMenuItem miCopy = null;
	private MMenuItem miPaste = null;
	private MMenuItem miDelete = null;
	private MMenuItem miSelectAll = null;

	private EditPopupMenu() {
		super();
		initComponents();
	}

	/**
	 * Returns an instance of <code>EditPopupMenu</code>.
	 * 
	 * @return Edit popup menu
	 * 
	 */
	public static synchronized EditPopupMenu getInstance() {
		if (instance == null) {
			instance = new EditPopupMenu();
		}
		return instance;
	}

	/**
	 * Sets the current text component to the given component.
	 * 
	 * @param textComponent
	 *            Text component
	 * 
	 */
	public void setTextComponent(MTextComponent textComponent) {
		this.textComponent = textComponent;
		miUndo.setEnabled(textComponent.canUndo());
		miRedo.setEnabled(textComponent.canRedo());
		miCut.setEnabled(textComponent.canCut());
		miCopy.setEnabled(textComponent.canCopy());
		miPaste.setEnabled(textComponent.canPaste());
		miDelete.setEnabled(textComponent.canDelete());
		miSelectAll.setEnabled(textComponent.canSelectAll());
	}

	/**
	 * Returns the current text component for this popup menu.
	 * 
	 * @return Current text component.
	 * 
	 */
	public MTextComponent getTextCompoent() {
		return textComponent;
	}

	/**
	 * Displays this edit popup menu.
	 * 
	 * invoker Invoking component x X position y Y position
	 * 
	 */
	@Override
	public void show(Component invoker, int x, int y) {
		if (invoker instanceof MTextComponent) {
			setTextComponent((MTextComponent) invoker);
			super.show(invoker, x, y);
		} else {
			throw new IllegalArgumentException(
					"invoker must be an instance of MTextComponent");
		}
	}

	public void actionPerformed(ActionEvent evt) {
		if (textComponent == null) {
			return;
		}
		String command = evt.getActionCommand();
		if (command.equals("cmd.undo")) {
			textComponent.undo();
		} else if (command.equals("cmd.redo")) {
			textComponent.redo();
		} else if (command.equals("cmd.cut")) {
			textComponent.cut();
		} else if (command.equals("cmd.copy")) {
			textComponent.copy();
		} else if (command.equals("cmd.paste")) {
			textComponent.paste();
		} else if (command.equals("cmd.delete")) {
			textComponent.delete();
		} else if (command.equals("cmd.selectAll")) {
			textComponent.selectAll();
		}
	}

	private void initComponents() {
		miUndo = new MMenuItem(resources.getString("text.undo"));
		miUndo.setActionCommand("cmd.undo");
		miUndo.addActionListener(this);

		miRedo = new MMenuItem(resources.getString("text.redo"));
		miRedo.setActionCommand("cmd.redo");
		miRedo.addActionListener(this);

		miCut = new MMenuItem(resources.getString("text.cut"));
		miCut.setActionCommand("cmd.cut");
		miCut.addActionListener(this);

		miCopy = new MMenuItem(resources.getString("text.copy"));
		miCopy.setActionCommand("cmd.copy");
		miCopy.addActionListener(this);

		miPaste = new MMenuItem(resources.getString("text.paste"));
		miPaste.setActionCommand("cmd.paste");
		miPaste.addActionListener(this);

		miDelete = new MMenuItem(resources.getString("text.delete"));
		miDelete.setActionCommand("cmd.delete");
		miDelete.addActionListener(this);

		miSelectAll = new MMenuItem(resources.getString("text.selectAll"));
		miSelectAll.setActionCommand("cmd.selectAll");
		miSelectAll.addActionListener(this);

		add(miUndo);
		add(miRedo);
		addSeparator();
		add(miCut);
		add(miCopy);
		add(miPaste);
		add(miDelete);
		addSeparator();
		add(miSelectAll);
	}
}
