package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Shield extends PhysicsActor {

	private final int SHIELD_TTL = 4;	

	private PhysicsActor spaceship;
	private ShaderProgram ShaderProgram;
	
	private boolean active;  
	
	private float activeTime = 0;	
	
	public Shield(PhysicsActor target, ShaderProgram ShaderProgram){ 
		super();
		this.spaceship = target;
		this.ShaderProgram = ShaderProgram;
		active = false;
	}

	public void activate(){
		clearActions();
		addAction(
					Actions.sequence(Actions.parallel(
													Actions.fadeIn(0.2f),
													Actions.scaleTo(1f, 1f, 0.2f)
												),
									Actions.delay(SHIELD_TTL-0.4f),
									Actions.parallel(
											Actions.fadeOut(0.2f),
											Actions.scaleTo(0, 0, 0.2f)
									)
					)
				);
		setVisible(true);
		active = true;
		activeTime = SHIELD_TTL;
	}
	
	public boolean isActive() {
		return active;
	}

	public void disable(){
		clearActions();
		setVisible(false);
		active = false;
		activeTime = 0;
		//System.out.println("Disable the shield");
		
	}
	
	public void act(float dt){
		
		setPosition(spaceship.getX()+spaceship.getOriginX()-getOriginX(),spaceship.getY()+spaceship.getOriginY()-getOriginY());		
		
		//reduce the activation time until 0
		if (activeTime>0){
			activeTime -= dt;
			activeTime = Math.max(activeTime, 0);		
		}
		
		if(active && activeTime==0 ){
			disable();				
		}
		
		super.act(dt);
		
	}
	
	
	public void draw(Batch batch, float parentAlpha) {

		if(isVisible()){
						
			batch.setShader(ShaderProgram);
			ShaderProgram.setUniformf("randomSid", activeTime);
			ShaderProgram.setUniformf("intensity",    1f - Math.max(0f,  Math.abs((activeTime-(SHIELD_TTL/2f))/(SHIELD_TTL/2f)) *2f - 1f)   );	// va da 0 a 1 a 0 in 4 secondi 
			ShaderProgram.setUniformf("effectColor",-0.4f,0.3f,0.5f);			
			super.draw(batch, parentAlpha);			
			batch.setShader(null);
		}else{
			batch.setShader(null);
			super.draw(batch, parentAlpha);						
		}
		
	}
	
	public void bounce(PhysicsActor other){

		other.setVelocityXY(MathUtils.clamp(spaceship.getVelocity().x, 50, 200), MathUtils.clamp(spaceship.getVelocity().y, 50, 200));

	}	

}
