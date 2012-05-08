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

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.IDTreeNode;
import com.myjavaworld.gui.ImageCellRenderer;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.gui.MScrollPane;
import com.myjavaworld.gui.MTree;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * A dialog box that allows the user to view/edit the user preferences.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class PreferencesDlg extends MDialog implements ActionListener,
		TreeSelectionListener {

	private static ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.PreferencesDlg");
	private static final String HELP_ID = "preferences";
	private static final int ID_ROOT = 0;
	private static final int ID_CONNECTION = 0;
	private static final int ID_CONNECTION_GENERAL = 2;
	private static final int ID_CONNECTION_ADVANCED = 3;
	private static final int ID_CONNECTION_PROXY = 4;
	private static final int ID_CONNECTION_SECURITY = 5;
	private static final int ID_TRANSFER_MODES = 6;
	private static final int ID_REGIONAL_SETTINGS = 7;
	private static final int ID_UI = 8;
	private static final int ID_SOFTWARE_UPDATES = 9;
	JPanel centerPanel = null;
	private CardLayout cardLayout = null;
	private GeneralConnectionPrefsPanel generalConnectionPrefsPanel = null;
	private UIPrefsPanel uiPrefsPanel = null;
	private LocalePrefsPanel localePrefsPanel = null;
	private TransferModesPrefsPanel transferModesPrefsPanel = null;
	private AdvancedConnectionPrefsPanel advancedConnectionPrefsPanel = null;
	private ProxyPrefsPanel proxyPrefsPanel = null;
	private SecurityPrefsPanel securityPrefsPanel = null;
	private SoftwareUpdatePrefsPanel softwareUpdatePrefsPanel = null;
	private MTree tree = null;
	private MButton butSave = null;
	private MButton butCancel = null;
	private MButton butHelp = null;
	private MButton butRestoreDefaults = null;

	public PreferencesDlg(JFTP jftp) {
		super(jftp, resources.getString("title.dialog"), true);
		getContentPane().setLayout(new GridBagLayout());
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), HELP_ID);
		initComponents();
		pack();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butSave) {
			saveButtonPressed();
		} else if (evt.getSource() == butCancel) {
			setVisible(false);
		} else if (evt.getSource() == butRestoreDefaults) {
			JFTPPreferences defaultPreferences = new JFTPPreferences();
			generalConnectionPrefsPanel.populateScreen(defaultPreferences);
			advancedConnectionPrefsPanel.populateScreen(defaultPreferences);
			proxyPrefsPanel.populateScreen(defaultPreferences);
			securityPrefsPanel.populateScreen(defaultPreferences);
			transferModesPrefsPanel.populateScreen(defaultPreferences);
			localePrefsPanel.populateScreen(defaultPreferences);
			uiPrefsPanel.populateScreen(defaultPreferences);
			softwareUpdatePrefsPanel.populateScreen(defaultPreferences);
		}
	}

	public void valueChanged(TreeSelectionEvent evt) {
		TreePath selectedPath = evt.getPath();
		if (selectedPath == null) {
			return;
		}
		IDTreeNode selectedNode = (IDTreeNode) selectedPath
				.getLastPathComponent();
		String cardName = String.valueOf(selectedNode.getID());
		cardLayout.show(centerPanel, cardName);
	}

	@Override
	protected void escape() {
		butCancel.doClick();
	}

	private MTree createTree() {
		IDTreeNode nodeRoot = new IDTreeNode(ID_ROOT,
				resources.getString("text.preferences"), true);

		IDTreeNode nodeRegionalSettings = new IDTreeNode(ID_REGIONAL_SETTINGS,
				resources.getString("text.regionalSettings"), false);

		IDTreeNode nodeUISettings = new IDTreeNode(ID_UI,
				resources.getString("text.userInterface"), false);

		IDTreeNode nodeTransferModes = new IDTreeNode(ID_TRANSFER_MODES,
				resources.getString("text.transferModes"), false);

		IDTreeNode nodeSoftwareUpdates = new IDTreeNode(ID_SOFTWARE_UPDATES,
				resources.getString("text.softwareUpdates"), false);

		IDTreeNode nodeConnectionSettings = new IDTreeNode(ID_CONNECTION,
				resources.getString("text.connectionSettings"), true);

		IDTreeNode nodeGeneral = new IDTreeNode(ID_CONNECTION_GENERAL,
				resources.getString("text.general"), false);

		IDTreeNode nodeAdvanced = new IDTreeNode(ID_CONNECTION_ADVANCED,
				resources.getString("text.advanced"), false);

		IDTreeNode nodeProxy = new IDTreeNode(ID_CONNECTION_PROXY,
				resources.getString("text.proxy"), false);

		IDTreeNode nodeSecurity = new IDTreeNode(ID_CONNECTION_SECURITY,
				resources.getString("text.security"), false);

		nodeConnectionSettings.add(nodeGeneral);
		nodeConnectionSettings.add(nodeAdvanced);
		nodeConnectionSettings.add(nodeProxy);
		nodeConnectionSettings.add(nodeSecurity);

		nodeRoot.add(nodeConnectionSettings);
		nodeRoot.add(nodeTransferModes);
		nodeRoot.add(nodeRegionalSettings);
		nodeRoot.add(nodeUISettings);
		nodeRoot.add(nodeSoftwareUpdates);

		MTree tree = new MTree(nodeRoot);
		tree.setCellRenderer(new ImageCellRenderer());
		tree.setShowsRootHandles(true);
		tree.setRootVisible(true);

		tree.expandRow(0);
		tree.expandRow(1);
		tree.expandRow(2);

		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		int rowCount = tree.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			tree.expandRow(i);
		}
		tree.setSelectionInterval(3, 3);

		return tree;
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		tree = createTree();
		MScrollPane treeScroller = new MScrollPane(tree);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		getContentPane().add(treeScroller, c);

		cardLayout = new CardLayout();
		centerPanel = new JPanel(cardLayout);
		localePrefsPanel = new LocalePrefsPanel();
		uiPrefsPanel = new UIPrefsPanel();
		transferModesPrefsPanel = new TransferModesPrefsPanel();
		generalConnectionPrefsPanel = new GeneralConnectionPrefsPanel();
		advancedConnectionPrefsPanel = new AdvancedConnectionPrefsPanel();
		proxyPrefsPanel = new ProxyPrefsPanel();
		securityPrefsPanel = new SecurityPrefsPanel();
		softwareUpdatePrefsPanel = new SoftwareUpdatePrefsPanel();

		MLabel labMessage = new MLabel(
				resources.getString("text.selectSubSection"));
		centerPanel.add("0", labMessage);
		centerPanel.add(String.valueOf(ID_REGIONAL_SETTINGS), localePrefsPanel);
		centerPanel.add(String.valueOf(ID_UI), uiPrefsPanel);
		centerPanel.add(String.valueOf(ID_TRANSFER_MODES),
				transferModesPrefsPanel);
		centerPanel.add(String.valueOf(ID_CONNECTION_GENERAL),
				generalConnectionPrefsPanel);
		centerPanel.add(String.valueOf(ID_CONNECTION_ADVANCED),
				advancedConnectionPrefsPanel);
		centerPanel.add(String.valueOf(ID_CONNECTION_PROXY), proxyPrefsPanel);
		centerPanel.add(String.valueOf(ID_CONNECTION_SECURITY),
				securityPrefsPanel);
		centerPanel.add(String.valueOf(ID_SOFTWARE_UPDATES),
				softwareUpdatePrefsPanel);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		getContentPane().add(centerPanel, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(12, 12, 12, 12);
		getContentPane().add(getCommandButtons(), c);

		tree.setSelectionInterval(2, 2);
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);

		butRestoreDefaults = new MButton(
				resources.getString("text.restoreDefaults"));
		butRestoreDefaults.addActionListener(this);

		butSave = new MButton(CommonResources.getString("text.save"));
		getRootPane().setDefaultButton(butSave);
		butSave.addActionListener(this);

		butCancel = new MButton(CommonResources.getString("text.cancel"));
		butCancel.addActionListener(this);

		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		panel.add(butRestoreDefaults);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butSave);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butCancel);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(butHelp);

		tree.getSelectionModel().addTreeSelectionListener(this);

		return panel;
	}

	private void saveButtonPressed() {
		if (!generalConnectionPrefsPanel.validateFields()) {
			tree.setSelectionInterval(2, 2);
			return;
		}
		if (!advancedConnectionPrefsPanel.validateFields()) {
			tree.setSelectionInterval(3, 3);
			return;
		}
		if (!proxyPrefsPanel.validateFields()) {
			tree.setSelectionInterval(4, 4);
			return;
		}
		if (!securityPrefsPanel.validateFields()) {
			tree.setSelectionInterval(5, 5);
			return;
		}
		if (!transferModesPrefsPanel.validateFields()) {
			tree.setSelectionInterval(6, 6);
			return;
		}
		if (!localePrefsPanel.validateFields()) {
			tree.setSelectionInterval(7, 7);
			return;
		}
		if (!uiPrefsPanel.validateFields()) {
			tree.setSelectionInterval(8, 8);
			return;
		}
		generalConnectionPrefsPanel.saveChanges();
		advancedConnectionPrefsPanel.saveChanges();
		proxyPrefsPanel.saveChanges();
		securityPrefsPanel.saveChanges();
		transferModesPrefsPanel.saveChanges();
		localePrefsPanel.saveChanges();
		uiPrefsPanel.saveChanges();
		softwareUpdatePrefsPanel.saveChanges();
		try {
			JFTP.savePreferences(JFTP.prefs);
			GUIUtil.showInformation(this,
					resources.getString("info.preferencesSaved"));
			Thread.sleep(200);
		} catch (IOException exp) {
			GUIUtil.showError(this, exp);
		} catch (InterruptedException exp) {
		}
		setVisible(false);
	}
}