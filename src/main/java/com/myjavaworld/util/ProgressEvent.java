/*
 * Copyright 2004 jMethods, Inc. All rights reserved.
 * jMethods PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myjavaworld.util;

import java.util.EventObject;

/**
 * 
 * @author Sai Pullabhotla
 * 
 */
public class ProgressEvent extends EventObject {

	private int progress = 0;

	public ProgressEvent(Object source, int progress) {
		super(source);
		this.progress = progress;
	}

	public int getProgress() {
		return progress;
	}
}
