package com.mygdx.game.spacerockemitter.actor;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Pool;


public class ParticleActorPoolable extends ParticleActor<PooledEffect>  implements Pool.Poolable {

	private Pool<ParticleActorPoolable> pool;	
	
	public ParticleActorPoolable(Pool<ParticleActorPoolable> pool){
		super();
		this.pool = pool;
	}

	public ParticleActorPoolable(Pool<ParticleActorPoolable> pool,boolean autoDestroy){
		super();
		this.autoDestroy = autoDestroy;
		this.pool = pool;		
	}	
		

	
	public void destroy() {
		super.destroy();
		pool.free(this);
	}		
	
	@Override
	public void reset() {
		pe.free(); 
	}



}
