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

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * An extension of <code>javax.swing.JComboBox</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MComboBox extends JComboBox {

	/**
	 * Constructs an object of <code>MComboBox</code>.
	 * 
	 */
	public MComboBox() {
		super();
	}

	/**
	 * Constructs an object of <code>MComboBox</code>.
	 * 
	 * @param items
	 *            Vector of items.
	 * 
	 */
	public MComboBox(Vector items) {
		super(items);
	}

	/**
	 * Constructs an object of <code>MComboBox</code>.
	 * 
	 * @param items
	 *            An array of items
	 * 
	 */
	public MComboBox(Object[] items) {
		super(items);
	}

	/**
	 * Constructs an object of <code>MComboBox</code>.
	 * 
	 * model Model for this combo box.
	 * 
	 */
	public MComboBox(ComboBoxModel model) {
		super(model);
	}

	/**
	 * Sets the data in this combobox to the given data.
	 * 
	 * @param data
	 *            An array of objects.
	 * 
	 */
	public void setData(Object[] data) {
		removeAllItems();
		for (int i = 0; i < data.length; i++) {
			addItem(data[i]);
		}
	}

	/**
	 * Sets the data in this combobox to the given data.
	 * 
	 * @param data
	 *            Vector of objects.
	 * 
	 */
	public void setData(Vector data) {
		removeAllItems();
		for (int i = 0; i < data.size(); i++) {
			addItem(data.get(i));
		}
	}
}