package com.mygdx.game.collision.hahsgrid;

import com.badlogic.gdx.Game;

public class GameHashGrid extends Game {

	@Override
	public void create() {
		ScreenSimulation simulation = new ScreenSimulation();
		setScreen(simulation);
	}

}
