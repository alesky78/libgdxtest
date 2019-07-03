package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;


public class SpaceShip extends Group {

	private PhysicsActor shipPhysic;
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
		shipPhysic = new PhysicsActor();
		shipPhysic.setMaxSpeed(MathUtils.clamp(maxSpeed, 50, MAX_SPEED));
		shipPhysic.setAcceleration(MathUtils.clamp(acceleration, 50, MAX_ACCELEATION));
		shipPhysic.setDeceleration(MathUtils.clamp(deceleration, 50, MAX_DECELERATION));
		shipPhysic.setTexture(shipTex);
		shipPhysic.setOriginCenter();
		shipPhysic.assignEllipseBoundary();	
		shipPhysic.setType(ActorType.SHIP_BODY);
		addActor(shipPhysic);

		//shield data
		shield = new Shield(shipPhysic,assetManager.get(AssetCatalog.SHADER_FLICKER));
		shield.setTexture( assetManager.get(AssetCatalog.TEXTURE_SHIP_SHILED) );
		shield.setOriginCenter();				
		shield.setPosition(shipPhysic.getX()+shipPhysic.getOriginX()-shipPhysic.getOriginX(),shipPhysic.getY()+shipPhysic.getOriginY()-shipPhysic.getOriginY());
		shield.assignEllipseBoundary();
		shield.setType(ActorType.SHIP_SHIELD);		
		//shield.setScale(shipPhysic.getWidth()/shield.getWidth(), shipPhysic.getHeight()/shield.getHeight());
		addActor(shield);


	}

	public void setGrid(SpatialHashGrid<BaseActor> grid) {
		shipPhysic.setGrid(grid);
		shield.setGrid(grid);
	}	

	public void act (float delta) {
		super.act(delta);

		//adjust the rotation of the thruster based on the ship
		thruster.setPosition(ActorCoordinateUtils.getPositionWest(shipPhysic));
		thruster.setRotation(shipPhysic.getRotation()+180);
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


	public boolean overlapsShip(Rock rock, boolean b) {
		return shipPhysic.overlaps(rock, b);
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

	public void overlapsShield(Rock rock, boolean resolve) {
		shield.overlaps(rock, resolve);
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
