/*
 * Copyright 2004 jMethods, Inc. All rights reserved.
 * jMethods PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
