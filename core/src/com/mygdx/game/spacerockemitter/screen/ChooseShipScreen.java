package com.mygdx.game.spacerockemitter.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.spacerockemitter.AssetCatalog;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;
import com.mygdx.game.spacerockemitter.actor.SpaceShip;
import com.mygdx.game.spacerockemitter.data.ShipData;

public class ChooseShipScreen extends BaseScreen {

	protected int gamePhase;
	private float PHASE_TIMER = 0;	
	private final int PHASE_GAME_WAIT = 0;
	private final int PHASE_GAME_START = 1; 	
	
	private Texture backgroundTxt;
	
	private ShipData[] spaceShips;
	private int SpaceShipsIndex = 0;
	
	private Label name;	
	private ProgressBar acceleartion;
	private ProgressBar deceleration;
	private ProgressBar speed;	
	private Image shipImage;

	
	public ChooseShipScreen(SpaceRockEmitterGame g) {
		super(g);
	}

	@Override
	public void create() {

		spaceShips = new ShipData[game.dataManager.ships.size()];
		
		ShipData shipData;
		for (int i = 0; i < spaceShips.length; i++) {
			spaceShips[i] = game.dataManager.ships.get(i);
		}
		
		//create all the UI elements		
		Label title = game.uiManager.getLabelTitle("Choose ship");	
		
		backgroundTxt = game.assetManager.get(AssetCatalog.TEXTURE_SPACE_BACKGROUND);
		TextureRegionDrawable background =  new TextureRegionDrawable(new TextureRegion(backgroundTxt));
		
		final TextButton selectAndStartGame = game.uiManager.getTextButon("Select Ship");
		selectAndStartGame.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				gamePhase = PHASE_GAME_START;
				PHASE_TIMER = 0;
				selectAndStartGame.setDisabled(true);
			}
		});

		final TextButton changeShip = game.uiManager.getTextButon("Change Ship"); 
		changeShip.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				SpaceShipsIndex++;
				valorizeFields();
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

			}
		});
		
		
		Label nameLabel = game.uiManager.getLabelDefault("name:"); 
		Label accelerationLabel  =  game.uiManager.getLabelDefault("acceleration:");  
		Label decelerationLabel  =  game.uiManager.getLabelDefault("deceleration:");  
		Label speedLabel  = game.uiManager.getLabelDefault("maxSpeed:");  
		
		shipImage = new Image();
		name = game.uiManager.getLabelDefault("");  
		acceleartion = game.uiManager.getProgressBar(0f, 1f, 0.1f);		
		deceleration = game.uiManager.getProgressBar(0f, 1f, 0.1f);
		speed = game.uiManager.getProgressBar(0f, 1f, 0.1f);		

		valorizeFields();
		
		//compose the table UI
		uiTable.setDebug(false);
		uiTable.pad(5);
		uiTable.setBackground(background);
		uiTable.add(title).colspan(2);
		uiTable.row();
		uiTable.add(shipImage).colspan(2).pad(10).expandY();
		uiTable.row();
		uiTable.add(nameLabel).right();
		uiTable.add(name);
		uiTable.row();
		uiTable.add(accelerationLabel).right();
		uiTable.add(acceleartion);
		uiTable.row();
		uiTable.add(decelerationLabel).right();
		uiTable.add(deceleration);
		uiTable.row();
		uiTable.add(speedLabel).right();
		uiTable.add(speed);
		uiTable.row().pad(50);		
		uiTable.add(selectAndStartGame);
		uiTable.add(changeShip);		
		

	}

	/**
	 * valorize the field of the ui when the ship change and manage the index
	 */
	private void valorizeFields() {
		//verify if the index is valid or restart from the first ship
		if(SpaceShipsIndex > spaceShips.length-1){
			SpaceShipsIndex = 0;
		}
		
		name.setText(spaceShips[SpaceShipsIndex].name);
		acceleartion.setValue(game.dataManager.getAccelerationRatio(spaceShips[SpaceShipsIndex].acceleration));
		deceleration.setValue(game.dataManager.getDecelerationRatio(spaceShips[SpaceShipsIndex].deceleration));
		speed.setValue(game.dataManager.getSpeedRatio(spaceShips[SpaceShipsIndex].maxSpeed));		
		
		AtlasRegion region = game.assetManager.get(AssetCatalog.TEXTURE_ATLAS_SPACESHIPS).findRegion(spaceShips[SpaceShipsIndex].shipTex); 
		
		shipImage.setDrawable(new TextureRegionDrawable(region));  
	
		
	}

	@Override
	public void update(float dt) {
		
		if(gamePhase == PHASE_GAME_WAIT){
			if(PHASE_TIMER == 0){
			}
			
			PHASE_TIMER = PHASE_TIMER +dt;
			
		}else if(gamePhase == PHASE_GAME_START){	
			
			if(PHASE_TIMER == 0){
				uiStage.addAction(Actions.fadeOut(2.0f));
			}
			 
			
			if(PHASE_TIMER > 2){
				//prepare the ship configured and send it
				SpaceShip ship = new SpaceShip(spaceShips[SpaceShipsIndex], game.assetManager);
				DestoryAsteroidLevel tl = new DestoryAsteroidLevel(game,ship);
				game.setScreen( tl );

			}
			
			PHASE_TIMER = PHASE_TIMER +dt;

		}

	}


	public void dispose() {
		super.dispose();
	}


}
