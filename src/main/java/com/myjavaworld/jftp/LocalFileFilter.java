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