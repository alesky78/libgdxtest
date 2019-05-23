package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SpaceRockEmitterMenu extends BaseScreen {

	protected int gamePhase;
	private float PHASE_TIMER = 0;	
	private final int PHASE_GAME_WAIT = 0;
	private final int PHASE_GAME_START = 1; 	
	
	private Texture backgroundTxt;
	private Music entryLoop;
	private Sound gameOnSound;		
	private float audioVolume;	
	
	public SpaceRockEmitterMenu(BaseGame g) {
		super(g);
	}

	@Override
	public void create() {

		audioVolume = 0.50f;
		
		entryLoop = Gdx.audio.newMusic(Gdx.files.internal("spacerockemitter/entry_loop.wav"));
		entryLoop.setLooping(true);
		entryLoop.setVolume(audioVolume);

		
		gameOnSound = Gdx.audio.newSound(Gdx.files.internal("spacerockemitter/game on.wav"));
		
		Label title = new Label("Space Rocker", game.skin, "title");	
		
		backgroundTxt = new Texture(Gdx.files.internal("spacerockemitter/space.png"));
		TextureRegionDrawable background =  new TextureRegionDrawable(new TextureRegion(backgroundTxt));
		
		final TextButton startGame = new TextButton("start game", game.skin, "default");
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

	}

	@Override
	public void update(float dt) {
		
		if(gamePhase == PHASE_GAME_WAIT){
			if(PHASE_TIMER == 0){
				entryLoop.play();
			}
			
			PHASE_TIMER = PHASE_TIMER +dt;
			
		}else if(gamePhase == PHASE_GAME_START){	
			
			if(PHASE_TIMER == 0){
				gameOnSound.play();
				uiStage.addAction(Actions.fadeOut(2.0f));
			}
			
			audioVolume = MathUtils.clamp(audioVolume-dt, 0.0f, 1.0f); 
			entryLoop.setVolume(MathUtils.clamp(audioVolume-dt, 0.0f, 1.0f));	
			
			if(PHASE_TIMER > 2){
				SpaceRockEmitterLevel tl = new SpaceRockEmitterLevel(game);
				game.setScreen( tl );
				entryLoop.pause();
				dispose();				
			}
			
			PHASE_TIMER = PHASE_TIMER +dt;

		}

	}


	public void dispose() {
		super.dispose();
		backgroundTxt.dispose();
		entryLoop.dispose();

	}


}
