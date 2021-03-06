package com.mygdx.game.spacerockemitter.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.spacerockemitter.AssetCatalog;
import com.mygdx.game.spacerockemitter.AudioManager;
import com.mygdx.game.spacerockemitter.ParticleEffectManager;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;
import com.mygdx.game.spacerockemitter.SpatialHashGrid;
import com.mygdx.game.spacerockemitter.actor.ActorType;
import com.mygdx.game.spacerockemitter.actor.BackGroundWrapAround;
import com.mygdx.game.spacerockemitter.actor.BaseActor;
import com.mygdx.game.spacerockemitter.actor.Laser;
import com.mygdx.game.spacerockemitter.actor.LaserPool;
import com.mygdx.game.spacerockemitter.actor.ParticleActorPool;
import com.mygdx.game.spacerockemitter.actor.ParticleActorPoolable;
import com.mygdx.game.spacerockemitter.actor.PhysicsActor;
import com.mygdx.game.spacerockemitter.actor.Rock;
import com.mygdx.game.spacerockemitter.actor.RockPool;
import com.mygdx.game.spacerockemitter.actor.SpaceShip;

public class DestoryAsteroidLevel extends BaseScreen {

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

	private LaserPool poolLaser;
	private RockPool poolRock;	
	private ParticleActorPool poolExplosion;

	private ParticleEffectManager particleEffectManager;

	private SpatialHashGrid<BaseActor> spatialGrid;

	private ArrayList<PhysicsActor> laserList;
	private ArrayList<Rock> rockList;
	private List<Rock> newRocks = new ArrayList<Rock>();	
	private ArrayList<BaseActor> removeList;

	private BackGroundWrapAround background;
	private SpaceShip spaceship;

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


	// game world dimensions and data
	final int mapWidth = 800;
	final int mapHeight = 600;


	public DestoryAsteroidLevel(SpaceRockEmitterGame g, SpaceShip spaceship){
		super();
		setGame(g);
		this.spaceship = spaceship;
		create();
	}


	public void dispose() {
		super.dispose();
	}



	@Override
	public void create() {

		//DEBUG MANAGEMENT
		mainStage.setDebugAll(MAIN_SCENE_DEBUG);
		if(UI_TABLE_DEBUG){
			uiTable.debugAll();	
		}


		//game spatial grid used for the collision resolver
		spatialGrid = new SpatialHashGrid<BaseActor>(100);

		//entity management list
		laserList = new ArrayList<PhysicsActor>();
		removeList = new ArrayList<BaseActor>();
		rockList = new ArrayList<Rock>();

		//prepare the manager of the particle effects
		particleEffectManager = new ParticleEffectManager();
		particleEffectManager.addParticleEffect(ParticleEffectManager.LASER, game.assetManager.get(AssetCatalog.PARTICLE_LASER));
		particleEffectManager.addParticleEffect(ParticleEffectManager.EXPLOSION, game.assetManager.get(AssetCatalog.PARTICLE_EXPLOSION));		

		//PREPARE the pools
		poolLaser = new LaserPool(spatialGrid,game.assetManager,particleEffectManager);
		poolExplosion = new ParticleActorPool(particleEffectManager, true, ParticleEffectManager.EXPLOSION);
		poolRock = new RockPool(spatialGrid,game.assetManager);

		//simulated lights
		batch = new SpriteBatch();
		light = game.assetManager.get(AssetCatalog.TEXTURE_SPOT_LIGHT);

		//SHIP		
		spaceship.setPosition( mapWidth/2-spaceship.getWidth()/2,mapHeight/2-spaceship.getHeight()/2 );
		spaceship.setVisible(true);
		spaceship.setGrid(spatialGrid);

		//BACKGROUND
		background = new BackGroundWrapAround(game.assetManager);
		background.setVelocity(spaceship.getVelocity()); //LINK SHIPT VELOCITY TO BACKGROUND -- SAME OBJECT --		
		mainStage.addActor( background );

		//add the ship after the background other way during the draw the ship is cover by the undergorund
		mainStage.addActor(spaceship);



		//////////////
		// UI STAGE //
		//////////////

		//uiStage preparation
		labelPoints = game.uiManager.getLabelDefault(" points: ");
		labelWarning = game.uiManager.getLabelTitle("warning \nwave "+wave+" caming"); 	
		immageLife = new Image[3];
		Texture hearTex = game.assetManager.get(AssetCatalog.TEXTURE_HEART);

		Table immageLifeTable = new Table();
		immageLifeTable.setFillParent(false);
		for (int i = 0; i < immageLife.length; i++) {
			immageLife[i] = new Image(new TextureRegionDrawable(new TextureRegion(hearTex)));
			immageLifeTable.add(immageLife[i]);
		}



		//WORKING
		uiTable.pad(5);
		uiTable.add(labelPoints).left();
		uiTable.add(immageLifeTable).right().expandX();
		uiTable.row();
		uiTable.add().height(150);      
		uiTable.row();
		uiTable.add(labelWarning).colspan(2).expand();	

		//////////////
		// AUDIO
		//////////////
		audioVolume = 0.5f;
		game.audioManager.setMusicVolume(audioVolume);
		game.audioManager.setSoundVolume(audioVolume);
		
		//sound
		game.audioManager.registerAudio(AudioManager.SOUND_EXPLOSION, game.assetManager.get(AssetCatalog.SOUND_EXPLOSION));
		game.audioManager.registerAudio(AudioManager.SOUND_LASER, game.assetManager.get(AssetCatalog.SOUND_LASER));
		game.audioManager.registerAudio(AudioManager.SOUND_WARNING, game.assetManager.get(AssetCatalog.SOUND_WARNING));
		game.audioManager.registerAudio(AudioManager.SOUND_GAME_OVER, game.assetManager.get(AssetCatalog.SOUND_GAME_OVER));				

		//music
		game.audioManager.registerAudio(AudioManager.MUSIC_LEVEL_LOOP, game.assetManager.get(AssetCatalog.MUSIC_LEVEL_LOOP), true);


		//START GAME PHASE
		gamePhase = PHASE_WARNING;


	}




	public void update(float dt) {

		if(gamePhase == PHASE_WARNING){

			if(PHASE_TIMER == 0){
				wave +=1;
				game.audioManager.playSound(AudioManager.SOUND_WARNING); 			

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
			game.audioManager.playMusic(AudioManager.MUSIC_LEVEL_LOOP); 

			for (Rock rock : poolRock.generateNewRocks(wave)) {
				rockList.add(rock);
				rock.setParentList(rockList);
				mainStage.addActor(rock);

			} ;


			//enable shield
			spaceship.activateShield();

			gamePhase = PHASE_GAME_ON;


		}else if(gamePhase == PHASE_WAVE_DESTROIED){

			if(PHASE_TIMER == 0){
				spaceship.setAccelerationXY(0,0);				
				spaceship.startThruster();				
				spaceship.addAction(Actions.sequence(Actions.rotateTo(0, 1.5f),Actions.moveTo(mapWidth/2-spaceship.getOriginX(), mapHeight/2-spaceship.getOriginY(), 2.0f) ));
			}			


			PHASE_TIMER = PHASE_TIMER + dt;	

			if(PHASE_TIMER > 4){
				gamePhase = PHASE_WARNING;
				PHASE_TIMER = 0;
				spaceship.stopThruster();
			}			


		}else if (gamePhase == PHASE_GAME_OVER){

			PHASE_TIMER = PHASE_TIMER + dt;

			audioVolume = MathUtils.clamp(audioVolume-dt, 0.0f, 1.0f); 
			game.audioManager.setMusicVolume(MathUtils.clamp(audioVolume-dt, 0.0f, 1.0f));	

			if(audioVolume == 0){
				game.audioManager.playSound(AudioManager.SOUND_GAME_OVER); 		
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
				game.setScreen(new StartScreen(game));
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

		}//end phases end common logic starting for here 


		//manage all the collision and clear the lists
		//spatialGrid.debugGrid();
		manageTheCollision();
		spatialGrid.reset();		


		wrapAroundAndRemoveAllActors();		
		
		//destroy  entities
		for (BaseActor ba : removeList){
			ba.destroy();
		}
		removeList.clear();		


		//add the new entity in the game
		for (Rock rock : newRocks) {
			rock.setParentList(rockList);
			rockList.add(rock);
			mainStage.addActor(rock);
		}
		newRocks.clear();





	}


	/**
	 * manage the collision using the potential collision,
	 * in this way the collision will be never rechecked more that one time: If match A-->B i will never need to match B-->A
	 */
	private void manageTheCollision() {
		for (List<BaseActor> actors : spatialGrid.getPotentialCollision()) {
			for (int a = 0; a < actors.size()-1; a++) {
				for (int b = a+1; b < actors.size(); b++) {
					//verify and manage the overlaps in both the direction A-->B and B-->A
					if(!actors.get(a).isDead && !actors.get(b).isDead) {	//don't manage death entities
						overlaps( actors.get(a), actors.get(b));						
					}


				}
			}
		}
	}


	/**
	 * note that  case A --> B must be managed as B --> A  
	 * 
	 *  this are the combinations:
	 *  
	 *  ROCK & ROCK
	 *  ROCK & LASER
	 *  ROCK & SHIELD
	 *  ROCK & SHIP
	 *  
	 *  all the other must not managed
	 * 
	 * @param actorA
	 * @param actorB
	 */
	private void overlaps(BaseActor actorA, BaseActor actorB) {

		//support variables should be external so i don''t have to initialize every time
		Rock rock,rock2;
		Laser laser;		


		if(actorA.type == ActorType.ROCK   && actorB.type == ActorType.ROCK){ //ROCK and ROCK
			rock = (Rock)actorA;
			rock2 = (Rock)actorB;			
			
			if(rock.overlaps(rock2, true)){	//if collide simulate the elastic collision
				rock.elasticCollision(rock2);
			}

		}else if(actorA.type == ActorType.ROCK && actorB.type == ActorType.LASER || actorA.type == ActorType.LASER && actorB.type == ActorType.ROCK ){ //ROCK and LASER


			if(actorA instanceof Laser){
				laser = (Laser)actorA;
				rock = (Rock)actorB; 
			}else{
				laser = (Laser)actorB;
				rock = (Rock)actorA; 				
			}
			
			if(laser.overlaps(rock, false)){
				
				
				
				rock.removeLife();
				shakeCamera = true;

				if(rock.isDistoried()){
					rock.isDead = true;
					removeList.add( rock );		
					//regenerate little rock from the big rock
					if(rock.getSize() > 1){
						newRocks.addAll(poolRock.generateSplitRock(rock)); 								
					}						
				}else{
					laser.elasticCollision(rock);	//the rock is influenced by the hit
				}

				laser.isDead = true;
				removeList.add( laser );		

				//sound the explosion
				game.audioManager.playSound(AudioManager.SOUND_EXPLOSION); 		
				mainStage.addActor(poolExplosion.obtain(rock.getX()+rock.getOriginX(), rock.getY()+rock.getOriginY()));
				
				//add points
				points += 1;
				labelPoints.setText(" points: "+points);				

			}

		}else if(actorA.type == ActorType.ROCK &&  actorB.type == ActorType.SHIP_SHIELD || actorA.type == ActorType.SHIP_SHIELD && actorB.type == ActorType.ROCK ){ //ROCK and SHIELD

			if(spaceship.isActiveShield()){
				
				if(actorB instanceof Rock){
					rock = (Rock) actorB; 
				}else{
					rock = (Rock) actorA;
				}
				
				if(rock.overlaps(spaceship.getShield(),true)){
					spaceship.getShield().bounce(rock);  //TODO this method must be factorized in the physyc actor correctly for now the bounce work but is not correct
				}
			}
			
		}else if(actorA.type == ActorType.ROCK && actorB.type == ActorType.SHIP_BODY || actorA.type == ActorType.SHIP_BODY && actorB.type == ActorType.ROCK ){ //ROCK and SHIP

			if(!spaceship.isActiveShield() && gamePhase == PHASE_GAME_ON){	//manage the collision only when the game is on

				if(actorB instanceof Rock){
					rock = (Rock)actorB;
				}else{
					rock = (Rock)actorA;
				}

				if(rock.overlaps(spaceship.getShip(),true)) {
					ParticleActorPoolable explosion = poolExplosion.obtain(spaceship.getX()+spaceship.getOriginX(), spaceship.getY()+spaceship.getOriginY()) ;

					game.audioManager.playSound(AudioManager.SOUND_EXPLOSION); 		
					mainStage.addActor(explosion);
					spaceship.stopThruster();

					gamePhase = PHASE_PLAYER_DESTROIED;
					PHASE_TIMER = 0;					
				}


			}

		}

	}


	/**
	 * this method perform the wrap around and remove the actor 
	 * that must be deleted because no more visible
	 * 
	 */
	private void wrapAroundAndRemoveAllActors() {
		for (PhysicsActor physicsActor : background.getActors()) {
			wraparound( physicsActor );
		}

		wraparound( spaceship );	

		for ( PhysicsActor laser : laserList ){
			if ( !laser.isVisible() && !laser.isDead ) {	//check if is Dead, could be already in remove list by the collision
				laser.isDead = true;
				removeList.add( laser );
			}else {
				wraparound( laser );				
			}
		}

		for ( PhysicsActor rock : rockList ){
			wraparound( rock );
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




	public boolean keyDown(int keycode)
	{
		if (keycode == Keys.SPACE && gamePhase == PHASE_GAME_ON)
		{
			Laser laser = poolLaser.obtain(spaceship);
			laser.setParentList(laserList);

			laserList.add(laser);
			mainStage.addActor(laser);

			game.audioManager.playSound(AudioManager.SOUND_LASER); 		

		}

		if (keycode == Keys.R){
			dispose();
			game.setScreen(new DestoryAsteroidLevel(this.game,spaceship));
		}

		if (keycode == Keys.M){
			game.audioManager.stopMusic(AudioManager.MUSIC_LEVEL_LOOP);
			game.setScreen(new StartScreen(game));
		}

		return false;
	}	




}
