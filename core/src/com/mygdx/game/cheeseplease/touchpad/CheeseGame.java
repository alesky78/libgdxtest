package com.mygdx.game.cheeseplease.touchpad;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CheeseGame extends Game {

	public Skin skin;
	
	@Override
	public void create() {
		skin = new Skin();
		
		MenuScreen screen = new MenuScreen(this);
		setScreen( screen );
	}

	
	public void dispose () {
		super.dispose();
		skin.dispose();
	}
	
}
