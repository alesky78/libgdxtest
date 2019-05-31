package com.mygdx.game.jumpingjack2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Box2DActor {

	public int groundCount;

	public Player(){
		super();
		groundCount = 0;
	}

	public void adjustGroundCount(int i){ 
		groundCount += i; 
	}

	public boolean isOnGround(){ 
		return (groundCount > 0); 
	}


	// uses data to initialize object and add to world
	public void initializePhysics(World world){
		
		// first, perform initialization tasks from Box2DActor class
		super.initializePhysics(world);
		
		// create additional player-specific fixture
		FixtureDef bottomSensor = new FixtureDef();
		bottomSensor.isSensor = true;
		PolygonShape sensorShape = new PolygonShape();
		
		// center coordinates of sensor box - offset from body center
		float x = 0f;
		float y = -15;
		
		// dimensions of sensor box
		float w = getWidth() - 8;
		float h = getHeight();
		sensorShape.setAsBox( w/SCALA_FACTOR_HALF, h/SCALA_FACTOR_HALF, new Vector2(x/SCALA_FACTOR_HALF, y/SCALA_FACTOR_HALF), 0 );
		bottomSensor.shape = sensorShape;
		
		// create and attach this new fixture
		Fixture bottomFixture = body.createFixture(bottomSensor);
		bottomFixture.setUserData("bottom");
	}


}
