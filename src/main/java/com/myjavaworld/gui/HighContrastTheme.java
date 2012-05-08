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
