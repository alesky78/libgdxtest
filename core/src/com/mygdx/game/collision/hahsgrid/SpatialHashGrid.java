package com.mygdx.game.collision.hahsgrid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class SpatialHashGrid {

	//implementation of the grid by hash map
	private HashMap<String, List<BaseActor>> grid;
	
	//contain the hash of the bucket with more that 1 object inside
	private HashSet<String> bucketsPotentialCollision;
	
	
	private List<List<BaseActor>> potentialCollisions;

	//size of a single bucket, it can be change at runtime, but not during the indexing phase or the object will not correspond more  to the grid
	private int bucketsSize;	

	public SpatialHashGrid(int bucketsSize){
		this.bucketsSize = bucketsSize;
		grid = new HashMap<String, List<BaseActor>>();
		bucketsPotentialCollision = new HashSet<String>();		
		potentialCollisions  = new ArrayList<List<BaseActor>>();

	}

	/**
	 * generate the index for this entity 
	 * 
	 * @param entity
	 * @return
	 */
	private List<String> generateIndex(BaseActor entity){
		List<String> index = new ArrayList<>();

		Rectangle rect = entity.getBoundingPoligon().getBoundingRectangle();
		
		int minX = MathUtils.floor(rect.getX()/bucketsSize);
		int maxX = MathUtils.floor((rect.getX()+rect.getWidth()) /bucketsSize);		
		int minY = MathUtils.floor(rect.getY()/bucketsSize);
		int maxY = MathUtils.floor((rect.getY()+rect.getHeight()) /bucketsSize);		

		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				index.add(x +"-"+y);
			}
		}

		return index;
	}


	public void resetGrid() {
		grid.clear();	
		bucketsPotentialCollision.clear();
	}

	
	public void addToGrid(BaseActor entity) {
		
		List<String> index = generateIndex(entity);
		List<BaseActor> bucketList;
		
		for (String key : index) {
			
			bucketList = grid.get(key);
			if(bucketList==null) {
				bucketList = new LinkedList<BaseActor>();
				grid.put(key, bucketList);
			}
			
			bucketList.add(entity);
			
			//correct the potential collision
			if(bucketList.size()>1){
				bucketsPotentialCollision.add(key);					
			}
			
		}
	}	
	
	
	public void removeFromGrid(BaseActor entity) {
		
		List<String> index = generateIndex(entity);
		List<BaseActor> bucketList;
		
		for (String key : index) {
			bucketList = grid.get(key); 
		
			//if there is the bucket then analyse it
			if(bucketList!=null) {
				bucketList.remove(entity); 
			
				if(bucketList.isEmpty()) {
					grid.remove(key);					
				}
				
				//correct the potential collision
				if(bucketList.size()>1){
					bucketsPotentialCollision.remove(key);					
				}
			}
			
		}
	}

	
	public List<BaseActor>  getNearby(BaseActor entity) {
		
		List<BaseActor> nearList = new LinkedList<>();
		List<BaseActor> bucketList;		
		List<String> index = generateIndex(entity);
				
		for (String key : index) {
			bucketList = grid.get(key);
			if(bucketList!=null) {
				nearList.addAll(bucketList);
				//remove itself
				nearList.remove(entity);
			}
		
		}
		
		return nearList;
	}
		
	/**
	 * return the list of all the possible collisions
	 * 
	 * @return
	 */
	public  List<List<BaseActor>>  getPotentialCollision() {
		
		potentialCollisions.clear();	 
		 
		//put in the list only the bucket with more that one object
		 for (String key : bucketsPotentialCollision) {
			 potentialCollisions.add( grid.get(key));
		}
		
		return potentialCollisions;
	}
	
		
		

}
