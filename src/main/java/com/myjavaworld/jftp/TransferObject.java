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
package com.myjavaworld.jftp;

import com.myjavaworld.ftp.RemoteFile;

/**
 * @author Sai Pullabhotla
 */
public class TransferObject {

	public static int DOWNLOAD = 1;
	public static int UPLOAD = 2;
	private int direction = 0;
	private LocalFile localFile = null;
	private RemoteFile remoteFile = null;

	public TransferObject(int direction, LocalFile localFile,
			RemoteFile remoteFile) {
		this.direction = direction;
		this.localFile = localFile;
		this.remoteFile = remoteFile;
	}

	public int getDirection() {
		return direction;
	}

	public LocalFile getLocalFile() {
		return localFile;
	}

	public RemoteFile getRemoteFile() {
		return remoteFile;
	}

	@Override
	public String toString() {
		return localFile + " -" + (direction == DOWNLOAD ? "<" : ">") + " "
				+ remoteFile;
	}
}