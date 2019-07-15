package com.mygdx.game.spacerockemitter.data;

import java.util.List;

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
		
	
	
}
