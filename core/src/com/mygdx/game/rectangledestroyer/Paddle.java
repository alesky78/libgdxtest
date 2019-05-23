package com.mygdx.game.rectangledestroyer;

import com.badlogic.gdx.math.Rectangle;

public class Paddle extends BaseActor {


	public Paddle(){ 
		super(); 
	}

	public Rectangle getRectangle(){ 
		return new Rectangle( getX(), getY(), getWidth(), getHeight() ); 
	}

}
