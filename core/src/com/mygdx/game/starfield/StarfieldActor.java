package com.mygdx.game.starfield;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class StarfieldActor extends Actor {

	
	public TextureRegion region;
	private ShaderProgram ShaderProgram;	
	private boolean activateShader = false;
	
	public void setTexture(Texture t){
		region = new TextureRegion();		
		int w = t.getWidth();
		int h = t.getHeight();
		setWidth( w );
		setHeight( h );
		region.setRegion( t );
		setOriginCenter();
	}
	
	public void setShaderProgram(ShaderProgram shaderProgram) {
		ShaderProgram = shaderProgram;
	}

	public void setOriginCenter(){
		if ( getWidth() == 0 )
			System.err.println("error: actor size not set");
		setOrigin( getWidth()/2, getHeight()/2 );
	}
	
	
	public void enableShader(){
		activateShader = true;
	}

	public void disableShader(){
		activateShader = false;
	}
	
	
	public void act(float dt){
		super.act( dt );
		timePass += dt;
	}
	
	private float timePass = 0;
	
	public void draw(Batch batch, float parentAlpha) {
		
		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a);
		if ( isVisible() ){
			if(activateShader){
				batch.setShader(ShaderProgram);
				ShaderProgram.setUniformf("timePass", timePass);
				//ShaderProgram.setUniformf("intensity", 0.05f);
			}
			
			batch.draw( region, getX(), getY(), getOriginX(), getOriginY(),getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
			super.draw(batch, parentAlpha);			
			
			if(activateShader){
				batch.setShader(null);				
			}			
		}
	}


}
