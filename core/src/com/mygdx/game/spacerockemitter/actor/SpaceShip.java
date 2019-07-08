package com.mygdx.game.spacerockemitter.actor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.spacerockemitter.ActorCoordinateUtils;
import com.mygdx.game.spacerockemitter.AssetCatalog;
import com.mygdx.game.spacerockemitter.SpatialHashGrid;


public class SpaceShip extends Group {

	private PhysicsActor ship;
	private ThrusterActor thruster;
	private Shield shield;		

	public static final float MAX_SPEED = 250;
	public static final float MAX_ACCELEATION = 250; 	
	public static final float MAX_DECELERATION = 250;	

	public SpaceShip(String name,float maxSpeed, float acceleration, float deceleration, Texture shipTex, AssetManager assetManager){

		setName(name);

		//thruster data
		thruster = new ThrusterActor();
		thruster.setParticleEffect(assetManager.get(AssetCatalog.PARTICLE_THRUSTER));
		thruster.stop();	
		addActor(thruster);

		//shipPhysic data
		ship = new PhysicsActor();
		ship.setMaxSpeed(MathUtils.clamp(maxSpeed, 50, MAX_SPEED));
		ship.setAcceleration(MathUtils.clamp(acceleration, 50, MAX_ACCELEATION));
		ship.setDeceleration(MathUtils.clamp(deceleration, 50, MAX_DECELERATION));
		ship.setTexture(shipTex);
		ship.setOriginCenter();
		ship.assignEllipseBoundary();	
		ship.setType(ActorType.SHIP_BODY);
		addActor(ship);

		//shield data
		shield = new Shield(ship,assetManager.get(AssetCatalog.SHADER_FLICKER));
		shield.setTexture( assetManager.get(AssetCatalog.TEXTURE_SHIP_SHILED) );
		shield.setOriginCenter();				
		shield.setPosition(ship.getX()+ship.getOriginX()-ship.getOriginX(),ship.getY()+ship.getOriginY()-ship.getOriginY());
		shield.assignEllipseBoundary();
		shield.setType(ActorType.SHIP_SHIELD);		
		//shield.setScale(shipPhysic.getWidth()/shield.getWidth(), shipPhysic.getHeight()/shield.getHeight());
		addActor(shield);


	}

	public PhysicsActor getShip() {
		return ship;
	}
	
	public Shield getShield() {
		return shield;
	}

	public void setGrid(SpatialHashGrid<BaseActor> grid) {
		ship.setGrid(grid);
		shield.setGrid(grid);
	}	

	public void act (float delta) {
		super.act(delta);

		//adjust the rotation of the thruster based on the ship
		thruster.setPosition(ActorCoordinateUtils.getPositionWest(ship));
		thruster.setRotation(ship.getRotation()+180);
	}

	////////////////////////////////////////////////
	//all the positions are based on the position of the 
	//PhysicsActor that represent the ship
	///////////////////////////////////////////////
	public void setPosition (float x, float y) {
		ship.setPosition(x, y);
	}

	public float getX() {
		return ship.getX();
	}

	public float getY() {
		return ship.getY();
	}

	public void setX(float x) {
		ship.setX(x);
	}

	public void setY(float y) {
		ship.setY(y);
	}	

	public float getOriginX(){
		return ship.getOriginX();
	}

	public float getOriginY(){
		return ship.getOriginY();
	}		

	public float getRotation () {
		return ship.getRotation();
	}	

	public Vector2 getVelocity() {
		return ship.getVelocity();
	}

	public void setAccelerationXY(int ax, int ay) {
		ship.setAccelerationXY(ax, ay);
	}

	public void setVelocity(int vx, int vy) {
		ship.setVelocity(vx, vy);
	}

	public void addAccelerationAS() {
		ship.addAccelerationAS(ship.getRotation());
	}

	public void rotateBy (float amountInDegrees) {
		ship.rotateBy(amountInDegrees);
	}
	

	//////////////////////////////////////////
	//thruster logic
	/////////////////////////////////////////
	public void startThruster(){
		thruster.start();		
	}

	public void stopThruster(){
		thruster.stop();		
	}

	//////////////////////////////////////////
	//shield logic
	/////////////////////////////////////////
	public void activateShield(){
		shield.activate();		
	}

	public boolean isActiveShield(){
		return shield.isActive();		
	}	

	////////////////////////////////////////////////
	//UI methods  
	///////////////////////////////////////////////

	public float getSpeedRatio(){
		return ship.getMaxSpeed()/MAX_SPEED;
	}

	public float getAccelerationRatio(){
		return ship.getAcceleration()/MAX_ACCELEATION;
	}

	public float getDecelerationRatio(){
		return ship.getDeceleration()/MAX_DECELERATION;
	}

	public TextureRegion getTextureRegion() {
		return ship.getTextureRegion();
	}

	public float getWidth(){
		return ship.getWidth();
	}

	public float getHeight(){
		return ship.getHeight();
	}

	public void addAction (Action action) {
		ship.addAction(action);
	}

	public void clearActions () {
		ship.clearActions();
	}



}
