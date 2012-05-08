/**
 *
 * This software is the confidential and proprietary information of the author,
 * Sai Pullabhotla. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with the author.
 *
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 *
 */
package com.myjavaworld.jftp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

import com.myjavaworld.util.Filter;

/**
 * Objects of this class represent files on the local system.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class LocalFile {

	private static final FileSystemView fsv = FileSystemView
			.getFileSystemView();
	private File file = null;
	private String name = null;
	private String extension = null;
	private long size = 0L;
	private long lastModified = 0L;
	private boolean dir = false;
	private String displayName = null;

	public LocalFile(File file) {
		this.file = file;
		name = file.getName();
		displayName = fsv.getSystemDisplayName(file);
		size = file.length();
		lastModified = file.lastModified();
		dir = file.isDirectory();
		if (dir) {
			extension = null;
		} else {
			int index = name.lastIndexOf(".");
			if (index < 0 || index == name.length() - 1) {
				extension = "";
			} else {
				extension = name.substring(index + 1).toUpperCase();
			}
		}
	}

	public File getFile() {
		return file;
	}

	public String getAbsolutePath() {
		return file.getAbsolutePath();
	}

	public LocalFile getAbsoluteFile() {
		return new LocalFile(file.getAbsoluteFile());
	}

	public String getCanonicalPath() throws IOException {
		return file.getCanonicalPath();
	}

	public LocalFile getCanonicalFile() throws IOException {
		return new LocalFile(file.getCanonicalFile());
	}

	public String getNameOld() {
		return file.getName();
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getExtension() {
		return extension;
	}

	public String getTypeOld() {
		if (extension == null) {
			return "Directory";
		}
		if (extension.length() > 0) {
			return extension + " File";
		}
		return "File";
	}

	public String getType() {
		String type = fsv.getSystemTypeDescription(file);
		if (type == null) {
			return getTypeOld();
		}
		return type;
	}

	public long getSize() {
		return size;
	}

	public long getLastModified() {
		return lastModified;
	}

	public boolean isDirectory() {
		return dir;
	}

	public boolean isFile() {
		return !dir;
	}

	public boolean exists() {
		return file.exists();
	}

	public boolean canRead() {
		return file.canRead();
	}

	public boolean canWrite() {
		return file.canWrite();
	}

	public boolean isHidden() {
		return file.isHidden();
	}

	public LocalFile getParentOld() {
		File parent = file.getParentFile();
		if (parent == null) {
			return null;
		}
		return new LocalFile(parent);
	}

	public LocalFile getParent() {
		File parent = fsv.getParentDirectory(file);
		if (parent == null) {
			return null;
		}
		return new LocalFile(parent);
	}

	public LocalFile[] listOld() {
		File[] f = file.listFiles();
		if (f == null) {
			return null;
		}
		LocalFile[] children = new LocalFile[f.length];
		for (int i = 0; i < f.length; i++) {
			children[i] = new LocalFile(f[i]);
		}
		return children;
	}

	public LocalFile[] list() {
		File[] f = fsv.getFiles(file, false);
		if (f == null) {
			return null;
		}
		LocalFile[] children = new LocalFile[f.length];
		for (int i = 0; i < f.length; i++) {
			children[i] = new LocalFile(f[i]);
		}
		return children;
	}

	public LocalFile[] listOld(Filter filter) {
		if (filter == null) {
			return list();
		}
		LocalFile[] f = list();
		if (f == null) {
			return null;
		}
		List list = new ArrayList(f.length);
		for (int i = 0; i < f.length; i++) {
			if (filter.accept(f[i])) {
				list.add(f[i]);
			}
		}
		LocalFile[] children = new LocalFile[list.size()];
		children = (LocalFile[]) list.toArray(children);
		return children;
	}

	public LocalFile[] list(Filter filter) {
		if (filter == null) {
			return list();
		}
		LocalFile[] f = list();
		if (f == null) {
			return null;
		}
		List list = new ArrayList(f.length);
		for (int i = 0; i < f.length; i++) {
			if (filter.accept(f[i])) {
				list.add(f[i]);
			}
		}
		LocalFile[] children = new LocalFile[list.size()];
		children = (LocalFile[]) list.toArray(children);
		return children;
	}

	public static LocalFile[] listRoots() {
		File[] f = fsv.getRoots();
		if (f == null) {
			return null;
		}
		LocalFile[] roots = new LocalFile[f.length];
		for (int i = 0; i < f.length; i++) {
			roots[i] = new LocalFile(f[i]);
		}
		return roots;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof LocalFile)) {
			return false;
		}
		LocalFile that = (LocalFile) obj;
		return this.file.equals(that.file);
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return 1;
		}
		LocalFile that = (LocalFile) obj;
		return name.toUpperCase().compareTo(that.name.toUpperCase());
	}

	@Override
	public String toString() {
		return file.getAbsolutePath();
	}

	public Icon getIcon() {
		return fsv.getSystemIcon(file);
	}

	public boolean isTraversable() {
		return fsv.isTraversable(file).booleanValue();
	}

	public boolean delete() {
		return file.delete();
	}

	public boolean isDrive() {
		return fsv.isFileSystemRoot(file);
	}
}