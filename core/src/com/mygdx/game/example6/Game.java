package com.mygdx.game.example6;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;


/**
 * 
 * move the camera
 * 
 * @author id837836
 *
 */
public class Game extends ApplicationAdapter {

	// game world dimensions
	final int mapWidth = 800;
	final int mapHeight = 800;
	// window dimensions
	final int viewWidth = 640;
	final int viewHeight = 480;
	
	//stages
	private Stage mainStage;
	private Stage uiStage;
	
	//object
	private AnimatetActor mousey;
	private BaseActor cheese;
	private BaseActor floor;
	private BaseActor winText;

	//labels
	private float timeElapsed;
	private Label timeLabel;
	private DecimalFormat df = new DecimalFormat("#.##");
	DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		
	
	boolean win;

	@Override
	public void create () {

		dfs.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(dfs);
		
		//UI OBJECT
		uiStage = new Stage();
		
		timeElapsed = 0;
		BitmapFont font = new BitmapFont();
		String text = "Time: 0";
		LabelStyle style = new LabelStyle( font, Color.NAVY );
		timeLabel = new Label( text, style );
		timeLabel.setFontScale(2);
		timeLabel.setPosition(500, 440);
		uiStage.addActor(timeLabel);
		
		winText = new BaseActor();
		winText.setTexture( new Texture(Gdx.files.internal("you-win.png")) );
		winText.setPosition( 170, 60 );
		winText.setVisible( false );
		uiStage.addActor( winText );
		
		//GAME OBJECTS
		mainStage = new Stage();

		floor = new BaseActor();
		floor.setTexture( new Texture(Gdx.files.internal("tiles-800-800.jpg")) );
		floor.setPosition( 0, 0 );
		mainStage.addActor( floor );

		cheese = new BaseActor();
		cheese.setTexture( new Texture(Gdx.files.internal("cheese.png")) );
		cheese.setPosition( 400, 300 );
		mainStage.addActor( cheese );

		//MOUSE
		TextureRegion[] frames = new TextureRegion[4];
		for (int n = 0; n < 4; n++)
		{
			String fileName = "mouse" + n + ".png";
			Texture tex = new Texture(Gdx.files.internal(fileName));
			tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			frames[n] = new TextureRegion( tex );
		}
		Array<TextureRegion> framesArray = new Array<TextureRegion>(frames);
		Animation<TextureRegion> anim = new Animation<TextureRegion>(0.1f, framesArray, Animation.PlayMode.LOOP_PINGPONG);		

		mousey = new AnimatetActor();
		mousey.setAnimation(anim);
		mousey.setPosition( 20, 20 );
		mainStage.addActor( mousey );

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

		// update game
		float dt = Gdx.graphics.getDeltaTime();
		mainStage.act(dt);
		uiStage.act(dt);

		//correct mouse coordinate clamping by the world size
		mousey.setX( MathUtils.clamp( mousey.getX(), 0, mapWidth - mousey.getWidth() ));
		mousey.setY( MathUtils.clamp( mousey.getY(), 0, mapHeight - mousey.getHeight() ));
		
		if(!winText.isVisible()){
			timeElapsed += dt;
			timeLabel.setText( "Time: "+ df.format(timeElapsed) );
		}

		
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
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//draw main stage and centre the camera
		Camera cam = mainStage.getCamera();
		cam.position.set( mousey.getX() + mousey.getOriginX(),mousey.getY() + mousey.getOriginY(), 0 );
		cam.position.x = MathUtils.clamp(cam.position.x, viewWidth/2, mapWidth - viewWidth/2);
		cam.position.y = MathUtils.clamp(cam.position.y, viewHeight/2, mapHeight - viewHeight/2);
		cam.update();		
		mainStage.draw();
		
		
		uiStage.draw();
		
	}

	@Override
	public void dispose () {
	}
}
