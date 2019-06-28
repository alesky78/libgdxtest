package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.utils.Pool;

public class Laser extends PhysicsActor {

	private Pool<Laser> pool;

	public Laser(){
		super();
	}
	
	public void setPool(Pool<Laser> pool) {
		this.pool = pool;
	}
	
	public void destroy(){
		super.destroy();
		
		if(pool != null) {	//return to the pool as free
			pool.free(this);
		}

	}	
	
}
