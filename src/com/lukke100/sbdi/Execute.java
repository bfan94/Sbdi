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
 * Used as functors (Function Pointer Objects)
 */
public abstract class Execute {
	private static IDHandler<Execute> handler = new IDHandler<Execute>();
	final private int id;
	
	public Execute(){
		id = handler.nextKey();
	}
	
	public final int getExecuteID(){
		return id;
	}
	
	public final static Execute getExecute(int id){
		return handler.getObject(id);
	}
	
	public final boolean equals(Object exec){
		if(exec instanceof Execute){
			return ((Execute) exec).getExecuteID() == id;
		}else {
			return this.equals(exec);
		}
	}

	public abstract void exec();
}
