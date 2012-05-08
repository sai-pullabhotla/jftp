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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 * An extension of <code>javax.swing.DesktopPane</code> with some additional
 * features.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MDesktopPane extends JDesktopPane {

	/**
	 * Constructs an object of <code>MDesktopPane</code>.
	 * 
	 */
	public MDesktopPane() {
		super();
	}

	/**
	 * Cascades all visible and non-iconified internal windows in this desktop.
	 * The minimized Internal Windows will remain unchanged. The currently
	 * selected window will still remain as the selected window in the desktop
	 * pane.
	 * 
	 */
	public void cascade() {
		JInternalFrame[] allFrames = getAllFrames();
		if (allFrames == null || allFrames.length == 0) {
			return;
		}
		Vector v = new Vector(allFrames.length);
		for (int i = allFrames.length - 1; i >= 0; i--) {
			if (allFrames[i].isIcon() || allFrames[i].isClosed()) {
				continue;
			}
			v.add(allFrames[i]);
		}
		JInternalFrame[] frames = new JInternalFrame[v.size()];
		frames = (JInternalFrame[]) v.toArray(frames);
		if (frames.length == 0) {
			return;
		}
		final int n = Math.min(7, frames.length);
		Dimension desktopSize = getSize();
		int vSpacing = frames[0].getHeight()
				- frames[0].getRootPane().getHeight();
		Dimension spacing = new Dimension(25, vSpacing);
		Dimension maxSize = new Dimension(desktopSize.width - (n - 1)
				* spacing.width, desktopSize.height - (n - 1) * spacing.height);
		Point location = new Point(0, 0);
		int j = 0;
		for (int i = 0; i < frames.length; i++) {
			frames[i].setBounds(location.x, location.y, maxSize.width,
					maxSize.height);
			j++;
			if (j % n == 0) {
				location.x = 0;
				location.y = 0;
			} else {
				location.x += spacing.width;
				location.y += spacing.height;
			}
		}
	}

	/**
	 * Tiles all visible, non-iconified internal windows horizontally. All
	 * iconified internal windows will remain unchanged. The currently selected
	 * window will still remain as the selected window in the desktop pane.
	 * 
	 */
	public void tileHorizontally() {
		tile(true);
	}

	/**
	 * Tiles all visible, non-iconified internal windows vertically. All
	 * iconified internal windows will remain unchanged. The currently selected
	 * window will still remain as the selected window in the desktop pane.
	 * 
	 */
	public void tileVertically() {
		tile(false);
	}

	private void tile(boolean horizontal) {
		JInternalFrame[] allFrames = getAllFrames();
		if (allFrames == null || allFrames.length == 0) {
			return;
		}
		Vector v = new Vector(allFrames.length);
		for (int i = allFrames.length - 1; i >= 0; i--) {
			if (allFrames[i].isIcon() || allFrames[i].isClosed()) {
				continue;
			}
			v.add(allFrames[i]);
		}
		JInternalFrame[] frames = new JInternalFrame[v.size()];
		frames = (JInternalFrame[]) v.toArray(frames);
		if (frames.length == 0) {
			return;
		}
		Rectangle r = new Rectangle(0, 0, getWidth(), getHeight());
		if (horizontal) {
			r = new Rectangle(r.y, r.x, r.height, r.width);
		}
		int n = frames.length;
		int remainingWindows = n;
		int columnCount = n;
		if (n > 3) {
			columnCount = (int) (Math.sqrt(n));
		}
		int x = 0;
		int y = 0;
		int i = 0;
		for (int col = 0; col < columnCount; col++) {
			int remainingColumns = columnCount - col;
			int rowCount = remainingWindows / remainingColumns;
			int columnWidth = (r.width - x) / remainingColumns;
			y = 0;
			for (int row = 0; row < rowCount; row++) {
				int curx = x;
				int cury = y;
				int curw = columnWidth;
				int curh = (r.height - y) / (rowCount - row);
				if (horizontal) {
					frames[i].setBounds(cury, curx, curh, curw);
				} else {
					frames[i].setBounds(curx, cury, curw, curh);
				}
				y += curh;
				remainingWindows--;
				i++;
			}
			x += columnWidth;
		}
	}
}