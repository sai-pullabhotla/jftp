/*
 * This software is the confidential and proprietary information of the author,
 * Sai Pullabhotla. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with the author. 
 * 
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF 
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR 
 * NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY 
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR 
 * ITS DERIVATIVES.
 */
package com.myjavaworld.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.event.EventListenerList;

import com.myjavaworld.jftp.LocalFile;
import com.myjavaworld.util.Filter;
import com.myjavaworld.util.ProgressEvent;
import com.myjavaworld.util.ProgressListener;

/**
 * This class is used to create ZIP files.
 * 
 * @author Sai Pullabhotla
 */
public class Zip {

	/**
	 * target ZIP file.
	 */
	private File file = null;
	/**
	 * Directory, relative to which the entries will be added to this zip file.
	 */
	private String relativeTo = null;
	/**
	 * Output stream to write to this zip file.
	 */
	private ZipOutputStream zout = null;
	/**
	 * Event listener list
	 */
	private EventListenerList listenerList = null;
	private Filter filter = null;

	/**
	 * Creates an instance of <code>ZipFile</code>.
	 * 
	 * @param file
	 *            compressed file to create
	 */
	public Zip(File file) {
		this.file = file;
		listenerList = new EventListenerList();
	}

	public File getFile() {
		return file;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Filter getFilter() {
		return filter;
	}

	public void addZipListener(ZipListener l) {
		listenerList.add(ZipListener.class, l);
	}

	public void removeZipListener(ZipListener l) {
		listenerList.remove(ZipListener.class, l);
	}

	public void addProgressListener(ProgressListener l) {
		listenerList.add(ProgressListener.class, l);
	}

	public void removeProgressListener(ProgressListener l) {
		listenerList.remove(ProgressListener.class, l);
	}

	protected void fireBeginFileEvent(File file) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ZipListener.class) {
				((ZipListener) listeners[i + 1]).beginFile(new ZipEvent(this,
						ZipEvent.ZIP, file.getName()));
			}
		}
	}

	protected void fireEndFileEvent(File file) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ZipListener.class) {
				((ZipListener) listeners[i + 1]).endFile(new ZipEvent(this,
						ZipEvent.ZIP, file.getName()));
			}
		}
	}

	protected void fireProgressEvent(int progress) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ProgressListener.class) {
				((ProgressListener) listeners[i + 1])
						.progressChanged(new ProgressEvent(this, progress));
			}
		}
	}

	/**
	 * Returns the directory, relative to which the entries will be added to
	 * this zip file.
	 * 
	 * @return relative directory.
	 */
	public File getRelativeTo() {
		return new File(relativeTo);
	}

	/**
	 * Sets the directory, relative to which all entries will be added to the
	 * zip file.
	 * 
	 * @param relativeTo
	 *            directory, relative to which the entries will be added.
	 */
	public void setRelativeTo(File relativeTo) {
		try {
			this.relativeTo = relativeTo.getCanonicalPath();
		} catch (IOException exp) {
			this.relativeTo = relativeTo.getAbsolutePath();
		}
	}

	/**
	 * Opens this <code>ZipFile</code> for writing. This method must be called
	 * before adding any entries to this <code>CompressedFile</code>.
	 * 
	 * @throws IOException
	 *             propogated.
	 */
	public void open() throws IOException {
		zout = new ZipOutputStream(new FileOutputStream(file));
	}

	/**
	 * Closes this <code>ZipFile</code>. No more entries can be added to this
	 * <code>ZipFile</code> after calling this method. An attempt to add more
	 * entries after calling this method will result in an
	 * <code>IllegalStateException</code>.
	 * 
	 * @throws IOException
	 *             propogated.
	 */
	public void close() throws IOException {
		if (zout != null) {
			zout.close();
		}
		zout = null;
	}

	/**
	 * Adds a new entry to this <code>ZipFile</code>.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void addEntry(File file) throws IOException {
		if (zout == null) {
			throw new IllegalStateException(
					"Zip File was never opened or is already closed. ");
		}
		// Let's not try to add the file that we are creating to itself.
		if (file.equals(this.file)) {
			return;
		}
		ZipEntry entry = new ZipEntry(computeEntryName(file));
		entry.setTime(file.lastModified());
		zout.putNextEntry(entry);
		if (file.isFile()) {
			addFile(file);
			zout.closeEntry();
		} else if (file.isDirectory()) {
			zout.closeEntry();
			LocalFile[] children = new LocalFile(file).list(filter);
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					addEntry(children[i].getFile());
				}
			}
		}
	}

	/**
	 * Adds the given data file to this <code>ZipFile</code>.
	 * 
	 * @param file
	 *            data file to add
	 * @throws IOException
	 *             propogated.
	 */
	private void addFile(File file) throws IOException {
		fireBeginFileEvent(file);
		final int bufferSize = 16 * 1024;
		byte[] buffer = new byte[bufferSize];
		int bytesRead = 0;
		final long fileSize = file.length();
		long totalBytesRead = 0L;
		BufferedInputStream bin = null;
		try {
			bin = new BufferedInputStream(new FileInputStream(file));
			while ((bytesRead = bin.read(buffer)) != -1) {
				zout.write(buffer, 0, bytesRead);
				totalBytesRead += bytesRead;
				int progress = (int) ((totalBytesRead * 100) / fileSize);
				fireProgressEvent(progress);
			}
		} finally {
			fireEndFileEvent(file);
			if (bin != null) {
				bin.close();
			}
		}
	}

	/**
	 * Computes and returns the enry name for the given file.
	 * 
	 * @param file
	 *            file to add.
	 * @return entry name
	 */
	private String computeEntryName(File file) {
		String entryName = null;
		String path = null;
		try {
			path = file.getCanonicalPath();
		} catch (IOException exp) {
			path = file.getAbsolutePath();
		}
		if (relativeTo == null) {
			entryName = path;
		} else {
			entryName = path.substring(relativeTo.length());
		}
		if (entryName.startsWith(File.separator)) {
			entryName = entryName.substring(1);
		}
		if (File.separatorChar != '/') {
			entryName = entryName.replace(File.separatorChar, '/');
		}
		if (file.isDirectory()) {
			entryName += "/";
		}
		return entryName;
	}
}