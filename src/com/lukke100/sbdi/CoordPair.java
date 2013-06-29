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

final public class CoordPair {
	final private float xCoord;
	final private float yCoord;

	public CoordPair(float xCoord, float yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	final public float getxCoord() {
		return xCoord;
	}

	final public float getxOffset() {
		return xCoord % 1;
	}

	/**
	 * Must be Math.floor rather than (int) cast due to issues with negatives
	 */
	final public int getxTile() {
		return (int) Math.floor(xCoord);
	}

	final public float getyCoord() {
		return yCoord;
	}

	final public float getyOffset() {
		return yCoord % 1;
	}

	/**
	 * Must be Math.floor rather than (int) cast due to issues with negatives
	 */
	final public int getyTile() {
		return (int) Math.floor(yCoord);
	}

}
