/*
 * Created on Oct 5, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.myjavaworld.gui;

import javax.swing.JOptionPane;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MOptionPane extends JOptionPane {

	public MOptionPane() {
		super();
	}

	public MOptionPane(Object message) {
		super(message);
	}

	public MOptionPane(Object message, int messageType) {
		super(message, messageType);
	}

	@Override
	public int getMaxCharactersPerLineCount() {
		return 80;
	}
}
