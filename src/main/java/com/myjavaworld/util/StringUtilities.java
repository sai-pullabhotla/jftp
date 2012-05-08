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

import java.util.StringTokenizer;

/**
 * 
 * @author Sai Pullabhotla
 * 
 */
public class StringUtilities {

	public static String getFormattedMessage(String input) {
		return getFormattedMessage(input, 120);
	}

	public static String getFormattedMessage(String input, int chars) {
		StringBuffer output = new StringBuffer(input.length() + 10);
		StringTokenizer lineTokenizer = new StringTokenizer(input, "\n", true);
		while (lineTokenizer.hasMoreTokens()) {
			String line = lineTokenizer.nextToken();
			if (line.length() < chars) {
				output.append(line);
			} else {
				int lineLength = 0;
				StringTokenizer wordTokenizer = new StringTokenizer(line, " ");
				while (wordTokenizer.hasMoreTokens()) {
					String word = wordTokenizer.nextToken();
					int wordLength = word.length();
					if (lineLength + wordLength + 1 < chars) {
						output.append(word);
						output.append(" ");
						lineLength += wordLength + 1;
					} else {
						output.append("\n");
						output.append(word);
						output.append(" ");
						lineLength = wordLength + 1;
					}
				}
			}
		}
		return output.toString();
	}
}
