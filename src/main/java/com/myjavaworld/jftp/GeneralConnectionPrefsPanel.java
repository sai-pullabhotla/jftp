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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MTextField;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * A supporting panel used by the preferences dialog that allows to view/edit
 * the general preferences.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class GeneralConnectionPrefsPanel extends JPanel implements
		ActionListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.GeneralConnectionPrefsPanel");
	private MTextField tfEmailAddress = null;
	private MTextField tfDefaultLocalDirectory = null;
	private MButton butBrowse = null;

	/**
	 * Creates an instance of <code>GeneralPrefsPanel</code>.
	 * 
	 */
	public GeneralConnectionPrefsPanel() {
		super();
		setLayout(new GridBagLayout());
		initComponents();
	}

	public boolean validateFields() {
		String error = null;
		Component errorComponent = null;
		if (tfEmailAddress.getText().trim().length() == 0) {
			error = resources.getString("error.email.required");
			errorComponent = tfEmailAddress;
		} else if (tfDefaultLocalDirectory.getText().trim().length() == 0) {
			error = resources.getString("error.defaultLocalDirectory.required");
			errorComponent = tfDefaultLocalDirectory;
		}

		if (error == null) {
			return true;
		}
		GUIUtil.showError(this, error);
		errorComponent.requestFocusInWindow();
		return false;
	}

	public void populateScreen(JFTPPreferences prefs) {
		tfEmailAddress.setText(prefs.getEmail());
		tfDefaultLocalDirectory.setText(prefs.getLocalDirectory());
	}

	public void populateScreen() {
		populateScreen(JFTP.prefs);
	}

	public void saveChanges() {
		JFTP.prefs.setEmail(tfEmailAddress.getText());
		JFTP.prefs.setLocalDirectory(tfDefaultLocalDirectory.getText());
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butBrowse) {
			browseButtonPressed();
		}
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		MLabel labEmailAddress = new MLabel(resources.getString("text.email"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.insets = new Insets(12, 12, 12, 12);
		add(labEmailAddress, c);

		tfEmailAddress = new MTextField(15);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.insets = new Insets(0, 12, 12, 12);
		add(tfEmailAddress, c);

		MLabel labDefaultLocalDirectory = new MLabel(
				resources.getString("text.defaultLocalDirectory"));
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.insets = new Insets(0, 12, 12, 12);
		add(labDefaultLocalDirectory, c);

		tfDefaultLocalDirectory = new MTextField(15);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.insets = new Insets(0, 12, 12, 12);
		add(tfDefaultLocalDirectory, c);

		butBrowse = new MButton(CommonResources.getString("text.browse"));
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 0, 12, 12);
		add(butBrowse, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		add(new JLabel(), c);

		populateScreen();
		butBrowse.addActionListener(this);
	}

	private void browseButtonPressed() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setApproveButtonText(CommonResources
				.getString("text.select"));

		String dir = tfDefaultLocalDirectory.getText();
		if (dir.trim().length() == 0) {
			dir = JFTP.prefs.getLocalDirectory();
		}
		File currentDirectory = null;
		if (dir.trim().length() > 0) {
			currentDirectory = new File(dir);
		} else {
			currentDirectory = JFTP.USER_HOME;
		}
		if (currentDirectory.exists() && currentDirectory.isDirectory()) {
			fileChooser.setSelectedFile(currentDirectory);
		}
		int option = fileChooser.showOpenDialog(this);
		if (option != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selectedFile = fileChooser.getSelectedFile();
		tfDefaultLocalDirectory.setText(selectedFile.getAbsolutePath());
	}
}