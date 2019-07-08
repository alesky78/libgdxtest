package com.mygdx.game.spacerockemitter.actor;


import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.spacerockemitter.AssetCatalog;

public class BackGroundWrapAround extends Group{
	
	private List<PhysicsActor> actors;
	private Texture tex;
	private Vector2 velocity;

	public BackGroundWrapAround(AssetManager assetManager){
		actors = new ArrayList<PhysicsActor>();
		PhysicsActor actor;
		for (int i = 0; i < 4; i++) {
			actor = new PhysicsActor();
			actors.add(actor);
			addActor(actor);
		}
		
		Texture backgroundTex = assetManager.get(AssetCatalog.TEXTURE_SPACE_BACKGROUND);
		storeAnimation( "default", backgroundTex );		
		setPosition( 0, 0 );		
		
	}

	public List<PhysicsActor> getActors() {
		return actors;
	}
	
	private void storeAnimation(String name, Texture tex){
		this.tex = tex;
		for (PhysicsActor physicsActor : actors) {
			physicsActor.setTexture(tex);
		}		
		
	}
	
	public void setPosition( float xPos, float yPos ){
		int pos = 0;
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				actors.get(pos).setPosition( ((float)x)*tex.getWidth(), ((float)y)*tex.getHeight() );
				pos++;
			}			
		}
	}
	
	public void act (float delta) {
		for (PhysicsActor physicsActor : actors) {
			physicsActor.setVelocity(new Vector2(-velocity.x, -velocity.y));
		}
		
		super.act(delta);
	
	}	
	
	public void setVelocity( Vector2 velocity ){
		this.velocity = velocity;

	}

		
	
}