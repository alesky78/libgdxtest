package com.mygdx.game.spacerockemitter;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class PhysicsActor extends AnimatedActor
{
	protected Vector2 velocity_V;
	protected Vector2 acceleration_V;

	// maximum speed
	protected float maxSpeed;

	// speed reduction, in pixels/second, when not accelerating
	protected float deceleration;
	
	// acceleration impulse	added to the actual acceleration
	protected float acceleration;	

	// should image rotate to match velocity?
	private boolean autoAngle;

	public PhysicsActor()
	{
		velocity_V = new Vector2();
		acceleration_V = new Vector2();
		maxSpeed = 9999;
		deceleration = 0;
		autoAngle = false;
	}

	
	
	// velocity methods
	public void setVelocityXY(float vx, float vy){  velocity_V.set(vx,vy);  }
	
	public Vector2 getVelocity() {
		return velocity_V;
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity_V = velocity;
	}
	public void setVelocity(float vx, float vy)
	{  velocity_V.set(vx,vy);  }	

	public void addVelocityXY(float vx, float vy)
	{  velocity_V.add(vx,vy);  }

	// set velocity from angle and speed
	public void setVelocityAS(float angleDeg, float speed)
	{
		velocity_V.x = speed * MathUtils.cosDeg(angleDeg);
		velocity_V.y = speed * MathUtils.sinDeg(angleDeg);
	}

	protected void rotationChanged () {
		setRotation(getRotation()%360);
	}
	
	public float getSpeed()
	{  return velocity_V.len();  }

	public void setSpeed(float s)
	{  velocity_V.setLength(s);  }

	public void setMaxSpeed(float ms)
	{  maxSpeed = ms;  }

	public float getMaxSpeed() {
		return maxSpeed;
	}
	
	public float getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public float getDeceleration() {
		return deceleration;
	}

	public float getMotionAngle()
	{  return MathUtils.atan2(velocity_V.y, velocity_V.x) * MathUtils.radiansToDegrees;  }

	public void setAutoAngle(boolean b)
	{  autoAngle = b;  }

	// acceleration methods

	public void setAccelerationXY(float ax, float ay)
	{  acceleration_V.set(ax,ay);  }

	public void addAccelerationXY(float ax, float ay)
	{  acceleration_V.add(ax,ay);  }

	// set acceleration from angle and speed
	public void setAccelerationAS(float angleDeg, float speed)
	{
		acceleration_V.x = speed * MathUtils.cosDeg(angleDeg);
		acceleration_V.y = speed * MathUtils.sinDeg(angleDeg);
	}

	public void addAccelerationAS(float angle, float amount){
		acceleration_V.add( amount * MathUtils.cosDeg(angle), amount * MathUtils.sinDeg(angle) );
	}

	public void addAccelerationAS(float angle){
		acceleration_V.add( acceleration * MathUtils.cosDeg(angle), acceleration * MathUtils.sinDeg(angle) );
	}	

	public void accelerateForward(float speed)
	{  setAccelerationAS( getRotation(), speed );  }

	public void setDeceleration(float d)
	{  deceleration = d;  }

	public void act(float dt) 
	{
		super.act(dt);

		// apply acceleration
		velocity_V.add( acceleration_V.x * dt, acceleration_V.y * dt );

		// decrease velocity when not accelerating
		if (acceleration_V.len() < 0.01)
		{
			float decelerateAmount = deceleration * dt;
			if ( getSpeed() < decelerateAmount )
				setSpeed(0);
			else
				setSpeed( getSpeed() - decelerateAmount );
		}

		// cap at max speed
		if ( getSpeed() > maxSpeed )
			setSpeed(maxSpeed);

		// apply velocity
		moveBy( velocity_V.x * dt, velocity_V.y * dt );

		// rotate image when moving
		if (autoAngle && getSpeed() > 0.1 )
			setRotation( getMotionAngle() );
	}

	public void copy(PhysicsActor original)
	{
		super.copy(original);
		this.velocity_V = new Vector2(original.velocity_V);
		this.acceleration_V = new Vector2(original.acceleration_V);
		this.maxSpeed = original.maxSpeed;
		this.deceleration = original.deceleration;
		this.autoAngle = original.autoAngle;
	}

	public PhysicsActor clone()
	{
		PhysicsActor newbie = new PhysicsActor();
		newbie.copy( this );  
		return newbie;
	}
	
	
}