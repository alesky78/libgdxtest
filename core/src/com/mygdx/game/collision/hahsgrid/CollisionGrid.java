package com.mygdx.game.collision.hahsgrid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class CollisionGrid {

	private HashMap<String, List<BaseActor>> grid;

	private int bucketsSize;	

	public CollisionGrid(int bucketsSize){
		this.bucketsSize = bucketsSize;
		grid = new HashMap<String, List<BaseActor>>();	

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
	}
	
	public void removeFromGrid(BaseActor entity) {
		
		List<String> index = generateIndex(entity);
		List<BaseActor> bucketList;
		
		for (String key : index) {
			bucketList = grid.get(key); 
		
			if(bucketList!=null) {
				if(bucketList.remove(entity)) 
			
				if(bucketList.isEmpty()) {
					grid.remove(key);					
				}
			}

		}
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
		
		
		
		

}
