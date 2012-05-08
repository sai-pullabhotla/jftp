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
package com.myjavaworld.jftp.ssl;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DNParser {

	private String dn = null;

	/**
	 * Creates an instance of <code>DNParser</code>.
	 * 
	 * @param dn
	 *            Distinugised name extracted from an X509 certificate.
	 * 
	 */
	public DNParser(String dn) {
		if (dn == null) {
			throw new NullPointerException();
		}
		this.dn = dn;
	}

	/**
	 * Returns the value of the given parameter <code>param</code>.
	 * 
	 * @param param
	 *            Parameter whose value is to be retrieved from the
	 *            distinguished name. For e.g. C, CN, O, OU etc.
	 * 
	 * @return value of the parameter.
	 * 
	 */
	public String getParameter(String param) {
		String value = null;
		int startIndex = 0;
		int endIndex = 0;
		param = param + "=";
		String dn = new String(this.dn);

		startIndex = dn.indexOf(param);
		if (startIndex >= 0) {
			dn = dn.substring(startIndex + param.length());
			if (dn.charAt(0) == '\"') {
				endIndex = dn.indexOf('\"', 1);
				if (endIndex < 0) {
					value = dn.substring(1);
				} else {
					value = dn.substring(1, endIndex);
				}
			} else {
				endIndex = dn.indexOf(',');
				if (endIndex < 0) {
					value = dn.substring(0);
				} else {
					value = dn.substring(0, endIndex);
				}
			}
		}
		return value;
	}

	public static String getParameter(String distinguishedName, String param) {
		String value = null;
		int startIndex = 0;
		int endIndex = 0;
		param = param + "=";
		String dn = new String(distinguishedName);

		startIndex = dn.indexOf(param);
		if (startIndex >= 0) {
			dn = dn.substring(startIndex + param.length());
			if (dn.charAt(0) == '\"') {
				endIndex = dn.indexOf('\"', 1);
				if (endIndex < 0) {
					value = dn.substring(1);
				} else {
					value = dn.substring(1, endIndex);
				}
			} else {
				endIndex = dn.indexOf(',');
				if (endIndex < 0) {
					value = dn.substring(0);
				} else {
					value = dn.substring(0, endIndex);
				}
			}
		}
		return value;
	}
}
