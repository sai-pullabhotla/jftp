/*
 * Copyright 2004 jMethods, Inc. All rights reserved.
 * jMethods PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myjavaworld.util;

import java.util.EventListener;

/**
 * 
 * @author Sai Pullabhotla
 * 
 */
public interface StatusListener extends EventListener {

	public void statusChanged(StatusEvent evt);
}
