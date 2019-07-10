package com.mygdx.game.spacerockemitter.data;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

public class HiperSpaceData {

	List<PlanetData> planets;
	List<RouteData> routes;
	
	public HiperSpaceData() {
		super();
	}
	
	public static HiperSpaceData createFromJson() {
		Json json = new Json();
		return json.fromJson(HiperSpaceData.class, Gdx.files.internal("spacerockemitter/data_hiperspace.json"));
		
	}
	
	public List<PlanetData> getPlanets() {
		return planets;
	}
	public void setPlanets(List<PlanetData> planets) {
		this.planets = planets;
	}
	public List<RouteData> getRoutes() {
		return routes;
	}
	public void setRoutes(List<RouteData> routes) {
		this.routes = routes;
	}	
		
	
	
}
