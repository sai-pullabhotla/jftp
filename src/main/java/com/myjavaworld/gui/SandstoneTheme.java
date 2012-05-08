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
 * Sand stone theme.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 * 
 */
public class SandstoneTheme extends DefaultTheme {

	@Override
	public String getName() {
		return "Sandstone";
	}

	private final ColorUIResource primary1 = new ColorUIResource(87, 87, 47);
	private final ColorUIResource primary2 = new ColorUIResource(159, 151, 111);
	private final ColorUIResource primary3 = new ColorUIResource(199, 183, 143);
	private final ColorUIResource secondary1 = new ColorUIResource(111, 111,
			111);
	private final ColorUIResource secondary2 = new ColorUIResource(159, 159,
			159);
	private final ColorUIResource secondary3 = new ColorUIResource(231, 215,
			183);

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
