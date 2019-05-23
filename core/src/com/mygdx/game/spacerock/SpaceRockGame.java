package com.mygdx.game.spacerock;

public class SpaceRockGame extends BaseGame {

	@Override
	public void create() {

		
		SpaceRockScreen tl = new SpaceRockScreen(this);
        setScreen( tl );
	}

}
