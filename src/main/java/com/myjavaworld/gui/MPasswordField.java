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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPasswordField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * An extension of <code>MPasswordField</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 1.0
 * 
 */
public class MPasswordField extends JPasswordField implements MTextComponent,
		MouseListener {

	// private UndoManager undoManager = null;
	/**
	 * Creates an instance of <code>MPasswordField</code>.
	 * 
	 */
	public MPasswordField() {
		super();
		setup();
	}

	/**
	 * Creates an instance of <code>MPasswordField</code>.
	 * 
	 * @param columns
	 *            Display size
	 * 
	 */
	public MPasswordField(int columns) {
		super(columns);
		setup();
	}

	public void setMaximumLength(int maximumLength) {
		MPlainDocument model = (MPlainDocument) getDocument();
		model.setMaximumLength(maximumLength);
	}

	public int getMaximumLength() {
		MPlainDocument model = (MPlainDocument) getDocument();
		return model.getMaximumLength();
	}

	public void setCharacterCase(int characterCase) {
		MPlainDocument model = (MPlainDocument) getDocument();
		model.setCharacterCase(characterCase);
	}

	public int getCharacterCase() {
		MPlainDocument model = (MPlainDocument) getDocument();
		return model.getCharacterCase();
	}

	public void setUndoLimit(int undoLimit) {
		// undoManager.setLimit(undoLimit);
	}

	public int getUndoLimit() {
		// return undoManager.getLimit();
		return 0;
	}

	public void delete() {
		Document model = getDocument();
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		if (!(selectionStart < 0 && selectionEnd < 0)) {
			try {
				model.remove(selectionStart, selectionEnd - selectionStart);
			} catch (BadLocationException exp) {
				System.err.println(exp);
				exp.printStackTrace();
			}
		}
	}

	public void undo() throws CannotUndoException {
		// undoManager.undo();
	}

	public void redo() throws CannotRedoException {
		// undoManager.redo();
	}

	public boolean canCut() {
		return false;
	}

	public boolean canCopy() {
		return false;
	}

	public boolean canPaste() {
		if (!isEditable()) {
			return false;
		}
		Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = clipBoard.getContents(this);
		return t.isDataFlavorSupported(DataFlavor.stringFlavor);
	}

	public boolean canDelete() {
		if (!isEditable()) {
			return false;
		}
		return getSelectionEnd() - getSelectionStart() > 0;
	}

	public boolean canSelectAll() {
		return true;
	}

	public boolean canUndo() {
		return false;
		// return undoManager.canUndo();
	}

	public boolean canRedo() {
		return false;
		// return undoManager.canRedo();
	}

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseExited(MouseEvent evt) {
	}

	public void mousePressed(MouseEvent evt) {
		requestFocusInWindow();
		if (evt.isPopupTrigger()) {
			EditPopupMenu.getInstance().show(this, evt.getX(), evt.getY());
		}
	}

	public void mouseReleased(MouseEvent evt) {
		if (evt.isPopupTrigger()) {
			EditPopupMenu.getInstance().show(this, evt.getX(), evt.getY());
		}
	}

	public void mouseClicked(MouseEvent evt) {
		// if (SwingUtilities.isRightMouseButton(evt)) {
		// EditPopupMenu.getInstance().show(this, evt.getX(), evt.getY());
		// }
	}

	@Override
	protected Document createDefaultModel() {
		SingleLineDocument model = new SingleLineDocument();
		return model;
	}

	// protected void processMouseEvent(MouseEvent evt) {
	// super.processMouseEvent(evt);
	// if (SwingUtilities.isRightMouseButton(evt)
	// && evt.getID() == MouseEvent.MOUSE_RELEASED) {
	// requestFocusInWindow();
	// EditPopupMenu.getInstance().show(this, evt.getX(), evt.getY());
	// }
	// }
	@Override
	protected void processFocusEvent(FocusEvent evt) {
		super.processFocusEvent(evt);
		if (evt.getID() == FocusEvent.FOCUS_GAINED && !evt.isTemporary()) {
			selectAll();
		} else if (evt.getID() == FocusEvent.FOCUS_LOST && !evt.isTemporary()) {
			select(0, 0);
		}
	}

	private void setup() {
		// undoManager = new UndoManager();
		// setUndoLimit(1);
		// getDocument().addUndoableEditListener(undoManager);
		addMouseListener(this);
	}
}
