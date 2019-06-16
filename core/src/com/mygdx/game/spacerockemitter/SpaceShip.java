package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class SpaceShip extends PhysicsActor {
	
	
	public static final float MAX_SPEED = 250;
	public static final float MAX_ACCELEATION = 250; 	
	public static final float MAX_DECELERATION = 250;	
	
	public SpaceShip(String name,float maxSpeed, float acceleration, float deceleration, Texture shipTex){
		
		setName(name);
		this.maxSpeed = MathUtils.clamp(maxSpeed, 50, MAX_SPEED);
		this.maxdAcceleration = MathUtils.clamp(acceleration, 50, MAX_ACCELEATION);
		this.deceleration = MathUtils.clamp(deceleration, 50, MAX_DECELERATION);
		
		storeAnimation( "default", shipTex );
		setOriginCenter();
		setEllipseBoundary();
	}
	
	public float getSpeedRatio(){
		return maxSpeed/MAX_SPEED;
	}

	public float getAccelerationRatio(){
		return maxdAcceleration/MAX_ACCELEATION;
	}

	public float getDecelerationRatio(){
		return deceleration/MAX_DECELERATION;
	}
	
}
