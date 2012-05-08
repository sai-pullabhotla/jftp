/*
 * This software is the confidential and proprietary information of the author,
 * Sai Pullabhotla. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with the author. 
 * 
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF 
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR 
 * NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY 
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR 
 * ITS DERIVATIVES.
 */
package com.myjavaworld.util;

import java.security.SecureRandom;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 */
public class RandomKeyGenerator {

	private static final char[] CHARSET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z' };
	private static final SecureRandom sr = new SecureRandom();

	/**
	 * Generates a random string with the given length.This method uses only the
	 * UPPER case alphabets and numbers from 0-9 to generate the random string.
	 * 
	 * @param length
	 *            length of the random string
	 * @return Random string with the specified length.
	 */
	public static String generate(int length) {
		if (length < 1) {
			throw new IllegalArgumentException("Invalid length: " + length);
		}
		StringBuffer output = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			int n = sr.nextInt(CHARSET.length);
			output.append(CHARSET[n]);
		}
		return output.toString();
	}

	/**
	 * Formats a string. All this method does is inserts a hyphen (-) after
	 * every 4 characters in the given input.
	 * 
	 * @param key
	 *            Key to format
	 * @return Formatted key.
	 */
	public static String formatKey(String key) {
		int length = key.length();
		StringBuffer buffer = new StringBuffer(length + length / 4);
		for (int i = 0; i < length; i++) {
			if (i > 0 && i % 4 == 0) {
				buffer.append('-');
			}
			buffer.append(key.charAt(i));
		}
		return buffer.toString();
	}
}