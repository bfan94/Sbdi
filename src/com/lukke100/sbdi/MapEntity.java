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
 * Entity that explicitly exists within the presence of a map.
 */
public abstract class MapEntity extends Entity {
	private Direction dir;
	private float speed = 5f;

	public MapEntity(float xCoord, float yCoord) {
		super(xCoord, yCoord);
		Sbdi.getActiveMap().addEntity(this);
	}

	/**
	 * Specifies if getxTile() and getyTile() change returns on next game tick.
	 * 
	 * @return True if get(x/y)Tile() change
	 */
	final public boolean changesTile() {
		return false;
	}

	/**
	 * Determines if the entity has been called to move explicitly.
	 * 
	 * @return True if the entity must move explicitly.
	 */
	final private boolean mustExplicitlyMove() {
		return false;
	}

	/**
	 * Explicit movement along the X axis.
	 */
	final private void explicitXMove() {

	}

	/**
	 * Explicitly movement along the Y axis.
	 */
	final private void explicitYMove() {

	}

	/**
	 * Determines if the entity must move implicitly, checks tile alignments.
	 * 
	 * @return
	 */
	final private boolean mustImplicitlyMove() {
		return false;
	}

	/**
	 * Implicitly moves along the X axis.
	 */
	final private void implicitXMove() {

	}

	/**
	 * Implicitly moves along the Y axis.
	 */
	final private void implicitYMove() {

	}

	/**
	 * Move function implementation that accounts for collision, size, and high
	 * speeds (more than 1 block per tick). Moves explicitly from outside method
	 * calls and also implicitly (grid snap, etc). If neither movement types
	 * must be applied, it will remove itself from the "Marked to Move" list of
	 * the map.
	 */
	public void move() {
		if (mustExplicitlyMove()) {
			explicitXMove();
			explicitYMove();
		} else if (mustImplicitlyMove()) {
			implicitXMove();
			implicitYMove();
		} else {
			unmarkMove();
		}
	}

	/**
	 * Removes itself from the "Marked to Move" list of the map.
	 */
	final private void unmarkMove() {

	}

}
