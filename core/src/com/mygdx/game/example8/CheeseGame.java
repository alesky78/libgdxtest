package com.mygdx.game.example8;

import com.badlogic.gdx.Game;

public class CheeseGame extends Game {

	@Override
	public void create() {
		MenuScreen screen = new MenuScreen(this);
		setScreen( screen );
	}

}
