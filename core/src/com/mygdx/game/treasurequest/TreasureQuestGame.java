package com.mygdx.game.treasurequest;

public class TreasureQuestGame extends BaseGame {

	@Override
	public void create() {

		TreasureQuestScreen screen = new TreasureQuestScreen(this);
		setScreen(screen);

	}

}
