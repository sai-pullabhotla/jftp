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
