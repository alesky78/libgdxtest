package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Rock extends PhysicsActor  implements Pool.Poolable {

	private int ROCK_MASS = 1;
	private int life;
	private int size;	

	private Pool<Rock> pool;
	
	public Rock(int life,int size){ 
		super(); 
		this.life = life;
		this.size = size;		
	}
	
	public void setPool(Pool<Rock> pool) {
		this.pool = pool;
	}
	

	public void act(float dt){
		super.act(dt);
	}


	public boolean isDistoried(){
		return life <= 0;
	}

	public void removeLife(){
		life = life -1;
	}

	public void setLife(int life){
		this.life = life;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean overlaps(Rock rock, boolean bounceOff){


		if (! super.overlaps(rock, true))
			return false;

		//if overlap then elastic collision
		Vector2 thisVelocity;
		Vector2 rockVelocity;
		if ( bounceOff ){
			thisVelocity = new Vector2((2 * ROCK_MASS * rock.velocity_V.x) / (ROCK_MASS + ROCK_MASS), (2 * ROCK_MASS * rock.velocity_V.y) / (ROCK_MASS + ROCK_MASS));
			rockVelocity = new Vector2((2 * ROCK_MASS * this.velocity_V.x) / (ROCK_MASS + ROCK_MASS), (2 * ROCK_MASS * this.velocity_V.y) / (ROCK_MASS + ROCK_MASS));

			this.setVelocityXY(thisVelocity.x, thisVelocity.y);
			rock.setVelocityXY(rockVelocity.x, rockVelocity.y);
		}

		return true;
	}

	public void destroy(){
		super.destroy();
		clearActions();			
		parentList = null;
		grid = null;			
		pool.free(this);	
	}	
	
	@Override
	public void reset() {


	}

}
