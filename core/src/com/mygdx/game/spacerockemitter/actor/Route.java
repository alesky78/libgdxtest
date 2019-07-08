package com.mygdx.game.spacerockemitter.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.spacerockemitter.ActorCoordinateUtils;

public class Route extends BaseActor {

	private Planet a;
	private Planet b;
	
	TextureRegion selected_texture = new TextureRegion();
	TextureRegion default_texture = new TextureRegion();	

	public Route(Planet a, Planet b){
		super();
		this.a = a;
		this.b = b;
	}
	
	public Planet getPlanetA() {
		return a;
	}

	public Planet getPlanetB() {
		return b;
	}

	public boolean isRoute(Planet a, Planet b) {
		return ( this.a.equals(a) && this.b.equals(b) ) || ( this.a.equals(b) && this.b.equals(a)); 
	}

	public void setTexture(Texture t){ 
		super.setTexture(t);
		default_texture = new TextureRegion(t);		
		setWidth(Vector2.dst(a.getX(), a.getY(), b.getX(), b.getY()));
		setPosition(a.getX()+a.getOriginX(), a.getY()+a.getOriginY()-getOriginY());
		
		//rotation		
		setOrigin( 0, getHeight()/2f );		
		setRotation(ActorCoordinateUtils.angle(a,b));

	}
	
	public void setTextureSelected(Texture texture) {
		selected_texture = new TextureRegion(texture); 
	}	
	
	public void selected() {
		region = selected_texture;
	}
	
	public void unSelected() {
		region = default_texture;
	}		
	
	
}
