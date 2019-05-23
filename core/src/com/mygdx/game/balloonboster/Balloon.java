package com.mygdx.game.balloonboster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Balloon extends BaseActor {

	public float speed;
	public float amplitude;
	public float oscillation;
	public float initialY;
	public float time;
	public int offsetX;
	
	public int life;
	
	public Balloon(){
		super();
		
		speed = 60 * MathUtils.random(0.5f, 2.0f);
		amplitude = 50 * MathUtils.random(0.5f, 2.0f);
		oscillation = 0.01f * MathUtils.random(0.5f, 2.0f);
		initialY = 120 * MathUtils.random(0.5f, 2.0f);
		time = 0;
		offsetX = -100;
		life = 2;
		setTexture( new Texture( Gdx.files.internal("balloonboster/green-balloon.png")) );
		// initial spawn location off-screen
		setX(offsetX);
		
	}
	
	public void hit(){
		life--;
		if(life==1){
			setTexture( new Texture( Gdx.files.internal("balloonboster/red-balloon.png")) );
		}
		if(life==0){
			remove();			
		}
		

	}
	
	
	public void act(float dt){
		
		super.act(dt);
		time += dt;
		
		// set starting location to left of window
		float xPos = speed * time + offsetX;
		float yPos = amplitude * MathUtils.sin(oscillation * xPos) + initialY;
		setPosition( xPos, yPos );
	}
	
}
