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

import java.util.TreeMap;

final class IDHandler<E> {
	final private TreeMap<Integer, E> list;
	private int currValue;
	
	IDHandler(){
		list = new TreeMap<Integer, E>();
	}
	
	final E getObject(Integer key){
		return list.get(key);
	}
	
	final int nextKey(){
		currValue += 1;
		return currValue;
	}
}
