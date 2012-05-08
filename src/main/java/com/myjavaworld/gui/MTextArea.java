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
package com.myjavaworld.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * An extension of <code>javax.swing.MTextArea</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 1.0
 * 
 */
public class MTextArea extends JTextArea implements MTextComponent,
		MouseListener {

	private UndoManager undoManager = null;

	/**
	 * Creates an instance of <code>MTextArea</code>.
	 * 
	 */
	public MTextArea() {
		super();
		setup();
	}

	/**
	 * Creates an instance of <code>MTextArea</code>.
	 * 
	 * @param rows
	 *            Display height
	 * @param columns
	 *            display width
	 * 
	 */
	public MTextArea(int rows, int columns) {
		super(rows, columns);
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
		undoManager.setLimit(undoLimit);
	}

	public int getUndoLimit() {
		return undoManager.getLimit();
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
		undoManager.undo();
	}

	public void redo() throws CannotRedoException {
		undoManager.redo();
	}

	public boolean canCut() {
		if (!isEditable()) {
			return false;
		}
		return getSelectionEnd() - getSelectionStart() > 0;
	}

	public boolean canCopy() {
		return getSelectionEnd() - getSelectionStart() > 0;
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
		return undoManager.canUndo();
	}

	public boolean canRedo() {
		return undoManager.canRedo();
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
		MPlainDocument model = new MPlainDocument();
		return model;
	}

	private void setup() {
		undoManager = new UndoManager();
		setUndoLimit(1);
		getDocument().addUndoableEditListener(undoManager);
		addMouseListener(this);
	}
}
