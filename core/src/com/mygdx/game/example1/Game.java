package com.mygdx.game.example1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


/**
 * example use of Sprite and Texture with SpriteBatch
 * 
 * 
 * @author id837836
 *
 */
public class Game extends ApplicationAdapter {
	
	SpriteBatch batch;
	
	Sprite mouseySprite;
	Sprite cheeseSprite;
	
	Texture floorTexture;
	Texture winMessage;	
	
	boolean win;
	
	@Override
	public void create () {
		batch = new SpriteBatch();		
		
		mouseySprite = new Sprite(new Texture( Gdx.files.internal("mouse.png")));
		mouseySprite.setPosition(10, 10);
		
		cheeseSprite = new Sprite(new Texture( Gdx.files.internal("cheese.png") ));
		cheeseSprite.setPosition(400, 350);
		
		floorTexture = new Texture( Gdx.files.internal("tiles.jpg") );
		winMessage = new Texture( Gdx.files.internal("you-win.png") );
		win = false;
	
	}

	@Override
	public void render () {
		
		// check user input
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			mouseySprite.translateX( -1 );
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			mouseySprite.translateX( 1 );
		if (Gdx.input.isKeyPressed(Keys.UP))
			mouseySprite.translateY( 1 );
		if (Gdx.input.isKeyPressed(Keys.DOWN))
			mouseySprite.translateY( -1 );
		
		// check win condition: mousey must be overlapping cheese
		Rectangle cheeseRectangle = cheeseSprite.getBoundingRectangle();
		Rectangle mouseyRectangle = mouseySprite.getBoundingRectangle();
		
		if(cheeseRectangle.contains(mouseyRectangle)){
			win = true;
		}
		
		// clear screen and draw graphics
		Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw( floorTexture, 0, 0 );
		cheeseSprite.draw( batch );
		mouseySprite.draw( batch );
		if (win)
			batch.draw( winMessage, 170, 60 );
		batch.end();
	}
	
	@Override
	public void dispose () {
	}
}
