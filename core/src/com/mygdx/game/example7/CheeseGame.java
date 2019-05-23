package com.mygdx.game.example7;

import com.badlogic.gdx.Game;

/**
 * 
 * add the game interface
 * 
 * @author id837836
 *
 */
public class CheeseGame extends Game {

	@Override
	public void create() {
		MenuScreen screen = new MenuScreen(this);
		setScreen( screen );
	}

}
