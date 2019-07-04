package com.mygdx.game.pathfinding;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class City extends Actor {
	
	String name;
	boolean inPath;
	
	/** Index used by the A* algorithm. Keep track of it so we don't have to recalculate it later. */
	int index;
	

	public City(float x, float y, String name){
		setPosition(x, y);
		this.name = name;
	}
	
	public void setInPath(boolean inPath) {
		this.inPath = inPath;
	}

	public void setIndex(int index){
		this.index = index;
	}
	
	
	public void draw (Batch batch, float parentAlpha) {

		
		batch.end();
		
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		if(inPath) {
			// green
			shapeRenderer.setColor(.57f, .76f, .48f, 1);
		}
		else{
			// blue
			shapeRenderer.setColor(.8f, .88f, .95f, 1);
		}
		shapeRenderer.circle(getX(), getY(), 20);
		shapeRenderer.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(0, 0, 0, 1);
		shapeRenderer.circle(getX(), getY(), 20);
		shapeRenderer.end();

		batch.begin();
		//write the name of the node
		PathFindingScreen.font.setColor(1.0f, 0, 0, 1);
		PathFindingScreen.font.draw(batch, name, getX()-5, getY()+5);
		
	}

	
}
