package com.mygdx.game.spacerockemitter;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;


public class BaseActor extends Group implements SpatialHashGrid.GridIndexable{
	
	protected TextureRegion region;
	protected Polygon boundingPolygon;
	protected Rectangle boundingRectangle;	//this is the AABB of the boundingPolygon
	
	public int type;			//label of the actor type
	public boolean isDead;		//identify if an entity is still valid or not
	
	protected List<? extends BaseActor> parentList;
	protected SpatialHashGrid<BaseActor> grid;
	
	public BaseActor(){
		super();
		region = new TextureRegion();
		boundingPolygon = null;
		boundingRectangle = null;
		parentList = null;
		type = ActorType.UNTAGGED;
		isDead = false;
	}
	
	public void setGrid(SpatialHashGrid<BaseActor> grid) {
		this.grid = grid;
	}

	public void setParentList(List<? extends BaseActor> pl){ 
		parentList = pl; 
	}
	
	public void setType(int type) {
		this.type = type;
	}

	public List<String> generateIndex(int bucketsSize) {
		List<String> index = new ArrayList<>();
		Rectangle rect = boundingRectangle;
		
		int minX = MathUtils.floor(rect.getX()/bucketsSize);
		int maxX = MathUtils.floor((rect.getX()+rect.getWidth()) /bucketsSize);		
		int minY = MathUtils.floor(rect.getY()/bucketsSize);
		int maxY = MathUtils.floor((rect.getY()+rect.getHeight()) /bucketsSize);		

		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				index.add(x +"-"+y);
			}
		}

		return index;
	}	

	public void setTexture(Texture t){ 
		int w = t.getWidth();
		int h = t.getHeight();
		setWidth( w );
		setHeight( h );
		region.setRegion( t );
		setOriginCenter();
	}
	
	public TextureRegion getTextureRegion() {
		return region;
	}

	
	public void setOriginCenter(){
		if ( getWidth() == 0 )
			System.err.println("error: actor size not set");
		setOrigin( getWidth()/2, getHeight()/2 );
	}


	public void assignRectangleBoundary(){
		float w = getWidth();
		float h = getHeight();
		float[] vertices = {0,0, w,0, w,h, 0,h};
		boundingPolygon = new Polygon(vertices);
		boundingPolygon.setOrigin( getOriginX(), getOriginY() );
		boundingPolygon.setScale(getScaleX(), getScaleY());		
	}

	public void assignEllipseBoundary(){
		int n = 12; // number of vertices
		float w = getWidth();
		float h = getHeight();
		float[] vertices = new float[2*n];
		for (int i = 0; i < n; i++)
		{
			float t = i * 6.28f / n;
			// x-coordinate
			vertices[2*i] = w/2 * MathUtils.cos(t) + w/2;
			// y-coordinate
			vertices[2*i+1] = h/2 * MathUtils.sin(t) + h/2;
		}
		boundingPolygon = new Polygon(vertices);
		boundingPolygon.setOrigin( getOriginX(), getOriginY() );
		boundingPolygon.setRotation( getRotation() );
		boundingPolygon.setScale(getScaleX(), getScaleY());
	}

	/**
	 * recalculate the bounding poligon and AABB of this entity 
	 */
	private void refreshBoundings(){
		boundingPolygon.setPosition( getX(), getY() );		
		boundingPolygon.setOrigin( getOriginX(), getOriginY() );		
		boundingPolygon.setRotation( getRotation() );
		boundingPolygon.setScale(getScaleX(), getScaleY());		
		
		boundingRectangle = boundingPolygon.getBoundingRectangle();
	}
	
	/**
	 *  Determine if the collision polygons of two BaseActor objects overlap.
	 *  If (resolve == true), then when there is overlap, move this BaseActor
	 *  along minimum translation vector until there is no overlap.
	 *  
	 *  this method consider that the refreshBoundings() si already called on the two actors
	 *  
	 */
	public boolean overlaps(BaseActor other, boolean resolve){


		if ( !boundingRectangle.overlaps(other.boundingRectangle ) )
			return false;

		if(!resolve){
			return Intersector.overlapConvexPolygons(boundingPolygon, other.boundingPolygon);
		}else{
			MinimumTranslationVector mtv = new MinimumTranslationVector();
			boolean polyOverlap = Intersector.overlapConvexPolygons(boundingPolygon, other.boundingPolygon, mtv);
			if (polyOverlap){
				this.moveBy( mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth );
			}
			
			float significant = 0.1f;
			return (polyOverlap && (mtv.depth > significant));
		}
		
	}
	

	/**
	 * basic implementation of the act, if the a new class overwrite this method
	 * the refreshBoundingPolygon() and the insertion of the entity in the grid
	 * must be executed after the entity is moved
	 *  
	 * then should be good that the super class call the super.act() only after is logic is completed
	 */
	public void act(float dt){
		
		super.act( dt );
		
		//refresh the bounding after the entity is moved and put in the grid
		if(boundingPolygon!=null){
			refreshBoundings();			
		}
		if(grid!=null){
			grid.addToGrid(this);			
		}

	}

	public void draw(Batch batch, float parentAlpha) 
	{
		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a);
		if ( isVisible() )
			batch.draw( region, getX(), getY(), getOriginX(), getOriginY(),
					getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );

		super.draw(batch, parentAlpha);
	}

	
	/**
	 * Extended to draw the bounding polygon of the actor
	 * 
	 */
	protected void drawDebugBounds (ShapeRenderer shapes) {
		super.drawDebugBounds(shapes);
		
		if (!getDebug() || boundingPolygon==null) 
			return;
		else{
			shapes.set(ShapeType.Line);
			shapes.setColor(Color.RED);
			shapes.polygon(boundingPolygon.getTransformedVertices());
			
		}
	}	
	

	
	public void destroy()
	{
		remove(); // removes self from Stage
		if(parentList!=null){
			parentList.remove(this);	
		}

	}

}