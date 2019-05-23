package com.mygdx.game.ballbounce;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Ball extends PhysicsActor {


	
	private final int BALL_MASS = 1;


	public Ball(){ 
		super(); 
	}

	public void act(float dt){
		super.act(dt);
	}

	public Circle getCircle(){ 
		return new Circle( getX() + getWidth()/2, getY() + getHeight()/2, getWidth()/2 ); 
	}



	public void multVelocityX(float m){ 
		velocity.x *= m; 
	}

	public void multVelocityY(float m){ 
		velocity.y *= m; 
	}	

	
	public boolean overlaps(Ball ball, boolean bounceOff){
		
		if (! Intersector.overlaps( this.getCircle(), ball.getCircle() ) )
			return false;
		
		super.overlaps(ball, true);
		
		Vector2 thisVelocity;
		Vector2 ballVelocity;
		if ( bounceOff ){
			thisVelocity = new Vector2((2 * BALL_MASS * ball.velocity.x) / (BALL_MASS + BALL_MASS), (2 * BALL_MASS * ball.velocity.y) / (BALL_MASS + BALL_MASS));
			ballVelocity = new Vector2((2 * BALL_MASS * this.velocity.x) / (BALL_MASS + BALL_MASS), (2 * BALL_MASS * this.velocity.y) / (BALL_MASS + BALL_MASS));
			
			this.setVelocityXY(thisVelocity.x, thisVelocity.y);
			ball.setVelocityXY(ballVelocity.x, ballVelocity.y);
		}
		return true;
	}

	public Ball clone(){
		Ball newbie = new Ball();
		newbie.copy( this );  
		return newbie;
	}
	
}
