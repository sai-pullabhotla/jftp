/*
 * Copyright 2004 jMethods, Inc. All rights reserved.
 * jMethods PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myjavaworld.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Sai Pullabhotla
 */
public class FileUtilities {

	public static void copyFile(File source, File target) throws IOException {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		final int bufferSize = 4096;
		byte[] buffer = new byte[bufferSize];
		int bytesRead = 0;
		try {
			in = new BufferedInputStream(new FileInputStream(source),
					bufferSize);
			out = new BufferedOutputStream(new FileOutputStream(target),
					bufferSize);
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}