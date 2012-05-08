/*
 * Copyright 2004 jMethods, Inc. All rights reserved.
 * jMethods PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myjavaworld.zip;

import java.util.EventObject;

/**
 * @author Sai Pullabhotla
 */
public class ZipEvent extends EventObject {

	public static final int ZIP = 1;
	public static final int UNZIP = 2;
	private int type = 0;
	private String file = null;

	public ZipEvent(Object source, int type, String file) {
		super(source);
		this.type = type;
		this.file = file;
	}

	public int getType() {
		return type;
	}

	public String getFile() {
		return file;
	}
}