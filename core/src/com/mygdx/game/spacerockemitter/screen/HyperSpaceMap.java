package com.mygdx.game.spacerockemitter.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.spacerockemitter.AssetCatalog;
import com.mygdx.game.spacerockemitter.PlanetGraph;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;
import com.mygdx.game.spacerockemitter.actor.BaseActor;
import com.mygdx.game.spacerockemitter.actor.Planet;
import com.mygdx.game.spacerockemitter.actor.Route;

public class HyperSpaceMap extends BaseScreen {

	
	protected Planet actualPlanet;
	protected PlanetGraph pathFinder;
	
	public HyperSpaceMap(SpaceRockEmitterGame g) {
		super(g);
	}
	
	
	
	@Override
	protected void create() {
		
		
		//background image
		BaseActor backgroundMap = new BaseActor();
		backgroundMap.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_MAP));
		backgroundMap.setPosition(0, 0);
		mainStage.addActor(backgroundMap);
		
		//create planets test
		PlanetListner listener = new PlanetListner();
		
		actualPlanet = new Planet();
		actualPlanet.addListener(listener);
		actualPlanet.setPosition(100, 100f);
		actualPlanet.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET));
		actualPlanet.setTextureSelected(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET_SELECT));
		actualPlanet.setBitmapFont(game.skin.getFont("font"));
		
		actualPlanet.selected();		

		Planet p2 = new Planet();
		p2.addListener(listener);
		p2.setPosition(300f, 150f);
		p2.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET));
		p2.setTextureSelected(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET_SELECT));		
		p2.setBitmapFont(game.skin.getFont("font"));
		
		Planet p3 = new Planet();
		p3.addListener(listener);		
		p3.setPosition(500f, 280f);
		p3.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET));
		p3.setTextureSelected(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET_SELECT));
		p3.setBitmapFont(game.skin.getFont("font"));
		
		Planet p4 = new Planet();
		p4.addListener(listener);		
		p4.setPosition(150f, 350f);
		p4.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET));
		p4.setTextureSelected(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET_SELECT));		
		p4.setBitmapFont(game.skin.getFont("font"));
		
		Route r1 = new Route(actualPlanet, p2);
		r1.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_ROUTE));
		r1.setTextureSelected(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_ROUTE_SELECT));
		
		Route r2 = new Route(p2, p3);
		r2.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_ROUTE));
		r2.setTextureSelected(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_ROUTE_SELECT));
		
		Route r3 = new Route(p2, p4);
		r3.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_ROUTE));
		r3.setTextureSelected(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_ROUTE_SELECT));		
		
		//add the route first for better graphic
		mainStage.addActor(r1);		
		mainStage.addActor(r2);
		mainStage.addActor(r3);				

		//add the planets
		mainStage.addActor(actualPlanet);		
		mainStage.addActor(p2);		
		mainStage.addActor(p3);
		mainStage.addActor(p4);				
		
		//prepare the path finder
		pathFinder = new PlanetGraph();
		pathFinder.addPlanet(actualPlanet);
		pathFinder.addPlanet(p2);		
		pathFinder.addPlanet(p3);
		pathFinder.addPlanet(p4);		
		
		pathFinder.addRoute(r1);
		pathFinder.addRoute(r2);
		pathFinder.addRoute(r3);				
		
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
