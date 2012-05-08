/**
 *
 * This software is the confidential and proprietary information of the author,
 * Sai Pullabhotla. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with the author.
 *
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 *
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
