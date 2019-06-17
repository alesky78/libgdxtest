package com.mygdx.game.spacerockemitter;


import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

public class BackGroundWrapAround extends Group
{
	
	private List<PhysicsActor> actors;
	private Texture tex;
	private Vector2 velocity;

	public BackGroundWrapAround(){
		actors = new ArrayList<PhysicsActor>();
		PhysicsActor actor;
		for (int i = 0; i < 4; i++) {
			actor = new PhysicsActor();
			actors.add(actor);
			addActor(actor);
		}
	}

	public List<PhysicsActor> getActors() {
		return actors;
	}
	
	public void storeAnimation(String name, Texture tex){
		this.tex = tex;
		for (PhysicsActor physicsActor : actors) {
			TextureRegion reg = new TextureRegion(tex);
			TextureRegion[] frames = { reg };
			Animation anim = new Animation(1.0f, frames);			
			physicsActor.storeAnimation(name, anim);
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