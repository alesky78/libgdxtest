package com.mygdx.game.ballbounce;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen extends BaseScreen {


	private Ball baseBall;
	private List<Ball> balls;

	// game world dimensions
	final int mapWidth = 800;
	final int mapHeight = 600;
	final int maxSpeed = 400;

	public GameScreen(BaseGame g)
	{ super(g); }

	@Override
	public void create() {

		baseBall = new Ball();
		Texture ballTex = new Texture(Gdx.files.internal("rectangledestroier/ball.png"));
		baseBall.storeAnimation( "default", ballTex );
		baseBall.setPosition( 400, 200 );
		baseBall.setVelocityAS( 30, 300 );
		baseBall.setEllipseBoundary();
		
		balls = new ArrayList<Ball>();
		Ball ball;
		for (int i = 0; i < 100; i++) {
			ball = baseBall.clone();
			ball.setPosition( MathUtils.random(0,mapWidth),  MathUtils.random(0,mapHeight));
			ball.setVelocityAS( MathUtils.random(-maxSpeed,maxSpeed), MathUtils.random(-maxSpeed,maxSpeed) );
			balls.add(ball);
			mainStage.addActor(ball);
		}

	}

	@Override
	public void update(float dt) {

		for (Ball ball : balls) {
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
		}
		
		overlap(balls, 0);

	}

	
	public void overlap(List<Ball> balls, int index){
		
		if(index == balls.size()-1){
			return;
		}
		
		Ball ball = balls.get(index);
		for (int i = index+1; i < balls.size(); i++) {
			ball.overlaps(balls.get(i), true);
		}
		
		overlap(balls, index+1);
		
	}
	
}
