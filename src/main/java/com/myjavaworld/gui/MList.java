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

import javax.swing.JList;
import javax.swing.ListModel;

/**
 * An extension of <code>JList</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MList extends JList {

	/**
	 * Creates a new instance of MList
	 */
	public MList() {
		super();
	}

	/**
	 * Creates a new instance of MList
	 * 
	 * @param data
	 *            Array of objects
	 * 
	 */
	public MList(Object[] data) {
		super(data);
	}

	/**
	 * Creates a new instance of MList
	 * 
	 * @param model
	 *            List model
	 * 
	 */
	public MList(ListModel model) {
		super(model);
	}
}
