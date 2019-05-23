package com.mygdx.game.spacerockparticle;

public class SpaceRockParticleGame extends BaseGame {

	@Override
	public void create() {

		
		SpaceRockScreen tl = new SpaceRockScreen(this);
        setScreen( tl );
	}

}
