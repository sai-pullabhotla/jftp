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
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.myjavaworld.ftp.FTPConstants;
import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.IntegerField;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MCheckBox;
import com.myjavaworld.gui.MComboBox;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MPasswordField;
import com.myjavaworld.gui.MRadioButton;
import com.myjavaworld.gui.MScrollPane;
import com.myjavaworld.gui.MTextArea;
import com.myjavaworld.gui.MTextField;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * A dialog box to view/add/edit favourite FTP sites.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class FavoritePropertiesDlg extends MDialog implements ActionListener,
		ComponentListener, ItemListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.FavoritePropertiesDlg");
	private static final String HELP_ID_ADD = "favorites.add";
	private static final String HELP_ID_EDIT = "favorites.edit";
	public static final int ADD_MODE = 1;
	public static final int EDIT_MODE = 2;
	private int mode = 0;
	private JTabbedPane tabs = null;
	private MTextField tfLabel = null;
	private MTextField tfHost = null;
	private IntegerField tfPort = null;
	private MTextField tfUser = null;
	private MPasswordField tfPassword = null;
	private MTextField tfAccount = null;
	private MButton butOK = null;
	private MButton butCancel = null;
	private MButton butHelp = null;
	private MComboBox comboFTPClient = null;
	private MComboBox comboListParser = null;
	private MTextField tfInitialLocalDirectory = null;
	private MTextField tfInitialRemoteDirectory = null;
	private MCheckBox cbPassive = null;
	private MTextArea taCommands = null;
	private MButton butBrowseLocalDirectory = null;
	private MRadioButton radioNoSSL = null;
	private MRadioButton radioUseSSLIfAvailable = null;
	private MRadioButton radioExplicitSSL = null;
	private MRadioButton radioImplicitSSL = null;
	private IntegerField tfImplicitSSLPort = null;
	private MCheckBox cbDataChannelEncryption = null;
	private Favorite favorite = null;
	private boolean approved = false;

	/**
	 * Creates an instance of <code>FavoritePropertiesDlg</code>.
	 * 
	 * @param owner
	 *            Parent frame
	 * @param mode
	 *            Mode in which to open this dialog. Possible modes are:
	 *            <ul>
	 *            <li>ADD_MODE</li>
	 *            <li>EDIT_MODE</li>
	 *            </ul>
	 * 
	 */
	public FavoritePropertiesDlg(Frame owner, int mode) {
		super(owner);
		this.mode = mode;
		String helpID = mode == ADD_MODE ? HELP_ID_ADD : HELP_ID_EDIT;
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), helpID);
		initDialog();
	}

	/**
	 * Creates an instance of <code>FavoritePropertiesDlg</code>.
	 * 
	 * @param owner
	 *            Parent Dialog
	 * @param mode
	 *            Mode in which to open this dialog. Possible modes are:
	 *            <ul>
	 *            <li>ADD_MODE</li>
	 *            <li>EDIT_MODE</li>
	 *            </ul>
	 * 
	 */
	public FavoritePropertiesDlg(Dialog owner, int mode) {
		super(owner);
		this.mode = mode;
		initDialog();
	}

	private void initDialog() {
		if (mode == ADD_MODE) {
			setTitle(resources.getString("title.addFavorite"));
		} else if (mode == EDIT_MODE) {
			setTitle(resources.getString("title.updateFavorite"));
		}
		setModal(true);
		getContentPane().setLayout(new GridBagLayout());
		initComponents();
		pack();
	}

	/**
	 * Sets the currently editing favorite FTP site to the given
	 * <code>favorite</code>.
	 * 
	 * @param favorite
	 *            Favorite site that is currently being edited
	 * 
	 */
	public void setFavorite(Favorite favorite) {
		this.favorite = favorite == null ? new Favorite() : favorite;
		populateScreen();
	}

	/**
	 * Returns the updated Favorite FTP site that is currently being edited.
	 * 
	 * @return Updated favourite FTP site
	 * 
	 */
	public Favorite getFavorite() {
		if (!approved) {
			return null;
		}
		return favorite;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource().equals(butOK)) {
			if (!validateInput()) {
				return;
			}
			approved = true;
			okButtonPressed();
			close();
		} else if (evt.getSource().equals(butCancel)) {
			cancelButtonPressed();
			close();
		} else if (evt.getSource().equals(butBrowseLocalDirectory)) {
			browseButtonPressed();
		}
	}

	public void itemStateChanged(ItemEvent evt) {
		if (evt.getSource() == radioNoSSL) {
			// radioUseSSLIfAvailable.setEnabled(! radioNoSSL.isSelected());
			// radioExplicitSSL.setEnabled(! radioNoSSL.isSelected());
			// radioImplicitSSL.setEnabled(! radioNoSSL.isSelected());
			// radioUseSSLIfAvailable.setEnabled(! radioNoSSL.isSelected());
			// tfImplicitSSLPort.setEnabled(! radioNoSSL.isSelected());
			cbDataChannelEncryption.setEnabled(!radioNoSSL.isSelected());
			tfImplicitSSLPort.setEnabled(radioImplicitSSL.isSelected());
		} else if (evt.getSource() == radioImplicitSSL) {
			tfImplicitSSLPort.setEnabled(radioImplicitSSL.isSelected());
		}
	}

	public void componentHidden(ComponentEvent evt) {
	}

	public void componentShown(ComponentEvent evt) {
		tabs.setSelectedIndex(0);
		tfLabel.requestFocus();
	}

	public void componentMoved(ComponentEvent evt) {
	}

	public void componentResized(ComponentEvent evt) {
	}

	private void close() {
		setVisible(false);
	}

	@Override
	protected void escape() {
		butCancel.doClick();
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		tabs = new JTabbedPane();
		tabs.addTab(resources.getString("text.general"), getGeneralTab());
		tabs.addTab(resources.getString("text.security"), getSSLTab());
		tabs.addTab(resources.getString("text.advanced"), getAdvancedTab());
		tabs.addTab(resources.getString("text.commands"), getCommandsTab());
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 6;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		getContentPane().add(tabs, c);

		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(6, 12, 12, 12);
		getContentPane().add(getCommandButtons(), c);

		addComponentListener(this);
	}

	private Component getGeneralTab() {
		GridBagLayout layout = new GridBagLayout();

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		JPanel panel = new JPanel(layout);

		MLabel labLabel = new MLabel(resources.getString("text.label"));
		c.insets = new Insets(12, 12, 12, 12);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		panel.add(labLabel, c);

		tfLabel = new MTextField(20);
		c.insets = new Insets(12, 0, 12, 12);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1.0;
		panel.add(tfLabel, c);

		MLabel labHost = new MLabel(resources.getString("text.host"));
		c.insets = new Insets(0, 12, 12, 12);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.0;
		panel.add(labHost, c);

		tfHost = new MTextField(20);
		c.insets = new Insets(0, 0, 12, 12);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 1.0;
		panel.add(tfHost, c);

		MLabel labUser = new MLabel(resources.getString("text.user"));
		c.insets = new Insets(0, 12, 12, 12);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.0;
		panel.add(labUser, c);

		tfUser = new MTextField(10);
		c.insets = new Insets(0, 0, 12, 12);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 1.0;
		panel.add(tfUser, c);

		MLabel labPassword = new MLabel(resources.getString("text.password"));
		c.insets = new Insets(0, 12, 12, 12);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 0.0;
		panel.add(labPassword, c);

		tfPassword = new MPasswordField(10);
		c.insets = new Insets(0, 0, 12, 12);
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 2;
		c.weightx = 1.0;
		panel.add(tfPassword, c);

		MLabel labPort = new MLabel(resources.getString("text.port"));
		c.insets = new Insets(0, 12, 12, 12);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.weightx = 0.0;
		panel.add(labPort, c);

		tfPort = new IntegerField(6);
		tfPort.setValue(FTPConstants.DEFAULT_PORT);
		c.insets = new Insets(0, 0, 12, 12);
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 2;
		c.weightx = 1.0;
		panel.add(tfPort, c);

		MLabel labAccount = new MLabel(resources.getString("text.account"));
		c.insets = new Insets(0, 12, 12, 12);
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.weightx = 0.0;
		panel.add(labAccount, c);

		tfAccount = new MTextField(20);
		c.insets = new Insets(0, 0, 12, 12);
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 2;
		c.weightx = 0.5;
		panel.add(tfAccount, c);

		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		panel.add(new MLabel(), c);

		return panel;
	}

	private Component getSSLTab() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		radioNoSSL = new MRadioButton(resources.getString("text.noSSL"));
		radioUseSSLIfAvailable = new MRadioButton(
				resources.getString("text.useSSLIfAvailable"));
		radioExplicitSSL = new MRadioButton(
				resources.getString("text.explicitSSL"));
		radioImplicitSSL = new MRadioButton(
				resources.getString("text.implicitSSL"));
		MLabel labImplicitSSLPort = new MLabel(
				resources.getString("text.implicitSSLPort"));

		tfImplicitSSLPort = new IntegerField(5);

		cbDataChannelEncryption = new MCheckBox(
				resources.getString("text.dataChannelEncryption"));
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioNoSSL);
		bg.add(radioUseSSLIfAvailable);
		bg.add(radioExplicitSSL);
		bg.add(radioImplicitSSL);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12, 12, 12, 12);
		panel.add(radioNoSSL, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(radioUseSSLIfAvailable, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(radioExplicitSSL, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 12, 6, 12);
		panel.add(radioImplicitSSL, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 60, 12, 12);
		panel.add(labImplicitSSLPort, c);

		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 12, 12);
		panel.add(tfImplicitSSLPort, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(cbDataChannelEncryption, c);

		radioNoSSL.addItemListener(this);
		radioImplicitSSL.addItemListener(this);

		radioNoSSL.setSelected(true);
		// cbEncryptDataChannel.setSelected(true);
		return panel;
	}

	private Component getAdvancedTab() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		MLabel labFTPClient = new MLabel(resources.getString("text.ftpClient"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(12, 12, 12, 12);
		panel.add(labFTPClient, c);

		comboFTPClient = new MComboBox(JFTP.getInstalledClients());
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.insets = new Insets(12, 0, 12, 12);
		panel.add(comboFTPClient, c);

		MLabel labListParser = new MLabel(
				resources.getString("text.listParser"));
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(labListParser, c);

		comboListParser = new MComboBox(JFTP.getInstalledParsers());
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.insets = new Insets(0, 0, 12, 12);
		panel.add(comboListParser, c);

		MLabel labInitialLocalDirectory = new MLabel(
				resources.getString("text.initialLocalDirectory"));
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(labInitialLocalDirectory, c);

		tfInitialLocalDirectory = new MTextField(20);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.insets = new Insets(0, 0, 12, 12);
		panel.add(tfInitialLocalDirectory, c);

		butBrowseLocalDirectory = new MButton(
				CommonResources.getString("text.browse"));
		butBrowseLocalDirectory.addActionListener(this);
		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 0, 12, 12);
		panel.add(butBrowseLocalDirectory, c);

		MLabel labInitialRemoteDirectory = new MLabel(
				resources.getString("text.initialRemoteDirectory"));
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(labInitialRemoteDirectory, c);

		tfInitialRemoteDirectory = new MTextField(20);
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.insets = new Insets(0, 0, 12, 12);
		panel.add(tfInitialRemoteDirectory, c);

		cbPassive = new MCheckBox(resources.getString("text.passive"),
				JFTP.prefs.isPassive());
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(cbPassive, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		panel.add(new MLabel(), c);

		return panel;
	}

	private Component getCommandsTab() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		MLabel labCommands = new MLabel(
				resources.getString("text.executeCommands"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.weighty = 0;
		c.insets = new Insets(12, 12, 12, 12);
		panel.add(labCommands, c);

		taCommands = new MTextArea(4, 30);
		MScrollPane scroller = new MScrollPane(taCommands);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 12, 12, 12);
		panel.add(scroller, c);

		return panel;
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);
		butOK = new MButton();
		if (mode == ADD_MODE) {
			butOK.setText(CommonResources.getString("text.add"));
		} else if (mode == EDIT_MODE) {
			butOK.setText(CommonResources.getString("text.save"));
		}

		butOK.addActionListener(this);
		getRootPane().setDefaultButton(butOK);
		butCancel = new MButton(CommonResources.getString("text.cancel"));
		butCancel.addActionListener(this);
		butHelp = new MButton(CommonResources.getString("text.help"));
		String helpID = mode == ADD_MODE ? HELP_ID_ADD : HELP_ID_EDIT;
		JFTPHelp2.getInstance().enableHelp(butHelp, helpID);

		panel.add(butOK);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butCancel);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);

		return panel;
	}

	private void populateScreen() {
		String temp = null;
		temp = favorite.getName();
		tfLabel.setText(temp);
		temp = favorite.getHostName();
		tfHost.setText(temp);
		temp = favorite.getUser();
		tfUser.setText(temp.trim().length() == 0 ? "anonymous" : temp);
		temp = favorite.getPassword();
		tfPassword.setText(temp.trim().length() == 0 ? JFTP.prefs.getEmail()
				: temp);
		tfPort.setValue(favorite.getPort());
		temp = favorite.getAccount();
		tfAccount.setText(temp);
		temp = favorite.getFTPClientClassName();
		comboFTPClient.setSelectedItem(JFTP.getClientName(temp));
		temp = favorite.getListParserClassName();
		comboListParser.setSelectedItem(JFTP.getParserName(temp));
		tfInitialLocalDirectory.setText(favorite.getInitialLocalDirectory());
		tfInitialRemoteDirectory.setText(favorite.getInitialRemoteDirectory());
		cbPassive.setSelected(favorite.isPassive());
		taCommands.setText(favorite.getCommandsAsString());

		int sslUsage = favorite.getSSLUsage();
		switch (sslUsage) {
		case FTPConstants.USE_NO_SSL:
			radioNoSSL.setSelected(true);
			break;
		case FTPConstants.USE_SSL_IF_AVAILABLE:
			radioUseSSLIfAvailable.setSelected(true);
			break;
		case FTPConstants.USE_EXPLICIT_SSL:
			radioExplicitSSL.setSelected(true);
			break;
		case FTPConstants.USE_IMPLICIT_SSL:
			radioImplicitSSL.setSelected(true);
			break;
		}
		tfImplicitSSLPort.setValue(favorite.getImplicitSSLPort());
		cbDataChannelEncryption
				.setSelected(favorite.isDataChannelUnencrypted());

	}

	private void okButtonPressed() {
		favorite = new Favorite();
		favorite.setName(tfLabel.getText());
		favorite.setHostName(tfHost.getText());
		favorite.setPort(tfPort.getValue());
		favorite.setUser(tfUser.getText());
		favorite.setPassword(new String(tfPassword.getPassword()));
		favorite.setAccount(tfAccount.getText());
		favorite.setFTPClientClassName(JFTP
				.getClientClassName((String) comboFTPClient.getSelectedItem()));
		favorite.setListParserClassName(JFTP
				.getParserClassName((String) comboListParser.getSelectedItem()));
		favorite.setInitialLocalDirectory(tfInitialLocalDirectory.getText());
		favorite.setInitialRemoteDirectory(tfInitialRemoteDirectory.getText());
		favorite.setPassive(cbPassive.isSelected());
		favorite.setCommands(taCommands.getText());
		int sslUsage = FTPConstants.USE_NO_SSL;
		if (radioUseSSLIfAvailable.isSelected()) {
			sslUsage = FTPConstants.USE_SSL_IF_AVAILABLE;
		} else if (radioExplicitSSL.isSelected()) {
			sslUsage = FTPConstants.USE_EXPLICIT_SSL;
		} else if (radioImplicitSSL.isSelected()) {
			sslUsage = FTPConstants.USE_IMPLICIT_SSL;
		}
		favorite.setSSLUsage(sslUsage);
		favorite.setImplicitSSLPort(tfImplicitSSLPort.getValue());
		favorite.setDataChannelUnencrypted(cbDataChannelEncryption.isSelected());
	}

	private void cancelButtonPressed() {
		favorite = null;
	}

	private void browseButtonPressed() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setApproveButtonText(CommonResources
				.getString("text.select"));

		String dir = tfInitialLocalDirectory.getText();
		if (dir.trim().length() == 0) {
			dir = JFTP.prefs.getLocalDirectory();
		}
		File currentDirectory = null;
		if (dir.trim().length() > 0) {
			currentDirectory = new File(dir);
		}
		if (currentDirectory.exists() && currentDirectory.isDirectory()) {
			fileChooser.setSelectedFile(currentDirectory);
		}
		int option = fileChooser.showOpenDialog(this);
		if (option != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selectedFile = fileChooser.getSelectedFile();
		tfInitialLocalDirectory.setText(selectedFile.getAbsolutePath());
	}

	private boolean validateInput() {
		String message = null;
		int errorTab = 0;
		Component errorComponent = null;
		int port = -1;
		int implicitSSLPort = -1;
		try {
			port = tfPort.getValue();
			implicitSSLPort = tfImplicitSSLPort.getValue();
		} catch (NumberFormatException exp) {
		}

		if (tfLabel.getText().trim().length() == 0) {
			message = resources.getString("error.label.required");
			errorComponent = tfLabel;
		} else if (tfHost.getText().trim().length() == 0) {
			message = resources.getString("error.host.required");
			errorComponent = tfHost;
		} else if (tfUser.getText().trim().length() == 0) {
			message = resources.getString("error.user.required");
			errorComponent = tfUser;
		} else if (new String(tfPassword.getPassword()).trim().length() == 0) {
			message = resources.getString("error.password.required");
			errorComponent = tfPassword;
		} else if (port < 0 || port > 0xFFFF) {
			message = resources.getString("error.port.invalid");
			errorComponent = tfPort;
		} else if (radioImplicitSSL.isSelected()) {
			if (implicitSSLPort < 0 || implicitSSLPort > 0xFFFF) {
				message = resources.getString("error.implicitSSLPort.invalid");
				errorTab = 1;
				errorComponent = tfImplicitSSLPort;
			}
		}

		if (message == null) {
			return true;
		}
		GUIUtil.showError(this, message);
		tabs.setSelectedIndex(errorTab);
		if (errorComponent != null) {
			errorComponent.requestFocus();
		}
		return false;
	}
}