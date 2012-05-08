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

import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.KeyStroke;
import javax.swing.event.MenuListener;

import com.myjavaworld.ftp.FTPConstants;
import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MMenu;
import com.myjavaworld.gui.MRadioButtonMenuItem;
import com.myjavaworld.util.ResourceLoader;

/**
 * TransferModesMenu
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class TransferModeMenu extends MMenu implements MenuListener {

	private JFTP jftp = null;
	private MRadioButtonMenuItem miAutoDetect = null;
	private MRadioButtonMenuItem miAscii = null;
	private MRadioButtonMenuItem miBinary = null;

	public TransferModeMenu(JFTP jftp) {
		super();
		this.jftp = jftp;
		ResourceBundle resources = ResourceLoader
				.getBundle("com.myjavaworld.jftp.TransferModeMenu");
		setText(resources.getString("text.transferMode"));
		setMnemonic(resources.getString("mnemonic.transferMode"),
				resources.getString("mnemonicIndex.transferMode"));

		prepareMenuItems(resources);

		addMenuListener(this);
	}

	private void prepareMenuItems(ResourceBundle resources) {
		miAutoDetect = new MRadioButtonMenuItem(
				resources.getString("text.autoDetect"));
		miAutoDetect.setMnemonic(resources.getString("mnemonic.autoDetect"),
				resources.getString("mnemonicIndex.autoDetect"));
		miAutoDetect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0,
				GUIUtil.ACCELERATOR_MASK));
		miAutoDetect.setActionCommand("cmd.transferType.autoDetect");
		miAutoDetect.addActionListener(jftp);
		add(miAutoDetect);

		miAscii = new MRadioButtonMenuItem(resources.getString("text.ascii"));
		miAscii.setMnemonic(resources.getString("mnemonic.ascii"),
				resources.getString("mnemonicIndex.ascii"));
		miAscii.setActionCommand("cmd.transferType.ascii");
		miAscii.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				GUIUtil.ACCELERATOR_MASK));
		miAscii.addActionListener(jftp);
		add(miAscii);

		miBinary = new MRadioButtonMenuItem(resources.getString("text.binary"));
		miBinary.setMnemonic(resources.getString("mnemonic.binary"),
				resources.getString("mnemonicIndex.binary"));
		miBinary.setActionCommand("cmd.transferType.binary");
		miBinary.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
				GUIUtil.ACCELERATOR_MASK));
		miBinary.addActionListener(jftp);
		add(miBinary);

		ButtonGroup bgTransferType = new ButtonGroup();
		bgTransferType.add(miAutoDetect);
		bgTransferType.add(miAscii);
		bgTransferType.add(miBinary);

		miAutoDetect.setSelected(true);
	}

	public void menuCanceled(javax.swing.event.MenuEvent menuEvent) {
	}

	public void menuDeselected(javax.swing.event.MenuEvent menuEvent) {
	}

	public void menuSelected(javax.swing.event.MenuEvent menuEvent) {
		FTPSession session = jftp.getCurrentSession();
		boolean enable = true;
		if (session == null) {
			enable = false;
		}
		getMenuComponent(0).setEnabled(enable);
		getMenuComponent(1).setEnabled(enable);
		getMenuComponent(2).setEnabled(enable);
		if (session != null) {
			boolean autoDetect = session.isAutoDetect();
			if (autoDetect) {
				miAutoDetect.setSelected(true);
			} else {
				int transferType = session.getTransferType();
				if (transferType == FTPConstants.TYPE_ASCII) {
					miAscii.setSelected(true);
				} else if (transferType == FTPConstants.TYPE_BINARY) {
					miBinary.setSelected(true);
				}
			}
		}
	}
}