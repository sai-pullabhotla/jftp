/*
 * Copyright 2004 jMethods, Inc. All rights reserved.
 * jMethods PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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