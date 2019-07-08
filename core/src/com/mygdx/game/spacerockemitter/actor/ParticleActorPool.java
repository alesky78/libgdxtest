package com.mygdx.game.spacerockemitter.actor;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.spacerockemitter.ParticleEffectManager;

public class ParticleActorPool {

	private ParticleEffectManager particleEffectManager;
	private boolean autoDestroy;
	private int type;	
	
	
	private final Pool<ParticleActorPoolable>pool = new Pool<ParticleActorPoolable>() {
		@Override
		protected ParticleActorPoolable newObject() {
			return newInstance();
		}

	};


	public ParticleActorPool(ParticleEffectManager particleEffectManager, boolean autoDestroy, int type) {
		super();
		this.particleEffectManager = particleEffectManager;
		this.autoDestroy = autoDestroy;
		this.type = type;
	}


	private ParticleActorPoolable newInstance() {
		return new ParticleActorPoolable(pool, autoDestroy);
	}


	public ParticleActorPoolable obtain(float px,float py) {

		//get the particle from the pool and configure it		
		ParticleActorPoolable particleActor = pool.obtain();
		particleActor.setParticleEffect(particleEffectManager.getPooledParticleEffect(type));
		particleActor.setPosition(px, py);
		particleActor.start();

		return particleActor;
	}
}
