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

import org.lwjgl.opengl.GL11;

import com.lukke100.sbdi.Tile;

final public class Draw {
	static private double blue = 0d;
	static private double green = 0d;
	static private double red = 0d;
	
	static public void drawDebugEnt(Entity ent, Camera cam, double red, double green, double blue){
		setPenColor(red, green, blue);
		drawRectangle(
				(ent.getxCoord() - cam.getxCoord()) * cam.getxScale(),
				(ent.getyCoord() - cam.getyCoord()) * cam.getyScale(), 
				ent.getxSize() * cam.getxScale(), 
				ent.getySize() * cam.getyScale());
	}
	
	static public void drawDebugTile(Tile tile, Camera cam, double red, double green, double blue){
		setPenColor(red, green, blue);
		drawRectangle(
				(tile.getxTile() - cam.getxCoord()) * cam.getxScale(),
				(tile.getyTile() - cam.getyCoord()) * cam.getyScale(), 
				cam.getxScale(), 
				cam.getyScale());
	}

	static public void drawCircle(double xCoord, double yCoord, double radius) {
		GL11.glColor3d(red, green, blue);
		drawCircle(xCoord, yCoord, radius, 20);
	}

	static public void drawCircle(double xCoord, double yCoord, double radius,
			int segments) {
		GL11.glColor3d(red, green, blue);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		// GL11.glVertex2d(xCoord, yCoord);
		double deltaAngle = (2 * Math.PI) / segments;
		for (double angle = 0; angle < Math.PI * 2; angle += deltaAngle) {
			GL11.glVertex2d(xCoord + Math.cos(angle) * radius,
					yCoord + Math.sin(angle) * radius);
		}
		GL11.glEnd();
	}

	static public void drawHollowCircle(double xCoord, double yCoord,
			double radius) {
		GL11.glColor3d(red, green, blue);
		drawHollowCircle(xCoord, yCoord, radius, 20);
	}

	static public void drawHollowCircle(double xCoord, double yCoord,
			double radius, int segments) {
		GL11.glColor3d(red, green, blue);
		double theta = (2 * Math.PI) / segments;
		double c = Math.cos(theta);
		double s = Math.sin(theta);
		double t;

		double x = radius; // start at angle = 0
		double y = 0;

		GL11.glBegin(GL11.GL_LINE_LOOP);
		for (int ii = 0; ii < segments; ii++) {
			GL11.glVertex2d(x + xCoord, y + yCoord); // output vertex

			// apply the rotation matrix
			t = x;
			x = c * x - s * y;
			y = s * t + c * y;
		}
		GL11.glEnd();
	}

	static public void drawHollowRectangle(double xCoord, double yCoord,
			double width, double height) {
		GL11.glColor3d(red, green, blue);
		width--;
		height--;
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex2d(xCoord, yCoord);
		GL11.glVertex2d(xCoord + width, yCoord);
		GL11.glVertex2d(xCoord + width, yCoord + height);
		GL11.glVertex2d(xCoord, yCoord + height);
		GL11.glEnd();
	}

	static public void drawHollowSquare(double xCoord, double yCoord,
			double size) {
		GL11.glColor3d(red, green, blue);
		size--;
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex2d(xCoord, yCoord);
		GL11.glVertex2d(xCoord + size, yCoord);
		GL11.glVertex2d(xCoord + size, yCoord + size);
		GL11.glVertex2d(xCoord, yCoord + size);
		GL11.glEnd();
	}

	static public void drawRectangle(double xCoord, double yCoord,
			double width, double height) {
		GL11.glColor3d(red, green, blue);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(xCoord, yCoord);
		GL11.glVertex2d(xCoord + width, yCoord);
		GL11.glVertex2d(xCoord + width, yCoord + height);
		GL11.glVertex2d(xCoord, yCoord + height);
		GL11.glEnd();
	}

	static public void drawSquare(double xCoord, double yCoord, double size) {
		GL11.glColor3d(red, green, blue);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(xCoord, yCoord);
		GL11.glVertex2d(xCoord + size, yCoord);
		GL11.glVertex2d(xCoord + size, yCoord + size);
		GL11.glVertex2d(xCoord, yCoord + size);
		GL11.glEnd();
	}

	/**
	 * Needed because float based colors must exist between 0 and 1f.
	 */
	static private boolean isValidColor(double test) {
		return (test >= 0 && test <= 1);
	}

	/**
	 * Makes sure that the float is a correct value.
	 */
	static public boolean setPenColor(double red, double green, double blue) {
		if (isValidColor(red) && isValidColor(blue) && isValidColor(green)) {
			Draw.red = red;
			Draw.blue = blue;
			Draw.green = green;
			return true;
		}
		return false;
	}

}
