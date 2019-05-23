package com.mygdx.game.example3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


/**
 * 
 * use of the Action over the actor
 * 
 * @author id837836
 *
 */
public class Game extends ApplicationAdapter {
	
	public Stage mainStage;
	private BaseActor mousey;
	private BaseActor cheese;
	private BaseActor floor;
	private BaseActor winText;
	
	boolean win;
	
	@Override
	public void create () {
		
		mainStage = new Stage();
		
		floor = new BaseActor();
		floor.setTexture( new Texture(Gdx.files.internal("tiles.jpg")) );
		floor.setPosition( 0, 0 );
		mainStage.addActor( floor );
		
		cheese = new BaseActor();
		cheese.setTexture( new Texture(Gdx.files.internal("cheese.png")) );
		cheese.setPosition( 400, 300 );
		mainStage.addActor( cheese );
	
		mousey = new BaseActor();
		mousey.setTexture( new Texture(Gdx.files.internal("mouse.png")) );
		mousey.setPosition( 20, 20 );
		mainStage.addActor( mousey );
		
		winText = new BaseActor();
		winText.setTexture( new Texture(Gdx.files.internal("you-win.png")) );
		winText.setPosition( 170, 60 );
		winText.setVisible( false );
		mainStage.addActor( winText );
		
	}

	@Override
	public void render () {
		
		// process input
		mousey.velocityX = 0;
		mousey.velocityY = 0;
		if (Gdx.input.isKeyPressed(Keys.LEFT))
		mousey.velocityX -= 200;
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
		mousey.velocityX += 200;
		if (Gdx.input.isKeyPressed(Keys.UP))
		mousey.velocityY += 200;
		if (Gdx.input.isKeyPressed(Keys.DOWN))
		mousey.velocityY -= 200;
		
		// update
		float dt = Gdx.graphics.getDeltaTime();
		mainStage.act(dt);
		
		
		// check win condition: mousey must be overlapping cheese
		Rectangle cheeseRectangle = cheese.getBoundingRectangle();
		Rectangle mouseyRectangle = mousey.getBoundingRectangle();
		
		if(!winText.isVisible() && cheeseRectangle.contains(mouseyRectangle)){
			
			Action spinShrinkFadeOut = Actions.parallel(
													Actions.alpha(1), // set transparency value
													Actions.rotateBy(360, 1), // rotation amount, duration
													Actions.scaleTo(0, 0, 1), // x amount, y amount, duration
													Actions.fadeOut(1) // duration of fade out
													);
			cheese.addAction( spinShrinkFadeOut );
			
			Action fadeInColorCycleForever = Actions.sequence(
												Actions.alpha(0), // set transparency value
												Actions.show(), // set visible to true
												Actions.fadeIn(1), // duration of fade in
												Actions.forever(
													Actions.sequence(
														// color shade to approach, duration
														Actions.color( new Color(1,0,0,1), 1 ),
														Actions.color( new Color(0,0,1,1), 1 )
													)
												)							
											);
			winText.addAction( fadeInColorCycleForever );
			
		}
		
		// clear screen and draw graphics
		Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		mainStage.draw();
	}
	
	@Override
	public void dispose () {
	}
}
