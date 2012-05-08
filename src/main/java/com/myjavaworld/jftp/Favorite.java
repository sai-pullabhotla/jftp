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

/**
 * Objects of this class encapsulate the information of FTP site parameters.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class Favorite extends RemoteHost implements java.io.Serializable,
		Comparable {

	private static final long serialVersionUID = -8742993458243896488L;

	/**
	 * Creates a new instance of Favorite
	 */
	public Favorite() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Favorite)) {
			return false;
		}
		Favorite that = (Favorite) obj;
		return this.name.equalsIgnoreCase(that.name);
	}

	public int hashcode() {
		return name.hashCode();
	}

	@Override
	public int compareTo(Object obj) {
		Favorite that = (Favorite) obj;
		return this.name.toUpperCase().compareTo(that.name.toUpperCase());
	}
}
