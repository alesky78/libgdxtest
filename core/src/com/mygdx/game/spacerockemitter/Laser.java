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
		poolLaser.free(this);
		laserParticleFX.destroy();
	}	
	
	
	@Override
	public void reset() {
		//free the effect actor associate
		clearChildren();
	}		
	
}
