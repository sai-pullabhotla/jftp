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

import java.util.Comparator;

import com.myjavaworld.ftp.RemoteFile;

/**
 * An implementation of <code>Comparator</code> interface used to compare
 * <code>RemoteFile</code> objects. This class provides various sort crteria
 * such as compare by name, size, date etc.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class RemoteFileComparator implements Comparator {

	public static final int COMPARE_BY_NAME = 1;
	public static final int COMPARE_BY_TYPE = 2;
	public static final int COMPARE_BY_SIZE = 3;
	public static final int COMPARE_BY_DATE = 4;
	public static final int ASC_ORDER = 1;
	public static final int DESC_ORDER = 2;
	private int compareBy = 0;
	private int order = 0;

	public RemoteFileComparator() {
		this(COMPARE_BY_NAME, ASC_ORDER);
	}

	public RemoteFileComparator(int compareBy) {
		this(compareBy, ASC_ORDER);
	}

	public RemoteFileComparator(int compareBy, int order) {
		setCompareBy(compareBy);
		setOrder(order);
	}

	public void setCompareBy(int compareBy) {
		this.compareBy = compareBy;
	}

	public int getCompareBy() {
		return compareBy;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public int compare(Object o1, Object o2) {
		RemoteFile r1 = (RemoteFile) o1;
		RemoteFile r2 = (RemoteFile) o2;
		if (compareBy == COMPARE_BY_NAME) {
			return compareByName(r1, r2);
		} else if (compareBy == COMPARE_BY_SIZE) {
			return compareBySize(r1, r2);
		} else if (compareBy == COMPARE_BY_DATE) {
			return compareByDate(r1, r2);
		} else {
			return compareByType(r1, r2);
		}
	}

	private int compareByName(RemoteFile r1, RemoteFile r2) {
		int retVal = 0;
		if (r1.isDirectory() && !r2.isDirectory()) {
			retVal = -1;
		} else if (!r1.isDirectory() && r2.isDirectory()) {
			retVal = 1;
		} else {
			retVal = r1.getName().toUpperCase()
					.compareTo(r2.getName().toUpperCase());
		}
		return order == ASC_ORDER ? retVal : -retVal;
	}

	private int compareBySize(RemoteFile r1, RemoteFile r2) {
		int retVal = 0;
		if (r1.isDirectory() && !r2.isDirectory()) {
			retVal = -1;
		} else if (!r1.isDirectory() && r2.isDirectory()) {
			retVal = 1;
		} else {
			if (r1.getSize() == r2.getSize()) {
				retVal = 0;
			} else {
				retVal = r1.getSize() < r2.getSize() ? -1 : 1;
			}
			if (retVal == 0) {
				retVal = r1.getName().toUpperCase()
						.compareTo(r2.getName().toUpperCase());
			}
		}
		return order == ASC_ORDER ? retVal : -retVal;
	}

	private int compareByDate(RemoteFile r1, RemoteFile r2) {
		int retVal = 0;
		if (r1.isDirectory() && !r2.isDirectory()) {
			retVal = -1;
		} else if (!r1.isDirectory() && r2.isDirectory()) {
			retVal = 1;
		} else {
			if (r1.getLastModified() == r2.getLastModified()) {
				retVal = 0;
			} else {
				retVal = r1.getLastModified() < r2.getLastModified() ? -1 : 1;
			}
		}
		return order == ASC_ORDER ? retVal : -retVal;
	}

	private int compareByType(RemoteFile r1, RemoteFile r2) {
		int retVal = 0;
		if (r1.isDirectory() && !r2.isDirectory()) {
			retVal = -1;
		} else if (!r1.isDirectory() && r2.isDirectory()) {
			retVal = 1;
		} else {
			retVal = r1.getType().compareTo(r2.getType());
			if (retVal == 0) {
				retVal = r1.getName().toUpperCase()
						.compareTo(r2.getName().toUpperCase());
			}
		}
		return order == ASC_ORDER ? retVal : -retVal;
	}
}