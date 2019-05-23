package com.mygdx.game.planedodger;

public class PlaneDodgerGame extends BaseGame {

	@Override
	public void create() {
		setScreen(new PlaneDodgerScreen(this));
	}

}
