package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;

public class LaserPool {
	
	private AssetManager assetManager;
	private ParticleEffectManager particleEffectManager;
	
	
	private final Pool<Laser> bulletPool = new Pool<Laser>() {
		@Override
		protected Laser newObject() {
			return newInstanceLaser();
		}
	};

	private final Pool<ParticleActorPoolable>particleEffectPool = new Pool<ParticleActorPoolable>() {
		@Override
		protected ParticleActorPoolable newObject() {
			return newInstanceParticle();
		}

	};
	    
	
	public LaserPool(AssetManager assetManager,ParticleEffectManager particleEffectManager) {
		super();
		
		this.assetManager = assetManager;
		this.particleEffectManager = particleEffectManager;
	}
	
	
	
	private Laser newInstanceLaser() {
		Laser laser = new Laser();
		Texture laserTex = assetManager.get(AssetCatalog.TEXTURE_LASER);
		laser.setTexture( laserTex );
		laser.storeAnimation( "default", laserTex );
		laser.setOriginCenter();		
		laser.setMaxSpeed(400);
		laser.setDeceleration(0);
		laser.setRectangleBoundary();
		laser.setAutoAngle(true);
		laser.setPool(bulletPool);
				
		return laser;
	}

	private ParticleActorPoolable newInstanceParticle() {
		return new ParticleActorPoolable(particleEffectPool, false);
	}
	
	
	public Laser obtain(SpaceShip spaceship) {
		
		//get the laser from the pool and configure the position based on the ship
		Laser laser = bulletPool.obtain();
		laser.moveToCenterShiftToRight( spaceship );
		laser.setVelocityAS( spaceship.getRotation(), 400 );
		laser.setVisible(true);
		laser.clearActions();
		laser.getColor().a = 1.0f;//reset the alpha to 1 becouse the previous execution set it to 0
		laser.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.delay(1.f), Actions.visible(false)) );
		 
		//get the particle from the pool and configure it		
		ParticleActorPoolable laserParticle = particleEffectPool.obtain();
		laserParticle.setParticleEffect(particleEffectManager.getPooledParticleEffect(ParticleEffectManager.LASER));
		laserParticle.setPosition(laser.getWidth(), laser.getHeight()/2);
		laserParticle.start();
		laser.addActor(laserParticle);
		laser.setLaserParticleFX(laserParticle);

		return laser;
	}


}
