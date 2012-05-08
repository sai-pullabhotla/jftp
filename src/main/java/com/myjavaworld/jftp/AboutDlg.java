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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;
import com.myjavaworld.util.SystemUtil;

/**
 *
 *
 */
public class AboutDlg extends MDialog implements ActionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.AboutDlg");
	private static final String CREDITS_HELP_ID = "credits";
	private MLabel labTotalMemory = null;
	private MLabel labAvailableMemory = null;
	private MLabel labUsedMemory = null;
	private MButton butClose = null;
	private MButton butReleaseMemory = null;
	private MButton butCredits = null;
	private Timer timer = null;

	public AboutDlg(Frame parent) {
		super(parent, resources.getString("title.dialog"), false);
		getAccessibleContext().setAccessibleDescription("dialog.description");
		initComponents();
		pack();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butClose) {
			timer.stop();
			dispose();
			System.gc();
		} else if (evt.getSource() == butReleaseMemory) {
			System.gc();
		} else if (evt.getSource() == timer) {
			recalculateMemory();
		}
	}

	@Override
	protected void escape() {
		butClose.doClick();
	}

	private void recalculateMemory() {
		long totalMemory = Runtime.getRuntime().totalMemory();
		totalMemory = (totalMemory + 1023) / 1024;
		long availableMemory = Runtime.getRuntime().freeMemory();
		availableMemory = (availableMemory + 1023) / 1024;
		labTotalMemory.setText(totalMemory + " KB");
		labAvailableMemory.setText(availableMemory + " KB");
		labUsedMemory.setText((totalMemory - availableMemory) + " KB");
	}

	private void initComponents() {
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 12, 12, 12);
		MessageFormat mf = new MessageFormat(
				resources.getString("text.productInfo"));
		String[] mfArgs = { JFTPConstants.PRODUCT_NAME,
				JFTPConstants.PRODUCT_VERSION, JFTPConstants.PRODUCT_BUILD,
				JFTPConstants.COMPANY_NAME, JFTPConstants.PRODUCT_WEBSITE,
				JFTPConstants.SUPPORT_EMAIL };
		String productInfo = mf.format(mfArgs);
		MLabel labProductName = new MLabel(productInfo, SwingConstants.CENTER);
		Icon logoIcon = JFTPUtil.getIcon("jftp32.gif");
		labProductName.setIcon(logoIcon);
		labProductName.setIconTextGap(20);
		getContentPane().add(labProductName, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new JSeparator(SwingConstants.HORIZONTAL), c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new MLabel(resources.getString("text.osName")), c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		getContentPane().add(new MLabel(SystemUtil.getOSName()), c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new MLabel(resources.getString("text.osVersion")),
				c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		getContentPane().add(new MLabel(SystemUtil.getOSVersion()), c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(
				new MLabel(resources.getString("text.jreVersion")), c);

		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		getContentPane().add(new MLabel(SystemUtil.getJREVersion()), c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new MLabel(resources.getString("text.jreVendor")),
				c);

		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		getContentPane().add(new MLabel(SystemUtil.getJREVendor()), c);

		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(new MLabel(resources.getString("text.userHome")),
				c);

		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		getContentPane().add(new MLabel(SystemUtil.getUserHome()), c);

		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(
				new MLabel(resources.getString("text.totalMemory")), c);

		c.gridx = 1;
		c.gridy = 7;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		long totalMemory = Runtime.getRuntime().totalMemory();
		totalMemory = (totalMemory + 1023) / 1024;
		labTotalMemory = new MLabel(totalMemory + " KB");
		getContentPane().add(labTotalMemory, c);

		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(
				new MLabel(resources.getString("text.availableMemory")), c);

		c.gridx = 1;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		long availableMemory = Runtime.getRuntime().freeMemory();
		availableMemory = (availableMemory + 1023) / 1024;
		labAvailableMemory = new MLabel(availableMemory + " KB");
		getContentPane().add(labAvailableMemory, c);

		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		getContentPane().add(
				new MLabel(resources.getString("text.usedMemory")), c);

		c.gridx = 1;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		long usedMemory = totalMemory - availableMemory;
		labUsedMemory = new MLabel(usedMemory + " KB");
		getContentPane().add(labUsedMemory, c);

		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(new MLabel(), c);

		c.gridx = 0;
		c.gridy = 11;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weightx = 0.0;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(6, 12, 12, 12);
		c.anchor = GridBagConstraints.EAST;
		getContentPane().add(getCommandButtons(), c);

		timer = new Timer(1000, this);
		timer.start();
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butClose = new MButton(CommonResources.getString("text.close"));
		butClose.addActionListener(this);
		getRootPane().setDefaultButton(butClose);

		butCredits = new MButton(resources.getString("text.credits"));
		JFTPHelp2.getInstance().enableHelp(butCredits, CREDITS_HELP_ID);

		butReleaseMemory = new MButton(
				resources.getString("text.releaseMemory"));
		butReleaseMemory.addActionListener(this);

		panel.add(butClose);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butCredits);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butReleaseMemory);

		return panel;
	}
}