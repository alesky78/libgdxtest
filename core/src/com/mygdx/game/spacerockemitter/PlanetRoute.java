package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.spacerockemitter.actor.Planet;

public class PlanetRoute implements Connection<Planet> {

	private Planet from;
	private Planet to;
	private float cost;
	
	TextureRegion selected_texture = new TextureRegion();
	TextureRegion default_texture = new TextureRegion();		
	
	public PlanetRoute(Planet from, Planet to){
		this.from = from;
		this.to = to;
		cost = Vector2.dst(from.getX(), from.getY(), to.getX(), to.getY());
	}
	
	@Override
	public float getCost() {
		return cost;
	}

	@Override
	public Planet getFromNode() {
		return from;
	}

	@Override
	public Planet getToNode() {
		return to;
	}
	
	
	

}
