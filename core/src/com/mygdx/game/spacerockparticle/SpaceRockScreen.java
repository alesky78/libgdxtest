package com.mygdx.game.spacerockparticle;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class SpaceRockScreen extends BaseScreen {

	// activate the graphic DEBUG
	private final boolean DEBUG = false;
	
	private BaseActor background;
	private PhysicsActor spaceship;
	private BaseActor rocketfire;

	private PhysicsActor baseLaser;
	private AnimatedActor baseExplosion;

	private ArrayList<PhysicsActor> laserList;
	private ArrayList<PhysicsActor> rockList;
	private ArrayList<BaseActor> removeList;

	// game world dimensions
	final int mapWidth = 800;
	final int mapHeight = 600;

	public SpaceRockScreen(BaseGame g){
		super(g);
	}

	@Override
	public void create() {

		background = new BaseActor();
		background.setTexture( new Texture(Gdx.files.internal("spacerock/space.png")) );
		background.setPosition( 0, 0 );
		mainStage.addActor( background );
		mainStage.setDebugAll(DEBUG);


		spaceship = new PhysicsActor();
		Texture shipTex = new Texture(Gdx.files.internal("spacerock/spaceship.png"));
		shipTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		spaceship.storeAnimation( "default", shipTex );
		spaceship.setPosition( 400,300 );
		spaceship.setOriginCenter();
		spaceship.setMaxSpeed(200);
		spaceship.setDeceleration(20);
		spaceship.setEllipseBoundary();
		mainStage.addActor(spaceship);

		rocketfire = new BaseActor();
		rocketfire.setPosition(-31,24);
		Texture fireTex = new Texture(Gdx.files.internal("spacerock/fire.png"));
		fireTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		rocketfire.setTexture( fireTex );
		rocketfire.addAction(Actions.forever(
				Actions.sequence(
						Actions.color(Color.FIREBRICK, 0.2f),
						Actions.color(Color.YELLOW, 0.2f)
						)));
		spaceship.addActor(rocketfire);


		baseLaser = new PhysicsActor();
		Texture laserTex = new Texture(Gdx.files.internal("spacerock/laser.png"));
		laserTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		baseLaser.setTexture( laserTex );
		baseLaser.storeAnimation( "default", laserTex );
		baseLaser.setOriginCenter();		
		baseLaser.setMaxSpeed(400);
		baseLaser.setDeceleration(0);
		baseLaser.setRectangleBoundary();
		baseLaser.setAutoAngle(true);

		laserList = new ArrayList<PhysicsActor>();
		removeList = new ArrayList<BaseActor>();

		rockList = new ArrayList<PhysicsActor>();
		int numRocks = 6;
		for (int n = 0; n < numRocks; n++){
			PhysicsActor rock = new PhysicsActor();
			String fileName = "spacerock/rock" + (n%4) + ".png";
			Texture rockTex = new Texture(Gdx.files.internal(fileName));
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


		baseExplosion = new AnimatedActor();
		Animation<TextureRegion> explosionAnim = GameUtils.parseSpriteSheet("spacerock/explosion.png", 6, 6, 0.02f, PlayMode.NORMAL);
		baseExplosion.storeAnimation( "default", explosionAnim );
		baseExplosion.setWidth(96);
		baseExplosion.setHeight(96);
		baseExplosion.setOriginCenter();

	}

	public void update(float dt) {

		removeList.clear();

		spaceship.setAccelerationXY(0,0);
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			spaceship.rotateBy(180 * dt);
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			spaceship.rotateBy(-180 * dt);
		if (Gdx.input.isKeyPressed(Keys.UP)){
			spaceship.addAccelerationAS(spaceship.getRotation(), 100);
		}

		rocketfire.setVisible( Gdx.input.isKeyPressed(Keys.UP) );


		wraparound( spaceship );


		for ( PhysicsActor rock : rockList ){
			wraparound( rock );
		}


		for ( PhysicsActor rock : rockList ){
			for (PhysicsActor laser : laserList) {
				if ( laser.overlaps(rock, false) )
				{
					removeList.add( laser );
					removeList.add( rock );
					AnimatedActor explosion = baseExplosion.clone();
					explosion.moveToOrigin(rock);
					mainStage.addActor(explosion);
					explosion.addAction( Actions.sequence(Actions.delay(1.08f), Actions.removeActor()) );
				}	
			}
		}

		for ( PhysicsActor laser : laserList ){
			wraparound( laser );
			if ( !laser.isVisible() )
				removeList.add( laser );
		}

		
		for (BaseActor ba : removeList){
			ba.destroy();
		}
		
	}

	public void wraparound(BaseActor ba){
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
		if (keycode == Keys.SPACE)
		{
			PhysicsActor laser = baseLaser.clone();	
			laser. moveToCenterShiftToRight( spaceship );
			laser.setVelocityAS( spaceship.getRotation(), 400 );	
			laserList.add(laser);
			laser.setParentList(laserList);
			mainStage.addActor(laser);
			laser.addAction(
					Actions.sequence(Actions.delay(1.0f),Actions.fadeOut(0.3f), Actions.visible(false)) );
					
		}
		return false;
	}	

}
