/*
 * Created on Dec 13, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.myjavaworld.jftp;

import javax.swing.JRootPane;

import com.myjavaworld.gui.MGlassPane;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class SessionPanel extends JRootPane {

	private String title = null;
	private MGlassPane glassPane = null;

	public SessionPanel() {
		super();
		glassPane = new MGlassPane();
		setGlassPane(glassPane);
		this.title = "Not Connected";
	}

	public void setBusy(boolean busy) {
		glassPane.setCursor(busy ? MGlassPane.BUSY_CURSOR
				: MGlassPane.NORMAL_CURSOR);
		glassPane.setVisible(busy);
	}

	public boolean isBusy() {
		return glassPane.isVisible();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void dispose() {
	}
}
