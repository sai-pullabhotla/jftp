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