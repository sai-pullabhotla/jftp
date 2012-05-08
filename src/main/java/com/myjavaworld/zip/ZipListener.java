/*
 * Copyright 2004 jMethods, Inc. All rights reserved.
 * jMethods PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myjavaworld.zip;

import java.util.EventListener;

/**
 * @author Sai Pullabhotla
 */
public interface ZipListener extends EventListener {

	public void beginFile(ZipEvent evt);

	public void endFile(ZipEvent evt);
}