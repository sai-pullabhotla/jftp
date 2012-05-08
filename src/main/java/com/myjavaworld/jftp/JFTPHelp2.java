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

import java.awt.Component;
import java.net.URL;
import java.util.Locale;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JFTPHelp2 {

	private static String HELPSET_NAME = "helpSet";
	private static String HELPSET_EXTENSION = ".xml";
	private HelpSet hs = null;
	private HelpBroker hb = null;
	private static JFTPHelp2 instance = null;

	private JFTPHelp2() {
		super();
		ClassLoader cl = getClass().getClassLoader();
		URL helpSetURL = HelpSet.findHelpSet(cl, "helpset/" + HELPSET_NAME,
				HELPSET_EXTENSION, Locale.getDefault());
		try {
			hs = new HelpSet(cl, helpSetURL);
		} catch (HelpSetException exp) {
			System.err.println(exp);
		}
		hb = hs.createHelpBroker("main window");
	}

	public HelpBroker getHelpBroker() {
		return hb;
	}

	public static synchronized JFTPHelp2 getInstance() {
		if (instance == null) {
			instance = new JFTPHelp2();
		}
		return instance;
	}

	public void enableHelp(Component comp, String id) {
		hb.enableHelpOnButton(comp, id, hs);
	}

	public void enableHelpKey(Component comp, String id) {
		hb.enableHelpKey(comp, id, hs);
	}
}
