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

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 * An extension of <code>javax.swing.JTree</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MTree extends JTree {

	public MTree() {
		super();
	}

	public MTree(Object[] value) {
		super(value);
	}

	public MTree(Hashtable value) {
		super(value);
	}

	public MTree(Vector value) {
		super(value);
	}

	public MTree(TreeModel model) {
		super(model);
	}

	public MTree(TreeNode root) {
		super(root);
	}

	public MTree(TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
	}
}
