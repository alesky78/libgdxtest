package com.mygdx.game.jumpingjack;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DActor extends AnimatedActor {

	final static protected float SCALA_FACTOR = 100.0f;
	final static protected float SCALA_FACTOR_HALF = SCALA_FACTOR * 2.0f;

	protected BodyDef bodyDef;
	protected Body body;
	protected Fixture fixture; 
	protected FixtureDef fixtureDef;

	protected Float maxSpeed;
	protected Float maxSpeedX;
	protected Float maxSpeedY;

	public Box2DActor(){

		body = null;
		fixture = null;
		bodyDef = new BodyDef();
		fixtureDef = new FixtureDef();
		maxSpeed = null;
		maxSpeedX = null;
		maxSpeedY = null;
	}


	public void initializePhysics(World w){
		body = w.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
		fixture.setUserData("main");
		body.setUserData(this);
	}


	public Body getBody() {
		return body;
	}


	public Fixture getFixture() {
		return fixture;
	}


	public void setMaxSpeed(float f){ 
		maxSpeed = f; 
	}


	public void setMaxSpeedX(float f){ 
		maxSpeedX = f; 
	}


	public void setMaxSpeedY(float f){ 
		maxSpeedY = f; 
	}


	public void setStatic(){ 
		bodyDef.type = BodyType.StaticBody; 
	}


	public void setDynamic(){ 
		bodyDef.type = BodyType.DynamicBody; 
	}


	public void setFixedRotation(){ 
		bodyDef.fixedRotation = true; 
	}


	public void setShapeRectangle(){
		setOriginCenter();
		bodyDef.position.set( (getX() + getOriginX()) / SCALA_FACTOR, (getY() + getOriginY())/SCALA_FACTOR );
		PolygonShape rect = new PolygonShape();
		rect.setAsBox( getWidth()/SCALA_FACTOR_HALF, getHeight()/SCALA_FACTOR_HALF );
		fixtureDef.shape = rect;
	}


	public void setShapeCircle(){
		setOriginCenter();
		bodyDef.position.set( (getX() + getOriginX()) / SCALA_FACTOR, (getY() + getOriginY())/SCALA_FACTOR );
		CircleShape circ = new CircleShape();
		circ.setRadius( getWidth()/SCALA_FACTOR_HALF );
		fixtureDef.shape = circ;
	}


	public void setPhysicsProperties(float density, float friction, float restitution){
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;
	}


	public void applyForce(Vector2 force){ 
		body.applyForceToCenter(force, true); 
	}


	public void applyImpulse(Vector2 impulse){ 
		body.applyLinearImpulse(impulse, body.getPosition(), true); 
	}


	public Vector2 getVelocity(){ 
		return body.getLinearVelocity(); 
	}


	public float getSpeed(){ 
		return getVelocity().len(); 
	}


	public void setVelocity(float vx, float vy){ 
		body.setLinearVelocity(vx,vy); 
	}


	public void setVelocity(Vector2 v){ 
		body.setLinearVelocity(v); 
	}


	public void setSpeed(float s){ 
		setVelocity( getVelocity().setLength(s) ); 
	}


	public void act(float dt)
	{
		super.act(dt);
		// cap max speeds, if they have been set
		if (maxSpeedX != null)
		{
			Vector2 v = getVelocity();
			v.x = MathUtils.clamp(v.x, -maxSpeedX, maxSpeedX);
			setVelocity(v);
		}
		if (maxSpeedY != null)
		{
			Vector2 v = getVelocity();
			v.y = MathUtils.clamp(v.y, -maxSpeedY, maxSpeedY);
			setVelocity(v);
		}
		if (maxSpeed != null)
		{
			float s = getSpeed();
			if (s > maxSpeed)
				setSpeed(maxSpeed);
		}
		// update image data - position and rotation - based on physics data
		Vector2 center = body.getWorldCenter();
		setPosition( SCALA_FACTOR*center.x - getOriginX(), SCALA_FACTOR*center.y - getOriginY() );
		float a = body.getAngle(); // angle in radians
		setRotation( a * MathUtils.radiansToDegrees ); // convert from radians to degrees
	}


	public Box2DActor clone(){
		Box2DActor newbie = new Box2DActor();
		newbie.copy( this ); // only copies AnimatedActor data
		return newbie;
	}	

	
}
