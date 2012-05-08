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
public class StatusEvent extends EventObject {

	private String status = null;

	public StatusEvent(Object source, String status) {
		super(source);
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
