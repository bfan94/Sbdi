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

package com.lukke100.sbdi.maps;

import com.lukke100.sbdi.TileMap;
import com.lukke100.sbdi.maps.tiles.VoidWalkable;

public class DebugMap extends TileMap {

	public DebugMap(int xSize, int ySize) {
		super(xSize, ySize);
	}

	/**
	 * This is kinda how the populate methods should be. Iterate through all of
	 * the tiles and occupy them.
	 */
	@Override
	public void populate() {
		for (int x = 0; x < getxSize(); x++) {
			for (int y = 0; y < getySize(); y++) {
				try {
					setTile(x,y,VoidWalkable.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
