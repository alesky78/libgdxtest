package com.mygdx.game.example9;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;


/**
 * Interesting example
 * 
 * slip the screen in two parts working with 2 different viewport,
 * 
 * the implementation change in the render method, because we have to declare the viewport before the draw 2 times
 * one for each stage
 * 
 * moreover in the LevelScreen is implemented also the zoom by the Z and X key only on the frame of the game
 * 
 * @author id837836
 *
 */
public abstract class BaseScreen implements Screen, InputProcessor {

	protected Game game;

	protected Stage mainStage;
	protected Stage uiStage;

	public int viewWidth = 640;
	public int viewHeight = 480;

	private boolean paused;


	private boolean testByGL = false;	// show here how to work directly wiht the GL or wiht the stage

	public BaseScreen(Game g){

		game = g;

		mainStage = new Stage( new FitViewport(viewWidth, viewHeight) );
		uiStage = new Stage( new FitViewport(viewWidth, viewHeight) );

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

		//////////////////////////////////////
		//DROW here the viewport on the left
		if(!testByGL){
			mainStage.getViewport().setScreenBounds(0, 0 , width/2, height);
			mainStage.getViewport().apply();	//call the glViewport
		}else{
			Gdx.gl.glViewport(0, 0 , width/2, height);			
		}

		mainStage.draw();	//draw the stage after we set its viewport

		
		//////////////////////////////////////
		//DROW here the viewport on the right
		if(!testByGL){
			uiStage.getViewport().setScreenBounds(width/2, 0 , width/2, height);
			uiStage.getViewport().apply();		//call the glViewport
		}else{
			Gdx.gl.glViewport(width/2, 0 , width/2, height);			
		}

		uiStage.draw();	//draw the stage after we set its viewport
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


	int width; 
	int height;

	public void resize(int width, int height)  {
		this.width = width;
		this.height = height;

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
