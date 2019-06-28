package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.IntMap;

/**
 *  A pooled particle effect manager to store particle effect pools
 *
 */
public class ParticleEffectManager {
	// DEFINE constants for particleEffects
	public static final int LASER = 0;
	public static final int EXPLOSION = 1;	

	private static final int POOL_START_CAPACITY = 5;
	private static final int POOL_MAX_CAPACITY = 20;	
	
	// create intmaps for effects and pools
	private IntMap<ParticleEffectPool> particleEffectPool;
	
	/**
	 *  Particle Effect Manager for controlling creating pools and dispensing particle effects
	 */
	public ParticleEffectManager(){
		particleEffectPool = new IntMap<ParticleEffectPool>();
	}
	
	/** Create a particle effect pool for type  with default values (scale 1, pool init capacity 5, max capacity 20)
	 * @param type int id of particle effect
	 * @param party the particle effect
	 */
	public void addParticleEffect(int type, ParticleEffect party){
		addParticleEffect(type,party,1);
	}
	
	/** Create a particle effect pool for type with scale and default pool sizes
	 * @param type int id of particle effect
	 * @param party the particle effect
	 * @param scale The particle effect scale
	 */
	public void addParticleEffect(int type, ParticleEffect party, float scale ){
		addParticleEffect(type,party,scale,POOL_START_CAPACITY,POOL_MAX_CAPACITY);
		
	}
	
	/** Create a particle effect pool for type
	 * @param type int id of particle effect
	 * @param party the particle effect
	 * @param scale The particle effect scale
	 * @param startCapacity pool initial capacity
	 * @param maxCapacity pool max capacity
	 */
	public void addParticleEffect(int type, ParticleEffect party, float scale, int startCapacity, int maxCapacity){
		party.scaleEffect(scale);
		particleEffectPool.put(type,new ParticleEffectPool(party,startCapacity,maxCapacity));
		
	}
	
	/**
	 *  Get a particle effect of type type
	 * @param type the type to get
	 * @return The pooled particle effect
	 */
	public PooledEffect getPooledParticleEffect(int type){
		return particleEffectPool.get(type).obtain();
	}
}