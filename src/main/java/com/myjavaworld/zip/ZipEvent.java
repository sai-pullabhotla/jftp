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