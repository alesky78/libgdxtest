package com.mygdx.game.spacerockemitter.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;

public class PostProcesActor extends BaseActor{

	
	private boolean enable;
	private float intensity;
	private float activeTime;	
	private ShaderProgram ShaderProgram;
	
	
	public PostProcesActor(ShaderProgram shaderProgram) {
		super();
		enable = true;
		intensity = 0;
		activeTime = 0;
		ShaderProgram = shaderProgram;
	}	
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void act(float dt){
		activeTime+=dt;
		super.act( dt );
	}

	public void increaseIntensity() {
		intensity+= 0.001;
		intensity = MathUtils.clamp(intensity, 0f, 0.2f);
	}

	public void decreaseIntensity() {
		intensity-= 0.001;
		intensity = MathUtils.clamp(intensity, 0f, 0.2f);
	}
	
	public void draw(Batch batch, float parentAlpha) {

		if(enable && intensity!=0){
						
			batch.setShader(ShaderProgram);
			ShaderProgram.setUniformf("timePass", activeTime);
			ShaderProgram.setUniformf("intensity", intensity  ); 			
			super.draw(batch, parentAlpha);			
			batch.setShader(null);
			
		}else{
			batch.setShader(null);
			super.draw(batch, parentAlpha);						
		}
		
	}
	
}
