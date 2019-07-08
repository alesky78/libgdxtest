package com.mygdx.game.spacerockemitter.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

public class SpaceRockEmitterChooseShip extends BaseScreen {

	protected int gamePhase;
	private float PHASE_TIMER = 0;	
	private final int PHASE_GAME_WAIT = 0;
	private final int PHASE_GAME_START = 1; 	
	
	private Texture backgroundTxt;
	
	private SpaceShip[] spaceShips;
	private int SpaceShipsIndex = 0;
	
	private Label name;	
	private ProgressBar acceleartion;
	private ProgressBar deceleration;
	private ProgressBar speed;	
	private Image shipImage;

	
	public SpaceRockEmitterChooseShip(SpaceRockEmitterGame g) {
		super(g);
	}

	@Override
	public void create() {

		spaceShips = new SpaceShip[3];
		
		//create all the ship here
		Texture shipTex = null;
		
		shipTex = game.assetManager.get(AssetCatalog.TEXTURE_SHIP_0);
		spaceShips[0] = new SpaceShip("xj-32", 200, 180, 200, shipTex, game.assetManager);
		
		shipTex = game.assetManager.get(AssetCatalog.TEXTURE_SHIP_1);;
		spaceShips[1] = new SpaceShip("cobra-mk1", 100, 200, 150, shipTex, game.assetManager);		
		
		shipTex = game.assetManager.get(AssetCatalog.TEXTURE_SHIP_2);
		spaceShips[2] = new SpaceShip("vertex-xt2", 150, 250, 220, shipTex, game.assetManager);		
		
		//create all the UI elements		
		Label title = new Label("Choose ship", game.skin, "title");	
		
		backgroundTxt = game.assetManager.get(AssetCatalog.TEXTURE_SPACE_BACKGROUND);
		TextureRegionDrawable background =  new TextureRegionDrawable(new TextureRegion(backgroundTxt));
		
		final TextButton selectAndStartGame = new TextButton("Select Ship", game.skin, "default");
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

		final TextButton changeShip = new TextButton("Change Ship", game.skin, "default");
		changeShip.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				SpaceShipsIndex++;
				valorizeFields();
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

			}
		});
		
		
		Label nameLabel = new Label("name:", game.skin, "default");
		Label accelerationLabel  = new Label("acceleration:", game.skin, "default");
		Label decelerationLabel  = new Label("deceleration:", game.skin, "default");
		Label speedLabel  = new Label("maxSpeed:", game.skin, "default");
		
		shipImage = new Image();
		name = new Label("", game.skin, "default");
		acceleartion = new ProgressBar(0, 1, 0.1f, false, game.skin, "default-horizontal");
		deceleration = new ProgressBar(0, 1, 0.1f, false, game.skin, "default-horizontal");
		speed = new ProgressBar(0, 1, 0.1f, false, game.skin, "default-horizontal");		

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
		
		name.setText(spaceShips[SpaceShipsIndex].getName());
		shipImage.setDrawable(new TextureRegionDrawable(spaceShips[SpaceShipsIndex].getTextureRegion()));  
		acceleartion.setValue(spaceShips[SpaceShipsIndex].getAccelerationRatio());
		deceleration.setValue(spaceShips[SpaceShipsIndex].getDecelerationRatio());
		speed.setValue(spaceShips[SpaceShipsIndex].getSpeedRatio());	
		
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
				SpaceRockEmitterLevel tl = new SpaceRockEmitterLevel(game,spaceShips[SpaceShipsIndex]);
				game.setScreen( tl );

			}
			
			PHASE_TIMER = PHASE_TIMER +dt;

		}

	}


	public void dispose() {
		super.dispose();
	}


}
