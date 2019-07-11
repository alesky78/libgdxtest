package com.mygdx.game.spacerockemitter.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Planet extends BaseActor {

	public int refIndex;

	protected String planetName;
	
	protected TextureRegion selected_texture = new TextureRegion();
	protected TextureRegion default_texture = new TextureRegion();	
	protected BitmapFont font;

	
	public Planet() {
		super();		
	}

	public String getPlanetName() {
		return planetName;
	}

	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}

	public void setTexture(Texture t){ 
		super.setTexture(t);
		default_texture = new TextureRegion(t);
	}

	public void setTextureSelected(Texture texture) {
		selected_texture = new TextureRegion(texture); 
	}

	public void setBitmapFont(BitmapFont font) {
		this.font = font;
	}

	public void selected() {
		region = selected_texture;
	}

	public void unSelected() {
		region = default_texture;
	}	

	public void draw(Batch batch, float parentAlpha) {
		font.setColor(Color.RED);
		font.draw(batch, planetName, getX()+getOriginX(), getY());
		super.draw(batch, parentAlpha);
	}

}
