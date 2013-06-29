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

import org.lwjgl.Sys;

final class FPSCounter {
	static private double fps = 1d;
	static private double lastFrame = getTime();
	static private double nowFrame;

	/**
	 * Returns the difference between frames. Used to make distance calculation
	 * independent of framerate.
	 */
	final static double getDelta() {
		return 1d / fps;		
	}

	final static double getfps() {
		return fps;
	}

	/**
	 * The getTime function is included in the lwjql and will return the time
	 * (nanoseconds/milliseconds/etc in whatever lwjgl seems to be most optimal.
	 * This is then mutiplied to fit the division by the resolution which is
	 * relative to seconds, not milliseconds, which is what we want.
	 */
	final static private double getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	final static void update() {
		lastFrame = nowFrame;
		nowFrame = getTime();
		fps = (1000d / (nowFrame - lastFrame));
	}

}
