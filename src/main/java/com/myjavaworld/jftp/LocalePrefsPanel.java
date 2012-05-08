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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.myjavaworld.gui.MComboBox;
import com.myjavaworld.gui.MLabel;
import com.myjavaworld.util.ResourceLoader;

/**
 * A Supporting panel used by Preferences dialog.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class LocalePrefsPanel extends JPanel implements ActionListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.LocalePrefsPanel");
	private MComboBox comboLocales = null;
	private JRadioButton radioDateFormatShort = null;
	private JRadioButton radioDateFormatMedium = null;
	private JRadioButton radioTimeFormatShort = null;
	private JRadioButton radioTimeFormatMedium = null;
	private MLabel labPreviewDateFormat = null;
	private MLabel labPreviewTimeFormat = null;

	/**
	 * Creates an instance of <code>LocalePrefsPanel</code>.
	 * 
	 */
	public LocalePrefsPanel() {
		super();
		setLayout(new GridBagLayout());
		initComponents();
	}

	public boolean validateFields() {
		return true;
	}

	public void populateScreen() {
		populateScreen(JFTP.prefs);
	}

	public void populateScreen(JFTPPreferences prefs) {
		comboLocales.setSelectedItem(Locale.getDefault());
		if (prefs.getDateFormat() == DateFormat.SHORT) {
			radioDateFormatShort.setSelected(true);
		} else {
			radioDateFormatMedium.setSelected(true);
		}
		if (prefs.getTimeFormat() == DateFormat.SHORT) {
			radioTimeFormatShort.setSelected(true);
		} else {
			radioTimeFormatMedium.setSelected(true);
		}
		updatePreview();
	}

	public void saveChanges() {
		JFTP.prefs.setLocale((Locale) comboLocales.getSelectedItem());
		if (radioDateFormatShort.isSelected()) {
			JFTP.prefs.setDateFormat(DateFormat.SHORT);
		} else {
			JFTP.prefs.setDateFormat(DateFormat.MEDIUM);
		}
		if (radioTimeFormatShort.isSelected()) {
			JFTP.prefs.setTimeFormat(DateFormat.SHORT);
		} else {
			JFTP.prefs.setTimeFormat(DateFormat.MEDIUM);
		}

	}

	private void initComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;

		MLabel labLocale = new MLabel(resources.getString("text.locale"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(12, 12, 12, 12);
		add(labLocale, c);

		comboLocales = new MComboBox(Locale.getAvailableLocales());
		comboLocales.setRenderer(new LocaleCellRenderer());
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.insets = new Insets(12, 0, 12, 12);
		add(comboLocales, c);

		MLabel labDateFormat = new MLabel(
				resources.getString("text.dateFormat"));
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		add(labDateFormat, c);

		radioDateFormatShort = new JRadioButton(
				resources.getString("text.short"));
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 0, 12, 12);
		add(radioDateFormatShort, c);

		radioDateFormatMedium = new JRadioButton(
				resources.getString("text.medium"));
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 0, 12, 12);
		add(radioDateFormatMedium, c);

		MLabel labPreview1 = new MLabel(resources.getString("text.preview"));
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		add(labPreview1, c);

		labPreviewDateFormat = new MLabel();
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.insets = new Insets(0, 0, 12, 12);
		add(labPreviewDateFormat, c);

		MLabel labTimeFormat = new MLabel(
				resources.getString("text.timeFormat"));
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		add(labTimeFormat, c);

		radioTimeFormatShort = new JRadioButton(
				resources.getString("text.short"));
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 0, 12, 12);
		add(radioTimeFormatShort, c);

		radioTimeFormatMedium = new JRadioButton(
				resources.getString("text.medium"));
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 0, 12, 12);
		add(radioTimeFormatMedium, c);

		MLabel labPreview2 = new MLabel(resources.getString("text.preview"));
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0, 12, 12, 12);
		add(labPreview2, c);

		labPreviewTimeFormat = new MLabel();
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.insets = new Insets(0, 0, 12, 12);
		add(labPreviewTimeFormat, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(new MLabel(), c);

		ButtonGroup bgDate = new ButtonGroup();
		bgDate.add(radioDateFormatShort);
		bgDate.add(radioDateFormatMedium);
		ButtonGroup bgTime = new ButtonGroup();
		bgTime.add(radioTimeFormatShort);
		bgTime.add(radioTimeFormatMedium);

		populateScreen();

		// updatePreview();

		comboLocales.addActionListener(this);
		radioDateFormatShort.addActionListener(this);
		radioDateFormatMedium.addActionListener(this);
		radioTimeFormatShort.addActionListener(this);
		radioTimeFormatMedium.addActionListener(this);
	}

	public void actionPerformed(ActionEvent evt) {
		updatePreview();
	}

	private void updatePreview() {
		Locale selectedLocale = (Locale) comboLocales.getSelectedItem();
		DateFormat df = null;
		if (radioDateFormatShort.isSelected()) {
			df = DateFormat.getDateInstance(DateFormat.SHORT, selectedLocale);
		} else {
			df = DateFormat.getDateInstance(DateFormat.MEDIUM, selectedLocale);
		}
		labPreviewDateFormat.setText(df.format(new Date()));
		if (radioTimeFormatShort.isSelected()) {
			df = DateFormat.getTimeInstance(DateFormat.SHORT, selectedLocale);
		} else {
			df = DateFormat.getTimeInstance(DateFormat.MEDIUM, selectedLocale);
		}
		labPreviewTimeFormat.setText(df.format(new Date()));
	}
}