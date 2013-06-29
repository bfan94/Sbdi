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

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Handles render order by iterating through layers defined in RenderLayer.
 */
final public class RenderBuffer {
	@SuppressWarnings("unchecked")
	static private Map<Integer, Renderable>[] trees = new TreeMap[24];

	final static public void addRender(RenderLayer layer, Renderable renderable) {
		trees[layer.getPriority()].put(renderable.getRenderID(), renderable);
	}
	
	final static public void addRender(RenderLayer layer, int id) {
		trees[layer.getPriority()].put(id, Renderable.getRenderable(id));
	}

	final static public void delRender(RenderLayer layer, Renderable renderable) {
		trees[layer.getPriority()].remove(renderable.getRenderID());
	}
	
	final static public void delRender(RenderLayer layer, int id) {
		trees[layer.getPriority()].remove(id);
	}

	/**
	 * MUST BE CALLED OR THINGS BREAK
	 */
	final static public void init() {
		for(int j = 0; j < 24; j++){
			trees[j] = new TreeMap<Integer, Renderable>();
		}
	}

	final static void process() {
		for(int j = 0; j < 24; j++){
			for(Entry<Integer, Renderable> entry: trees[j].entrySet()){
				entry.getValue().render();
			}
		}
	}
}
