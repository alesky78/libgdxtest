package com.mygdx.game.rectangledestroyer;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GameScreen extends BaseScreen {

	private Paddle paddle;
	private Ball ball;
	private Brick baseBrick;
	private ArrayList<Brick> brickList;
	private Powerup basePowerup;
	private ArrayList<Powerup> powerupList;
	private ArrayList<BaseActor> removeList;
	// game world dimensions
	final int mapWidth = 800;
	final int mapHeight = 600;

	public GameScreen(BaseGame g)
	{ super(g); }

	@Override
	public void create() {

		paddle = new Paddle();
		Texture paddleTex = new Texture(Gdx.files.internal("rectangledestroier/paddle.png"));
		paddleTex.setFilter( TextureFilter.Linear, TextureFilter.Linear );
		paddle.setTexture( paddleTex );
		mainStage.addActor(paddle);

		baseBrick = new Brick();
		Texture brickTex = new Texture(Gdx.files.internal("rectangledestroier/brick-gray.png"));
		baseBrick.setTexture( brickTex );
		baseBrick.setOriginCenter();
		brickList = new ArrayList<Brick>();

		ball = new Ball();
		Texture ballTex = new Texture(Gdx.files.internal("rectangledestroier/ball.png"));
		ball.storeAnimation( "default", ballTex );
		ball.setPosition( 400, 200 );
		ball.setVelocityAS( 30, 300 );
		ball.setAccelerationXY( 0, -10 );
		mainStage.addActor( ball );

		basePowerup = new Powerup();
		basePowerup.setVelocityXY(0, -100);
		basePowerup.storeAnimation("paddle-expand",
				new Texture(Gdx.files.internal("rectangledestroier/paddle-expand.png")) );
		basePowerup.storeAnimation("paddle-shrink",
				new Texture(Gdx.files.internal("rectangledestroier/paddle-shrink.png")) );
		basePowerup.setOriginCenter();
		powerupList = new ArrayList<Powerup>();
		removeList = new ArrayList<BaseActor>();

		Color[] colorArray = { Color.RED, Color.ORANGE, Color.YELLOW,Color.GREEN, Color.BLUE, Color.PURPLE };

		for (int j = 0; j < 6; j++)
		{
			for (int i = 0; i < 10; i++)
			{
				Brick brick = baseBrick.clone();
				brick.setPosition( 8 + 80*i, 500 - (24 + 16)*j );
				brick.setColor( colorArray[j] );
				brickList.add( brick );
				brick.setParentList( brickList );
				mainStage.addActor( brick );
			}
		}

	}

	@Override
	public void update(float dt) {

		paddle.setPosition( Gdx.input.getX() - paddle.getWidth()/2, 32 );
		if ( paddle.getX() < 0 )
			paddle.setX(0);
		if ( paddle.getX() + paddle.getWidth() > mapWidth )
			paddle.setX(mapWidth - paddle.getWidth());


		if (ball.getX() < 0)
		{
			ball.setX(0);
			ball.multVelocityX(-1);
		}
		if (ball.getX() + ball.getWidth() > mapWidth)
		{
			ball.setX( mapWidth - ball.getWidth() );
			ball.multVelocityX(-1);
		}
		if (ball.getY() < 0)
		{
			ball.setY(0);
			ball.multVelocityY(-1);
		}
		if (ball.getY() + ball.getHeight() > mapHeight)
		{
			ball.setY( mapHeight - ball.getHeight() );
			ball.multVelocityY(-1);
		}


		ball.overlaps(paddle, true);

		removeList.clear();
		for (Brick br : brickList)
		{
			if ( ball.overlaps(br, true) ) // bounces off bricks
			{
				removeList.add(br);
				if (Math.random() < 0.20)
				{
					Powerup pow = basePowerup.clone();
					pow.randomize();
					pow.moveToOrigin(br);
					pow.setScale(0,0);
					pow.addAction( Actions.scaleTo(1,1, 0.5f) );
					powerupList.add(pow);
					pow.setParentList(powerupList);
					mainStage.addActor(pow);
				}
			}
		}


		for (Powerup pow : powerupList)
		{
			if ( pow.overlaps(paddle) )
			{
				String powName = pow.getAnimationName();
				if ( powName.equals("paddle-expand") && paddle.getWidth() < 256)
				{
					paddle.addAction( Actions.sizeBy(32,0, 0.5f) );
				}
				else if ( powName.equals("paddle-shrink") && paddle.getWidth() > 64)
				{
					paddle.addAction( Actions.sizeBy(-32,0, 0.5f) );
				}
				removeList.add(pow);
			}
		}

		for (BaseActor b : removeList)
		{
			b.destroy();
		}

	}

}
