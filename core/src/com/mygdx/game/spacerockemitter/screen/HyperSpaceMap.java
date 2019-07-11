package com.mygdx.game.spacerockemitter.screen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.spacerockemitter.AssetCatalog;
import com.mygdx.game.spacerockemitter.PlanetGraph;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;
import com.mygdx.game.spacerockemitter.actor.BaseActor;
import com.mygdx.game.spacerockemitter.actor.Planet;
import com.mygdx.game.spacerockemitter.actor.Route;
import com.mygdx.game.spacerockemitter.data.HiperSpaceData;
import com.mygdx.game.spacerockemitter.data.PlanetData;
import com.mygdx.game.spacerockemitter.data.RouteData;

public class HyperSpaceMap extends BaseScreen {

	
	protected Planet actualPlanet;
	protected PlanetGraph pathFinder;
	protected HiperSpaceData data;
	
	public HyperSpaceMap(SpaceRockEmitterGame g) {
		super(g);
	}
	
	
	
	@Override
	protected void create() {
		
		//load the hiper space map data
		Json json = new Json();
		data = json.fromJson(HiperSpaceData.class, Gdx.files.internal("spacerockemitter/data_hiperspace.json"));
		
		//background image
		BaseActor backgroundMap = new BaseActor();
		backgroundMap.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_MAP));
		backgroundMap.setPosition(0, 0);
		mainStage.addActor(backgroundMap);
		
		//create planets actors
		PlanetListner listener = new PlanetListner();		
		Planet planet;
		List<Planet> planets = new ArrayList<>();
		
		for (PlanetData planetData : data.getPlanets()) {
			planet = new Planet();
			planet.addListener(listener);
			planet.setPosition(planetData.getX(), planetData.getY());
			planet.setPlanetName(planetData.getName());
			planet.refIndex = planetData.getRef();
			planet.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET));
			planet.setTextureSelected(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET_SELECT));
			planet.setBitmapFont(game.skin.getFont("font"));		
			planets.add(planet);
		}

		//create route actors
		Route route;
		List<Route> routes = new ArrayList<>();		
		Planet planetA = null,planetB = null;
		boolean found = false;
		Iterator<Planet> it;
		for (RouteData routeData : data.getRoutes()) {
			
			//search A
			it =  planets.iterator();
			found = false;
			while(!found && it.hasNext()) {
				planet = it.next();
				if(planet.refIndex == routeData.getRefA()) {
					planetA = planet;
					found = true;
				}
			}
			//search B
			it =  planets.iterator();
			found = false;
			while(!found && it.hasNext()) {
				planet = it.next();
				if(planet.refIndex == routeData.getRefB()) {
					planetB = planet;
					found = true;
				}
			}
			
			if(planetA==null || planetB==null) {
				System.err.print("planet null");
			}
			
			route = new Route(planetA, planetB);
			route.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_ROUTE));
			route.setTextureSelected(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_ROUTE_SELECT));
			routes.add(route);
		}

		
		//add the actor to the stage
		for (Route route2 : routes) {
			mainStage.addActor(route2);		
		}
	
		for (Planet planet2 : planets) {
			mainStage.addActor(planet2);		
		}			
	
		//crete the path finder
		pathFinder = new PlanetGraph();
		for (Route route2: routes) {	
			pathFinder.addRoute(route2);
		}
		
		
		//TODO test here we force the fist as start planet
		actualPlanet = planets.get(0);
	}

	@Override
	protected void update(float dt) {


	}
	
	
	protected class PlanetListner  extends InputListener{
		
		
		public boolean touchDown (InputEvent ev, float x, float y, int pointer, int button){
			if(button == Input.Buttons.LEFT){
				pathFinder.findPath(actualPlanet, (Planet)ev.getListenerActor());				
			}else if (button == Input.Buttons.RIGHT){
				pathFinder.findPath(actualPlanet, (Planet)ev.getListenerActor());
			}else{
				System.err.println("no event manager SO");								
			}

			return true;

		}
		
		
	}

}
