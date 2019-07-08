package com.mygdx.game.spacerockemitter.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.ScaledNumericValue;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class ThrusterActor extends Actor {


	private ParticleEffect pe;


	public ThrusterActor(){
		super();
	}


	public void load(String pfxFile, String imageDirectory){
		pe = new ParticleEffect();
		pe.load(Gdx.files.internal(pfxFile), Gdx.files.internal(imageDirectory)); 
	}

	
	public void setParticleEffect(ParticleEffect pe) {
		this.pe = pe;
	}


	public ParticleEffect getParticleEffect() {
		return pe;
	}


	public void start(){ 
		pe.start(); 
	}

	// pauses continuous emitters
	public void stop(){ 
		pe.allowCompletion(); 
	}

	public boolean isRunning(){ 
		return !pe.isComplete(); 
	}


	public void setPosition(float px, float py){		
		pe.setPosition(px, py);
	}
	
	public void setPosition(Vector2 position){		
		pe.setPosition(position.x, position.y);
	}	
	
	/**
	 * rotate the emitters when the actor rotate
	 * 
	 */
	public void setRotation(float degrees) {
		for (ParticleEmitter e : pe.getEmitters() ){
			   ScaledNumericValue val = e.getAngle();
	           float amplitude = 15;
	           float h1 = degrees + amplitude;                                            
	           float h2 = degrees - amplitude;                                            
	           val.setHigh(h1, h2);                                           
	           val.setLow(degrees);       			   
		}
	}

	public void act(float dt)
	{
		super.act( dt );
		pe.update( dt );
		if ( pe.isComplete() && !pe.getEmitters().first().isContinuous() )
		{
			pe.dispose();
			this.remove();
		}
	}

	public void draw(Batch batch, float parentAlpha){ 
		super.draw(batch, parentAlpha);
		pe.draw(batch); 
	}		

}
