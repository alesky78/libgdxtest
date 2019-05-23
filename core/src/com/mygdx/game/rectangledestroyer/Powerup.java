package com.mygdx.game.rectangledestroyer;

import java.util.ArrayList;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Powerup extends PhysicsActor {

	public Powerup()
	{ super(); }

	public Rectangle getRectangle()
	{ return new Rectangle( getX(), getY(), getWidth(), getHeight() ); }

	public Powerup clone()
	{
		Powerup newbie = new Powerup();
		newbie.copy( this );
		return newbie;
	}

	public boolean overlaps(Paddle other)
	{
		return Intersector.overlaps( this.getRectangle(), other.getRectangle() );
	}
	// randomly select one of the stored animations
	public void randomize()
	{
		ArrayList<String> names = new ArrayList<String>( animationStorage.keySet() );
		int n = MathUtils.random( names.size() - 1 );
		setActiveAnimation( names.get(n) );
	}	

}
