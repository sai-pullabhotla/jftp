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