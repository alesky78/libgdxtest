package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.utils.Pool;

public class Laser extends PhysicsActor  implements Pool.Poolable {

	private Pool<Laser> poolLaser;

	private ParticleActorPoolable laserParticleFX;

	public Laser(){
		super();
	}
	
	public void setPool(Pool<Laser> pool) {
		this.poolLaser = pool;
	}

	public void setLaserParticleFX(ParticleActorPoolable laserParticleFX) {
		this.laserParticleFX = laserParticleFX;
	}

	public void destroy(){
		super.destroy();
		clearActions();		
		parentList = null;
		grid = null;	 
		//free the effect actor associate		
		laserParticleFX.destroy();		
		laserParticleFX = null;
		clearChildren();
		poolLaser.free(this);		
	}	
	
	
	@Override
	public void reset() {

	}		
	
}
