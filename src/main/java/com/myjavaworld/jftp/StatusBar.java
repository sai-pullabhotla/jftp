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
package com.myjavaworld.jftp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.myjavaworld.gui.MLabel;
import com.myjavaworld.util.ResourceLoader;

/**
 * Main Status bar used by JFTP's main window.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class StatusBar extends JPanel {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.StatusBar");
	private MLabel labStatus = null;
	private JProgressBar progressBar = null;
	private MLabel labSpeed = null;
	private MLabel labElapsedTime = null;
	private MLabel labSecured = null;
	private MessageFormat speedFormat;
	private MessageFormat elapsedTimeFormat;

	public StatusBar() {
		super();
		setLayout(new BorderLayout(0, 0));
		setBorder(BorderFactory.createEtchedBorder());
		speedFormat = new MessageFormat(resources.getString("value.speed"));
		elapsedTimeFormat = new MessageFormat(
				resources.getString("value.elapsedTime"));
		initComponents();
	}

	public void setStatus(String status) {
		labStatus.setText(status);
	}

	public void setMinimum(int min) {
		progressBar.setMinimum(min);
	}

	public void setMaximum(int max) {
		progressBar.setMaximum(max);
	}

	public void setProgress(int value) {
		progressBar.setValue(value);
	}

	public void setSpeed(long speed) {
		Object[] args = { new Long(speed) };
		labSpeed.setText(speedFormat.format(args));
	}

	public void setTimeElapsed(String timeElapsed) {
		Object[] args = { timeElapsed };
		labElapsedTime.setText(elapsedTimeFormat.format(args));
	}

	public void setSecured(boolean secured) {
		labSecured.setIcon(secured ? JFTPUtil.getIcon("lock16.gif") : null);
		labSecured.setText(secured ? "" : "  ");
	}

	public void setIndeterminate(boolean indeterminate) {
		progressBar.setIndeterminate(indeterminate);
		progressBar.setStringPainted(!indeterminate);
	}

	public void reset() {
		labStatus.setText("");
		setIndeterminate(false);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setValue(0);
		labSpeed.setText("");
		labElapsedTime.setText("");
	}

	private void initComponents() {
		labStatus = new MLabel();
		progressBar = new JProgressBar(SwingConstants.HORIZONTAL);
		progressBar.setStringPainted(true);
		progressBar.setMinimum(0);
		progressBar.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.LOWERED));
		labSpeed = new MLabel("", SwingConstants.RIGHT);
		labSpeed.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		labElapsedTime = new MLabel("");
		labElapsedTime.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.LOWERED));

		labSecured = new MLabel("  ");
		labSecured.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.LOWERED));

		JPanel subPanel = new JPanel(new GridLayout(1, 4, 5, 0));

		subPanel.add(labStatus);
		subPanel.add(progressBar);
		subPanel.add(labElapsedTime);
		subPanel.add(labSpeed);

		add(subPanel, BorderLayout.CENTER);
		add(labSecured, BorderLayout.EAST);
	}
}