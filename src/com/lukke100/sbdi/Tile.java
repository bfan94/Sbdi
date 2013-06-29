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

import java.util.Collection;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * Represents a Tile object. Stores all entities on the tile.
 * @author lukke100
 *
 */
public abstract class Tile extends Renderable implements Express {
	private SortedMap<Integer, MapEntity> entities;
	final private int xTile;
	final private int yTile;

	public Tile(int xTile, int yTile) {
		super();
		entities = new TreeMap<Integer, MapEntity>();
		this.xTile = xTile;
		this.yTile = yTile;
	}
	
	/**
	 * Tests if there is a tangible entity on this tile.
	 * @param exception
	 * @return
	 * Whether or not there a tangible entity on the tile.
	 */
	final private boolean hasEntityCollision(MapEntity exception){
		for(Entry<Integer, MapEntity> entry: entities.entrySet()){
			if(entry.getKey() != exception.getEntityID() && entry.getValue().isCollision()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Puts an entity on this tile.
	 * @param entity
	 * @return
	 * The previous entity that existed on this tile. Returns null if non-existent.
	 */
	final MapEntity addEntity(MapEntity entity){
		return entities.put(entity.getEntityID(), entity);
	}
	
	/**
	 * Removes the given MapEntity
	 * @param entity
	 * @return
	 * Returns the removed MapEntity, returns null if non-existent.
	 */
	final MapEntity removeEntity(MapEntity entity){
		return entities.remove(entity.getEntityID());
	}
	
	/**
	 * Removes the MapEntity associated with the given ID.
	 * @param id
	 * @return
	 * Returns the MapEntity associated with the given ID, returns null if non-existent.
	 */
	final MapEntity removeEntity(int id){
		return entities.remove(id);
	}

	/**
	 * Get the Entity list still in ID -> Entity map format.
	 * @return
	 * Returns an unmodifiableSortedMap of the map.
	 */
	final public SortedMap<Integer, MapEntity> getEntityMap() {
		return Collections.unmodifiableSortedMap(entities);
	}
	/**
	 * Get the Entity list in regular Collection format.
	 * @return
	 * Returns an unmodifiableCollection of the value list.
	 */
	final public Collection<MapEntity> getEntities() {
		return Collections.unmodifiableCollection(entities.values());
	}

	final public int getxTile() {
		return xTile;
	}

	final public int getyTile() {
		return yTile;
	}

	/**
	 * Tests if the tile itself is tangible or if there are any entities that
	 * are tangible.
	 * @param exception
	 * @return
	 * Returns true if tangible, otherwise false.
	 */
	final public boolean isAnyCollision(MapEntity exception) {
		return (isCollision() || hasEntityCollision(exception));
	}

	/**
	 * Tests if the tile itself is tangible.
	 * Must be implemented per tile class.
	 * @return
	 * Returns true if will collide, otherwise false.
	 */
	public abstract boolean isCollision();

	@Override
	public void render() {
		Draw.drawDebugTile(this, Sbdi.getActiveCamera(), 1, 1, 1);
	}
}