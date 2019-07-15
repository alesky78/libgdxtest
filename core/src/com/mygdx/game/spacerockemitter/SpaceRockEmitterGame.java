package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.spacerockemitter.screen.StartScene;

public class SpaceRockEmitterGame extends Game {

	// used to store resources common to multiple screens
	public UIManager uiManager;
	public AssetManager assetManager; 
	public AssetCatalog assetCatalog;
	public AudioManager audioManager;
	public DataManager dataManager;
	
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
		
		//load the game business data
		dataManager = new DataManager();
		dataManager.load();
		
		//start the main screen here
		StartScene scene = new StartScene(this);
        setScreen( scene );
	}
	
	public void dispose () {
		super.dispose();
		assetManager.dispose();
		uiManager.dispose();
	}		

}
