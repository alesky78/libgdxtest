package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class ParticleActor<T extends ParticleEffect> extends Actor {

	protected T pe;
	protected boolean autoDestroy;	

	public ParticleActor(){
		super();
		autoDestroy = true;
	}

	public ParticleActor(boolean autoDestroy){
		super();
		this.autoDestroy = autoDestroy;
	}
	
	
	public void setParticleEffect(T pe) {
		this.pe = pe;
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

	public void act(float dt){
		super.act( dt );
		pe.update( dt );
		if (autoDestroy &&  pe.isComplete() && !pe.getEmitters().first().isContinuous() ){
			destroy();
		}
	}
	
	public void destroy() {
		this.remove();
	}		

	public void draw(Batch batch, float parentAlpha){ 
		super.draw(batch, parentAlpha);
		pe.draw(batch); 
	}

	
	public void moveToCenterShiftToRight(BaseActor target){
		this.setPosition(
				target.getX() + target.getOriginX() - this.getOriginX() + MathUtils.cosDeg(target.getRotation())*target.getWidth()/2,
				target.getY() + target.getOriginY() - this.getOriginY() + MathUtils.sinDeg (target.getRotation())*target.getWidth()/2);
	}		

}
