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
package com.myjavaworld.util;

import java.io.File;
import java.io.IOException;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class SystemUtil {

	private static String osName = null;
	private static String osVersion = null;
	private static String jreVersion = null;
	private static String jreVendor = null;
	private static String javaHome = null;
	private static String userHome = null;
	private static String workingDirectory = null;
	private static boolean isMac = false;

	static {
		osName = System.getProperty("os.name");
		if (osName == null) {
			osName = "Unknown";
		}
		osVersion = System.getProperty("os.version");
		if (osVersion == null) {
			osVersion = "Unknown";
		}
		jreVersion = System.getProperty("java.version");
		if (jreVersion == null) {
			jreVersion = "Unknown";
		}
		jreVendor = System.getProperty("java.vendor");
		if (jreVendor == null) {
			jreVendor = "Unknown";
		}
		javaHome = System.getProperty("java.home");
		if (javaHome == null) {
			javaHome = "Unknown";
		}
		userHome = System.getProperty("user.home");
		if (userHome == null) {
			userHome = "Unknown";
		}
		File wd = new File(".");
		try {
			workingDirectory = wd.getCanonicalPath();
		} catch (IOException exp) {
			workingDirectory = "Unknown";
		}
		// isMac = System.getProperty("mrj.version") != null;
		isMac = osName.indexOf("Mac OS X") >= 0;
	}

	public static boolean isMac() {
		return isMac;
	}

	public static String getOSName() {
		return osName;
	}

	public static String getOSVersion() {
		return osVersion;
	}

	public static String getJREVersion() {
		return jreVersion;
	}

	public static String getJREVendor() {
		return jreVendor;
	}

	public static String getJavaHome() {
		return javaHome;
	}

	public static String getUserHome() {
		return userHome;
	}

	public static String getWorkingDirectory() {
		return workingDirectory;
	}
}
