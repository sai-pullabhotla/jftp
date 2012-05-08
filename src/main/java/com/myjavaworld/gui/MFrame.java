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

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * An extension of <code>javax.swing.JFrame</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MFrame extends JFrame {

	private MGlassPane glassPane = null;

	/**
	 * Constructs an object of <code>MFrame</code> with the given parameters.
	 * 
	 * @param title
	 *            Title
	 * 
	 */
	public MFrame(String title) {
		this(title, new Insets(0, 0, 0, 0));
	}

	/**
	 * Constructs an object of <code>MFrame</code> with the given parameters.
	 * 
	 * @param title
	 *            Title for this frame.
	 * @param insets
	 *            Insets to set on the content pane of this frame.
	 * 
	 */
	public MFrame(String title, final Insets insets) {
		super(title);
		setContentPane(new JPanel() {

			@Override
			public Insets getInsets() {
				return insets;
			}
		});
		getContentPane().setLayout(new BorderLayout());
		glassPane = new MGlassPane();
		this.setGlassPane(glassPane);
	}

	/**
	 * Sets the state of this frame to the given state.
	 * 
	 * @param busy
	 *            If this is <code>true</code>, all mouse and key events will be
	 *            consumed and the cursor on the frame is set to a busy
	 *            (hour-glass) cursor. If false, the frame will have the regular
	 *            behaviour and cursor.
	 * 
	 */
	public void setBusy(boolean busy) {
		glassPane.setCursor(busy ? MGlassPane.BUSY_CURSOR
				: MGlassPane.NORMAL_CURSOR);
		glassPane.setVisible(busy);
	}
}