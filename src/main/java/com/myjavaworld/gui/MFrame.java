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

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * An extension of <code>javax.swing.JFrame</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MFrame extends JFrame {

	private MGlassPane glassPane = null;

	/**
	 * Constructs an object of <code>MFrame</code> with the given parameters.
	 * 
	 * @param title
	 *            Title
	 * 
	 */
	public MFrame(String title) {
		this(title, new Insets(0, 0, 0, 0));
	}

	/**
	 * Constructs an object of <code>MFrame</code> with the given parameters.
	 * 
	 * @param title
	 *            Title for this frame.
	 * @param insets
	 *            Insets to set on the content pane of this frame.
	 * 
	 */
	public MFrame(String title, final Insets insets) {
		super(title);
		setContentPane(new JPanel() {

			@Override
			public Insets getInsets() {
				return insets;
			}
		});
		getContentPane().setLayout(new BorderLayout());
		glassPane = new MGlassPane();
		this.setGlassPane(glassPane);
	}

	/**
	 * Sets the state of this frame to the given state.
	 * 
	 * @param busy
	 *            If this is <code>true</code>, all mouse and key events will be
	 *            consumed and the cursor on the frame is set to a busy
	 *            (hour-glass) cursor. If false, the frame will have the regular
	 *            behaviour and cursor.
	 * 
	 */
	public void setBusy(boolean busy) {
		glassPane.setCursor(busy ? MGlassPane.BUSY_CURSOR
				: MGlassPane.NORMAL_CURSOR);
		glassPane.setVisible(busy);
	}
}