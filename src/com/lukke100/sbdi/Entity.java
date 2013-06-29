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

public abstract class Entity extends Renderable implements Express {
	static private IDHandler<Entity> handler = new IDHandler<Entity>();
	private float xCoord;
	private int xSize = 1;
	private float xVelocity;
	private float yCoord;
	private int ySize = 1;
	private float yVelocity;
	final private int id;

	public Entity(float xCoord, float yCoord) {
		super();
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		id = handler.nextKey();
	}

	final public float getxCoord() {
		return xCoord;
	}

	final public double getxOffset() {
		return xCoord % 1;
	}

	final public int getxSize() {
		return xSize;
	}

	/**
	 * Must be Math.floor rather than (int) cast due to issues with negatives
	 */
	final public int getxTile() {
		return (int) Math.floor(xCoord);
	}

	final public float getxVelocity() {
		return xVelocity;
	}

	final public float getyCoord() {
		return yCoord;
	}

	final public double getyOffset() {
		return yCoord % 1;
	}

	final public int getySize() {
		return ySize;
	}

	/**
	 * Must be Math.floor rather than (int) cast due to issues with negatives
	 */
	final public int getyTile() {
		return (int) Math.floor(yCoord);
	}

	final public float getyVelocity() {
		return yVelocity;
	}

	public abstract boolean isCollision();

	final public boolean isXAlign() {
		return (getxOffset() == 0d);
	}

	final public boolean isYAlign() {
		return (getyOffset() == 0d);
	}

	/**
	 * Can be over-ridden for varying movement methods
	 */
	public void move() {
		shiftX();
		shiftY();
	}

	/**
	 * Predicts future xCoord
	 */
	final protected float predictX() {
		return (float) (xCoord + xVelocity * FPSCounter.getDelta());
	}

	/**
	 * Predicts future yCoord
	 */
	final protected float predictY() {
		return (float) (yCoord + yVelocity * FPSCounter.getDelta());
	}

	@Override
	public void render() {
		Draw.drawDebugEnt(this, Sbdi.getActiveCamera(), 0, 1, 1);
	}

	final void setxCoord(float xCoord) {
		this.xCoord = xCoord;
	}

	final public void setxSize(int xSize) throws Exception{
		if (xSize <= 0){
			throw new Exception("Size must be greater than 0");
		}
		this.xSize = xSize;
	}

	final void setxVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}

	final void setyCoord(float yCoord) {
		this.yCoord = yCoord;
	}

	final public void setySize(int ySize) throws Exception {
		if (ySize <= 0){
			throw new Exception("Size must be greater than 0");
		}
		this.ySize = ySize;
	}

	final void setyVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}

	/**
	 * Applies xVelocity
	 */
	final protected void shiftX() {
		xCoord += xVelocity * FPSCounter.getDelta();
	}

	/**
	 * Applies yVelocity
	 */
	final protected void shiftY() {
		yCoord += yVelocity * FPSCounter.getDelta();
	}
	
	final public int getEntityID(){
		return id;
	}
	
	final public static Entity getEntity(int id){
		return handler.getObject(id);
	}
	
	public boolean equals(Object ent){
		if(ent instanceof Entity){
			return ((Entity) ent).getEntityID() == id;
		}else {
			return this.equals(ent);
		}
	}
}
