/*
 * Copyright 2004 jMethods, Inc. All rights reserved.
 * jMethods PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myjavaworld.util;

import java.io.File;
import java.util.EventObject;

/**
 * @author Sai Pullabhotla
 */
public class FileChangeEvent extends EventObject {

	public File file = null;
	public long oldDate = 0L;
	public long newDate = 0L;

	public FileChangeEvent(Object source, File file, long oldDate, long newDate) {
		super(source);
		this.file = file;
		this.oldDate = oldDate;
		this.newDate = newDate;
	}

	public File getFile() {
		return file;
	}

	public long getOldDate() {
		return oldDate;
	}

	public long getnewDate() {
		return newDate;
	}
}