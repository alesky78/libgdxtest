package com.mygdx.game.balloonboster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Bird extends BaseActor {

	public float speed;
	public float amplitude;
	public float oscillation;
	public float initialY;
	public float time;
	public int offsetX;
	
	public boolean hit;

	public Bird(){
		super();

		speed = 60 * MathUtils.random(0.5f, 2.0f);
		amplitude = 50 * MathUtils.random(0.5f, 2.0f);
		oscillation = 0.01f * MathUtils.random(0.5f, 2.0f);
		initialY = 120 * MathUtils.random(0.5f, 2.0f);
		time = 0;
		offsetX = -100;
		hit = false;

		setTexture( new Texture( Gdx.files.internal("balloonboster/bird.png")) );
		// initial spawn location off-screen
		setX(offsetX);

	}

	public void setTexture(Texture t){
		setWidth(50);
		setHeight(50);
		region.setRegion( t );
		
		//set origin for the rotation
		setOrigin( getWidth()/2,getHeight()/2 );
	}
	
	public void hit(){
		
		if(!hit){
			Action kill = Actions.forever(
					Actions.rotateBy(360, 1) // rotation amount, duration
					);
			addAction( kill );
			hit = true;
		}


	}
	
	public void act(float dt){

		super.act(dt);
		time += dt;

		if(!hit){
			// set starting location to left of window
			float xPos = speed * time + offsetX;
			float yPos = amplitude * MathUtils.sin(oscillation * xPos) + initialY;
			setPosition( xPos, yPos );
		}else{
			setY(getY()-80*dt);
		}
	}

}
