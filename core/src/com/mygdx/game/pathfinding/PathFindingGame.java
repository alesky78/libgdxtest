package com.mygdx.game.pathfinding;

import com.badlogic.gdx.Game;

public class PathFindingGame extends Game {

	@Override
	public void create() {
		PathFindingScreen screen = new PathFindingScreen(this);
		setScreen( screen );
	}

}
