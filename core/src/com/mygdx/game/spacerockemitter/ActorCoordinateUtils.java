package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorCoordinateUtils {

	
	/**
	 * move the toMove actor Origin coordinate to the Origin coordinate of the target
	 * the origin is considered has the center of the actor
	 * 
	 * @param toMove 
	 * @param target
	 */
	public void moveToOrigin(Actor toMove, Actor target){
		toMove.setPosition(
				target.getX() + target.getOriginX() - toMove.getOriginX(),
				target.getY() + target.getOriginY() - toMove.getOriginY());
	}
	
	/**
	 * calculate the point on the west of the actor
	 * 
	 * @param target
	 * @return
	 */
	public static Vector2 getPositionWest(Actor target){
		Vector2 position = new Vector2();
		position.x = target.getX() + target.getOriginX() - MathUtils.cosDeg(target.getRotation())*target.getWidth()/2;
		position.y = target.getY() + target.getOriginY() - MathUtils.sinDeg(target.getRotation())*target.getWidth()/2;
		return position;
	}	

	/**
	 * calculate the point on the est of the actor
	 * 
	 * @param target
	 * @return
	 */
	public static  Vector2 getPositionEst(Actor target){
		Vector2 position = new Vector2();
		position.x = target.getX() + target.getOriginX() + MathUtils.cosDeg(target.getRotation())*target.getWidth()/2;
		position.y = target.getY() + target.getOriginY() + MathUtils.sinDeg(target.getRotation())*target.getWidth()/2;
		return position;
	}	

	/**
	 * calculate the point on the north of the actor
	 * 
	 * @param target
	 * @return
	 */
	public static  Vector2 getPositionNorth(Actor target){
		Vector2 position = new Vector2();
		position.x = target.getX() + target.getOriginX() - MathUtils.sinDeg(target.getRotation())*target.getHeight()/2;
		position.y = target.getY() + target.getOriginY() + MathUtils.cosDeg(target.getRotation())*target.getHeight()/2;
		return position;
	}
	
	/**
	 * calculate the point on the north-est of the actor
	 * 
	 * @param target
	 * @return
	 */	
	public static  Vector2 getPositionNorthEst(Actor target){
		Vector2 position = new Vector2( target.getX()+target.getWidth(),target.getY()+target.getHeight() );
		Vector2 reference = new Vector2( target.getX()+target.getOriginX(),target.getY()+target.getOriginY() );		
		position.rotateAround(reference, target.getRotation());
		return position;
	}	

	/**
	 * calculate the point on the north-west of the actor
	 * 
	 * @param target
	 * @return
	 */	
	public static  Vector2 getPositionNorthWest(Actor target){
		Vector2 position = new Vector2( target.getX(),target.getY()+target.getHeight() );
		Vector2 reference = new Vector2( target.getX()+target.getOriginX(),target.getY()+target.getOriginY() );		
		position.rotateAround(reference, target.getRotation());
		return position;
	}		
	
	/**
	 * calculate the point on the south of the actor
	 * 
	 * @param target
	 * @return
	 */
	public static  Vector2 getPositionSouth(Actor target){
		Vector2 position = new Vector2();
		position.x = target.getX() + target.getOriginX() + MathUtils.sinDeg(target.getRotation())*target.getHeight()/2;
		position.y = target.getY() + target.getOriginY() - MathUtils.cosDeg(target.getRotation())*target.getHeight()/2;
		return position;
	}
	
	/**
	 * calculate the point on the south-est of the actor
	 * 
	 * @param target
	 * @return
	 */	
	public static  Vector2 getPositionSouthEst(Actor target){
		Vector2 position = new Vector2( target.getX()+target.getWidth(),target.getY() );
		Vector2 reference = new Vector2( target.getX()+target.getOriginX(),target.getY()+target.getOriginY() );		
		position.rotateAround(reference, target.getRotation());
		return position;
	}	

	/**
	 * calculate the point on the south-west of the actor
	 * 
	 * @param target
	 * @return
	 */	
	public static  Vector2 getPositionSouthWest(Actor target){
		Vector2 position = new Vector2( target.getX(),target.getY() );
		Vector2 reference = new Vector2( target.getX()+target.getOriginX(),target.getY()+target.getOriginY() );		
		position.rotateAround(reference, target.getRotation());
		return position;
	}			
		
	

}
