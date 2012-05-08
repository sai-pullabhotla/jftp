/*
 * Created on Nov 23, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.myjavaworld.gui;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IDTreeNode extends DefaultMutableTreeNode {

	protected int id = 0;

	public IDTreeNode() {
		super();
	}

	public IDTreeNode(int id, Object userObject) {
		super(userObject);
		this.id = id;
	}

	public IDTreeNode(int id, Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
		this.id = id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}
}
