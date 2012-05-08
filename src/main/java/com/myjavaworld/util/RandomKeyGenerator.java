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