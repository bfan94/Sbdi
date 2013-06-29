/**
 * Copyright (C) 2013 Lucas Hart (Lukke100)
 *
 * This file is part of Sbdi
 *
 *  Sbdi is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  Sbdi is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with Sbdi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.lukke100.sbdi;

/**
 * Hardcoded the available layers.
 */
public enum RenderLayer {
	BACK0(0), BACK1(1), BACK2(2), DECOR0(6), DECOR0_DECAL(7), DECOR1(11), DECOR1_DECAL(
			12), DECOR2(16), DECOR2_DECAL(17), EFFECT0(8), EFFECT1(13), EFFECT2(
			18), ENT_EFFECT0(10), ENT_EFFECT1(15), ENT_EFFECT2(20), ENTITY0(9), ENTITY1(
			14), ENTITY2(19), FORE0(21), FORE1(22), FORE2(23), TILE(3), TILE_DECAL(
			4), TILE_EFFECT(5);

	final private int priority;

	private RenderLayer(int priority) {
		this.priority = priority;
	}

	final public int getPriority() {
		return priority;
	}
}
