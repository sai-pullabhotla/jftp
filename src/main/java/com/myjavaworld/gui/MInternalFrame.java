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

import java.awt.event.MouseEvent;

import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;

/**
 * An extension of <code>javax.swing.JInternalFrame</code>.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class MInternalFrame extends JInternalFrame {

	private MGlassPane glassPane = null;
	private boolean busy = false;

	/**
	 * Constructs an object of <code>MInternalFrame</code>.
	 * 
	 * @param title
	 *            Title for this internal frame.
	 * 
	 */
	public MInternalFrame(String title) {
		super(title, true, true, true, true);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		glassPane = new MGlassPane();
		this.setGlassPane(glassPane);
	}

	/**
	 * Sets the state of this internal frame to busy.
	 * 
	 * @param busy
	 *            If this is <code>true</code>, all the mouse and key events are
	 *            trapped and consumed. The cursor on this internal frame is
	 *            seto a busy cursor.
	 * 
	 */
	public void setBusy(boolean busy) {
		glassPane.setCursor(busy ? com.myjavaworld.gui.MGlassPane.BUSY_CURSOR
				: com.myjavaworld.gui.MGlassPane.NORMAL_CURSOR);
		glassPane.setVisible(busy);
		this.busy = busy;
	}

	/**
	 * Tells whether or not this internal frame is in busy state.
	 * 
	 * @return <code>true</code>, if this internal frame is in busy state.
	 *         <code>false</code>, otherwise.
	 * 
	 */
	public boolean isBusy() {
		return busy;
	}

	private class MGlassPane extends com.myjavaworld.gui.MGlassPane {

		@Override
		public void mousePressed(MouseEvent evt) {
			MInternalFrame.this.moveToFront();
			try {
				MInternalFrame.this.setSelected(true);
			} catch (Exception exp) {
				System.err.println(exp);
			}
			evt.consume();
		}
	}
}