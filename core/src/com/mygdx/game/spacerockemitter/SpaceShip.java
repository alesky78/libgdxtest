package com.mygdx.game.spacerockemitter;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Disposable;

public class SpaceShip extends Group implements Disposable {
	
	private PhysicsActor shipPhysic;
	private ThrusterActor thruster;
	private Shield shield;		
	private ShaderProgram shieldShader; 
	
	public static final float MAX_SPEED = 250;
	public static final float MAX_ACCELEATION = 250; 	
	public static final float MAX_DECELERATION = 250;	
	
	public SpaceShip(String name,float maxSpeed, float acceleration, float deceleration, Texture shipTex){
		
		setName(name);
		
		//shipPhysic data
		shipPhysic = new PhysicsActor();
		shipPhysic.setMaxSpeed(MathUtils.clamp(maxSpeed, 50, MAX_SPEED));
		shipPhysic.setAcceleration(MathUtils.clamp(acceleration, 50, MAX_ACCELEATION));
		shipPhysic.setDeceleration(MathUtils.clamp(deceleration, 50, MAX_DECELERATION));
		shipPhysic.storeAnimation("default", shipTex);
		shipPhysic.setOriginCenter();
		shipPhysic.setEllipseBoundary();		
		addActor(shipPhysic);
		
		//thruster data
		thruster = new ThrusterActor();
		thruster.load("spacerockemitter/thruster.pfx", "spacerockemitter/");
		thruster.stop();	
		addActor(thruster);
		
		//shield data
		shieldShader = compileShieldShader();
		shield = new Shield(shipPhysic,shieldShader);
		Texture shieldTex = new Texture(Gdx.files.internal("spacerockemitter/shield.png"));		
		shieldTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		shield.setTexture( shieldTex );
		shield.storeAnimation( "default", shieldTex );
		shield.setOriginCenter();				
		shield.setPosition(shipPhysic.getX()+shipPhysic.getOriginX()-shipPhysic.getOriginX(),shipPhysic.getY()+shipPhysic.getOriginY()-shipPhysic.getOriginY());
		shield.setEllipseBoundary();
		addActor(shield);
		

	}

	private ShaderProgram compileShieldShader() {
		String vertexShader;
	    String fragmentShader;
	    ShaderProgram shader;

		//create all the shaders
        vertexShader = Gdx.files.internal("shader/passthrough.vrtx").readString();        
        fragmentShader = Gdx.files.internal("shader/Flicker.frgm").readString();             
        

        shader = new ShaderProgram(vertexShader,fragmentShader);	
        
        if (!shader.isCompiled()) {
            System.err.println(shader.getLog());
            System.exit(0);
        }

        if (shader.getLog().length()!=0){
            System.out.println(shader.getLog());
        }
		return shader;
	}
	
	
	public void act (float delta) {
		super.act(delta);
		
		//adjust the rotation of the thruster based on the ship
		thruster.setPosition(shipPhysic.getPositionCenterShiftToLeft());
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

	public Vector2 getPositionCenterShiftToLeft() {
		return shipPhysic.getPositionCenterShiftToLeft();
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


	////////////////////////////////////////////////
	//Dispose all the resources
	///////////////////////////////////////////////
	@Override
	public void dispose() {

		//ship
		Map<String,Animation<TextureRegion>> disposableAnimations;
		TextureRegion[] disposableTextureRegions;
		
		disposableAnimations = shipPhysic.getAnimationStorage();
		for (String key : disposableAnimations.keySet()) {
			disposableTextureRegions = disposableAnimations.get(key).getKeyFrames();
			for (TextureRegion textureRegion : disposableTextureRegions) {
				textureRegion.getTexture().dispose();
			}
		}
		
		//truster
		thruster.getParticleEffect().dispose();
		
		//shield
		shield.getTextureRegion().getTexture().dispose();
		shieldShader.dispose();
	}



}
