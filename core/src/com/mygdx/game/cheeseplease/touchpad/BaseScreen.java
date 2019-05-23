package com.mygdx.game.cheeseplease.touchpad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class BaseScreen implements Screen, InputProcessor {

	protected CheeseGame game;

	protected Stage mainStage;
	protected Stage uiStage;

	public final int viewWidth = 600;
	public final int viewHeight = 600;
	public final int uiWidth = 600;
	public final int uiHeight = 800;
	
	private int screenWidth;
	private int screenHeight;
	

	private boolean paused;

	public BaseScreen(CheeseGame g){

		
		game = g;

		mainStage = new Stage( new FitViewport(viewWidth, viewHeight) );
		uiStage = new Stage( new FitViewport(uiWidth, uiHeight) );

		paused = false;

		InputMultiplexer im = new InputMultiplexer(this, uiStage, mainStage);
		Gdx.input.setInputProcessor( im );

		create();
	}

	protected abstract void create();

	protected abstract void update(float dt);	


	public void render(float dt){
		
		uiStage.act(dt);
		// only pause gameplay events, not UI events
		if ( !isPaused() ){
			mainStage.act(dt);
			update(dt);
		}
		
		// render
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		Vector2 scaled = Scaling.fit.apply(viewWidth, viewHeight, screenWidth, (int)(screenHeight*0.75));
		int viewportWidth = Math.round(scaled.x);
		int viewportHeight = Math.round(scaled.y);
		mainStage.getViewport().setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight), viewportWidth, viewportHeight);
		mainStage.getViewport().apply();
		mainStage.draw();
		

		uiStage.getViewport().update(screenWidth, screenHeight); 
		uiStage.draw();
	}	

	// pause methods
	public boolean isPaused(){ 
		return paused;
	}
	
	public void setPaused(boolean b){ 
		paused = b; 
	}
	
	public void togglePaused(){ 
		paused = !paused; 
	}	
	
	@Override
	public void show()  {}


	public void resize(int width, int height)  {
		
		screenWidth = width;
		screenHeight = height;
		
	}

	@Override
	public void pause()  {}

	@Override
	public void resume()  {}

	@Override
	public void hide()  {}

	@Override
	public void dispose() {}


	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(int amount) {

		return false;
	}



}
