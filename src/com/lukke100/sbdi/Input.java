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

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

/**
 * Executes an array of hotkeys if hotkey isn't null.
 */
final public class Input {
	private static transient int keyTest;
	private static transient Execute execTest;
	private static Execute[] pressHotkeys = new Execute[256]; //There is a maximum of 256 keys in LWJGL
	private static Execute[] releaseHotkeys = new Execute[256];
	private static ArrayList<KeyTotal> keyTotals = new ArrayList<KeyTotal>();
	
	final static private class KeyTotal {
		final private Execute posExec;
		final private Execute zedExec;
		final private Execute negExec;
		private boolean pos;
		private boolean neg;
		
		private KeyTotal(Execute posExec, Execute zedExec, Execute negExec){
			this.posExec = posExec;
			this.zedExec = zedExec;
			this.negExec = negExec;
		}
		
		private void down(){
			if(pos){
				this.pos = false;
			} else {
				this.neg = true;
			}
		}
		
		private void up(){
			if(neg){
				this.neg = false;
			} else {
				this.pos = true;
			}
		}
		
		public void exec() {
			if (pos) {
				posExec.exec();
			} else if (neg) {
				negExec.exec();
			} else {
				zedExec.exec();
			}
		}
	}
	
	final public static void addKeyTotal(int posKey, int negKey, Execute posExec, Execute negExec, Execute zedExec){
		final KeyTotal tempKeyTotal = new KeyTotal(posExec, negExec, zedExec);
		addPressHotkey(posKey, new Execute() {
			@Override
			public void exec() {
				tempKeyTotal.up();
				
			}
		});
		addPressHotkey(negKey, new Execute() {
			@Override
			public void exec() {
				tempKeyTotal.down();
				
			}
		});
		addReleaseHotkey(posKey, new Execute() {
			@Override
			public void exec() {
				tempKeyTotal.down();
				
			}
		});
		addReleaseHotkey(negKey, new Execute() {
			@Override
			public void exec() {
				tempKeyTotal.up();
				
			}
		});
		keyTotals.add(tempKeyTotal);
	}
	
	final public static Execute addPressHotkey(int key, Execute method){
		execTest = pressHotkeys[key];
		pressHotkeys[key] = method;
		return execTest;
	}
	
	final public static Execute addReleaseHotkey(Integer key, Execute method){
		execTest = releaseHotkeys[key];
		releaseHotkeys[key] = method;
		return execTest;
	}
	
	final public static void clearKeys(){
		pressHotkeys = new Execute[256];
		releaseHotkeys = new Execute[256];
	}

	final public static void parseHotkeys(){
		while(Keyboard.next()){
			keyTest = Keyboard.getEventKey();
			if(Keyboard.getEventKeyState() && (pressHotkeys[keyTest] != null)){
				pressHotkeys[keyTest].exec();
			}
			else if(releaseHotkeys[keyTest] != null){
				releaseHotkeys[keyTest].exec();
			}
		}
		for(KeyTotal keyTotal: keyTotals){
			keyTotal.exec();
		}
	}
	
	final public static Execute remPressHotkey(int key){
		execTest = pressHotkeys[key];
		pressHotkeys[key] = null;
		return execTest;
		
	}
	
	final public static Execute remReleaseHotkey(int key){
		execTest = releaseHotkeys[key];
		releaseHotkeys[key] = null;
		return execTest;
	}

}
