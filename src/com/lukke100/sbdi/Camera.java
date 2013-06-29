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

import org.lwjgl.opengl.Display;

/**
 * Represents the camera position and the viewport.
 */
final public class Camera {
	final private static IDHandler<Camera> handler = new IDHandler<Camera>();
	final private int id;
	private double xCoord;
	private double xSize;
	private double yCoord;
	private double ySize;
	@SuppressWarnings("unused")
	private double fromX;
	@SuppressWarnings("unused")
	private double fromY;

	/**
	 * Camera sizes cannot be adjusted once the camera is created.
	 */
	Camera(double xCoord, double yCoord, double xSize, double ySize) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.xSize = xSize;
		this.ySize = ySize;
		id = handler.nextKey();
		
	}

	public void center(Entity ent) {
		setxCoord(ent.getxCoord() - (xSize - ent.getxSize()) / 2);
		setyCoord(ent.getyCoord() - (ySize - ent.getySize()) / 2);
	}

	/**
	 * Renders tile boundaries.
	 */
	final public void debugTiles() {
		Draw.setPenColor(1f, 0f, 1f);
		for (double x = -this.getxOffset() * getxScale(); x < Display.getWidth(); x += getxScale()) {
			for (double y = -this.getyOffset() * getyScale(); y < Display.getHeight(); y += getyScale()) {
				Draw.drawHollowRectangle(x, y, getxScale(), getyScale());
			}
		}
	}

	final public double getxCoord() {
		return xCoord;
	}

	final public double getxOffset() {
		return xCoord % 1;
	}

	final public double getxScale() {
		return Display.getWidth() / xSize;
	}

	final public double getxSize() {
		return xSize;
	}

	/**
	 * Must be Math.floor rather than (int) cast due to issues with negatives
	 */
	final public int getxTile() {
		return (int) Math.floor(xCoord);
	}

	final public double getyCoord() {
		return yCoord;
	}

	final public double getyOffset() {
		return yCoord % 1;
	}

	final public double getyScale() {
		return Display.getHeight() / ySize;
	}

	final public double getySize() {
		return ySize;
	}

	/**
	 * Must be Math.floor rather than (int) cast due to issues with negatives
	 */
	final public int getyTile() {
		return (int) Math.floor(yCoord);
	}

	final private void setxCoord(double newxCoord) {
		fromX = xCoord;
		xCoord = newxCoord;
	}

	final private void setyCoord(double newyCoord) {
		fromY = yCoord;
		yCoord = newyCoord;
	}
	
	final public int getCameraID(){
		return id;
	}
	
	final public static Camera getCamera(int id){
		return handler.getObject(id);
	}
	
	public boolean equals(Object cam){
		if(cam instanceof Camera){
			return ((Camera) cam).getCameraID() == id;
		}else {
			return this.equals(cam);
		}
	}

}
