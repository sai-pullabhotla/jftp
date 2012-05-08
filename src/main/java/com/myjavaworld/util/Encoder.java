/*
 * This software is the confidential and proprietary information of the 
 * author, Sai Pullabhotla. You shall not disclose such Confidential 
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with the author.
 *
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF 
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
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
