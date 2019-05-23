package com.mygdx.game.spacerockparticle;



import java.util.ArrayList;

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
import com.badlogic.gdx.scenes.scene2d.Group;


public class BaseActor extends Group
{
	public TextureRegion region;
	public Polygon boundingPolygon;

	private ArrayList<? extends BaseActor> parentList;



	
	public BaseActor()
	{
		super();
		region = new TextureRegion();
		boundingPolygon = null;
		parentList = null;
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
	}

	public Polygon getBoundingPolygon()
	{          
		boundingPolygon.setPosition( getX(), getY() );		
		boundingPolygon.setOrigin( getOriginX(), getOriginY() );		
		boundingPolygon.setRotation( getRotation() );
		return boundingPolygon;
	}

	public void setParentList(ArrayList<? extends BaseActor> pl){ 
		parentList = pl; 
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
	
	public void moveToCenterShiftToRight(BaseActor target){
		this.setPosition(
				target.getX() + target.getOriginX() - this.getOriginX() + (float)(Math.cos(Math.toRadians(target.getRotation()))*target.getWidth()/2),
				target.getY() + target.getOriginY() - this.getOriginY() + (float)(Math.sin(Math.toRadians(target.getRotation()))*target.getWidth()/2));
	}	

	public void destroy()
	{
		remove(); // removes self from Stage
		if (parentList != null)
			parentList.remove(this);
	}	
}