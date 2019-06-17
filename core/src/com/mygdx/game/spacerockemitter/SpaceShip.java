package com.mygdx.game.spacerockemitter;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;

public class SpaceShip extends Group {
	
	PhysicsActor shipPhysic;
	
	public static final float MAX_SPEED = 250;
	public static final float MAX_ACCELEATION = 250; 	
	public static final float MAX_DECELERATION = 250;	
	
	public SpaceShip(String name,float maxSpeed, float acceleration, float deceleration, Texture shipTex){
		
		setName(name);
		
		shipPhysic = new PhysicsActor();
		shipPhysic.setMaxSpeed(MathUtils.clamp(maxSpeed, 50, MAX_SPEED));
		shipPhysic.setAcceleration(MathUtils.clamp(acceleration, 50, MAX_ACCELEATION));
		shipPhysic.setDeceleration(MathUtils.clamp(deceleration, 50, MAX_DECELERATION));
		shipPhysic.storeAnimation("default", shipTex);
		shipPhysic.setOriginCenter();
		shipPhysic.setEllipseBoundary();		
		
		addActor(shipPhysic);

	}
	
	////////////////////////////////////////////////
	//all the positions are based on the position of the 
	//PhysicsActor that represent the ship
	///////////////////////////////////////////////
	public void setPosition (float x, float y) {
		shipPhysic.setPosition(x, y);
	}
	
	public float getX() {
		return shipPhysic.getX();
	}

	public float getY() {
		return shipPhysic.getY();
	}
	
	public void setX(float x) {
		shipPhysic.setX(x);
	}

	public void setY(float y) {
		shipPhysic.setY(y);
	}	
	
	public float getOriginX(){
		return shipPhysic.getOriginX();
	}
	
	public float getOriginY(){
		return shipPhysic.getOriginY();
	}		
	
	public float getRotation () {
		return shipPhysic.getRotation();
	}	
	
	public Vector2 getVelocity() {
		return shipPhysic.getVelocity();
	}

	public void setAccelerationXY(int ax, int ay) {
		shipPhysic.setAccelerationXY(ax, ay);
	}

	public void setVelocity(int vx, int vy) {
		shipPhysic.setVelocity(vx, vy);
	}

	public void addAccelerationAS() {
		shipPhysic.addAccelerationAS(shipPhysic.getRotation());
	}

	public void rotateBy (float amountInDegrees) {
		shipPhysic.rotateBy(amountInDegrees);
	}

	public boolean overlaps(Rock rock, boolean b) {
		return shipPhysic.overlaps(rock, b);
	}

	public Vector2 getPositionCenterShiftToLeft() {
		return shipPhysic.getPositionCenterShiftToLeft();
	}	

	////////////////////////////////////////////////
	//UI methods  
	///////////////////////////////////////////////
	
	public float getSpeedRatio(){
		return shipPhysic.getMaxSpeed()/MAX_SPEED;
	}

	public float getAccelerationRatio(){
		return shipPhysic.getAcceleration()/MAX_ACCELEATION;
	}

	public float getDecelerationRatio(){
		return shipPhysic.getDeceleration()/MAX_DECELERATION;
	}

	public Map<String, Animation<TextureRegion>> getAnimationStorage() {
		return shipPhysic.getAnimationStorage(); 
	}

	public TextureRegion getTextureRegion() {
		return shipPhysic.getTextureRegion();
	}
	
	public float getWidth(){
		return shipPhysic.getWidth();
	}

	public float getHeight(){
		return shipPhysic.getHeight();
	}
	
	public void addAction (Action action) {
		shipPhysic.addAction(action);
	}
	
	public void clearActions () {
		shipPhysic.clearActions();
	}	

}
