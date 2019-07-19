package com.mygdx.game.spacerockemitter.data;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

public class HiperSpaceMapData {

	List<PlanetData> planets;
	List<RouteData> routes;
	
	public HiperSpaceMapData() {
		super();
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
	
	
	public static void main(String[] args) {
		
		HiperSpaceMapData bean = new HiperSpaceMapData();
		bean.planets = new ArrayList<PlanetData>();
		
		PlanetData data = new PlanetData();
		
		data.ref = 1;
		data.name = "name";
		data.x = 1;
		data.y = 1;
		data.challenge = 1;
		data.summary = "summary";
		data.image = "immage";
		data.factionRef = 1;
		
		bean.planets.add(data);
	
		Json json = new Json();
		System.out.println(json.prettyPrint(bean));
		System.out.println(json.toJson(bean));
		
		
	}
	
}
