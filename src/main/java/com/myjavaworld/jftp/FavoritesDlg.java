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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

import javax.swing.AbstractListModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.ImageCellRenderer;
import com.myjavaworld.gui.MButton;
import com.myjavaworld.gui.MDialog;
import com.myjavaworld.gui.MList;
import com.myjavaworld.gui.MScrollPane;
import com.myjavaworld.util.CommonResources;
import com.myjavaworld.util.ResourceLoader;

/**
 * A central point for managing favourites.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class FavoritesDlg extends MDialog implements ActionListener,
		ListSelectionListener, MouseListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.FavoritesDlg");
	private static final String HELP_ID = "favorites.favoritesManager";
	private JFTP jftp = null;
	private MList listFavorites = null;
	private FavoritesListModel model = null;
	private MButton butAdd = null;
	private MButton butEdit = null;
	private MButton butDelete = null;
	private MButton butConnect = null;
	private MButton butClose = null;
	private MButton butHelp = null;

	/**
	 * Creates an instance of <code>FavoritesDlg</code>.
	 * 
	 * @param jftp
	 *            JFTP main window
	 * 
	 */
	public FavoritesDlg(JFTP jftp) {
		super(jftp);
		this.jftp = jftp;
		setTitle(resources.getString("title.dialog"));
		setModal(true);
		getContentPane().setLayout(new GridBagLayout());
		JFTPHelp2.getInstance().enableHelpKey(getRootPane(), HELP_ID);
		initComponents();
		loadFavorites();
		pack();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == butAdd) {
			addButtonPressed();
		} else if (evt.getSource() == butEdit) {
			editButtonPressed();
		} else if (evt.getSource() == butDelete) {
			deleteButtonPressed();
		} else if (evt.getSource() == butClose) {
			saveFavorites();
			setVisible(false);
		} else if (evt.getSource() == butConnect) {
			connectButtonPressed();
		}
	}

	@Override
	public void windowClosing(WindowEvent evt) {
		saveFavorites();
		super.windowClosing(evt);
	}

	public void valueChanged(ListSelectionEvent listSelectionEvent) {
		int[] selectedIndices = listFavorites.getSelectedIndices();
		if (selectedIndices == null || selectedIndices.length == 0) {
			butAdd.setEnabled(true);
			butEdit.setEnabled(false);
			butDelete.setEnabled(false);
			butConnect.setEnabled(false);
		} else if (selectedIndices.length == 1) {
			butAdd.setEnabled(true);
			butEdit.setEnabled(true);
			butDelete.setEnabled(true);
			butConnect.setEnabled(true);
		} else if (selectedIndices.length > 1) {
			butAdd.setEnabled(true);
			butEdit.setEnabled(false);
			butDelete.setEnabled(true);
			butConnect.setEnabled(false);
		}
	}

	@Override
	public void windowOpened(WindowEvent evt) {
		valueChanged(null);
	}

	public void mouseClicked(MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			int index = listFavorites.locationToIndex(evt.getPoint());
			if (index < 0) {
				return;
			}
			connectButtonPressed();
		}
	}

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseExited(MouseEvent evt) {
	}

	public void mousePressed(MouseEvent evt) {
	}

	public void mouseReleased(MouseEvent evt) {
	}

	@Override
	protected void escape() {
		butClose.doClick();
	}

	private void addButtonPressed() {
		FavoritePropertiesDlg dlg = new FavoritePropertiesDlg(this,
				FavoritePropertiesDlg.ADD_MODE);
		Favorite favorite = new Favorite();
		favorite.setFTPClientClassName(JFTP.prefs.getClient());
		favorite.setListParserClassName(JFTP.prefs.getListParser());
		favorite.setPassive(JFTP.prefs.isPassive());
		dlg.setFavorite(favorite);
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
		favorite = dlg.getFavorite();
		dlg.dispose();
		if (favorite != null) {
			if (!model.contains(favorite)) {
				model.add(favorite);
				listFavorites.setSelectedValue(favorite, true);
			} else {
				GUIUtil.showError(this,
						resources.getString("error.duplicateFavorite"));
				listFavorites.clearSelection();
			}
		}
	}

	private void editButtonPressed() {
		int selectedIndex = listFavorites.getSelectedIndex();
		if (selectedIndex < 0) {
			return;
		}
		Favorite selectedFavorite = (Favorite) (model.get(selectedIndex));
		FavoritePropertiesDlg dlg = new FavoritePropertiesDlg(this,
				FavoritePropertiesDlg.EDIT_MODE);
		dlg.setFavorite(selectedFavorite);
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
		Favorite favorite = dlg.getFavorite();
		dlg.dispose();

		if (favorite != null) {
			if (!(model.contains(favorite) && !favorite.getName()
					.equalsIgnoreCase(selectedFavorite.getName()))) {
				model.set(selectedIndex, favorite);
				listFavorites.setSelectedValue(favorite, true);
			} else {
				GUIUtil.showError(this,
						resources.getString("error.duplicateFavorite"));
				listFavorites.clearSelection();
			}
		}
	}

	private void deleteButtonPressed() {
		Object[] selectedObjects = listFavorites.getSelectedValues();
		if (selectedObjects == null || selectedObjects.length == 0) {
			return;
		}
		int option = GUIUtil.showConfirmation(this,
				resources.getString("confirm.deleteFavorites"));
		if (option != JOptionPane.YES_OPTION) {
			return;
		}
		java.util.List list = new ArrayList(selectedObjects.length);
		for (int i = 0; i < selectedObjects.length; i++) {
			list.add(selectedObjects[i]);
		}
		model.delete(list);
		listFavorites.clearSelection();
	}

	private void connectButtonPressed() {
		int selectedIndex = listFavorites.getSelectedIndex();
		if (selectedIndex < 0) {
			return;
		}
		RemoteHost selectedHost = (RemoteHost) (model.get(selectedIndex));
		FTPSession session = jftp.getCurrentSession();
		if (session == null) {
			jftp.newSession();
			session = jftp.getCurrentSession();
			// Double check to make sure we got a non-null session.
			if (session == null) {
				return;
			}
		}
		saveFavorites();
		setVisible(false);
		session.connect(selectedHost);
	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 5;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(12, 12, 12, 12);
		model = new FavoritesListModel();
		listFavorites = new MList(model);
		listFavorites.setVisibleRowCount(15);
		listFavorites.setCellRenderer(new ImageCellRenderer(JFTPUtil
				.getIcon("server16.gif")));
		listFavorites.setPrototypeCellValue("ftp.jMethods.com");
		MScrollPane listScroller = new MScrollPane(listFavorites);
		getContentPane().add(listScroller, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(6, 12, 12, 12);
		getContentPane().add(getCommandButtons(), c);

		listFavorites.addListSelectionListener(this);
		listFavorites.addMouseListener(this);
	}

	private Component getCommandButtons() {
		Box panel = new Box(BoxLayout.X_AXIS);

		butAdd = new MButton(CommonResources.getString("text.add"));
		butAdd.addActionListener(this);

		butEdit = new MButton(CommonResources.getString("text.edit"));
		butEdit.addActionListener(this);

		butDelete = new MButton(CommonResources.getString("text.delete"));
		butDelete.addActionListener(this);

		butConnect = new MButton(resources.getString("text.connect"));
		butConnect.addActionListener(this);

		butClose = new MButton(CommonResources.getString("text.close"));
		butClose.addActionListener(this);

		butHelp = new MButton(CommonResources.getString("text.help"));
		JFTPHelp2.getInstance().enableHelp(butHelp, HELP_ID);

		getRootPane().setDefaultButton(butConnect);

		panel.add(butAdd);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(butEdit);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(butDelete);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(butConnect);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(butClose);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(butHelp);
		return panel;
	}

	private void loadFavorites() {
		java.util.List favorites = new ArrayList();
		try {
			favorites = FavoritesManager.getFavorites();
			model.setFavorites(favorites);
		} catch (Exception exp) {
			GUIUtil.showError(this, exp);
		}
	}

	private void saveFavorites() {
		java.util.List favorites = model.getFavorites();
		try {
			FavoritesManager.saveFavorites(favorites);
		} catch (Exception exp) {
			GUIUtil.showError(this, exp);
		}
	}

	/**
	 * An implementation of List model to display the favourites in a JList
	 * component.
	 * 
	 */
	class FavoritesListModel extends AbstractListModel {

		private java.util.List favorites = null;

		public FavoritesListModel() {
			this(new ArrayList());
		}

		public FavoritesListModel(java.util.List favorites) {
			setFavorites(favorites);
		}

		public void setFavorites(java.util.List favorites) {
			if (favorites == null) {
				favorites = new ArrayList();
			}
			this.favorites = favorites;
			Collections.sort(this.favorites);
			fireContentsChanged(this, 0, this.favorites.size());
		}

		public java.util.List getFavorites() {
			return favorites;
		}

		public int getSize() {
			return favorites.size();
		}

		public Object getElementAt(int index) {
			return favorites.get(index);
		}

		public void add(Object obj) {
			favorites.add(obj);
			Collections.sort(favorites);
			fireContentsChanged(this, 0, this.favorites.size());
		}

		public void set(int index, Object obj) {
			favorites.set(index, obj);
			Collections.sort(favorites);
			fireContentsChanged(this, 0, this.favorites.size());
		}

		public Object get(int index) {
			return favorites.get(index);
		}

		public void delete(Collection objects) {
			favorites.removeAll(objects);
			fireContentsChanged(this, 0, this.favorites.size());
		}

		public boolean contains(Object obj) {
			return favorites.contains(obj);
		}
	}
}
