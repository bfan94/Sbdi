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
	
	final private boolean canChangeX() {
		return (getyVelocity() == 0 && isYAlign());
	}

	final private boolean canChangeY() {
		return (getxVelocity() == 0 && isXAlign());
	}
	
	final public Direction getDirection() {
		return dir;
	}

	final public float getSpeed() {
		return speed;
	}

	final public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void idleX() {
		//TODO: Have this method's effects apply on next move() call.
		if(isXAlign()){
			setxVelocity(0);
		} else if (xTileChanges()) {
			snapX();
			setxVelocity(0);
		}
	}
	
	public void idleY() {
		//TODO: Same as idleY()
		if(isYAlign()){
			setyVelocity(0);
		} else if (yTileChanges()){
			snapY();
			setyVelocity(0);
		}
	}
	
	public boolean goLeft(){
		if(canChangeX()){
			dir = Direction.EAST;
			setxVelocity(-speed);
			return true;
		}
		return false;
	}
	
	public boolean goRight(){
		if(canChangeX()){
			dir = Direction.WEST;
			setxVelocity(speed);
			return true;
		}
		return false;
	}
	
	public boolean goUp(){
		if(canChangeY()){
			dir = Direction.NORTH;
			setyVelocity(speed);
			return true;
		}
		return false;
	}
	
	public boolean goDown(){
		if(canChangeY()){
			dir = Direction.SOUTH;
			setyVelocity(-speed);
			return true;
		}
		return false;
	}

	final private boolean isCollX() {
		for (int y = 0; y < getySize(); y++) {
			if (Sbdi.getActiveMap().getTile((int) (Math.floor(predictX() + (getxVelocity() > 0? getxSize(): 0))), getyTile() + y).isAnyCollision(this)) {
				return true;
			}
		}
		return false;
	}

	final private boolean isCollY() {
		for (int x = 0; x < getxSize(); x++) {
			if (Sbdi.getActiveMap().getTile(getxTile() + x, (int) (Math.floor(predictY() + (getyVelocity() > 0? getxSize(): 0)))).isAnyCollision(this)) {
				return true;
			}
		}
		return false;
	}

	final public boolean isMoving() {
		return !(getxVelocity() == 0 && getyVelocity() == 0);
	}
	
	final public boolean willChangeTile() {
		return xTileChanges() || yTileChanges();
	}
	
	final public boolean xTileChanges(){
		return Math.floor(predictX()) != getxTile();
	}
	
	final public boolean yTileChanges(){
		return Math.floor(predictY()) != getyTile();
	}

	@Override
	public void move() {		
		if (isCollX()) {
			snapX();
		} else {
			shiftX();
		}
		
		if (isCollY()) {
			snapY();
		} else {
			shiftY();
		}
	}

	final private void snapX() {
		if (getxVelocity() > 0 && !isXAlign()) {
			setxCoord(getxTile() + 1);
		} else if (getxVelocity() < 0) {
			setxCoord(getxTile());
		}
	}

	final private void snapY() {
		if (getyVelocity() > 0 && !isYAlign()) {
			setyCoord(getyTile() + 1);
		} else if (getyVelocity() < 0) {
			setyCoord(getyTile());
		}
	}
}
