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

/**
 * An interface that defines the contract for all major kinds of text
 * components.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 1.0
 * 
 */
public interface MTextComponent {

	/**
	 * Constant to indicate mixed case.
	 */
	public static final int MIXED_CASE = 0;
	/**
	 * Constant to indicate lower case.
	 */
	public static final int LOWER_CASE = 1;
	/**
	 * Constant to indicate upper case.
	 */
	public static final int UPPER_CASE = 2;

	/**
	 * Sets the character case to the given <code>characterCase</code>.
	 * 
	 * @param characterCase
	 *            Character case to set for this text component.
	 * 
	 */
	public void setCharacterCase(int characterCase);

	/**
	 * Gets the current chatacter case in use by this text component.
	 * 
	 * @return character case.
	 * 
	 */
	public int getCharacterCase();

	/**
	 * Sets the maxumum number of characters allowed in this text component to
	 * the given value, <code>maximumLength</code>.
	 * 
	 * @param maximumLength
	 *            Maximum length to set.
	 * 
	 */
	public void setMaximumLength(int maximumLength);

	/**
	 * Returns the maximum number of characters allowed in this text component.
	 * 
	 * @return maximum length
	 * 
	 */
	public int getMaximumLength();

	/**
	 * Sets the undo/redo limit to the given <code>limit</code>.
	 * 
	 * @param limit
	 *            Number of changes to be recorded by the undo/redo manager.
	 * 
	 */
	public void setUndoLimit(int limit);

	/**
	 * Returns the current undo/redo limit of this text component.
	 * 
	 * @return Undo/Redo limit.
	 * 
	 */
	public int getUndoLimit();

	/**
	 * Cuts the selected text from this text component and places it in the
	 * system clipboard.
	 * 
	 */
	public void cut();

	/**
	 * Copies the selected text from this text component to the system
	 * clipboard.
	 * 
	 */
	public void copy();

	/**
	 * Pastes the contents of system clipboard at the current caret position.
	 * 
	 */
	public void paste();

	/**
	 * Deletes the selected text from this text component.
	 * 
	 */
	public void delete();

	/**
	 * Selects all the text in this text component.
	 * 
	 */
	public void selectAll();

	/**
	 * Undo the changes made to this text component.
	 * 
	 */
	public void undo();

	/**
	 * Redo the changes to this text component.
	 * 
	 */
	public void redo();

	/**
	 * Checks to see if text can be cut from this text component.
	 * 
	 * @return <code>true</code>If the text can be cut from this text component.
	 *         <code>false</code>, otherwise.
	 * 
	 */
	public boolean canCut();

	/**
	 * Checks to see if text can be copied from this text component.
	 * 
	 * @return <code>true</code>, if text is copiable. <code>false</code>,
	 *         otherwise.
	 * 
	 */
	public boolean canCopy();

	/**
	 * Checks to see if text can be pasted in to this text component.
	 * 
	 * @return <code>true</code>, if text is pastable. <code>false</code>,
	 *         otherwise.
	 * 
	 */
	public boolean canPaste();

	/**
	 * Checks to see if text can be deleted from this text component.
	 * 
	 * return <code>true</code>, if text can be deleted from this text
	 * component. <code>false</code>, otherwise.
	 * 
	 */
	public boolean canDelete();

	/**
	 * Checks to see if whole text in this component can be selected.
	 * 
	 * @return <code>true</code>, if the text is selectable. <code>false</code>,
	 *         otherwise.
	 * 
	 */
	public boolean canSelectAll();

	/**
	 * Cecks to see if the changes made to this text component can be undone.
	 * 
	 * @return <code>true</code>, if the changes can be undone.
	 *         <code>false</code>, otherwise.
	 * 
	 */
	public boolean canUndo();

	/**
	 * Checks to see if the changes made to this component can be redone.
	 * 
	 * @return <code>true</code>, if the changes can be redone.
	 *         </code>false</code>, otherwise.
	 * 
	 */
	public boolean canRedo();
}
