package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.spacerockemitter.screen.MainMenu;

public class SpaceRockEmitterGame extends Game {

	// used to store resources common to multiple screens
	public UIManager uiManager;
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
		uiManager = new UIManager(assetManager);
		
		
		MainMenu scene = new MainMenu(this);
		//HyperSpaceMap scene = new HyperSpaceMap(this);
        setScreen( scene );
	}
	
	public void dispose () {
		super.dispose();
		assetManager.dispose();
		uiManager.dispose();
	}		

}
