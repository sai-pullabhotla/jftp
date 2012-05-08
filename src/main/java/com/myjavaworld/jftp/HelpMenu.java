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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MMenu;
import com.myjavaworld.gui.MMenuItem;
import com.myjavaworld.util.ResourceLoader;
import com.myjavaworld.util.SystemUtil;

/**
 * Help Menu containing various help items.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class HelpMenu extends MMenu implements ActionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.HelpMenu");
	private static final String HELP_ID_HOME = "index";
	private static final String HELP_ID_TIPS = "tips";
	private static final String HELP_ID_FAQ = "faq";
	private static final String HELP_ID_SUPPORT = "support";
	private static final String HELP_ID_FEEDBACK = "feedback";
	private JFTP jftp = null;

	/**
	 * Creates an instance of <code>HelpMenu</code>.
	 * 
	 * @param jftp
	 *            JFTP main window
	 * 
	 */
	public HelpMenu(JFTP jftp) {
		super(resources.getString("text.help"));
		setMnemonic(resources.getString("mnemonic.help"),
				resources.getString("mnemonicIndex.help"));
		this.jftp = jftp;
		prepareMenuItems();
	}

	private void prepareMenuItems() {
		MMenuItem miContents = new MMenuItem();
		miContents.setText(resources.getString("text.contents"));
		miContents.setMnemonic(resources.getString("mnemonic.contents"),
				resources.getString("mnemonicIndex.contents"));
		miContents.setAccelerator(GUIUtil.getHelpKeyStroke());
		JFTPHelp2.getInstance().enableHelp(miContents, HELP_ID_HOME);

		MMenuItem miTips = new MMenuItem(resources.getString("text.tips"));
		miTips.setMnemonic(resources.getString("mnemonic.tips"),
				resources.getString("mnemonicIndex.tips"));
		JFTPHelp2.getInstance().enableHelp(miTips, HELP_ID_TIPS);

		MMenuItem miFAQ = new MMenuItem(resources.getString("text.faq"));
		miFAQ.setMnemonic(resources.getString("mnemonic.faq"),
				resources.getString("mnemonicIndex.faq"));
		JFTPHelp2.getInstance().enableHelp(miFAQ, HELP_ID_FAQ);

		MMenuItem miSupport = new MMenuItem(resources.getString("text.support"));
		miSupport.setMnemonic(resources.getString("mnemonic.support"),
				resources.getString("mnemonicIndex.support"));
		JFTPHelp2.getInstance().enableHelp(miSupport, HELP_ID_SUPPORT);

		MMenuItem miFeedback = new MMenuItem(
				resources.getString("text.feedback"));
		miFeedback.setMnemonic(resources.getString("mnemonic.feedback"),
				resources.getString("mnemonicIndex.feedback"));
		JFTPHelp2.getInstance().enableHelp(miFeedback, HELP_ID_FEEDBACK);

		MMenuItem miAbout = new MMenuItem(resources.getString("text.about"));
		miAbout.setMnemonic(resources.getString("mnemonic.about"),
				resources.getString("mnemonicIndex.about"));
		miAbout.setActionCommand("cmd.about");
		miAbout.addActionListener(this);

		add(miContents);
		addSeparator();
		add(miTips);
		add(miFAQ);
		add(miSupport);
		add(miFeedback);
		if (!SystemUtil.isMac()) {
			addSeparator();
			add(miAbout);
		}
	}

	public void actionPerformed(ActionEvent evt) {
		String command = evt.getActionCommand();
		if (command.equals("cmd.about")) {
			AboutDlg dlg = new AboutDlg(jftp);
			dlg.setLocationRelativeTo(jftp);
			dlg.setVisible(true);
		}
	}
}