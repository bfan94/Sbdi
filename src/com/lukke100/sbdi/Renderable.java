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

public abstract class Renderable{
	final private int id;
	final private static IDHandler<Renderable> handler = new IDHandler<Renderable>();
	
	public Renderable(){
		id = handler.nextKey();
	}
	
	/**
	 * Removes itself from RenderBuffer
	 */
	final public void delRender(RenderLayer rLayer) {
		RenderBuffer.delRender(rLayer, this);
	}

	/**
	 * Queue is persistent between frames.
	 */
	final public void queueRender(RenderLayer rLayer) {
		RenderBuffer.addRender(rLayer, this);
	}

	abstract void render();
	
	final public int getRenderID(){
		return id;
	}
	
	final public static Renderable getRenderable(int id){
		return handler.getObject(id);
	}
	
	public boolean equals(Object obj){
		if(obj instanceof Renderable){
			return id == ((Renderable) obj).getRenderID();
		} else {
			return this.equals(obj);
		}
	}

}
