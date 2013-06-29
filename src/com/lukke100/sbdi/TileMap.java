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

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.collect.TreeBasedTable;
import com.lukke100.sbdi.maps.tiles.VoidTile;

/**
 * Represents a two dimensional grid of Tiles.
 * @author lukke
 *
 */
public abstract class TileMap {
	final private SortedMap<Integer, Entity> allEntities;
	final private SortedMap<Integer, Execute> markedAct;
	final private SortedMap<Integer, MapEntity> markedMove;
	private transient Tile tempTile;
	final private Tile tileMap[][];
	
	final private TreeBasedTable<Integer, Integer, VoidTile> voids;
	final private int xSize;
	final private int ySize;

	public TileMap(int xSize, int ySize) {
		tileMap = new Tile[xSize][ySize];
		this.xSize = xSize;
		this.ySize = ySize;
		markedAct = new TreeMap<Integer, Execute>();
		markedMove = new TreeMap<Integer, MapEntity>();
		allEntities = new TreeMap<Integer, Entity>();
		voids = TreeBasedTable.create();
		populate();
	}
	
	final private void transferEntity(MapEntity entity){
		removeEntity(entity);
		addEntity(entity);
	}
	
	/**
	 * Gets the VoidTile that exists at that invalid location, and
	 * creates a new one if non-existent.
	 * @param xTile
	 * @param yTile
	 * @return
	 * Returns the VoidTile for that location.
	 */
	final private VoidTile getVoidTile(int xTile, int yTile){
		if(voids.contains(xTile, yTile)){
			return voids.get(xTile, yTile);
		} else {
			voids.put(xTile, yTile, new VoidTile(xTile, yTile));
			return voids.get(xTile, yTile);
		}
	}
	
	/**
	 * Will iterate the Execute objects marked for action and will optionally
	 * clear the marked for act list.
	 * @param willClear
	 * @return
	 * Returns the marked for act list (before optional clearing.)
	 */
	public Collection<Execute> actAll(boolean willClear){
		for(Entry<Integer, Execute> entry: markedAct.entrySet()){
			entry.getValue().exec();
		}
		if(willClear){
			Collection<Execute> tempColl = Collections.unmodifiableCollection(markedAct.values());
			markedAct.clear();
			return tempColl;
		} else {
			return Collections.unmodifiableCollection(markedAct.values());
		}
	}
	
	/**
	 * Adds an entity to the map. Uses the entity's current x and yTile to determine
	 * the correct tiles to add to..
	 * @param entity
	 * @return
	 * Returns a 2-dimensional array of the MapEntities that previously occupied that position.
	 * Returns null if there was no MapEntity previously.
	 */
	public MapEntity[][] addEntity(MapEntity entity){
		MapEntity[][] occupied = new MapEntity[entity.getxSize()][entity.getySize()];
		for(int x = 0; x < entity.getxSize(); x++){
			for(int y = 0; y < entity.getySize(); y++){
				occupied[x][y] = tileMap[entity.getxTile() + x][entity.getyTile() + y].addEntity(entity);		
			}
		}
		return occupied;
	}
	
	/**
	 * Get all of the MapEntities within this map.
	 * @return
	 * Returns an unmodifiableCollection of all entities.
	 */
	public Collection<Entity> getEntities() {
		return Collections.unmodifiableCollection(allEntities.values());
	}
	
	/**
	 * Get all of the MapEntities within this map, as a map.
	 * @return
	 * Returns an unmodifiableSortedMap of all entities.
	 */
	public SortedMap<Integer, Entity> getEntityMap() {
		return Collections.unmodifiableSortedMap(allEntities);
	}
	
	/**
	 * Gets the tile at the provided location.
	 * @param coordPair
	 * @return
	 * Returns the tile that exists at that location.
	 */
	final public Tile gettile(CoordPair coordPair){
		return getTile(coordPair.getxTile(), coordPair.getyTile());
	}
	
	/**
	 * Gets the tile at the provided location.
	 * @param xTile
	 * @param yTile
	 * @return
	 * Returns the tile that exists at that location.
	 */
	final public Tile getTile(int xTile, int yTile){
		if(validCoords(xTile, yTile)){
			return tileMap[xTile][yTile];			
		} else {
			return getVoidTile(xTile, yTile);
		}
	}
	
	final public int getxSize(){
		return xSize;
	}
	
	final public int getySize(){
		return ySize;
	}
	
	/**
	 * Mark an Execute object for execution on next act iteration.
	 * @param actor
	 * @return
	 * Returns the previous Execute object with that given ID, returns
	 * null if non-existent.
	 */
	public Execute markActor(Execute actor){
		return markedAct.put(actor.getExecuteID(), actor);
	}
	
	/**
	 * Mark a MapEntity object for movement on next movement iteration.
	 * @param entity
	 * @return
	 * Returns the previous MapEntity object with that given ID, returns
	 * null if non-existent.
	 */
	public MapEntity markMoving(MapEntity entity){
		return markedMove.put(entity.getEntityID(), entity);
	}
	
	
	/**
	 * Will iterate the MapEntity objects marked for movement and will optionally
	 * clear the marked for movement list.
	 * @param willClear
	 * @return
	 * Returns the marked for movement list (before optional clearing.)
	 */
	public Collection<MapEntity> moveAll(boolean willClear){
		for(Entry<Integer, MapEntity> entry: markedMove.entrySet()){
			if(entry.getValue().willChangeTile()){
				transferEntity(entry.getValue());
				System.out.println("moved");
			}
			entry.getValue().move();
		}
		if(willClear){
			Collection<MapEntity> tempColl = Collections.unmodifiableCollection(markedMove.values());
			markedMove.clear();
			return tempColl;
		} else {
			return Collections.unmodifiableCollection(markedMove.values());
		}
	}
	
	/**
	 * Called on initialization of a map. Used to generate and "populate" the 
	 * entities, tiles, and other things.
	 */
	protected abstract void populate();
	
	/**
	 * Removes an entity from the map. Uses the entity's current x and yTile to determine
	 * the correct tile.
	 * NOTE: ID must be associated with a MapEntity.
	 * @param entity
	 * @return
	 * Returns the previous associated MapEntity, null if non-existent.
	 */
	public MapEntity[][] removeEntity(int id){
		if(Entity.getEntity(id) instanceof MapEntity){
			return removeEntity((MapEntity)Entity.getEntity(id));
		} else {
			return null;
		}
	}
	
	/**
	 * Removes an entity from the map. Uses the entity's current x and yTile to determine
	 * the correct tile.
	 * @param entity
	 * @return
	 * Returns the previous associated MapEntity, null if non-existent.
	 */
	public MapEntity[][] removeEntity(MapEntity entity){
		MapEntity[][] occupied = new MapEntity[entity.getxSize()][entity.getySize()];
		for(int x = 0; x < entity.getxSize(); x++){
			for(int y = 0; y < entity.getySize(); y++){
				occupied[x][y] = tileMap[entity.getxTile() + x][entity.getyTile() + y].removeEntity(entity);		
			}
		}
		return occupied;
	}

	/**
	 * Creates a new tile at the specified x and y coordinates of the specified
	 * type that extends the abstract class Tile.
	 * @param coordPair
	 * @param tileType
	 * @return
	 * Returns the new tile.
	 */
	final public Tile setTile(CoordPair coordPair, Class<? extends Tile> tileType){
		return setTile(coordPair.getxTile(), coordPair.getyTile(), tileType);
	}
	
	/**
	 * Creates a new tile at the specified x and y coordinates of the specified
	 * type that extends the abstract class Tile.
	 * @param xTile
	 * @param yTile
	 * @param tileType
	 * @return
	 * Returns the new tile.
	 */
	final public Tile setTile(int xTile, int yTile, Class<? extends Tile> tileType){
		try {
			if(tileMap[xTile][yTile] != null){
				tempTile = tileType.getConstructor(int.class, int.class).newInstance(xTile, yTile);
				for(MapEntity entity: tileMap[xTile][yTile].getEntities()){
					tempTile.addEntity(entity);
				}
				tileMap[xTile][yTile] = tempTile;
			} else {
				tileMap[xTile][yTile] = tileType.getConstructor(int.class, int.class).newInstance(xTile, yTile);
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tileMap[xTile][yTile];
	}

	/**
	 * Check if coordinates are valid for this map, otherwise, it exists 
	 * within the map bounds.
	 * @param xTile
	 * @param yTile
	 * @return
	 * Returns true if valid, otherwise false.
	 */
	final private boolean validCoords(int xTile, int yTile) {
		return (xTile < xSize && yTile < ySize && xTile >= 0 && yTile >= 0);
	}

}
