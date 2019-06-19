package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SpaceRockEmitterGame extends Game {

	// used to store resources common to multiple screens
	public Skin skin;
	public AssetManager assetManager; 
	
	@Override
	public void create() {

		skin = new Skin();
		
		//LOAD THE UI		
		assetManager = new AssetManager();
		assetManager.load("spacerockemitter/quantum-horizon-ui.atlas", TextureAtlas.class);
		assetManager.finishLoading();
		
		TextureAtlas atlas = assetManager.get("spacerockemitter/quantum-horizon-ui.atlas", TextureAtlas.class);
		skin = new Skin(Gdx.files.internal("spacerockemitter/quantum-horizon-ui.json"), atlas);
		
		SpaceRockEmitterMenu scene = new SpaceRockEmitterMenu(this);
        setScreen( scene );
	}
	
	public void dispose () {
		super.dispose();
		assetManager.dispose();
		skin.dispose();
	}		

}
