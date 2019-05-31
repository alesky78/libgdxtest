package com.mygdx.game.jumpingjack2;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class JumpingJack2Screen extends BaseScreen {

	
	// activate the graphic DEBUG
	private final boolean MAIN_SCENE_DEBUG = true;

	
	private Player player;
	private World world;
	private int coins = 0;
	private ArrayList<Box2DActor> removeList;
	// game world dimensions
	final int mapWidth = 800;
	final int mapHeight = 600;


	public JumpingJack2Screen(BaseGame g) {
		super(g);
	}

	@Override
	public void create() {

		
		mainStage.setDebugAll(MAIN_SCENE_DEBUG);
		
		
		world = new World(new Vector2(0, -9.8f), true);
		removeList = new ArrayList<Box2DActor>();

		// background image
		BaseActor bg = new BaseActor();
		Texture t = new Texture(Gdx.files.internal("jumpingjack/sky.png"));
		bg.setTexture( t );
		mainStage.addActor( bg );

		// solid objects
		Texture groundTex = new Texture(Gdx.files.internal("jumpingjack/ground.png"));
		Texture dirtTex = new Texture(Gdx.files.internal("jumpingjack/dirt.png"));
		addSolid( groundTex, 0,0, 800,32 );
		addSolid( groundTex, 150,250, 100,32 );
		addSolid( groundTex, 282,250, 100,32 );
		addSolid( dirtTex, 0,0, 32,600 );
		addSolid( dirtTex, 768,0, 32,600 );


		Box2DActor crate = new Box2DActor();
		Texture crateTex = new Texture(Gdx.files.internal("jumpingjack/crate.png"));
		crateTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		crate.storeAnimation( "default", crateTex );
		crate.setPosition(500, 100);
		mainStage.addActor(crate);
		crate.setDynamic();
		crate.setShapeRectangle();
		// set standard density, average friction, small restitution
		crate.setPhysicsProperties(1.0f, 0.2f, 0.1f);
		crate.initializePhysics(world);


		Box2DActor ball = new Box2DActor();
		Texture ballTex = new Texture(Gdx.files.internal("jumpingjack/ball.png"));
		ballTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		ball.storeAnimation( "default", ballTex );
		ball.setPosition(300, 320);
		mainStage.addActor(ball);
		ball.setDynamic();
		ball.setShapeCircle();
		// set standard density, small friction, average restitution
		ball.setPhysicsProperties(0.3f, 0.9f, 0.5f);
		ball.initializePhysics(world);

		Coin baseCoin = new Coin();
		Texture coinTex = new Texture(Gdx.files.internal("jumpingjack/coin.png"));
		coinTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		baseCoin.storeAnimation( "default", coinTex );

		Coin coin1 = baseCoin.clone();
		coin1.setPosition(500, 250);
		mainStage.addActor(coin1);
		coin1.initializePhysics(world);

		Coin coin2 = baseCoin.clone();
		coin2.setPosition(550, 250);
		mainStage.addActor(coin2);
		coin2.initializePhysics(world);

		Coin coin3 = baseCoin.clone();
		coin3.setPosition(600, 250);
		mainStage.addActor(coin3);
		coin3.initializePhysics(world);


		player = new Player();
		Animation walkAnim = GameUtils.parseImageFiles("jumpingjack/walk-", ".png", 3, 0.15f, Animation.PlayMode.LOOP_PINGPONG);
		player.storeAnimation( "walk", walkAnim );
		Texture standTex = new Texture(Gdx.files.internal("jumpingjack/stand.png"));
		standTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		player.storeAnimation( "stand", standTex );
		Texture jumpTex = new Texture(Gdx.files.internal("jumpingjack/jump.png"));
		jumpTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		player.storeAnimation( "jump", jumpTex );

		player.setPosition( 164, 300 );
		player.setSize(60,90);
		mainStage.addActor(player);
		player.setDynamic();
		player.setShapeRectangle();
		// set standard density, average friction, small restitution
		player.setPhysicsProperties(1, 0.5f, 0.1f);
		player.setFixedRotation();
		player.setMaxSpeedX(2);
		player.initializePhysics(world);


		world.setContactListener(new ContactListener() {

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {


			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {


			}

			@Override
			public void endContact(Contact contact) {
				Object objC = GameUtils.getContactObject(contact, Coin.class);
				if (objC != null)
					return;
				Object objP = GameUtils.getContactObject(contact, Player.class, "bottom");
				if ( objP != null ){
					Player p = (Player)objP;
					p.adjustGroundCount(-1);
				}

			}

			@Override
			public void beginContact(Contact contact) {
				Object objC = GameUtils.getContactObject(contact, Coin.class);
				if (objC != null){
					Object p = GameUtils.getContactObject(contact, Player.class, "main");
					if (p != null){
						Coin c = (Coin)objC;
						removeList.add( c );
					}
					return; // avoid possible jumps
				}

				Object objP = GameUtils.getContactObject(contact, Player.class, "bottom");
				if ( objP != null ){
					Player p = (Player)objP;
					p.adjustGroundCount(1);
					p.setActiveAnimation("stand");
				}
			}

		});

	}

	@Override
	public void update(float dt) {
		removeList.clear();
		world.step(1/60f, 6, 2);
		for (Box2DActor ba : removeList){
			ba.destroy();
			world.destroyBody( ba.getBody() );
		}

		if( Gdx.input.isKeyPressed(Keys.LEFT) ){
			player.setScale(-1,1);
			player.applyForce( new Vector2(-3.0f, 0) );
		}
		if( Gdx.input.isKeyPressed(Keys.RIGHT) ){
			player.setScale(1,1);
			player.applyForce( new Vector2(3.0f, 0) );
		}
		if ( player.getSpeed() > 0.1 && player.getAnimationName().equals("stand") )
			player.setActiveAnimation("walk");
		if ( player.getSpeed() < 0.1 && player.getAnimationName().equals("walk") )
			player.setActiveAnimation("stand");

	}


	public void addSolid (Texture t, float x, float y, float w, float h){
		Box2DActor solid = new Box2DActor();
		t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		solid.storeAnimation( "default", t );
		solid.setPosition(x,y);
		solid.setSize(w,h);
		mainStage.addActor( solid );
		solid.setStatic();
		solid.setShapeRectangle();
		solid.initializePhysics(world);
	}

	
	public boolean keyDown(int keycode){
		if (keycode == Keys.P)
			togglePaused();
		if (keycode == Keys.R)
			game.setScreen( new JumpingJack2Screen(game) );
		if (keycode == Keys.SPACE && player.isOnGround() )
		{
			Vector2 jumpVec = new Vector2(0,3);
			player.applyImpulse(jumpVec);
			player.setActiveAnimation("jump");
		}
		return false;
	}

}
