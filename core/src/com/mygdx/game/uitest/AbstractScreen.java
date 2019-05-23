package com.mygdx.game.uitest;

import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen {

	protected UIGame game;
	
	public AbstractScreen(UIGame g){
		game = g;
	}
	
	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {


	}

	@Override
	public void resize(int width, int height) {


	}

	@Override
	public void pause() {


	}

	@Override
	public void resume() {


	}

	@Override
	public void hide() {


	}

	public void dispose () {
		
		
	}	

}
