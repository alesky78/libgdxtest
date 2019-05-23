package com.mygdx.game.cheeseplease.touchpad;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.utils.Array;


/**
 * 
 * use of the multiple stages
 * one for game object one for UI
 * 
 * @author id837836
 *
 */
public class LevelScreen extends BaseScreen {

	// game world dimensions
	final int mapWidth = 1024;
	final int mapHeight = 1024;


	//object
	private AnimatetActor mousey;
	private BaseActor cheese;
	private BaseActor floor;
	private BaseActor winText;

	//labels
	private Table uiTable;
	private float timeElapsed;
	private Label timeLabel;
	private DecimalFormat df = new DecimalFormat("0.00");

	//touchpad
	private Touchpad touchPad;

	boolean win;

	public LevelScreen(CheeseGame g){
		super(g);
	}


	public void create () {

		
		
		//TOUCHPAD
		TouchpadStyle touchStyle = new TouchpadStyle();
		Texture padKnobTex = new Texture(Gdx.files.internal("cheeseplease/touchpad/joystick-knob.png"));
		game.skin.add("padKnobImage", padKnobTex );
		touchStyle.knob = game.skin.getDrawable("padKnobImage");
		Texture padBackTex = new Texture(Gdx.files.internal("cheeseplease/touchpad/joystick-bg.png"));
		game.skin.add("padBackImage", padBackTex );
		touchStyle.background = game.skin.getDrawable("padBackImage");
		touchPad = new Touchpad(5, touchStyle);		

		//PAUSE
		Texture pauseTexture = new Texture(Gdx.files.internal("cheeseplease/touchpad/pause.png"));
		game.skin.add("pauseImage", pauseTexture );
		ButtonStyle pauseStyle = new ButtonStyle();
		pauseStyle.up = game.skin.getDrawable("pauseImage");
		Button pauseButton = new Button( pauseStyle );
		pauseButton.addListener(
				new InputListener()
				{
					public boolean touchDown (InputEvent event, float x, float y, int pointer,
							int button)
					{
						togglePaused();
						return true;
					}
				});		

		//UI OBJECT
		timeElapsed = 0;
		BitmapFont font = new BitmapFont();
		String text = "Time: 0";
		LabelStyle style = new LabelStyle( font, Color.NAVY );
		timeLabel = new Label( text, style );
		timeLabel.setFontScale(2);

		winText = new BaseActor();
		winText.setTexture( new Texture(Gdx.files.internal("cheeseplease/touchpad/you-win.png")) );
		winText.setVisible( false );
		uiStage.addActor( winText );

		
		uiTable = new Table();
		uiStage.addActor(uiTable);		
		uiTable.setFillParent(true);
		uiTable.setDebug(false);
		
		uiTable.add(timeLabel).right().pad(10).width(200);
		uiTable.row().expandX();
		uiTable.add(winText).padTop(50);
		uiTable.row();
		uiTable.add().expandY();
		uiTable.row();
		
		Table controlTable = new Table();
		controlTable.pad(25);
		Texture controlTex = new Texture(Gdx.files.internal("cheeseplease/touchpad/pixels-white.png"), true);
		game.skin.add( "controlTex", controlTex );
		controlTable.background( game.skin.getTiledDrawable("controlTex") );
		controlTable.add(touchPad);
		controlTable.add().expandX();
		controlTable.add(pauseButton);
		uiTable.add(controlTable).width(600).height(200);		
		
		//GAME OBJECTS
		floor = new BaseActor();
		floor.setTexture( new Texture(Gdx.files.internal("cheeseplease/touchpad/tiles-1024-1024.jpg")) );
		floor.setPosition( 0, 0 );
		mainStage.addActor( floor );

		cheese = new BaseActor();
		cheese.setTexture( new Texture(Gdx.files.internal("cheeseplease/touchpad/cheese.png")) );
		cheese.setPosition( 600, 600 );
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
	public void update (float dt) {

		// process input
		mousey.velocityX = 0;
		mousey.velocityY = 0;

		//mouse speed by touchpad
		mousey.setVelocity(touchPad.getKnobPercentX() * 200, touchPad.getKnobPercentY() * 200 );

		if (Gdx.input.isKeyPressed(Keys.M))
			game.setScreen( new MenuScreen(game) );


		// update game
		mainStage.act(dt);
		uiStage.act(dt);

		//correct mouse coordinate clamping by the world size
		mousey.setX( MathUtils.clamp( mousey.getX(), 0, mapWidth - mousey.getWidth() ));
		mousey.setY( MathUtils.clamp( mousey.getY(), 0, mapHeight - mousey.getHeight() ));

		if(!winText.isVisible()){
			timeElapsed += dt;
			timeLabel.setText( "Time: " +  Float.parseFloat(df.format(timeElapsed)) );
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

		//draw main stage and centre the camera
		Camera cam = mainStage.getCamera();
		cam.position.set( mousey.getX() + mousey.getOriginX(),mousey.getY() + mousey.getOriginY(), 0 );
		cam.position.x = MathUtils.clamp(cam.position.x, viewWidth/2, mapWidth - viewWidth/2);
		cam.position.y = MathUtils.clamp(cam.position.y, viewHeight/2, mapHeight - viewHeight/2);
		cam.update();		

	}


	public boolean keyDown(int keycode){

		if (keycode == Keys.M)
			game.setScreen( new MenuScreen(game) );
		if (keycode == Keys.P)
			togglePaused();
		if (keycode == Keys.R)
			game.setScreen( new LevelScreen(game) );
		
		return false;
	}	

	@Override
	public void dispose () {}

	@Override
	public void show() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

}
