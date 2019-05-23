package com.mygdx.game.starfield;

import com.badlogic.gdx.Game;

public class UIGame extends Game {

	
	@Override
	public void create() {

		//start screen
		setScreen(new UIScreen(this));
		
	}

	
	public void dispose () {
		super.dispose();
		
	}
	
	
}
