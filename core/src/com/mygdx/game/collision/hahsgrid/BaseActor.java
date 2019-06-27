package com.mygdx.game.collision.hahsgrid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {

	public TextureRegion region;
	public Polygon boundingPolygon;
	public float velocityX;
	public float velocityY;
	
	public SpatialHashGrid grid;
	
	protected boolean hit= false;

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

	public void setGrid(SpatialHashGrid grid) {
		this.grid = grid;
	}
	
	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public Polygon getBoundingPoligon(){
		boundingPolygon.setPosition( getX(), getY() );		
		boundingPolygon.setOrigin( getOriginX(), getOriginY() );		
		boundingPolygon.setRotation( getRotation() );
		return boundingPolygon;
	}
	
	public Rectangle getBoundingRectangle(){
		return getBoundingPoligon().getBoundingRectangle();
	}
	
	
	
	public void overlaps(BaseActor other){
		Polygon poly1 = this.getBoundingPoligon();
		Polygon poly2 = other.getBoundingPoligon();
		

		if (poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()) ) {		//check middle level
			if(Intersector.overlapConvexPolygons(poly1, poly2)) {	//check detail level
				hit = true;				
			}
			
		}
		
	}	

	
	public void act(float dt){
		
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


