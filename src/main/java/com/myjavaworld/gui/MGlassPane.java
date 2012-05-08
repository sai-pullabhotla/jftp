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

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

/**
 * A glass pane used by <code>MFrame</code> objects to change their state to
 * busy state. This glasspane consumes all mouse and key events.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MGlassPane extends JComponent implements MouseListener,
		KeyListener {

	public static final Cursor NORMAL_CURSOR = Cursor
			.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	public static final Cursor BUSY_CURSOR = Cursor
			.getPredefinedCursor(Cursor.WAIT_CURSOR);

	/**
	 * Constructs an object of <code>MGlassPane</code>.
	 * 
	 */
	public MGlassPane() {
		super();
		// setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		addMouseListener(this);
		addKeyListener(this);
	}

	public void mouseEntered(MouseEvent evt) {
		evt.consume();
	}

	public void mouseExited(MouseEvent evt) {
		evt.consume();
	}

	public void mousePressed(MouseEvent evt) {
		evt.consume();
	}

	public void mouseReleased(MouseEvent evt) {
		evt.consume();
	}

	public void mouseClicked(MouseEvent evt) {
		evt.consume();
	}

	public void keyPressed(KeyEvent evt) {
		evt.consume();
	}

	public void keyReleased(KeyEvent evt) {
		evt.consume();
	}

	public void keyTyped(KeyEvent evt) {
		evt.consume();
	}
}