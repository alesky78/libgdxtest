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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;


public class BaseActor extends Group implements SpatialHashGrid.GridIndexable{
	
	protected TextureRegion region;
	protected Polygon boundingPolygon;
	
	protected int type;	//TAG of thi actor type
	protected List<? extends BaseActor> parentList;
	protected SpatialHashGrid<BaseActor> grid;
	
	public BaseActor(){
		super();
		region = new TextureRegion();
		boundingPolygon = null;
		parentList = null;
		type = ActorType.UNTAGGED;
	}
	
	public void setGrid(SpatialHashGrid<BaseActor> grid) {
		this.grid = grid;
	}

	public void setParentList(List<? extends BaseActor> pl){ 
		parentList = pl; 
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<String> generateIndex(int bucketsSize) {
		List<String> index = new ArrayList<>();
		Rectangle rect = boundingPolygon.getBoundingRectangle();
		
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

	public void setTexture(Texture t)
	{ 
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


	public void setRectangleBoundary()
	{
		float w = getWidth();
		float h = getHeight();
		float[] vertices = {0,0, w,0, w,h, 0,h};
		boundingPolygon = new Polygon(vertices);
		boundingPolygon.setOrigin( getOriginX(), getOriginY() );
		boundingPolygon.setScale(getScaleX(), getScaleY());		
	}

	public void setEllipseBoundary()
	{
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

	public Polygon getBoundingPolygon()
	{          
		boundingPolygon.setPosition( getX(), getY() );		
		boundingPolygon.setOrigin( getOriginX(), getOriginY() );		
		boundingPolygon.setRotation( getRotation() );
		boundingPolygon.setScale(getScaleX(), getScaleY());		
		return boundingPolygon;
	}

	/**
	 *  Determine if the collision polygons of two BaseActor objects overlap.
	 *  If (resolve == true), then when there is overlap, move this BaseActor
	 *    along minimum translation vector until there is no overlap.
	 */
	public boolean overlaps(BaseActor other, boolean resolve)
	{
		Polygon poly1 = this.getBoundingPolygon();
		Polygon poly2 = other.getBoundingPolygon();

		if ( !poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()) )
			return false;

		MinimumTranslationVector mtv = new MinimumTranslationVector();
		boolean polyOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);
		if (polyOverlap && resolve)
		{
			this.moveBy( mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth );
		}
		float significant = 0.5f;
		return (polyOverlap && (mtv.depth > significant));
	}

	public void act(float dt)
	{
		super.act( dt );
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

	
	protected void drawDebugBounds (ShapeRenderer shapes) {
		super.drawDebugBounds(shapes);
		
		if (!getDebug() || boundingPolygon==null) 
			return;
		else{
			shapes.set(ShapeType.Line);
			shapes.setColor(Color.RED);
			shapes.polygon(getBoundingPolygon().getTransformedVertices());
			
		}
	}	
	
	public void copy(BaseActor original)
	{
		if(original.region != null){
 	 		this.region =  original.region ;			
		}
		if (original.boundingPolygon != null)
			this.boundingPolygon = new Polygon( original.boundingPolygon.getVertices() );
		this.setPosition( original.getX(), original.getY() );
		this.setOriginX( original.getOriginX() );
		this.setOriginY( original.getOriginY() );
		this.setWidth( original.getWidth() );
		this.setHeight( original.getHeight() );
		this.setColor( original.getColor() );
		this.setVisible( original.isVisible() );
	}

	public BaseActor clone()
	{
		BaseActor newbie = new BaseActor();
		newbie.copy( this );
		return newbie;
	}	

	public void moveToOrigin(BaseActor target){
		this.setPosition(
				target.getX() + target.getOriginX() - this.getOriginX(),
				target.getY() + target.getOriginY() - this.getOriginY());
	}
	
	public void moveToCenterShiftToRight(Actor target){
		this.setPosition(
				target.getX() + target.getOriginX() - this.getOriginX() + MathUtils.cosDeg(target.getRotation())*target.getWidth()/2,
				target.getY() + target.getOriginY() - this.getOriginY() + MathUtils.sinDeg (target.getRotation())*target.getWidth()/2);
	}	

	public Vector2 getPositionCenterShiftToLeft(){
		Vector2 position = new Vector2();
		position.x = getX() + getOriginX() - MathUtils.cosDeg(getRotation())*getWidth()/2;
		position.y = getY() + getOriginY() - MathUtils.sinDeg(getRotation())*getWidth()/2;
		return position;

	}	

	
	public void destroy()
	{
		remove(); // removes self from Stage

	}

}