package com.mygdx.game.spacerockemitter.screen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.spacerockemitter.AssetCatalog;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;

public class OrbitScreen extends BaseScreen {

	private final boolean MAIN_SCENE_DEBUG = false;
	private final boolean UI_TABLE_DEBUG = false;	
	
	
	public OrbitScreen(SpaceRockEmitterGame g) {
		super(g);
	}

	@Override
	protected void create() {
		
		//prepare the ui components
		TextureRegionDrawable background =  new TextureRegionDrawable(new TextureRegion(game.assetManager.get(AssetCatalog.TEXTURE_OBLO_BACKGROUND)));
		
		TextButton navigation = game.uiManager.getTextButon("Navigation");
		navigation.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen( new NavigationScreen(game) );	
				return true;
			}
		});		

		TextButton chooseShip = game.uiManager.getTextButon("Choose Ship");
		chooseShip.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen( new ChooseShipScreen(game) );	
				return true;
			}
		});		
		
		TextButton findJob = game.uiManager.getTextButon("Find Contract");
		findJob.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen( new FindContractScreen(game) );		
				return true;
			}
		});			
		
		
		//planet info
		TextureAtlas texture = game.assetManager.get(AssetCatalog.TEXTURE_ATLAS_PLANETS);
		Image planetImage = new Image(new TextureRegionDrawable(texture.findRegion(game.dataManager.getActualPlanet().getImage())));
		Label labelPlanetName = game.uiManager.getLabelDefault(game.dataManager.getActualPlanet().name);
		Label labelFactionName = game.uiManager.getLabelDefault(game.dataManager.getActualPlanet().faction);
		
		//prepare the table
		uiTable.pad(5);
		uiTable.setBackground(background);
		uiTable.row();
		uiTable.add().colspan(3).height(200).expandX();
		uiTable.row();		
		uiTable.add(navigation).left().width(200);
		uiTable.add(planetImage).center();
		uiTable.add().width(200);
		uiTable.row();
		uiTable.add(chooseShip).left().width(200);
		uiTable.add(labelPlanetName).center();
		uiTable.add().width(200);		
		uiTable.row();
		uiTable.add(findJob).left().width(200);
		uiTable.add(labelFactionName).center();
		uiTable.add().width(200);		
		uiTable.row();		
		uiTable.add().colspan(3).expandY();
		
		
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
