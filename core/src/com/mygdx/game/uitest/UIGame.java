package com.mygdx.game.uitest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UIGame extends Game {

	public Skin skin;
	
	@Override
	public void create() {

		skin = new Skin();
		
		//LOAD THE UI		
		AssetManager manager = new AssetManager();
		manager.load("uitest/quantum-horizon-ui.atlas", TextureAtlas.class);
		manager.finishLoading();
		TextureAtlas atlas = manager.get("uitest/quantum-horizon-ui.atlas", TextureAtlas.class);
		
		skin = new Skin(Gdx.files.internal("uitest/quantum-horizon-ui.json"), atlas);
		
		
		//start screen
		setScreen(new UIScreen(this));
		
	}

	
	public void dispose () {
		super.dispose();
		skin.dispose();
		
	}
	
	
}
