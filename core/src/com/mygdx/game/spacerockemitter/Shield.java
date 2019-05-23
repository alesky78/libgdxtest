package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Shield extends PhysicsActor {

	private final int ROCK_MASS = 1;
	private final int SHIELD_TTL = 4;	

	private PhysicsActor spaceship;
	private ShaderProgram ShaderProgram;
	
	private boolean active;  
	
	private float activeTime = 0;	
	
	public Shield(PhysicsActor spaceship, ShaderProgram ShaderProgram){ 
		super();
		this.spaceship = spaceship;
		this.ShaderProgram = ShaderProgram;
		active = false;
		setScale(0f);			
	}

	public void activate(){
		clearActions();
		addAction(
					Actions.sequence(Actions.parallel(
													Actions.fadeIn(0.2f),
													Actions.scaleTo(0.1f, 0.1f, 0.2f)
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
		super.act(dt);
		setPosition(spaceship.getX()+spaceship.getOriginX()-getOriginX(),spaceship.getY()+spaceship.getOriginY()-getOriginY());
		
		//reduce the activation time until 0
		if (activeTime>0){
			activeTime -= dt;
			activeTime = Math.max(activeTime, 0);
			//System.out.println("activeTime"+ activeTime);		
			//System.out.println("intensity" + (   1f - Math.max(0f,  Math.abs((activeTime-(SHIELD_TTL/2f))/(SHIELD_TTL/2f)) *2f - 1f)   ));			
		}
		
		if(active && activeTime==0 ){
			disable();				
		}
		
	}


	
	public boolean overlaps(Rock rock, boolean bounceOff){

		if(!active){
			return false;
		}

		if (! rock.overlaps(this, true))
			return false;

		//if overlap then elastic collision only for the rock not for the shield and ship
		//Vector2 rockVelocity;
		
		if ( bounceOff ){
			//rockVelocity = new Vector2((2 * ROCK_MASS * spaceship.velocity.x) / (ROCK_MASS + ROCK_MASS), (2 * ROCK_MASS * spaceship.velocity.y) / (ROCK_MASS + ROCK_MASS));
			rock.setVelocityXY(MathUtils.clamp(spaceship.velocity.x, 50, 200), MathUtils.clamp(spaceship.velocity.y, 50, 200));
		}

		return true;
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

}
