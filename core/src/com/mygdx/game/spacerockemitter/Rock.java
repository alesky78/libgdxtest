package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Rock extends PhysicsActor  implements Pool.Poolable {

	private float mass = 1;
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
	
	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public boolean overlaps(Rock rock, boolean bounceOff){


		if (! super.overlaps(rock, true))
			return false;

		//if overlap then elastic collision
		Vector2 thisVelocity;
		Vector2 rockVelocity;
		if ( bounceOff ){
			thisVelocity = new Vector2(
										(this.velocity_V.x*(this.mass - rock.mass ) + (2 * rock.mass * rock.velocity_V.x) ) / (this.mass + rock.mass), 
										(this.velocity_V.y*(this.mass - rock.mass ) + (2 * rock.mass * rock.velocity_V.y) ) / (this.mass + rock.mass)
								);
			
			
			rockVelocity = new Vector2(
										(rock.velocity_V.x*(rock.mass - this.mass )  + (2 * this.mass * this.velocity_V.x)) / (this.mass + rock.mass), 
										(rock.velocity_V.x*(rock.mass - this.mass )  + (2 * this.mass * this.velocity_V.y)) / (this.mass + rock.mass)
										);

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
