/*
 * Created on Dec 13, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.myjavaworld.gui;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class MScrollPane extends JScrollPane {

	public MScrollPane(Component comp, int vPolicy, int hPolicy) {
		super(comp, vPolicy, hPolicy);
	}

	public MScrollPane(Component comp) {
		super(comp, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	}

	public MScrollPane(int vPolicy, int hPolicy) {
		super(vPolicy, hPolicy);
	}

	public MScrollPane() {
		super();
	}
}
