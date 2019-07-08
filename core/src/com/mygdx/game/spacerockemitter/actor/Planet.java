package com.mygdx.game.spacerockemitter.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Planet extends BaseActor {

	public int index;
	
	TextureRegion selected_texture = new TextureRegion();
	TextureRegion default_texture = new TextureRegion();	
	
	public Planet() {

		super();
		
//		addListener(new InputListener(){
//				public boolean touchDown (InputEvent ev, float x, float y, int pointer, int button){
//					if(button == Input.Buttons.LEFT){
//						System.out.println("LEFT set start city:  "+Planet.this.getName());	
//						region = selected_texture;
//		
//					}else if (button == Input.Buttons.RIGHT){
//						System.out.println("RIGHT set goal city:  "+Planet.this.getName());
//						region = default_texture;
//							
//					}else{
//						System.out.println("no event manager SO");								
//					}
//	
//					return true;
//	
//				}
//			}
//		);

		
	}
	
	public void setTexture(Texture t){ 
		super.setTexture(t);
		default_texture = new TextureRegion(t);
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
