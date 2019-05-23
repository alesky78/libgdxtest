package com.mygdx.game.cardpickup;


public class CardPickUpGame extends BaseGame {

	@Override
	public void create() {

		setScreen(new GameScreen(this));
	}

}
