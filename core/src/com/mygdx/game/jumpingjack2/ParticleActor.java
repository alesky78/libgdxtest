package com.mygdx.game.jumpingjack2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class ParticleActor extends Actor {


	private ParticleEffect pe;


	public ParticleActor(){
		super();
		pe = new ParticleEffect();
	}


	public void load(String pfxFile, String imageDirectory){ 
		pe.load(Gdx.files.internal(pfxFile), Gdx.files.internal(imageDirectory)); 
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
		for (ParticleEmitter e : pe.getEmitters() ){
			e.setPosition(px, py);			
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

	public ParticleActor clone(){
		ParticleActor newbie = new ParticleActor();
		newbie.pe = new ParticleEffect(this.pe);
		return newbie;
	}
	
	public void moveToCenterShiftToRight(BaseActor target){
		this.setPosition(
				target.getX() + target.getOriginX() - this.getOriginX() + MathUtils.cosDeg(target.getRotation())*target.getWidth()/2,
				target.getY() + target.getOriginY() - this.getOriginY() + MathUtils.sinDeg (target.getRotation())*target.getWidth()/2);
	}		

}
