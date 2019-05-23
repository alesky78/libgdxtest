package com.mygdx.game.balloonboster;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class LevelScreen extends BaseScreen {

	private BaseActor background;

	private float spawnTimer;
	private float spawnBirdTimer;
	private float spawnInterval;
	private float spawnBirdInterval;	
	private int popped;
	private int escaped;
	private int clickCount;

	private Label poppedLabel;
	private Label escapedLabel;
	private Label hitRatioLabel;

	public LevelScreen(Game g) {
		super(g);
	}

	@Override
	protected void create() {

		background = new BaseActor();
		background.setTexture( new Texture(Gdx.files.internal("balloonboster/sky.jpg")) );
		background.setPosition( 0, 0 );
		mainStage.addActor( background );

		spawnTimer = 0;
		spawnInterval = 0.8f;
		
		spawnBirdTimer = 0;
		spawnBirdInterval = 4f;

		// set up user interface
		BitmapFont font = new BitmapFont();
		LabelStyle style = new LabelStyle( font, Color.NAVY );

		popped = 0;
		poppedLabel = new Label( "Popped: 0", style );
		poppedLabel.setFontScale(2);
		poppedLabel.setPosition(20, 440);
		uiStage.addActor( poppedLabel );

		escaped = 0;
		escapedLabel = new Label( "Escaped: 0", style );
		escapedLabel.setFontScale(2);
		escapedLabel.setPosition(220, 440);
		uiStage.addActor( escapedLabel );

		clickCount = 0;
		hitRatioLabel = new Label( "Hit Ratio: ---", style );
		hitRatioLabel.setFontScale(2);
		hitRatioLabel.setPosition(420, 440);
		uiStage.addActor( hitRatioLabel );



	}

	public void addPopped(){
		popped++;
	}


	@Override
	protected void update(float dt) {

		spawnTimer += dt;
		spawnBirdTimer += dt;

		// check time for next balloon spawn
		if (spawnTimer > spawnInterval){

			spawnTimer -= spawnInterval;
			Balloon b = new Balloon();
			b.addListener(new BalloonInputListner(b, this));
			mainStage.addActor(b);
		}
		
		if (spawnBirdTimer > spawnBirdInterval){

			spawnBirdTimer -= spawnBirdInterval;
			Bird b = new Bird();
			b.addListener(new BirdInputListner(b, this));
			mainStage.addActor(b);
		}


		for ( Actor a : mainStage.getActors() ){
			if (a.getX() > viewWidth || a.getY() > viewHeight){
				escaped++;
				a.remove();
			}
		}

		// update user interface
		poppedLabel.setText( "Popped: " + popped );
		escapedLabel.setText( "Escaped: " + escaped );if ( clickCount > 0 )
		{
			int percent = (int)(100.0 * popped / clickCount);
			hitRatioLabel.setText( "Hit Ratio: " + percent + "%" );
		}

	}


	public boolean keyDown(int keycode){

		if (keycode == Keys.P){
			togglePaused();
			return true;
		}

		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		clickCount++;
		return false;
	}	

}
