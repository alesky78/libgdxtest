package com.mygdx.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Street extends Actor implements Connection<City>  {

	City fromCity;
	City toCity;
	float cost;
	

	public Street(City fromCity, City toCity){
		this.fromCity = fromCity;
		this.toCity = toCity;
		cost = Vector2.dst(fromCity.getX(), fromCity.getY(), toCity.getX(), toCity.getY());
	}

	public void draw (Batch batch, float parentAlpha) {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 0, 1);
		shapeRenderer.rectLine(fromCity.getX(), fromCity.getY(), toCity.getX(), toCity.getY(), 4);
		shapeRenderer.end();		
	}


	@Override
	public float getCost() {
		return cost;
	}

	@Override
	public City getFromNode() {
		return fromCity;
	}

	@Override
	public City getToNode() {
		return toCity;
	}
}
