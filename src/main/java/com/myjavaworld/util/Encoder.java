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

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 */
public class Encoder {

	public static String hexEncode(byte[] input) {
		final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			char high = HEX_DIGITS[(input[i] & 0xf0) >> 4];
			char low = HEX_DIGITS[input[i] & 0x0f];
			output.append(high);
			output.append(low);
		}
		return output.toString();
	}
}
