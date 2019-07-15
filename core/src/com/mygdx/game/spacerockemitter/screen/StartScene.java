package com.mygdx.game.spacerockemitter.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.spacerockemitter.AssetCatalog;
import com.mygdx.game.spacerockemitter.AudioManager;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;

public class StartScene extends BaseScreen {

	private final boolean MAIN_SCENE_DEBUG = false;
	private final boolean UI_TABLE_DEBUG = false;	
	
	protected int gamePhase;
	private float PHASE_TIMER = 0;	
	private final int PHASE_GAME_WAIT = 0;
	private final int PHASE_GAME_START = 1; 	
	
	private Texture backgroundTxt;
	
	//audio
	private AudioManager audioManager;
			
	private float audioVolume;	
	
	public StartScene(SpaceRockEmitterGame g) {
		super(g);
	}

	@Override
	public void create() {

		audioVolume = 0.50f;
	
		audioManager = new AudioManager(audioVolume);
	
		//register music
		audioManager.registerAudio(AudioManager.MUSIC_MENU_LOOP, game.assetManager.get(AssetCatalog.MUSIC_MENU_LOOP), true);

		//register sound		
		audioManager.registerAudio(AudioManager.SOUND_GAME_ON, game.assetManager.get(AssetCatalog.SOUND_GAME_ON));
		
	
		Label title = game.uiManager.getLabelTitle("Space Rocker"); 
		
		backgroundTxt = game.assetManager.get(AssetCatalog.TEXTURE_SPACE_BACKGROUND);
		TextureRegionDrawable background =  new TextureRegionDrawable(new TextureRegion(backgroundTxt));
		
		final TextButton startGame = game.uiManager.getTextButon("start game");
		startGame.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				gamePhase = PHASE_GAME_START;
				PHASE_TIMER = 0;
				startGame.setDisabled(true);
			}
		});

		uiTable.pad(5);
		uiTable.setBackground(background);
		uiTable.add(title);
		uiTable.row();
		uiTable.add().height(100);
		uiTable.row();
		uiTable.add(startGame);

		if(MAIN_SCENE_DEBUG) {
			mainStage.setDebugAll(MAIN_SCENE_DEBUG);
		}

		if(UI_TABLE_DEBUG) {
			uiStage.setDebugAll(UI_TABLE_DEBUG);
			uiTable.debugAll();
		}

	}

	@Override
	public void update(float dt) {
		
		if(gamePhase == PHASE_GAME_WAIT){
			if(PHASE_TIMER == 0){
				audioManager.playMusic(AudioManager.MUSIC_MENU_LOOP);
			}
			
			PHASE_TIMER = PHASE_TIMER +dt;
			
		}else if(gamePhase == PHASE_GAME_START){	
			
			if(PHASE_TIMER == 0){
				audioManager.playSound(AudioManager.SOUND_GAME_ON);
				uiStage.addAction(Actions.fadeOut(2.0f));
			}
			
			audioVolume = MathUtils.clamp(audioVolume-dt, 0.0f, 1.0f); 
			audioManager.setMusicVolume(audioVolume);
			
			if(PHASE_TIMER > 2){

				audioManager.stopMusic(AudioManager.MUSIC_MENU_LOOP);
				OrbitScene tl = new OrbitScene(game);
				game.setScreen( tl );				
			}
			
			PHASE_TIMER = PHASE_TIMER +dt;

		}

	}


	public void dispose() {
		super.dispose();
	}


}
