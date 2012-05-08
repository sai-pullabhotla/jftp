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
