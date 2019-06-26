package com.mygdx.game.collision.hahsgrid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {

	public TextureRegion region;
	public Polygon boundingPolygon;
	public float velocityX;
	public float velocityY;
	
	public CollisionGrid grid;
	
	private boolean hit= false;

	public BaseActor(float width,float height,Texture t){
		super();
		region = new TextureRegion();
		region.setRegion( t );
		velocityX = 0;
		velocityY = 0;
		setWidth(width);
		setHeight(height);
		setOrigin( getWidth()/2,getHeight()/2 );
		
		float w = getWidth();
		float h = getHeight();
		float[] vertices = {0,0, w,0, w,h, 0,h};
		boundingPolygon = new Polygon(vertices);
		boundingPolygon.setOrigin( getOriginX(), getOriginY() );
		
	}

	public void setGrid(CollisionGrid grid) {
		this.grid = grid;
	}

	public Polygon getBoundingPoligon(){
		boundingPolygon.setPosition( getX(), getY() );		
		boundingPolygon.setOrigin( getOriginX(), getOriginY() );		
		boundingPolygon.setRotation( getRotation() );
		return boundingPolygon;
	}
	
	public void overlaps(BaseActor other){
		Polygon poly1 = this.getBoundingPoligon();
		Polygon poly2 = other.getBoundingPoligon();
		
		//if a high level check hit enter in the detail check
		if (poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()) ) {
			if(Intersector.overlapConvexPolygons(poly1, poly2)) {
				hit = true;				
			}
			
		}
		
	}	

	
	public void act(float dt){
		grid.removeFromGrid(this);
		
		super.act( dt );
		moveBy( velocityX * dt, velocityY * dt );
		
		grid.addToGrid(this);
	}

	
	public void draw(Batch batch, float parentAlpha){
		Color c = getColor();
		if(!hit) {
			batch.setColor(c.r, c.g, c.b, c.a);
		}else {
			batch.setColor(0, 1, 0, c.a);
			hit = false;
		}
		
		if ( isVisible() )
			batch.draw( region, getX(), getY(), getOriginX(), getOriginY(),getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
	}
	
	
}


