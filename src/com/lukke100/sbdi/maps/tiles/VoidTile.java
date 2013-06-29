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

package com.lukke100.sbdi.maps.tiles;

import com.lukke100.sbdi.Tile;

/**
 * To be used when otherwise would return null.
 */
public class VoidTile extends Tile {
	public VoidTile(int xTile, int yTile) {
		super(xTile, yTile);
	}

	@Override
	public String declare() {
		return null;
	}

	@Override
	public String describe() {
		return null;
	}

	@Override
	public boolean isCollision() {
		return true;
	}

}
