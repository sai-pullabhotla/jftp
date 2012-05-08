/*
 * Copyright 2004 jMethods, Inc. All rights reserved.
 * jMethods PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myjavaworld.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.Timer;
import javax.swing.event.EventListenerList;

/**
 * A thread that monitors zero or more files to see if they are changed. A
 * <code>FileChangedEvent</code> will be delivered to all registered listeners
 * when a file is modified.
 * 
 * @author Sai Pullabhotla
 */
public class FileChangeMonitor implements ActionListener {

	private Hashtable filesToMonitor = null;
	private EventListenerList listenerList = null;
	private Timer timer = null;

	public FileChangeMonitor() {
		filesToMonitor = new Hashtable();
		listenerList = new EventListenerList();
	}

	public synchronized void add(File file) {
		filesToMonitor.put(file, new Long(file.lastModified()));
		if (timer == null) {
			if (timer == null) {
				timer = new Timer(1000, this);
				timer.start();
			}
		}
	}

	public void remove(File file) {
		filesToMonitor.remove(file);
	}

	public void addFileChangeListener(FileChangeListener listener) {
		listenerList.add(FileChangeListener.class, listener);
	}

	public void removeFileChangeListener(FileChangeListener listener) {
		listenerList.remove(FileChangeListener.class, listener);
	}

	public void stopMonitor() {
		if (timer != null) {
			timer.stop();
		}
	}

	public void actionPerformed(ActionEvent evt) {
		Enumeration keys = filesToMonitor.keys();
		while (keys.hasMoreElements()) {
			File file = (File) keys.nextElement();
			Long prevDate = (Long) filesToMonitor.get(file);
			Long currDate = new Long(file.lastModified());
			if (currDate.compareTo(prevDate) > 0) {
				// the file is modified.
				// fire FileChangedEvent.
				FileChangeEvent fce = new FileChangeEvent(this, file,
						prevDate.longValue(), currDate.longValue());
				fireFileChanged(fce);
				filesToMonitor.put(file, new Long(file.lastModified()));
			}
		}
	}

	protected void fireFileChanged(FileChangeEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == FileChangeListener.class) {
				((FileChangeListener) listeners[i + 1]).fileChanged(evt);
			}
		}
	}
}