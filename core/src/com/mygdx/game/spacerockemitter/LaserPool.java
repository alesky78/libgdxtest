package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;

public class LaserPool {
	
	private AssetManager assetManager;
	private ParticleEffectManager particleEffectManager;
	private SpatialHashGrid<BaseActor> spatialGrid;
	
	
	private final Pool<Laser> pool = new Pool<Laser>(50) {
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
	    
	
	public LaserPool(SpatialHashGrid<BaseActor> spatialGrid, AssetManager assetManager,ParticleEffectManager particleEffectManager) {
		super();
		
		this.spatialGrid = spatialGrid;
		this.assetManager = assetManager;
		this.particleEffectManager = particleEffectManager;
	}
	
	
	
	private Laser newInstanceLaser() {
		Laser laser = new Laser();
		laser.setTexture( assetManager.get(AssetCatalog.TEXTURE_LASER) );
		laser.setOriginCenter();		
		laser.setMaxSpeed(400);
		laser.setDeceleration(0);
		laser.setRectangleBoundary();
		laser.setAutoAngle(true);
		laser.setType(ActorType.LASER);
				
		return laser;
	}

	private ParticleActorPoolable newInstanceParticle() {
		return new ParticleActorPoolable(particleEffectPool, false);
	}
	
	
	public Laser obtain(SpaceShip spaceship) {
		
		//get the laser from the pool and configure the position based on the ship
		Laser laser = pool.obtain();	
		laser.setPool(pool);
		laser.setGrid(spatialGrid);
		
		laser.isDead = false;
		
		//position the laser on the Est of the ship
		Vector2 position = ActorCoordinateUtils.getPositionEst(spaceship);
		laser.setAccelerationXY(position.x-laser.getOriginX(), position.y-laser.getOriginY());
		
		//laser.moveToCenterShiftToRight( spaceship );
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
		laser.setGrid(spatialGrid);

		return laser;
	}


}
