package com.mygdx.game.pathfinding;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class City extends Actor {
	
	String name;
	boolean inPath;
	
	/** Index used by the A* algorithm. Keep track of it so we don't have to recalculate it later. */
	int index;
	
	
	public int radius = 20;
	
	public City(float x, float y, String name){
		setPosition(x, y);
		setSize(radius*2, radius*2);
		setOrigin( getWidth()/2,getHeight()/2 );		
		this.name = name;
		addListener(new InputListener(){
						public boolean touchDown (InputEvent ev, float x, float y, int pointer, int button){
							if(button == Input.Buttons.LEFT){
								System.out.println("LEFT set start city:  "+City.this.name);								
								PathFindingScreen.setStartCity(City.this);
							}else if (button == Input.Buttons.RIGHT){
								System.out.println("RIGHT set goal city:  "+City.this.name);
								PathFindingScreen.setGoalCity(City.this);					
							}else{
								System.out.println("no event manager SO");								
							}

							return true;

						}
					}
				);
	}
	
	public void setInPath(boolean inPath) {
		this.inPath = inPath;
	}

	public void setIndex(int index){
		this.index = index;
	}
	
	
	public void draw (Batch batch, float parentAlpha) {
		
		batch.end();
		
		PathFindingScreen.sr.begin(ShapeRenderer.ShapeType.Filled);
		if(inPath) {
			// green
			PathFindingScreen.sr.setColor(.57f, .76f, .48f, 1);
		}
		else{
			// blue
			PathFindingScreen.sr.setColor(.8f, .88f, .95f, 1);
		}
		PathFindingScreen.sr.circle(getX()+radius, getY()+radius, radius);
		PathFindingScreen.sr.end();

		PathFindingScreen.sr.begin(ShapeRenderer.ShapeType.Line);
		PathFindingScreen.sr.setColor(0, 0, 0, 1);
		PathFindingScreen.sr.circle(getX()+radius, getY()+radius, 20);
		PathFindingScreen.sr.end();

		batch.begin();
		//write the name of the node
		PathFindingScreen.font.setColor(1.0f, 0, 0, 1);
		PathFindingScreen.font.draw(batch, name+":"+index, getX()+radius-10, getY()+radius+5);
		
	}

	
}
