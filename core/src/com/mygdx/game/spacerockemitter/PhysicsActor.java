package com.mygdx.game.spacerockemitter;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class PhysicsActor extends AnimatedActor
{
	protected Vector2 velocity;
	protected Vector2 acceleration;

	// maximum speed
	protected float maxSpeed;

	// speed reduction, in pixels/second, when not accelerating
	protected float deceleration;
	
	// acceleration impulse	added to the actual acceleration
	protected float maxdAcceleration = 100f;	

	// should image rotate to match velocity?
	private boolean autoAngle;

	public PhysicsActor()
	{
		velocity = new Vector2();
		acceleration = new Vector2();
		maxSpeed = 9999;
		deceleration = 0;
		autoAngle = false;
	}

	
	
	// velocity methods
	public void setVelocityXY(float vx, float vy){  velocity.set(vx,vy);  }
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	public void setVelocity(float vx, float vy)
	{  velocity.set(vx,vy);  }	

	public void addVelocityXY(float vx, float vy)
	{  velocity.add(vx,vy);  }

	// set velocity from angle and speed
	public void setVelocityAS(float angleDeg, float speed)
	{
		velocity.x = speed * MathUtils.cosDeg(angleDeg);
		velocity.y = speed * MathUtils.sinDeg(angleDeg);
	}

	protected void rotationChanged () {
		setRotation(getRotation()%360);
	}
	
	public float getSpeed()
	{  return velocity.len();  }

	public void setSpeed(float s)
	{  velocity.setLength(s);  }

	public void setMaxSpeed(float ms)
	{  maxSpeed = ms;  }

	public float getMotionAngle()
	{  return MathUtils.atan2(velocity.y, velocity.x) * MathUtils.radiansToDegrees;  }

	public void setAutoAngle(boolean b)
	{  autoAngle = b;  }

	// acceleration methods

	public void setAccelerationXY(float ax, float ay)
	{  acceleration.set(ax,ay);  }

	public void addAccelerationXY(float ax, float ay)
	{  acceleration.add(ax,ay);  }

	// set acceleration from angle and speed
	public void setAccelerationAS(float angleDeg, float speed)
	{
		acceleration.x = speed * MathUtils.cosDeg(angleDeg);
		acceleration.y = speed * MathUtils.sinDeg(angleDeg);
	}

	public void addAccelerationAS(float angle, float amount){
		acceleration.add( amount * MathUtils.cosDeg(angle), amount * MathUtils.sinDeg(angle) );
	}

	public void addAccelerationAS(float angle){
		acceleration.add( maxdAcceleration * MathUtils.cosDeg(angle), maxdAcceleration * MathUtils.sinDeg(angle) );
	}	

	public void accelerateForward(float speed)
	{  setAccelerationAS( getRotation(), speed );  }

	public void setDeceleration(float d)
	{  deceleration = d;  }

	public void act(float dt) 
	{
		super.act(dt);

		// apply acceleration
		velocity.add( acceleration.x * dt, acceleration.y * dt );

		// decrease velocity when not accelerating
		if (acceleration.len() < 0.01)
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
		moveBy( velocity.x * dt, velocity.y * dt );

		// rotate image when moving
		if (autoAngle && getSpeed() > 0.1 )
			setRotation( getMotionAngle() );
	}

	public void copy(PhysicsActor original)
	{
		super.copy(original);
		this.velocity = new Vector2(original.velocity);
		this.acceleration = new Vector2(original.acceleration);
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