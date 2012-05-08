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

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * An utility class used to load resources.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 1.0
 * 
 */
public class ResourceLoader {

	public static ResourceBundle getBundle(String baseName) {
		return getBundle(baseName, Locale.getDefault());
	}

	public static ResourceBundle getBundle(String baseName, Locale locale) {
		try {
			return ResourceBundle.getBundle(baseName, locale);
		} catch (MissingResourceException exp) {
			exp.printStackTrace();
			fireResourceNotFound(baseName, locale);
			return null;
		}
	}

	public static ResourceBundle getBundle(String baseName, Locale locale,
			ClassLoader loader) {
		try {
			return ResourceBundle.getBundle(baseName, locale, loader);
		} catch (MissingResourceException exp) {
			exp.printStackTrace();
			fireResourceNotFound(baseName, locale);
			return null;
		}
	}

	private static void fireResourceNotFound(String baseName, Locale locale) {
		System.err.println("Resource " + baseName + " Not Found for Locale "
				+ locale + ". ");
		System.exit(1);
	}
}
