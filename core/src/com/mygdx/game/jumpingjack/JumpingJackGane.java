package com.mygdx.game.jumpingjack;

public class JumpingJackGane extends BaseGame {

	@Override
	public void create() {

		JumpingJackScreen screen = new JumpingJackScreen(this);
		setScreen(screen);

	}

}
