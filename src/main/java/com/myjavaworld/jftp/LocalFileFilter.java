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
package com.myjavaworld.jftp;

import com.myjavaworld.util.DateFilter;
import com.myjavaworld.util.Filter;
import com.myjavaworld.util.RegexFilter;

/**
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 1.0
 */
public class LocalFileFilter implements Filter {

	private RegexFilter regexFilter = null;
	private DateFilter dateFilter = null;
	private boolean showHiddenFiles = true;
	private boolean exclusionFilter = false;

	/**
	 * Creates an instance of <code>LocalFileFilter</code>.
	 */
	public LocalFileFilter() {
		this(null, null, true, false);
	}

	/**
	 * Creates an instance of <code>LocalFileFilter</code>.
	 * 
	 * @param regexFilter
	 * @param dateFilter
	 */
	public LocalFileFilter(RegexFilter regexFilter, DateFilter dateFilter) {
		this(regexFilter, dateFilter, true, false);
	}

	/**
	 * Creates an instance of <code>LocalFileFilter</code>.
	 * 
	 * @param regexFilter
	 * @param dateFilter
	 * @param showHiddenFiles
	 */
	public LocalFileFilter(RegexFilter regexFilter, DateFilter dateFilter,
			boolean showHiddenFiles) {
		this(regexFilter, dateFilter, showHiddenFiles, false);
	}

	public LocalFileFilter(RegexFilter regexFilter, DateFilter dateFilter,
			boolean showHiddenFiles, boolean exclusionFilter) {
		setRegexFilter(regexFilter);
		setDateFilter(dateFilter);
		setShowHiddenFiles(showHiddenFiles);
		setExclusionFilter(exclusionFilter);
	}

	/**
	 * @return Returns the dateFilter.
	 */
	public DateFilter getDateFilter() {
		return dateFilter;
	}

	/**
	 * @param dateFilter
	 *            The dateFilter to set.
	 */
	public void setDateFilter(DateFilter dateFilter) {
		this.dateFilter = dateFilter;
	}

	/**
	 * @return Returns the regexFilter.
	 */
	public RegexFilter getRegexFilter() {
		return regexFilter;
	}

	/**
	 * @param regexFilter
	 *            The regexFilter to set.
	 */
	public void setRegexFilter(RegexFilter regexFilter) {
		this.regexFilter = regexFilter;
	}

	/**
	 * @return Returns the showHiddenFiles.
	 */
	public boolean isShowHiddenFiles() {
		return showHiddenFiles;
	}

	/**
	 * @param showHiddenFiles
	 *            The showHiddenFiles to set.
	 */
	public void setShowHiddenFiles(boolean showHiddenFiles) {
		this.showHiddenFiles = showHiddenFiles;
	}

	public void setExclusionFilter(boolean exclusionFilter) {
		this.exclusionFilter = exclusionFilter;
	}

	public boolean isExclusionFilter() {
		return exclusionFilter;
	}

	public boolean accept(Object value) {
		LocalFile lf = (LocalFile) value;
		if (!showHiddenFiles && lf.isHidden()) {
			return exclusionFilter ? true : false;
		}
		if (lf.isFile()) {
			if (regexFilter != null) {
				if (!regexFilter.accept(lf.getName())) {
					return exclusionFilter ? true : false;
				}
			}
			if (dateFilter != null) {
				if (!dateFilter.accept(new Long(lf.getLastModified()))) {
					return exclusionFilter ? true : false;
				}
			}
			return exclusionFilter ? false : true;
		}
		return true;
	}
}