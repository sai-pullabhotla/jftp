/*
 * Copyright 2004 jMethods, Inc. All rights reserved.
 * jMethods PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myjavaworld.util;

import java.util.ResourceBundle;

/**
 * 
 * @author Sai Pullabhotla
 * 
 */
public class CommonResources {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.util.CommonResources");

	public static String getString(String key) {
		return resources.getString(key);
	}
}
