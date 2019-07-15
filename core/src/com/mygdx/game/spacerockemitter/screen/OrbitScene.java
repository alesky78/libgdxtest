package com.mygdx.game.spacerockemitter.screen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.spacerockemitter.AssetCatalog;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;

public class OrbitScene extends BaseScreen {

	private final boolean MAIN_SCENE_DEBUG = false;
	private final boolean UI_TABLE_DEBUG = false;	
	
	
	public OrbitScene(SpaceRockEmitterGame g) {
		super(g);
	}

	@Override
	protected void create() {
		
		//prepare the ui components
		TextureRegionDrawable background =  new TextureRegionDrawable(new TextureRegion(game.assetManager.get(AssetCatalog.TEXTURE_OBLO_BACKGROUND)));
		
		TextButton navigation = game.uiManager.getTextButon("Navigation  ");
		navigation.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen( new NavigationScene(game) );	
				return true;
			}
		});		

		TextButton chooseShip = game.uiManager.getTextButon("Choose Ship");
		chooseShip.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen( new ChooseShip(game) );	
				return true;
			}
		});		
		
		//prepare the table
		uiTable.pad(5);
		uiTable.setBackground(background);
		uiTable.row();
		uiTable.add(navigation).left();
		uiTable.add().expandX();
		uiTable.row();
		uiTable.add(chooseShip).left();
		uiTable.add().expandX();		

		
		if(MAIN_SCENE_DEBUG) {
			mainStage.setDebugAll(MAIN_SCENE_DEBUG);
		}

		if(UI_TABLE_DEBUG) {
			uiStage.setDebugAll(UI_TABLE_DEBUG);
			uiTable.debugAll();
		}
		
	}

	@Override
	protected void update(float dt) {

	}

}
