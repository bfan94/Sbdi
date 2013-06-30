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

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.lukke100.sbdi.entities.DebugEntity;
import com.lukke100.sbdi.jython.JyInjector;
import com.lukke100.sbdi.maps.DebugMap;

final public class Sbdi {
	/**
	 * We use a static Map, Window and Camera fields to allow other objects to determine their
	 * behavior on these fields. MapEntity constructor adds itself to the Active Map, what is 
	 * rendered is partially dependent on the active Camera, and a lot of the camera methods
	 * are dependent on the camera.
	 */
	static private TileMap activeMap;
	static private Camera activeCamera;

	public static Camera getActiveCamera() {
		return activeCamera;
	}

	static public TileMap getActiveMap() {
		return activeMap;
	}
	
	static public long memUsage(char type){
		switch(type){
		case 'k':
			return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;
		case 'm':
			return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
		default:
			return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		}
	}

	public static void main(String[] args) {
		System.out.println("Beginning Main Method");
		JyInjector jyInjector = new JyInjector();
		jyInjector.main();
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		GL11.glOrtho(0, 640, 0, 480, 0, 1);
		GL11.glTranslated(.5d, .5d, 0f);
		RenderBuffer.init();
		
		setActiveMap(new DebugMap(1024, 1024));
		final DebugEntity player = new DebugEntity(128, 128);
		DebugEntity temp = new DebugEntity(130, 128);
		activeCamera = new Camera(0, 0, 16, 12);
		activeCamera.center(player);
		player.queueRender(RenderLayer.ENTITY0);
		temp.queueRender(RenderLayer.ENTITY0);
		
		Input.addKeyTotal(Keyboard.KEY_RIGHT, Keyboard.KEY_LEFT, new Execute() {
			@Override
			public void exec() {
				
			}
		}, new Execute() {
			@Override
			public void exec() {
				
			}
		}, new Execute() {
			
			@Override
			public void exec() {

			}
		});
		
		Input.addKeyTotal(Keyboard.KEY_UP, Keyboard.KEY_DOWN, new Execute() {
			
			@Override
			public void exec() {
				
			}
		}, new Execute() {
			
			@Override
			public void exec() {
				
			}
		}, new Execute() {
			
			@Override
			public void exec() {
				
			}
		});
		
		System.gc();
		while (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			Input.parseHotkeys();
			activeMap.markMoving(player);
			activeMap.markMoving(temp);
			activeMap.moveAll(true);
			activeCamera.center(player);
			Display.setTitle("COORDS: "
					+ player.getxCoord() + " (" + player.getxTile() + "), " 
					+ player.getyCoord() + " (" + player.getyTile() + "), "
					+ " - MEM: " + memUsage('m'));
			RenderBuffer.process();
			activeCamera.debugTiles();
			FPSCounter.update();
			Display.sync(240);
			Display.update();
		}
		Display.destroy();
		//jyInjector.exit();
	}

	public static void setActiveCamera(Camera globalCamera) {
		Sbdi.activeCamera = globalCamera;
	}

	static public void setActiveMap(TileMap refMap) {
		Sbdi.activeMap = refMap;
	}
}
