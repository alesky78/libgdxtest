package com.mygdx.game.ballbounce;


public class BallFlippingGame extends BaseGame {

	@Override
	public void create() {

		setScreen(new GameScreen(this));
	}

}
