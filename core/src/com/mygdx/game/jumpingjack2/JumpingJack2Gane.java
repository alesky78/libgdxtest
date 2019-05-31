package com.mygdx.game.jumpingjack2;

public class JumpingJack2Gane extends BaseGame {

	@Override
	public void create() {

		JumpingJack2Screen screen = new JumpingJack2Screen(this);
		setScreen(screen);

	}

}
