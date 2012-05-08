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
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JWindow;

/**
 * A generic splash window.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class SplashWindow extends JWindow {

	/**
	 * Creates an instance of <code>SplashWindow</code>.
	 * 
	 * @param icon
	 *            to show in the splash window
	 * 
	 */
	public SplashWindow(Icon icon) {
		super();
		getContentPane().setLayout(new BorderLayout());
		initComponents(icon);
		pack();
	}

	private void initComponents(Icon icon) {
		MLabel labSplash = new MLabel(icon);
		labSplash.setBorder(BorderFactory.createLineBorder(Color.black));
		getContentPane().add(labSplash, BorderLayout.CENTER);
	}

	/**
	 * Makes this splash window visible/invisible on the screen. This method
	 * blocks the current thread for <code>displayTime</code> number of milli
	 * seconds.
	 * 
	 * @param visible
	 *            if <code>true</code>, displays this window. <code>false</code>
	 *            , otherwise.
	 * 
	 */
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			setLocation(GUIUtil.getCenterPointRelativeToScreen(getSize()));
		}
		super.setVisible(visible);
	}
}
