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
 * The camera object is a representation of the viewport of the screen. The
 * sizes of the viewport are intended to be immutable, with the position of the
 * camera to be the only mutable characteristic. The bottom left of the screen
 * represents (0, 0) on the screen with all of the position reference points
 * being focused around that point.
 * 
 * @author lukke
 * 
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
	 * Here, the size of the camera's viewport is permantly set, and an initial
	 * point for the viewport is set. Also, a new ID is generated for this
	 * object from the IDHandler.
	 * 
	 * @param xCoord
	 *            Initial x position
	 * @param yCoord
	 *            Initial y position
	 * @param xSize
	 *            Viewport width
	 * @param ySize
	 *            Viewport height
	 */
	Camera(double xCoord, double yCoord, double xSize, double ySize) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.xSize = xSize;
		this.ySize = ySize;
		id = handler.nextKey();

	}

	/**
	 * Here we specify an Entity to which we wish to center the camera around,
	 * with the proper offsets dependent on the entity's size. This is to be
	 * called on each game tick - this is not called automatically.
	 * 
	 * @param ent
	 */
	public void center(Entity ent) {
		setxCoord(ent.getxCoord() - (xSize - ent.getxSize()) / 2);
		setyCoord(ent.getyCoord() - (ySize - ent.getySize()) / 2);
	}

	/**
	 * Renders obnoxious pink tiles that demonstrate where tiles would be if
	 * they weren't otherwise drawn. This should be called before the
	 * RenderBuffer is processed, to insure that all other objects are rendered
	 * atop these tiles.
	 */
	final public void debugTiles() {
		Draw.setPenColor(1f, 0f, 1f);
		for (double x = -this.getxOffset() * getxScale(); x < Display
				.getWidth(); x += getxScale()) {
			for (double y = -this.getyOffset() * getyScale(); y < Display
					.getHeight(); y += getyScale()) {
				Draw.drawHollowRectangle(x, y, getxScale(), getyScale());
			}
		}
	}

	/**
	 * Gets the x position of the bottom left corner.
	 * 
	 * @return xCoord of the bottom left corner.
	 */
	final public double getxCoord() {
		return xCoord;
	}

	/**
	 * Gets the x offset from the integer value of the coordinate. (xCoord % 1)
	 * 
	 * @return xOffset of the camera
	 */
	final public double getxOffset() {
		return xCoord % 1;
	}

	/**
	 * Gets the x scale of this camera. This is dependent of the width of the
	 * screen, and also the size of the viewport.
	 * 
	 * If the game contains tiles, this number is generally the size of those
	 * tiles in pixels.
	 * 
	 * @return The scale of the viewport for coordinates vs pixels
	 */
	final public double getxScale() {
		return Display.getWidth() / xSize;
	}

	/**
	 * Gets the width of the viewport.
	 * 
	 * @return The width of the viewport
	 */
	final public double getxSize() {
		return xSize;
	}

	/**
	 * Gets the integer representation of the x value on which the camera
	 * exists. Rather than the float of xCoord being casted to an integer,
	 * instead, the method of Math.floor is used due to rounding issues when
	 * dealing with negative numbers.
	 * 
	 * @return The integer representation of the xCoord
	 */
	final public int getxTile() {
		return (int) Math.floor(xCoord);
	}

	/**
	 * Gets the y position of the bottom left corner.
	 * 
	 * @return yCoord of the bottom left corner.
	 */
	final public double getyCoord() {
		return yCoord;
	}

	/**
	 * Gets the y position of the bottom right corner.
	 * 
	 * @return yCoord of the bottom left corner.
	 */
	final public double getyOffset() {
		return yCoord % 1;
	}

	/**
	 * Gets the y scale of this camera. This is dependent of the width of the
	 * screen, and also the size of the viewport.
	 * 
	 * If the game contains tiles, this number is generally the size of those
	 * tiles in pixels.
	 * 
	 * @return The scale of the viewport for coordinates vs pixels
	 */
	final public double getyScale() {
		return Display.getHeight() / ySize;
	}

	/**
	 * Gets the height of the viewport.
	 * 
	 * @return The height of the viewport
	 */
	final public double getySize() {
		return ySize;
	}

	/**
	 * Gets the integer representation of the y value on which the camera
	 * exists. Rather than the float of yCoord being casted to an integer,
	 * instead, the method of Math.floor is used due to rounding issues when
	 * dealing with negative numbers.
	 * 
	 * @return The integer representation of the yCoord
	 */
	final public int getyTile() {
		return (int) Math.floor(yCoord);
	}

	/**
	 * Changes the x position of the camera.
	 * 
	 * @param newxCoord
	 *            New x position
	 */
	final private void setxCoord(double newxCoord) {
		fromX = xCoord;
		xCoord = newxCoord;
	}

	/**
	 * Changes the y position of the camera.
	 * 
	 * @param newyCoord
	 *            New y position
	 */
	final private void setyCoord(double newyCoord) {
		fromY = yCoord;
		yCoord = newyCoord;
	}

	/**
	 * Gets the ID for this camera.
	 * 
	 * @return The ID
	 */
	final public int getCameraID() {
		return id;
	}

	/**
	 * Static method for class Camera that gets the camera associated with a
	 * specified ID.
	 * 
	 * @param id
	 *            The ID of the desired camera
	 * @return The camera associated with the specified camera, returns null if
	 *         there is no camera associated with that ID.
	 */
	final public static Camera getCamera(int id) {
		return handler.getObject(id);
	}

	/**
	 * If the given object is an instance of a Camera, then it this method will
	 * use IDs to determine whether or not the two objects are equal, otherwise,
	 * use the default method.
	 * 
	 * @param cam
	 *            The compared object
	 * @return True or false dependent on whether or not the objects are equal.
	 */
	public boolean equals(Object cam) {
		if (cam instanceof Camera) {
			return ((Camera) cam).getCameraID() == id;
		} else {
			return ((Object) this).equals(cam);
		}
	}

}
