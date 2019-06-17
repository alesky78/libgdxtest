package com.mygdx.game.spacerockemitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SpaceRockEmitterLevel extends BaseScreen {

	// activate the graphic DEBUG
	private final boolean MAIN_SCENE_DEBUG = false;
	private final boolean UI_TABLE_DEBUG = false;	

	protected int gamePhase;
	private float PHASE_TIMER = 0;	
	private final int PHASE_WARNING = 0;
	private final int PHASE_PREPARE_WAVE = 1;    
	private final int PHASE_GAME_ON = 2;
	private final int PHASE_WAVE_DESTROIED = 3;    	
	private final int PHASE_GAME_OVER = 4;
	private final int PHASE_GAME_EXIT = 5;   	
	private final int PHASE_PLAYER_DESTROIED = 6;	

	private BackGroundWrapAround background;
	private SpaceShip spaceship;

	private PhysicsActor baseLaser;
	private ParticleActor laserParticle;	

	private ParticleActor baseExplosion;	

	private ArrayList<PhysicsActor> laserList;
	private ArrayList<Rock> rockList;
	private List<Rock> newRocks = new ArrayList<Rock>();	
	private ArrayList<BaseActor> removeList;

	private Texture[] rockTexture;

	//LIGHT SIMULATION
	private Texture light;
	private SpriteBatch batch;

	//CAMERA SHAKE	 
	private float shakeCameraAngle = 0;
	private float shakeCameraDuration = 0.5f;	
	private float shakeCameraDurationCounter = 0;	
	private boolean shakeCamera = false;	
		
	
	//UI
	private Label labelPoints;
	private Label labelWarning;
	private Image[] immageLife;	

	//GAME DATA
	private int points = 0;
	private int wave = 0;	
	private int life = 3;

	//AUDIO
	private float audioVolume;
	private Sound laserSound;
	private Sound explosionSound;
	private Sound warningSound;
	private Sound gameOverSound;		
	private Music spaceLoop;


	// game world dimensions and data
	final int mapWidth = 800;
	final int mapHeight = 600;
    
	
	public SpaceRockEmitterLevel(BaseGame g, SpaceShip spaceship){
		super();
		setGame(g);
		this.spaceship = spaceship;
		create();
	}


	public void dispose() {
		super.dispose();

		spaceship.dispose();

		Map<String,Animation<TextureRegion>> disposableAnimations;
		TextureRegion[] disposableTextureRegions;

		//background
		for (PhysicsActor actor : background.getActors()) {
			disposableAnimations = actor.getAnimationStorage();
			for (String key : disposableAnimations.keySet()) {
				disposableTextureRegions = disposableAnimations.get(key).getKeyFrames();
				for (TextureRegion textureRegion : disposableTextureRegions) {
					textureRegion.getTexture().dispose();
				}
			}
		}

		//laser
		baseLaser.getTextureRegion().getTexture().dispose();
		laserParticle.getParticleEffect().dispose();

		//rock
		for (Texture texture : rockTexture) {
			texture.dispose();
		}

		//explosion
		baseExplosion.getParticleEffect().dispose();

		//light
		light.dispose();
		batch.dispose();

		//sounds
		laserSound.dispose();
		explosionSound.dispose();	
		spaceLoop.dispose();
		warningSound.dispose();
		gameOverSound.dispose();
		
	}



	@Override
	public void create() {

		mainStage.setDebugAll(MAIN_SCENE_DEBUG);
		
		//simulated lights
		batch = new SpriteBatch();
		light = new Texture(Gdx.files.internal("spacerockemitter/spotLight.png"));
		light.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		background = new BackGroundWrapAround();
		Texture backgroundTex = new Texture(Gdx.files.internal("spacerockemitter/space.png"));
		backgroundTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		background.storeAnimation( "default", backgroundTex );		
		background.setPosition( 0, 0 );		
		mainStage.addActor( background );

		//SHIP		
		spaceship.setPosition( mapWidth/2-spaceship.getWidth()/2,mapHeight/2-spaceship.getHeight()/2 );
		mainStage.addActor(spaceship);

		//LINK SHIPT VELOCITY TO BACKGROUND -- SAME OBJECT --
		background.setVelocity(spaceship.getVelocity());


		baseLaser = new PhysicsActor();
		Texture laserTex = new Texture(Gdx.files.internal("spacerockemitter/laser.png"));
		laserTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		baseLaser.setTexture( laserTex );
		baseLaser.storeAnimation( "default", laserTex );
		baseLaser.setOriginCenter();		
		baseLaser.setMaxSpeed(400);
		baseLaser.setDeceleration(0);
		baseLaser.setRectangleBoundary();
		baseLaser.setAutoAngle(true);

		laserParticle = new ParticleActor();
		laserParticle.load("spacerockemitter/laser.pfx", "spacerockemitter/");		

		laserList = new ArrayList<PhysicsActor>();
		removeList = new ArrayList<BaseActor>();

		rockList = new ArrayList<Rock>();


		rockTexture = new Texture[4];
		for (int i = 0; i < rockTexture.length; i++) {
			rockTexture[i] =  new Texture(Gdx.files.internal("spacerockemitter/rock" + (i%4) + ".png"));
		}


		baseExplosion = new ParticleActor();
		baseExplosion.load("spacerockemitter/explosion.pfx", "spacerockemitter/");


		//////////////
		// UI STAGE //
		//////////////

		//uiStage preparation
		labelPoints = new Label(" points: "+points, game.skin, "default");
		labelWarning = new Label("warning \nwave "+wave+" caming", game.skin, "title");		
		immageLife = new Image[3];
		Texture hearTex = new Texture(Gdx.files.internal("spacerockemitter/heart.png"));
		hearTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		Table immageLifeTable = new Table();
		immageLifeTable.setFillParent(false);
		for (int i = 0; i < immageLife.length; i++) {
			immageLife[i] = new Image(new TextureRegionDrawable(new TextureRegion(hearTex)));
			immageLifeTable.add(immageLife[i]);
		}

		if(UI_TABLE_DEBUG){
			uiTable.debugAll();	
		}
		
		//WORKING
		uiTable.pad(5);
		uiTable.add(labelPoints).left();
		uiTable.add(immageLifeTable).right().expandX();
		uiTable.row();
		uiTable.add().height(150);      
		uiTable.row();
		uiTable.add(labelWarning).colspan(2).expand();	

		//audio
		laserSound = Gdx.audio.newSound(Gdx.files.internal("spacerockemitter/laser.wav"));
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("spacerockemitter/explosion.wav"));
		spaceLoop = Gdx.audio.newMusic(Gdx.files.internal("spacerockemitter/theme_loop.wav"));
		warningSound = Gdx.audio.newSound(Gdx.files.internal("spacerockemitter/warning.wav"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("spacerockemitter/game over.wav"));

		audioVolume = 0.50f;
		spaceLoop.setLooping(true);
		spaceLoop.setVolume(audioVolume);


		gamePhase = PHASE_WARNING;


	}


	private void generateRocks(int numRocks ) {
		for (int n = 0; n < numRocks; n++){
			Rock rock = new Rock(2,2);
			Texture rockTex = rockTexture[n%4];
			rockTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			rock.storeAnimation( "default", rockTex );
			rock.setPosition(800 * MathUtils.random(), 600 * MathUtils.random() );
			rock.setOriginCenter();
			rock.setEllipseBoundary();
			rock.setAutoAngle(false);
			float speedUp = MathUtils.random(0.0f, 1.0f);
			rock.setVelocityAS( 360 * MathUtils.random(), 75 + 50*speedUp );
			rock.addAction( Actions.forever( Actions.rotateBy(360, 2 - speedUp) ) );
			mainStage.addActor(rock);
			rockList.add(rock);
			rock.setParentList(rockList);
		}
	}


	private void generateLittleRock(Rock rock) {
		Rock littleRock;
		int rockAmount = 2;
		int distance = 5;
		for (int n = 0; n < rockAmount; n++){
			littleRock = new Rock(rock.getSize()-1, rock.getSize()-1);
			littleRock.setPosition(rock.getX()+ distance*MathUtils.cosDeg(360/2*n), rock.getY()+ distance*MathUtils.sinDeg(360/2*n));
			littleRock.storeAnimation( "default", rock.getTextureRegion().getTexture() );
			littleRock.setScale(0.5f, 0.5f);
			littleRock.setOriginCenter();			
			littleRock.setEllipseBoundary();
			littleRock.setAutoAngle(false);
			float speedUp = MathUtils.random(0.0f, 1.0f);
			littleRock.setVelocityAS( 360 * MathUtils.random(), 75 + 50*speedUp );
			littleRock.addAction( Actions.forever( Actions.rotateBy(360, 2 - speedUp) ) );
			newRocks.add(littleRock);			
		}

	}





	public void update(float dt) {

		removeList.clear();		

		if(gamePhase == PHASE_WARNING){

			if(PHASE_TIMER == 0){
				wave +=1;
				warningSound.play();			

				labelWarning.setText("    warning \nwave "+wave+" caming");
				labelWarning.clearActions();
				labelWarning.addAction(
						Actions.sequence(
								Actions.alpha( 1, 0.4f ),
								Actions.alpha( 0, 0.4f ),
								Actions.alpha( 1, 0.4f ),
								Actions.alpha( 0, 0.6f ),
								Actions.visible(false)
								)

						);

				labelWarning.setVisible(true);				
			}

			PHASE_TIMER = PHASE_TIMER + dt;
			if(PHASE_TIMER > 2){
				labelWarning.setVisible(false);
				gamePhase = PHASE_PREPARE_WAVE;
			}

		}else if(gamePhase == PHASE_PREPARE_WAVE){
			spaceLoop.play();
			generateRocks(wave);

			//enable shield
			spaceship.activateShield();

			gamePhase = PHASE_GAME_ON;


		}else if(gamePhase == PHASE_WAVE_DESTROIED){

			if(PHASE_TIMER == 0){
				spaceship.setAccelerationXY(0,0);				
				spaceship.startThruster();				
				spaceship.addAction(Actions.sequence(
						Actions.rotateTo(0, 1.5f),
						Actions.moveTo(mapWidth/2-spaceship.getOriginX(), mapHeight/2-spaceship.getOriginY(),  2.0f) 
						)
						);
			}			


			PHASE_TIMER = PHASE_TIMER + dt;
			for (PhysicsActor physicsActor : background.getActors()) {
				wraparound( physicsActor );
			}

			wraparound( spaceship );		

			if(PHASE_TIMER > 4){
				gamePhase = PHASE_WARNING;
				PHASE_TIMER = 0;
				spaceship.stopThruster();
			}			


		}else if (gamePhase == PHASE_GAME_OVER){

			PHASE_TIMER = PHASE_TIMER + dt;

			audioVolume = MathUtils.clamp(audioVolume-dt, 0.0f, 1.0f); 
			spaceLoop.setVolume(MathUtils.clamp(audioVolume-dt, 0.0f, 1.0f));	

			if(audioVolume == 0){
				gameOverSound.play();
				gamePhase = PHASE_GAME_EXIT;
				PHASE_TIMER = 0;
			}

		}else if (gamePhase == PHASE_GAME_EXIT){

			if(PHASE_TIMER == 0){
				uiStage.addAction(Actions.fadeOut(2));

				//main stage will no fadeOut because the Actor don't use the parentAlpha in the draw method
				//we should re-implement it deciding when use the parent alpha and when not				
				//mainStage.addAction(Actions.fadeOut(2));				
			}

			if(PHASE_TIMER > 2){
				dispose();
				game.setScreen(new SpaceRockEmitterMenu(game));
			}

			PHASE_TIMER = PHASE_TIMER + dt;

		}else if(gamePhase == PHASE_PLAYER_DESTROIED){
			
			//start of the phase
			if(PHASE_TIMER == 0){
				spaceship.setVisible(false);
				//remove the life and reset the ship
				life = life - 1;
				if(life >= 0){
					gamePhase = PHASE_PLAYER_DESTROIED;
					PHASE_TIMER = 0;
					spaceship.setAccelerationXY(0, 0);
					spaceship.setVelocity(0, 0);
					spaceship.setRotation(0);
					spaceship.setPosition( mapWidth/2-spaceship.getWidth()/2,mapHeight/2-spaceship.getHeight()/2 );


					for (int i = 0; i < immageLife.length; i++) {
						if(i < life ){
							immageLife[i].setVisible(true);
						}else{
							immageLife[i].setVisible(false);
						}
					}
				}else{
					gamePhase = PHASE_GAME_OVER;
					PHASE_TIMER = 0;
				}
				
			}

			//end of the phase
			if(PHASE_TIMER > 2){
				gamePhase = PHASE_GAME_ON;
				PHASE_TIMER = 0;
				
				spaceship.clearActions();
				spaceship.setVisible(true);
				spaceship.getColor().sub(0, 0, 0, 1);
				spaceship.addAction(Actions.sequence(
										Actions.fadeIn(0.4f),
										Actions.fadeOut(0.4f), 
										Actions.fadeIn(0.4f),
										Actions.fadeOut(0.4f),
										Actions.fadeIn(0.4f)										
										)
									);

				spaceship.activateShield();				
			}
			
			PHASE_TIMER = PHASE_TIMER + dt;
			
		}else if(gamePhase == PHASE_GAME_ON){
		

			if(rockList.isEmpty()){
				gamePhase = PHASE_WAVE_DESTROIED;
				PHASE_TIMER = 0;
			}

			spaceship.setAccelerationXY(0,0);
			if (Gdx.input.isKeyPressed(Keys.LEFT))
				spaceship.rotateBy(180 * dt);
			if (Gdx.input.isKeyPressed(Keys.RIGHT))
				spaceship.rotateBy(-180 * dt);
			if (Gdx.input.isKeyPressed(Keys.UP)){
				spaceship.addAccelerationAS();
				spaceship.startThruster();
			}else{
				spaceship.stopThruster();
			}

			for (PhysicsActor physicsActor : background.getActors()) {
				wraparound( physicsActor );
			}

			wraparound( spaceship );


			for ( PhysicsActor rock : rockList ){
				wraparound( rock );
			}


			for ( Rock rock : rockList ){

				//check if contact with shield
				if(spaceship.isActiveShield()){
					spaceship.overlapsShield(rock, true);					
				}else if(!spaceship.isActiveShield() && spaceship.overlapsShip(rock, true)){ //check if contact with ship only if shield is disable
					ParticleActor explosion = baseExplosion.clone();
					explosion.start();					
					explosion.setPosition(spaceship.getX()+spaceship.getOriginX(), spaceship.getY()+spaceship.getOriginY());
					explosionSound.play(audioVolume); 
					mainStage.addActor(explosion);
					spaceship.stopThruster();

					gamePhase = PHASE_PLAYER_DESTROIED;
					PHASE_TIMER = 0;
				}

				//check if contact with laser
				for (PhysicsActor laser : laserList) {
					if ( laser.overlaps(rock, false) )
					{
						rock.removeLife();
						
						shakeCamera = true;

						if(rock.isDisotried()){
							removeList.add( rock );		
							//regenerate little rock from the big rock
							if(rock.getSize() > 1){
								generateLittleRock(rock);								
							}

						}

						removeList.add( laser );

						ParticleActor explosion = baseExplosion.clone();
						explosion.start();					
						explosion.setPosition(rock.getX()+rock.getOriginX(), rock.getY()+rock.getOriginY());

						explosionSound.play(audioVolume); 

						mainStage.addActor(explosion);
						points += 1;
						labelPoints.setText(" points: "+points);						

					}	
				}
			}

			for (Rock rock : newRocks) {
				rock.setParentList(rockList);
				rockList.add(rock);
				mainStage.addActor(rock);
			}
			newRocks.clear();

			for ( PhysicsActor laser : laserList ){
				wraparound( laser );
				if ( !laser.isVisible() )
					removeList.add( laser );
			}

			roksOverlap(rockList, 0);

		}//end phases end common logic starting for here 

		
		

		for (BaseActor ba : removeList){
			ba.destroy();
		}





	}



	@Override
	public void postDrawMainStage(float dt) {
				
		//lighting
		batch.begin();
		
		batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_SRC_ALPHA); // Blend with background

		for ( PhysicsActor laser : laserList ){
			batch.draw(light, laser.getX()-light.getWidth()/2+laser.getOriginX(), laser.getY()-light.getHeight()/2+laser.getOriginY());
		}

		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // Normal rendering
		batch.end();
		
		
		//camera shake explosion
	
		if(shakeCamera){			
		
			Camera camera = mainStage.getCamera();
			
			if(shakeCameraDurationCounter <=shakeCameraDuration){
				shakeCameraDurationCounter +=dt;
				shakeCameraAngle = shakeCameraAngle + MathUtils.PI2*4*dt;
				
				camera.position.set( camera.position.x+MathUtils.cos(shakeCameraAngle)/(1+shakeCameraDurationCounter*2),camera.position.y+MathUtils.sin(shakeCameraAngle)/(1+shakeCameraDurationCounter*2), 0 );
				camera.update();			
			}else{
				shakeCameraAngle = 0;
				shakeCameraDurationCounter = 0;
				shakeCamera = false;
				if(camera.position.x != viewWidth/2 && camera.position.y != viewHeight/2){
					mainStage.getViewport().apply(true);
				}
			}

		}

		
	}


	public void wraparound(Actor ba){
		if ( ba.getX() + ba.getWidth() < 0 )
			ba.setX( mapWidth );
		if ( ba.getX() > mapWidth )
			ba.setX( -ba.getWidth() );
		if ( ba.getY() + ba.getHeight() < 0 )
			ba.setY( mapHeight );
		if ( ba.getY() > mapHeight )
			ba.setY( -ba.getHeight() );
	}

	public boolean keyDown(int keycode)
	{
		if (keycode == Keys.SPACE && gamePhase == PHASE_GAME_ON)
		{
			PhysicsActor laser = baseLaser.clone();	
			laser.moveToCenterShiftToRight( spaceship );
			laser.setVelocityAS( spaceship.getRotation(), 400 );
			ParticleActor laserFx = laserParticle.clone();
			laserFx.start();
			laserFx.setPosition(laser.getWidth(), laser.getHeight()/2);
			laser.addActor(laserFx);


			laserList.add(laser);
			laser.setParentList(laserList);
			mainStage.addActor(laser);
			laser.addAction(
					Actions.sequence(Actions.fadeOut(0.1f), Actions.delay(0.9f),Actions.fadeOut(0.3f), Actions.visible(false)) );

			laserSound.play(audioVolume);


		}

		if (keycode == Keys.R){
			dispose();
			game.setScreen(new SpaceRockEmitterLevel(this.game,spaceship));
		}

		if (keycode == Keys.M){
			dispose();
			game.setScreen(new SpaceRockEmitterMenu(game));
		}
		
		return false;
	}	

	public void roksOverlap(List<Rock> rocks, int index){

		if(index == rocks.size()-1 || rocks.size() == 0){
			return;
		}

		Rock rock = rocks.get(index);
		for (int i = index+1; i < rocks.size(); i++) {
			rock.overlaps(rocks.get(i), true);
		}

		roksOverlap(rocks, index+1);

	}



}
