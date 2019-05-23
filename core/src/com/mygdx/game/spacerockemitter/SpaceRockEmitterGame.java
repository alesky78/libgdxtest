package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SpaceRockEmitterGame extends BaseGame {

	@Override
	public void create() {

		skin = new Skin();
		
		//LOAD THE UI		
		AssetManager manager = new AssetManager();
		manager.load("spacerockemitter/quantum-horizon-ui.atlas", TextureAtlas.class);
		manager.finishLoading();
		TextureAtlas atlas = manager.get("spacerockemitter/quantum-horizon-ui.atlas", TextureAtlas.class);
		
		skin = new Skin(Gdx.files.internal("spacerockemitter/quantum-horizon-ui.json"), atlas);
		
		SpaceRockEmitterMenu scene = new SpaceRockEmitterMenu(this);
        setScreen( scene );
	}

}
