package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameUtils {

	
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

}
