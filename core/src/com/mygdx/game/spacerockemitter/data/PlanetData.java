package com.mygdx.game.spacerockemitter.data;

public class PlanetData {

	public String name;
	public float x,y;
	public int ref;
	
	public PlanetData() {
		super();
	}
	
	public PlanetData(String name, float x, float y, int index) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.ref = index;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
		
}
