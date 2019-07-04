package com.mygdx.game.pathfinding;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Queue;

public class Agent extends Actor{

	CityGraph cityGraph;
	Queue<City> pathQueue = new Queue<>();
	City actualCity;

	boolean reachGoal = false;

	float speed = 100f;
	float deltaX = 0;
	float deltaY = 0;


	public Agent(CityGraph cityGraph) {
		this.cityGraph = cityGraph;
		reachGoal = true;
	}


	@Override
	public void act(float delta){

		if(!reachGoal){
			moveBy(deltaX * speed * delta, deltaY * speed * delta);
			checkCollision();
		}
		super.act(delta);
	}

	public void draw (Batch batch, float parentAlpha) {

		batch.end();
		
		if(actualCity!=null){
			ShapeRenderer shapeRenderer = new ShapeRenderer();
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
			shapeRenderer.setTransformMatrix(batch.getTransformMatrix());

			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(1f, 0f, 0f, 1);
			shapeRenderer.circle(getX()+actualCity.radius, getY()+actualCity.radius, 5);
			shapeRenderer.end();

			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			shapeRenderer.setColor(0, 0, 0, 1);
			shapeRenderer.circle(getX()+actualCity.radius, getY()+actualCity.radius, 5);
			shapeRenderer.end();
		}
		
		batch.begin();
	}


	public void setPath(GraphPath<City> graphPath, City startCity) {

		reachGoal = false;
		pathQueue.clear();

		for (int i = 1; i < graphPath.getCount(); i++) {
			pathQueue.addLast(graphPath.get(i));
		}

		actualCity = startCity;
		setX(actualCity.getX());
		setY(actualCity.getY());

		setSpeedToNextCity();
	}

	/**
	 * Check whether Agent has reached the next City in its path.
	 */
	private void checkCollision() {
		if (pathQueue.size > 0) {
			City targetCity = pathQueue.first();
			if (Vector2.dst(getX(), getY(), targetCity.getX(), targetCity.getY()) < 10) {
				reachNextCity();
			}
		}
	}

	/**
	 * Agent has collided with the next City in its path.
	 */
	private void reachNextCity() {

		City nextCity = pathQueue.first();

		// Set the position to keep the Agent in the middle of the path
		setX(nextCity.getX());
		setY(nextCity.getY());

		this.actualCity = nextCity;
		pathQueue.removeFirst();

		if (pathQueue.size == 0) {
			reachGoal = true;
		} else {
			setSpeedToNextCity();
		}
	}

	/**
	 * Set xSpeed and ySpeed to move towards next City on path.
	 */
	private void setSpeedToNextCity() {
		City nextCity = pathQueue.first();
		float angle = MathUtils.atan2(nextCity.getY() - actualCity.getY(), nextCity.getX() - actualCity.getX());
		deltaX = MathUtils.cos(angle);
		deltaY = MathUtils.sin(angle);
	}


}
