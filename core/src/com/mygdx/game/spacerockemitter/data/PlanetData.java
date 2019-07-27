package com.mygdx.game.spacerockemitter.data;

import java.util.List;

import com.badlogic.gdx.utils.Json;

public class PlanetData {

	public int ref;
	public String name;
	public float x,y;
	public int challenge;
	public String summary;
	public String image;
	public int factionRef;
	public FactionData faction;	


	public PlanetData() {
		super();
	}
	
	public int getFactionRef() {
		return factionRef;
	}

	public void setFactionRef(int factionRef) {
		this.factionRef = factionRef;
	}

	public int getChallenge() {
		return challenge;
	}

	public void setChallenge(int challenge) {
		this.challenge = challenge;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public FactionData getFaction() {
		return faction;
	}

	public void setFaction(FactionData faction) {
		this.faction = faction;
	}	
		
	
	public static void main(String[] args) {
		PlanetData data = new PlanetData();
		
		data.ref = 1;
		data.name = "name";
		data.x = 1;
		data.y = 1;
		data.challenge = 1;
		data.summary = "summary";
		data.image = "immage";
		data.factionRef = 1;
	
		Json json = new Json();
		System.out.println(json.prettyPrint(data));
		System.out.println(json.toJson(data));
		
		
	}
	
}
