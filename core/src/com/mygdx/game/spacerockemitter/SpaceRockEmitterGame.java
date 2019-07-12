package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.spacerockemitter.screen.HyperSpaceMap;

public class SpaceRockEmitterGame extends Game {

	// used to store resources common to multiple screens
	public Skin skin;
	public AssetManager assetManager; 
	public AssetCatalog assetCatalog;
	public AudioManager audioManager;
	
	@Override
	public void create() {
				
		assetCatalog = new AssetCatalog();
		assetCatalog.init();

		assetManager = new AssetManager();		
		audioManager = new AudioManager();
		
		assetCatalog.loadAssetsManagerQueue(assetManager);
		assetManager.finishLoading();

		//prepare the UI skin
		skin = assetManager.get(AssetCatalog.UI_JSON, Skin.class);
		
		
		//MainMenu scene = new MainMenu(this);
		HyperSpaceMap scene = new HyperSpaceMap(this);
        setScreen( scene );
	}
	
	public void dispose () {
		super.dispose();
		assetManager.dispose();
		skin.dispose();
	}		

}
