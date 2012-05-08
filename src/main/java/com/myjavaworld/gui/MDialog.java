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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

/**
 * An extension of <code>javax.swing.JDialog</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MDialog extends JDialog implements WindowListener {

	/**
	 * Constructs an <code>MDialog</code> with no title and default invisible
	 * owner.
	 * 
	 */
	public MDialog() {
		super();
		configure();
	}

	/**
	 * Constructs an <code>MDialog</code> whose owner window is set to the given
	 * <code>owner</code>.
	 * 
	 * @param owner
	 *            Owner
	 * 
	 */
	public MDialog(Frame owner) {
		super(owner);
		configure();
	}

	/**
	 * Constructs an <code>MDialog</code> with the given owner window and title.
	 * 
	 * @param owner
	 *            Owner window
	 * @param title
	 *            Title for this dialog
	 * 
	 */
	public MDialog(Frame owner, String title) {
		super(owner, title);
		configure();
	}

	/**
	 * Constructs an <code>MDialog</code> with the given parameters.
	 * 
	 * @param owner
	 *            Owner frame
	 * @param title
	 *            Title
	 * @param modal
	 *            Whether or not this dialog is a modal dialog.
	 * 
	 */
	public MDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		configure();
	}

	/**
	 * Constructs an <code>MDialog</code> with the given parameters.
	 * 
	 * @param owner
	 *            Owner dialog.
	 * 
	 */
	public MDialog(Dialog owner) {
		super(owner);
		configure();
	}

	/**
	 * Constructs an <code>MDialog</code> with the given parameters.
	 * 
	 * @param owner
	 *            Owner dialog
	 * @param title
	 *            Title for this dialog
	 * 
	 */
	public MDialog(Dialog owner, String title) {
		super(owner, title);
		configure();
	}

	/**
	 * Constructs an <code>MDialog</code> with the given parameters.
	 * 
	 * @param owner
	 *            Owner dialog
	 * @param title
	 *            Title
	 * @param modal
	 *            Whther or not this dialog is a modal dialog
	 * 
	 */
	public MDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		configure();
	}

	/**
	 * Called when the ESCAPE key is pressed when this dialog has focus. By
	 * default, all dialogs will be disposed when the ESCAPE key is pressed. To
	 * provide a different behavior, subclass this class and override this
	 * method.
	 * 
	 */
	protected void escape() {
		// dispose();
		// System.gc();
		setVisible(false);
	}

	/**
	 * Configures this dialog.
	 */
	private void configure() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		getRootPane().registerKeyboardAction(new EscapeAction(), "cmd.escape",
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		setResizable(false);
		addWindowListener(this);
	}

	/**
	 * An action listener for ESCAPE key presses.
	 * 
	 */
	private class EscapeAction extends AbstractAction {

		public void actionPerformed(ActionEvent evt) {
			escape();
		}
	}

	public void windowOpened(WindowEvent evt) {
	}

	public void windowActivated(WindowEvent evt) {
	}

	public void windowDeactivated(WindowEvent evt) {
	}

	public void windowIconified(WindowEvent evt) {
	}

	public void windowDeiconified(WindowEvent evt) {
	}

	public void windowClosing(WindowEvent evt) {
		// dispose();
		// System.gc();
		setVisible(false);
	}

	public void windowClosed(WindowEvent evt) {
	}
}