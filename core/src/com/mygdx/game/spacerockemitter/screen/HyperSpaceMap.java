package com.mygdx.game.spacerockemitter.screen;

import com.mygdx.game.spacerockemitter.AssetCatalog;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;
import com.mygdx.game.spacerockemitter.actor.BaseActor;

public class HyperSpaceMap extends BaseScreen {

	
	public HyperSpaceMap(SpaceRockEmitterGame g) {
		super(g);
	}
	
	
	@Override
	protected void create() {
		
		
		//background immage
		BaseActor backgroundMap = new BaseActor();
		backgroundMap.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_MAP));
		backgroundMap.setPosition(0, 0);
		mainStage.addActor(backgroundMap);
		

	}

	@Override
	protected void update(float dt) {


	}

}
