package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.utils.Pool;

public class Rock extends PhysicsActor  implements Pool.Poolable {

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
