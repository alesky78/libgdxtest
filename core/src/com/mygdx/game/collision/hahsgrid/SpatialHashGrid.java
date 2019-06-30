package com.mygdx.game.collision.hahsgrid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.mygdx.game.collision.hahsgrid.SpatialHashGrid.GridIndexable;


/**
 * implementazione di una Griglia spaziale su un hashset utile per associare entita un uno specifico quadrante di una griglia regolare
 * 
 * la giriglia ha una dimenzione fissa che deve essere passata come paramentro utilizando il costruttore della griliglia {@link SpatialHashGrid#SpatialHashGrid(int)}
 * 
 * gli ogetti possono essere introdotti nella griglia attraverso il metodo {@link SpatialHashGrid#addToGrid(BaseActor)}. 
 * Attenzione un ogetto viene indicizzato nella grigli atraverso il suo bounding box che viene generato in funzioni delle coordinate dove si trova l'oggetto, 
 * quindi se l'ogetto si muove variando le sue coordinate non esiste piu una corrispondenza esatta tra i bucket dove e stato inserito inizialmente e quelli dove potrebbe torvarsi dopo il movimento.
 * Di conseguenza utilizzare il metodo {@link SpatialHashGrid#removeFromGrid(BaseActor)} dopo che l'oggetto e stato mosso potenzialmente non rimuovera' l'ggetto da tutti i bucket in cui si trovava originariamente corrompendo la griglia
 *   
 * il corretto utilizzo della grigli prevede l'utilizzo del metodo reset che pulisce definitivamente la griglia. questa e la corretta sequenza con la quale andrebe usata questa griglia
 * normalemte la griglia dovrebbe essere integrata nelle entita' e alla fine del motodo update andrebbe aggiunto l'ogetto alla grigli. in questo modo si e sicuri che i bucket associati sono corretti fino al prossimo reset
 * 
 * <pre>
 * {@code
 * 		
 * 		//init the game one time
 * 		SpatialHashGrid grid = new SpatialHashGrid(size);
 * 		.....
 * 		.....
 * 
 * 
 * 		for game loop{
 * 						grid.reset();	
 *						for entity in game{
 *									enity.update(); 
 * 									grid.addToGrid(entity);
 * 						}
 * 
 * 						//verify collisions
 * 						grid.getPotentialCollision();
 * 		}
 * }
 * 
 * la griglia mette a disposizione due metodi per verificare le collisioni:
 * 1 - data una entita' richiede ti ricevere tutti gli ogetti che possono potenzialmente collidere con questa entita: {@link SpatialHashGrid#getNearby(BaseActor)} 
 * 2 - recevere la lista di tutti i bucket che potenzialemten possono avere delle entita' in collisione:   {@link SpatialHashGrid#getPotentialCollision()}
 * 
 * 
 * 
 * @author Alessandro
 *
 */
public class SpatialHashGrid<T extends GridIndexable> {

	//implementation of the grid by hash map
	private HashMap<String, List<T>> grid;
	
	//contain the hash of the bucket with more that 1 object inside
	private HashSet<String> bucketsPotentialCollision;
	
	//size of a single bucket, it can be change at runtime, but not during the indexing phase or the object will not correspond more  to the grid
	private int bucketsSize;	

	public SpatialHashGrid(int bucketsSize){
		this.bucketsSize = bucketsSize;
		grid = new HashMap<String, List<T>>();
		bucketsPotentialCollision = new HashSet<String>();		

	}


	public void reset() {
		grid.clear();	
		bucketsPotentialCollision.clear();
	}

	public void reset(int bucketsSize) {
		grid.clear();	
		bucketsPotentialCollision.clear();
		this.bucketsSize = bucketsSize;
	}	
	
	public void addToGrid(T entity) {
		
		List<String> index = entity.generateIndex(bucketsSize);
		List<T> bucketList;
		
		for (String key : index) {
			
			bucketList = grid.get(key);
			if(bucketList==null) {
				bucketList = new LinkedList<T>();
				grid.put(key, bucketList);
			}
			
			bucketList.add(entity);
			
			//correct the potential collision
			if(bucketList.size()>1){
				bucketsPotentialCollision.add(key);					
			}
			
		}
	}	
	
	
	public void removeFromGrid(T entity) {
		
		List<String> index = entity.generateIndex(bucketsSize);
		List<T> bucketList;
		
		for (String key : index) {
			bucketList = grid.get(key); 
		
			//if there is the bucket then analyse it
			if(bucketList!=null) {
				bucketList.remove(entity); 
			
				if(bucketList.isEmpty()) {
					grid.remove(key);							
				}
				
				//correct the potential collision
				if(bucketList.size()<2){
					bucketsPotentialCollision.remove(key);
				}
			}
			
		}
	}

	
	/**
	 * return the list of all the object that are in the same bucket of the target
	 * 
	 * @return
	 */
	public List<T>  getNearby(T target) {
		
		List<T> nearList = new LinkedList<>();
		List<T> bucketList;		
		List<String> index = target.generateIndex(bucketsSize);
				
		for (String key : index) {
			bucketList = grid.get(key);
			if(bucketList!=null) {
				nearList.addAll(bucketList);
				//remove itself
				nearList.remove(target);
			}
		
		}
		
		return nearList;
	}
		
	/**
	 * return the list of all the bucket that contain more that one object
	 * and then are elegible to have collisions
	 * 
	 * @return
	 */
	public  List<List<T>>  getPotentialCollision() {
		
		List<List<T>> potentialCollisions = new ArrayList<>();	 
		 
		//put in the list only the bucket with more that one object
		 for (String key : bucketsPotentialCollision) {
			 potentialCollisions.add( grid.get(key));
		}
		
		return potentialCollisions;
	}
	
		
	/**
	 * object that implement this interface can be managed in the grid
	 * 
	 */
	static public interface GridIndexable {
		
		/**
		 * generate the index for this entity, this code is dependent
		 * 
		 * @param bucketsSize
		 * @return
		 */
		public List<String> generateIndex(int bucketsSize);
	}	
		

}
