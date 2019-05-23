package com.mygdx.game.balloonboster;

import com.badlogic.gdx.Game;

public class BalloonBosterGame extends Game {

	@Override
	public void create() {
		LevelScreen screen = new LevelScreen(this);
		setScreen( screen );
	}

}
