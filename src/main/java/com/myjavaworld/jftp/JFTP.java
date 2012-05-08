/*
 * Copyright 2012 jMethods, Inc. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.myjavaworld.jftp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.myjavaworld.ftp.FTPClient;
import com.myjavaworld.ftp.FTPConstants;
import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.LicenseAgreementDlg;
import com.myjavaworld.gui.MFrame;
import com.myjavaworld.jftp.ssl.CertificateManagerDlg;
import com.myjavaworld.util.Filter;
import com.myjavaworld.util.ResourceLoader;

/**
 * The main window that contains various menu items, tool bar and manages
 * various FTP sessions.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class JFTP extends MFrame implements WindowListener, ActionListener,
		ChangeListener {

	private static ResourceBundle resources = null;
	public static final File USER_HOME = new File(
			System.getProperty("user.home"));
	public static final File DATA_HOME = new File(USER_HOME, ".jftp"
			+ File.separator + "data");
	private static final Map THEMES;
	private static final Map PARSERS;
	private static final Map CLIENTS;
	private static Map transferTypes = null;
	public static JFTPPreferences prefs;

	static {
		// Create Hashmap of all themes. We could actually load these
		// from an external file if we want to.
		THEMES = new TreeMap();
		THEMES.put("Default", "com.myjavaworld.gui.DefaultTheme");
		THEMES.put("Default - Large", "com.myjavaworld.gui.DefaultLargeTheme");
		THEMES.put("Green", "com.myjavaworld.gui.GreenMetalTheme");
		THEMES.put("Green - Large", "com.myjavaworld.gui.GreenMetalLargeTheme");
		THEMES.put("Sandstone", "com.myjavaworld.gui.SandstoneTheme");
		THEMES.put("Sandstone - Large",
				"com.myjavaworld.gui.SandstoneLargeTheme");
		THEMES.put("High Contrast", "com.myjavaworld.gui.HighContrastTheme");
		THEMES.put("High Contrast - Large",
				"com.myjavaworld.gui.HighContrastLargeTheme");

		// Create a Hashmap of all FTP client implementations
		CLIENTS = new TreeMap();
		CLIENTS.put("Default FTP Client",
				"com.myjavaworld.ftp.DefaultFTPClient");
		CLIENTS.put("AS/400 FTP Client", "com.myjavaworld.ftp.AS400FTPClient");

		// Create a Hashmap of all FTP list parsers
		PARSERS = new TreeMap();
		PARSERS.put("Default List Parser (Unix)",
				"com.myjavaworld.ftp.DefaultListParser");
		PARSERS.put("MS-DOS List Parser", "com.myjavaworld.ftp.DosListParser");
		try {
			prefs = loadPreferences();
			transferTypes = prefs.getTransferTypes();
			JFTPUtil.updateProxySettings();
		} catch (IOException exp) {
			System.err
					.println("Failed to load the preferences. Using Defaults. ");
		}
	}
	private JFTPToolBar toolBar = null;
	private JTabbedPane tabs = null;
	private boolean applet = false;

	/**
	 * Creates an instance of JFTP, the main window and the central point for
	 * the software.
	 */
	public JFTP() {
		this(false);
	}

	public JFTP(boolean applet) {
		super(JFTPConstants.PRODUCT_NAME);
		this.applet = applet;
		if (resources == null) {
			if (resources == null) {
				resources = ResourceLoader
						.getBundle("com.myjavaworld.jftp.JFTP");
			}
		}
		setIconImage(JFTPUtil.getImage("jftp16.gif"));
		showLicenseAgreement();
		getContentPane().setLayout(new BorderLayout(0, 0));
		setJMenuBar(prepareMenuBar());
		addWindowListener(this);
		setBounds(getPreferredBounds());
		tabs = new JTabbedPane(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabs.addChangeListener(this);
		toolBar = new JFTPToolBar(this);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		getContentPane().add(tabs, BorderLayout.CENTER);
	}

	/**
	 * Creates a new FTP session
	 */
	public void newSession() {
		FTPSession session = new FTPSession(this);
		String title = resources.getString("text.notConnected");
		tabs.addTab(title, session);
		tabs.setSelectedComponent(session);
	}

	public void windowOpened(WindowEvent evt) {
		if (prefs.getWindowBounds() == null) {
			setExtendedState(Frame.MAXIMIZED_BOTH);
		}
		if (prefs.getCheckForUpdates()) {
			AutoUpdater updater = new AutoUpdater(this, true, false);
			updater.start();
		}
	}

	public void windowActivated(WindowEvent evt) {
	}

	public void windowDeactivated(WindowEvent evt) {
	}

	public void windowIconified(WindowEvent evt) {
	}

	public void windowDeiconified(WindowEvent evt) {
	}

	public void windowClosing(WindowEvent evt) {
		exit();
	}

	public void windowClosed(WindowEvent evt) {
	}

	public void stateChanged(ChangeEvent evt) {
		updateToolBar();
	}

	public void closeSession() {
		FTPSession session = (FTPSession) tabs.getSelectedComponent();
		if (session != null) {
			session.closeSession();
			tabs.remove(session);
		}
	}

	/**
	 * Closes JFTP
	 */
	public void exit() {
		int sessionCount = tabs.getTabCount();
		for (int i = 0; i < sessionCount; i++) {
			FTPSession session = (FTPSession) tabs.getComponentAt(i);
			session.closeSession();
		}
		Rectangle windowBounds = getBounds();
		prefs.setWindowBounds(windowBounds);
		try {
			savePreferences(prefs);
		} catch (Exception exp) {
			// Ignore this.
		}
		dispose();
		System.gc();
		if (!applet) {
			System.exit(0);
		}
	}

	public void actionPerformed(ActionEvent evt) {
		String command = evt.getActionCommand();

		if (command.equals("cmd.closeSession")) {
			closeSession();
		} else if (command.equals("cmd.exit")) {
			exit();
		} else if (command.equals("cmd.refreshLocalPane")) {
			FTPSession session = getCurrentSession();
			if (session != null) {
				session.refreshLocalPane();
			}
		} else if (command.equals("cmd.refreshRemotePane")) {
			FTPSession session = getCurrentSession();
			if (session != null) {
				session.refreshRemotePane();
			}
		} else if (command.equals("cmd.manageCertificates")) {
			manageCertificates();
		} else if (command.equals("cmd.addToFavorites")) {
			addToFavorites();
		} else if (command.equals("cmd.preferences")) {
			showPreferencesDialog();
		} else if (command.equals("cmd.newSession")) {
			newSession();
		} else if (command.equals("cmd.transferType.autoDetect")) {
			FTPSession session = getCurrentSession();
			if (session != null) {
				session.setAutoDetect(true);
			}
		} else if (command.equals("cmd.transferType.ascii")) {
			FTPSession session = getCurrentSession();
			if (session != null) {
				session.setAutoDetect(false);
				session.setTransferType(FTPConstants.TYPE_ASCII);
			}
		} else if (command.equals("cmd.transferType.binary")) {
			FTPSession session = getCurrentSession();
			if (session != null) {
				session.setAutoDetect(false);
				session.setTransferType(FTPConstants.TYPE_BINARY);
			}
		} else if (command.equals("cmd.help")) {
			executeCommand("HELP");
		} else if (command.equals("cmd.stat")) {
			executeCommand("STAT");
		} else if (command.equals("cmd.syst")) {
			executeCommand("SYST");
		} else if (command.equals("cmd.noop")) {
			executeCommand("NOOP");
		} else if (command.equals("cmd.other")) {
			executeCustomCommand();
		} else if (command.equals("cmd.remoteFileProperties")) {
			showRemoteFileProperties();
		} else if (command.equals("cmd.localFileProperties")) {
			showLocalFileProperties();
		} else if (command.equals("cmd.applyLocalFileFilter")) {
			showLocalFileFilter();
		} else if (command.equals("cmd.clearLocalFileFilter")) {
			clearLocalFileFilter();
		} else if (command.equals("cmd.applyRemoteFileFilter")) {
			showRemoteFileFilter();
		} else if (command.equals("cmd.clearRemoteFileFilter")) {
			clearRemoteFileFilter();
		} else if (command.equals("cmd.localSelectAll")) {
			selectAllLocalFiles();
		} else if (command.equals("cmd.localInvertSelection")) {
			invertLocalFileSelection();
		} else if (command.equals("cmd.remoteSelectAll")) {
			selectAllRemoteFiles();
		} else if (command.equals("cmd.remoteInvertSelection")) {
			invertRemoteFileSelection();
		}
	}

	private void executeCustomCommand() {
		FTPSession session = getCurrentSession();
		if (session == null || !session.isConnected()) {
			return;
		}
		ExecuteCommandDlg dlg = new ExecuteCommandDlg(this);
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
		String[] commands = dlg.getCommands();
		dlg.dispose();
		if (commands == null) {
			return;
		}
		session.executeCommands(commands);
	}

	private void executeCommand(String command) {
		FTPSession session = getCurrentSession();
		if (session == null || !session.isConnected()) {
			return;
		}
		session.executeCommand(command);
	}

	private void manageCertificates() {
		CertificateManagerDlg dlg = new CertificateManagerDlg(this);
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
		dlg.dispose();
	}

	private void showRemoteFileProperties() {
		FTPSession session = getCurrentSession();
		if (session == null || !session.isConnected()) {
			return;
		}
		RemoteFile selectedFile = session.getSelectedRemoteFile();
		if (selectedFile == null) {
			return;
		}
		FTPClient client = session.getFTPClient();
		RemoteFilePropertiesDlg dlg = new RemoteFilePropertiesDlg(this, client,
				selectedFile, session.getRemoteFileFilter());
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
		RemoteFile newFile = dlg.getFile();
		dlg.dispose();
		if (newFile != null) {
			session.changeRemoteFilePermissions(selectedFile, newFile,
					dlg.isRecursive());
		}
	}

	private void showLocalFileProperties() {
		FTPSession session = getCurrentSession();
		if (session == null) {
			return;
		}
		LocalFile selectedFile = session.getSelectedLocalFile();
		if (selectedFile == null) {
			return;
		}
		LocalFilePropertiesDlg dlg = new LocalFilePropertiesDlg(this,
				selectedFile, session.getLocalFileFilter());
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
		dlg.dispose();
	}

	private void showLocalFileFilter() {
		FTPSession session = getCurrentSession();
		if (session == null) {
			return;
		}
		LocalFileFilterDlg dlg = new LocalFileFilterDlg(this);
		dlg.setFilter(session.getLocalFileFilter());
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
		Filter filter = dlg.getFilter();
		dlg.dispose();
		if (filter != null) {
			session.setLocalFileFilter(filter);
		}
	}

	private void clearLocalFileFilter() {
		FTPSession session = getCurrentSession();
		if (session == null) {
			return;
		}
		session.setLocalFileFilter(null);
	}

	private void showRemoteFileFilter() {
		FTPSession session = getCurrentSession();
		if (session == null) {
			return;
		}
		RemoteFileFilterDlg dlg = new RemoteFileFilterDlg(this);
		dlg.setFilter(session.getRemoteFileFilter());
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
		Filter filter = dlg.getFilter();
		dlg.dispose();
		if (filter != null) {
			session.setRemoteFileFilter(filter);
		}
	}

	private void clearRemoteFileFilter() {
		FTPSession session = getCurrentSession();
		if (session == null) {
			return;
		}
		session.setRemoteFileFilter(null);
	}

	private void addToFavorites() {
		FTPSession session = getCurrentSession();
		if (session == null || !session.isConnected()) {
			return;
		}
		RemoteHost host = session.getRemoteHost();
		Favorite favorite = new Favorite();
		favorite.setName(host.getName());
		favorite.setHostName(host.getHostName());
		favorite.setPort(host.getPort());
		favorite.setUser(host.getUser());
		favorite.setPassword(host.getPassword());
		favorite.setAccount(host.getAccount());
		favorite.setFTPClientClassName(host.getFTPClientClassName());
		favorite.setListParserClassName(host.getListParserClassName());
		favorite.setInitialLocalDirectory(host.getInitialLocalDirectory());
		favorite.setInitialRemoteDirectory(host.getInitialRemoteDirectory());
		favorite.setPassive(host.isPassive());
		favorite.setCommands(host.getCommands());
		FavoritePropertiesDlg dlg = new FavoritePropertiesDlg(this,
				FavoritePropertiesDlg.ADD_MODE);
		dlg.setFavorite(favorite);
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
		favorite = dlg.getFavorite();
		if (favorite != null) {
			try {
				java.util.List favorites = FavoritesManager.getFavorites();
				if (favorites.contains(favorite)) {
					GUIUtil.showError(this,
							resources.getString("error.duplicateFavorite"));
					return;
				}
				FavoritesManager.addFavorite(favorite);
			} catch (Exception exp) {
				GUIUtil.showError(this, exp);
			}
		}
	}

	public void showPreferencesDialog() {
		PreferencesDlg dlg = new PreferencesDlg(this);
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
		dlg.dispose();
	}

	private JMenuBar prepareMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		FTPMenu menuFTP = new FTPMenu(this);
		LocalSystemMenu menuLocalSystem = new LocalSystemMenu(this);
		RemoteSystemMenu menuRemoteSystem = new RemoteSystemMenu(this);
		TransferModeMenu menuTransferTypes = new TransferModeMenu(this);
		ToolsMenu menuTools = new ToolsMenu(this);
		HelpMenu menuHelp = new HelpMenu(this);

		menuBar.add(menuFTP);
		menuBar.add(menuLocalSystem);
		menuBar.add(menuRemoteSystem);
		menuBar.add(menuTransferTypes);
		menuBar.add(menuTools);
		menuBar.add(menuHelp);
		return menuBar;
	}

	private Rectangle getPreferredBounds() {
		Rectangle windowBounds = prefs.getWindowBounds();
		if (windowBounds == null) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension preferredSize = new Dimension(screenSize.width - 100,
					screenSize.height - 100);
			windowBounds = new Rectangle(
					(screenSize.width - preferredSize.width) / 2,
					(screenSize.height - preferredSize.height) / 2,
					preferredSize.width, preferredSize.height);
		}
		return windowBounds;
	}

	/**
	 * Returns the currently selected session.
	 * 
	 * @return Currently selected session
	 */
	public FTPSession getCurrentSession() {
		int selectedTabIndex = tabs.getSelectedIndex();
		if (selectedTabIndex >= 0) {
			return (FTPSession) tabs.getComponentAt(selectedTabIndex);
		}
		return null;
	}

	/**
	 * Serializes and saves the preferences to a file in the USER_HOME
	 * directory.
	 * 
	 * @param prefs
	 *            Preferences
	 * @exception IOException
	 *                if an IO error occurs
	 */
	public static synchronized void savePreferences(JFTPPreferences prefs)
			throws IOException {
		ObjectOutputStream out = null;
		try {
			File prefsFile = new File(DATA_HOME, "preferences.ser");
			out = new ObjectOutputStream(new FileOutputStream(prefsFile));
			out.writeObject(prefs);
			transferTypes = prefs.getTransferTypes();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * Loads the preferences by reading them from the file. If the file is not
	 * found, all preferences are set to their default values and they will be
	 * stored in the file.
	 * 
	 * @exception IOException
	 *                if an IO error occurs
	 */
	public static synchronized JFTPPreferences loadPreferences()
			throws IOException {
		FileInputStream fin = null;
		ObjectInputStream in = null;
		JFTPPreferences prefs = new JFTPPreferences();

		try {
			if (!DATA_HOME.exists()) {
				DATA_HOME.mkdirs();
			}
			File prefsFile = new File(DATA_HOME, "preferences.ser");
			if (!prefsFile.exists()) {
				prefsFile.createNewFile();
				savePreferences(new JFTPPreferences());
			}
			fin = new FileInputStream(prefsFile);
			in = new ObjectInputStream(fin);
			if (fin.available() != 0) {
				try {
					prefs = (JFTPPreferences) in.readObject();
				} catch (ClassNotFoundException exp) {
				} catch (ClassCastException exp) {
				}
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (fin != null) {
				fin.close();
			}
			return prefs;
		}
	}

	/**
	 * Returns all the installed theme names
	 * 
	 * @return An array of strings containing the installed theme names.
	 */
	public static String[] getInstalledThemes() {
		String[] themes = new String[THEMES.size()];
		themes = (String[]) THEMES.keySet().toArray(themes);
		Arrays.sort(themes);
		return themes;
	}

	/**
	 * Returns the class name implementing the given theme name
	 * 
	 * @param themeName
	 *            Name of the theme
	 * @return Class name of the theme
	 */
	public static String getThemeClassName(String themeName) {
		return (String) THEMES.get(themeName);
	}

	/**
	 * Returns an array of installed list parsers
	 * 
	 * @return Installed List parser names
	 */
	public static String[] getInstalledParsers() {
		String[] parsers = new String[PARSERS.size()];
		parsers = (String[]) PARSERS.keySet().toArray(parsers);
		return parsers;
	}

	/**
	 * Returns the class name of the List Parser, given the name of the parser
	 * 
	 * @param parserName
	 *            Name of the list parser
	 * @return Class name implementing the list parser
	 */
	public static String getParserClassName(String parserName) {
		return (String) PARSERS.get(parserName);
	}

	/**
	 * Returns the parser name given the parser class name
	 * 
	 * @param parserClassName
	 *            Class name of the list parser
	 * @return Name of the list parser
	 */
	public static String getParserName(String parserClassName) {
		Set keySet = PARSERS.keySet();
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (parserClassName.equals(PARSERS.get(key))) {
				return key;
			}
		}
		return null;
	}

	/**
	 * Gets the installed FTP client implementations.
	 * 
	 * @return Installed FTP client implementations
	 */
	public static String[] getInstalledClients() {
		String[] clients = new String[CLIENTS.size()];
		clients = (String[]) CLIENTS.keySet().toArray(clients);
		return clients;
	}

	/**
	 * Returns the client class name given the name of the client
	 * implementation.
	 * 
	 * @param clientName
	 *            name of the FTP client implementation
	 * @return Class name implementing the FTP client interface
	 */
	public static String getClientClassName(String clientName) {
		return (String) CLIENTS.get(clientName);
	}

	/**
	 * Returns the name of the implementation given the class name.
	 * 
	 * @param clientClassName
	 *            Class name
	 * @return Name of the FTP client implementation
	 */
	public static String getClientName(String clientClassName) {
		Set keySet = CLIENTS.keySet();
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (clientClassName.equals(CLIENTS.get(key))) {
				return key;
			}
		}
		return null;
	}

	public void updateSessionTitle(FTPSession session) {
		int index = tabs.indexOfComponent(session);
		if (index < 0) {
			return;
		}
		String title = resources.getString("text.notConnected");
		String tooltip = null;
		if (session.isConnected()) {
			title = session.getRemoteHost().getName();
			tooltip = session.getRemoteHost().getHostName();
			if (title == null || title.trim().length() == 0) {
				title = tooltip;
			}
		}
		tabs.setTitleAt(index, title);
		tabs.setToolTipTextAt(index, tooltip);
	}

	public void showAboutDialog() {
		AboutDlg dlg = new AboutDlg(this);
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
	}

	/**
	 * Updates the state of various actions(menu items/toolbar buttons) based on
	 * the currently selected FTP session.
	 */
	public void updateToolBar() {
		toolBar.updateButtons();
	}

	private void showLicenseAgreement() {
		String licenseAgreedForVersion = prefs.getLicenseAgreedForVersion();
		if (!JFTPConstants.PRODUCT_VERSION.equals(licenseAgreedForVersion)) {
			LicenseAgreementDlg dlg = new LicenseAgreementDlg(this);
			dlg.setURL(getClass().getResource("license.html"));
			dlg.setLocationRelativeTo(this);
			dlg.setVisible(true);
			boolean licenseAgreed = dlg.isLicenseAgreed();
			dlg.dispose();
			System.gc();
			if (!licenseAgreed) {
				System.exit(1);
			}
			prefs.setLicenseAgreedForVersion(JFTPConstants.PRODUCT_VERSION);
			try {
				savePreferences(prefs);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void selectAllLocalFiles() {
		FTPSession session = getCurrentSession();
		if (session != null) {
			session.selectAllLocalFiles();
		}
	}

	private void invertLocalFileSelection() {
		FTPSession session = getCurrentSession();
		if (session != null) {
			session.invertLocalFileSelection();
		}
	}

	private void selectAllRemoteFiles() {
		FTPSession session = getCurrentSession();
		if (session != null && session.isConnected()) {
			session.selectAllRemoteFiles();
		}
	}

	private void invertRemoteFileSelection() {
		FTPSession session = getCurrentSession();
		if (session != null && session.isConnected()) {
			session.invertRemoteFileSelection();
		}
	}
}