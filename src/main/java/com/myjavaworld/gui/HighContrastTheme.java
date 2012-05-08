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
package com.myjavaworld.gui;

import javax.swing.plaf.ColorUIResource;

/**
 * High Contrast theme for the user interface.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class HighContrastTheme extends DefaultTheme {

	private static final ColorUIResource primary1 = new ColorUIResource(0, 0, 0);
	private static final ColorUIResource primary2 = new ColorUIResource(153,
			153, 153);
	private static final ColorUIResource primary3 = new ColorUIResource(204,
			204, 204);
	private static final ColorUIResource secondary1 = new ColorUIResource(102,
			102, 102);
	private static final ColorUIResource secondary2 = new ColorUIResource(204,
			204, 204);
	private static final ColorUIResource secondary3 = new ColorUIResource(255,
			255, 255);

	@Override
	public String getName() {
		return "High Contrast Theme";
	}

	@Override
	protected ColorUIResource getPrimary1() {
		return primary1;
	}

	@Override
	protected ColorUIResource getPrimary2() {
		return primary2;
	}

	@Override
	protected ColorUIResource getPrimary3() {
		return primary3;
	}

	@Override
	protected ColorUIResource getSecondary1() {
		return secondary1;
	}

	@Override
	protected ColorUIResource getSecondary2() {
		return secondary2;
	}

	@Override
	protected ColorUIResource getSecondary3() {
		return secondary3;
	}
}
