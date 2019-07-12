package com.mygdx.game.spacerockemitter.screen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.spacerockemitter.AssetCatalog;
import com.mygdx.game.spacerockemitter.AudioManager;
import com.mygdx.game.spacerockemitter.PlanetGraph;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;
import com.mygdx.game.spacerockemitter.actor.BaseActor;
import com.mygdx.game.spacerockemitter.actor.Planet;
import com.mygdx.game.spacerockemitter.actor.PlanetAgent;
import com.mygdx.game.spacerockemitter.actor.Route;
import com.mygdx.game.spacerockemitter.data.HiperSpaceData;
import com.mygdx.game.spacerockemitter.data.PlanetData;
import com.mygdx.game.spacerockemitter.data.RouteData;

public class HyperSpaceMap extends BaseScreen implements PlanetAgent.ArriveListener {

	// activate the graphic DEBUG
	private final boolean MAIN_SCENE_DEBUG = false;
	private final boolean UI_TABLE_DEBUG = false;	

	protected int gamePhase;
	private final int PHASE_SELECT = 0;
	private final int PHASE_TRAVEL = 1;    
	private final int PHASE_TRAVEL_FINISH = 2;	

	private PlanetAgent agent;
	private PlanetGraph pathFinder;
	private GraphPath<Planet> path;
	private HiperSpaceData data;
	private List<Planet> planets;
	private List<Route> routes;

	//route data
	private Planet actualPlanet;
	private Planet selectedPlanet;	
	private boolean agentArriveDestination;

	//AUDIO data
	private float soundEngineVolume;
	private long  soundEngineInstance;
	
	//UI data
	private Window window;
	private Image planetImage;
	private Label labelPlanetName;
	private Label labelFactionName;
	private Label labelSummary;	
	private Label labelChalleng;
	private TextButton goButton;
	private TextButton closeButton;


	public HyperSpaceMap(SpaceRockEmitterGame g) {
		super(g);
	}



	@Override
	protected void create() {

		gamePhase = PHASE_SELECT;
		
		//////////////
		// AUDIO
		//////////////

		//sound
		game.audioManager.registerAudio(AudioManager.SOUND_WARP_ENGINE, game.assetManager.get(AssetCatalog.SOUND_WARP_ENGINE));

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
		planets = new ArrayList<>();

		for (PlanetData planetData : data.getPlanets()) {
			planet = new Planet(planetData);
			planet.addListener(listener);
			planet.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET));
			planet.setTextureSelected(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET_SELECT));
			planet.setBitmapFont(game.skin.getFont("font"));		
			planets.add(planet);
		}

		//create route actors
		Route route;
		routes = new ArrayList<>();		
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

		//TODO create the agent also here 
		agentArriveDestination = true;
		agent = new PlanetAgent();
		agent.setPosition(actualPlanet);
		agent.setTexture(game.assetManager.get(AssetCatalog.TEXTURE_HYPERSPACE_PLANET));	//TODO set the correct texture
		agent.setColor(Color.BLUE);															//TODO remove this line when the correct texture is inserted
		agent.setListener(this);
		mainStage.addActor(agent);	

		///////////////////
		//prepare the UI
		/////////////////

		labelPlanetName = new Label("", game.skin, "default");
		labelFactionName = new Label("", game.skin, "default");
		labelSummary = new Label("", game.skin, "default");	
		labelChalleng = new Label("", game.skin, "default");

		goButton = new TextButton("go", game.skin, "default");
		goButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

				agentArriveDestination = false;
				path = pathFinder.findPath(actualPlanet, selectedPlanet);
				highLightsRoute(path, actualPlanet);

				actualPlanet = selectedPlanet;
				agent.setPath(path);
				window.setVisible(false);
				gamePhase = PHASE_TRAVEL;
				
				//start the sound at 0
				soundEngineInstance = game.audioManager.loopSound(AudioManager.SOUND_WARP_ENGINE, 0f);
				//game.audioManager.setSoundVolume(AudioManager.SOUND_WARP_ENGINE, soundEngineInstance, 0f);

			}
		});


		closeButton = new TextButton("close", game.skin, "default");	
		closeButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				window.setVisible(false);
			}
		});


		if(MAIN_SCENE_DEBUG) {
			mainStage.setDebugAll(MAIN_SCENE_DEBUG);
		}

		if(UI_TABLE_DEBUG) {
			uiStage.setDebugAll(UI_TABLE_DEBUG);
			uiTable.debugAll();
		}


	}


	public void prepareWindow(Planet target) {

		uiTable.clear();
		//preparing window
		window = new Window("", game.skin, "default");
		window.setMovable(false); 
		window.setVisible(false);
		window.getTitleTable().clear();
		uiTable.add(window);

		//preparing content table
		Table descTable = new Table();
		descTable.setFillParent(true);
		window.addActor(descTable);

		TextureAtlas texture = game.assetManager.get(AssetCatalog.TEXTURE_ATLAS_PLANETS);
		planetImage = new Image(new TextureRegionDrawable(texture.findRegion(target.getPlanetData().getImage())));

		labelPlanetName.setText(target.getPlanetData().getName());
		labelFactionName.setText(target.getPlanetData().getFaction());
		labelSummary.setText(target.getPlanetData().getSummary());
		labelChalleng.setText(target.getPlanetData().getChallenge()+"");

		//planet and name
		descTable.add().height(30);
		descTable.row();
		descTable.add(planetImage);
		descTable.add(labelPlanetName);
		descTable.row();
		descTable.add().height(30);      
		descTable.row();		
		descTable.add(new Label("Faction:", game.skin, "default")).left();
		descTable.add(labelFactionName);
		descTable.row();
		descTable.add(new Label("Challeng:", game.skin, "default")).left();
		descTable.add(labelChalleng);
		descTable.row();
		descTable.add().height(30);      
		descTable.row();	
		descTable.add(new Label("Summary", game.skin, "default")).colspan(2);
		descTable.row();
		descTable.add(labelSummary).colspan(2).left().top().expandY();
		descTable.row();
		descTable.add(goButton);
		descTable.add(closeButton);
		descTable.row();
		descTable.add().height(30);		
	}

	private void clearRoute() {
		//clear previous selection
		for (Planet planet : planets) {
			planet.unSelected();
		}

		for (Route route : routes) {
			route.unSelected();
		}	
	}

	private void highLightsRoute(GraphPath<Planet> planetPath,Planet start) {
		//highlight selected route and planet
		Iterator<Planet> iterator = planetPath.iterator();

		Planet actual = start;
		Planet next = null;		

		actual.selected();		

		while (iterator.hasNext()) {
			next = iterator.next();	
			next.selected();
			for (Route route : routes) {
				if(route.isRoute(actual,next)) {
					route.selected();
				}
			}		

			actual = next;
		}
	}


	@Override
	protected void update(float dt) {

		if(gamePhase == PHASE_SELECT) {
			
			
		}else if(gamePhase == PHASE_TRAVEL) {
			
			//Increase the volume until 0.5
			if(soundEngineVolume<0.5f) {
				soundEngineVolume += 0.3*dt;
				if(soundEngineVolume>0.5f) {
					soundEngineVolume = 0.5f;
				}
				game.audioManager.setSoundVolume(AudioManager.SOUND_WARP_ENGINE, soundEngineInstance, soundEngineVolume);
			}
			
		}else if(gamePhase == PHASE_TRAVEL_FINISH) {
			
			//decrease the volume until 0
			if(soundEngineVolume > 0f) {
				soundEngineVolume -= 0.4*dt;
				if(soundEngineVolume<0 ) {
					soundEngineVolume = 0;
					gamePhase = PHASE_SELECT;
					game.audioManager.setSoundVolume(AudioManager.SOUND_WARP_ENGINE, soundEngineInstance, soundEngineVolume);					
					game.audioManager.stopSound(AudioManager.SOUND_WARP_ENGINE,soundEngineInstance);
				}else {
					game.audioManager.setSoundVolume(AudioManager.SOUND_WARP_ENGINE, soundEngineInstance, soundEngineVolume);					
				}

			}
			
		}
		

	}


	protected class PlanetListner  extends InputListener{


		public boolean touchDown (InputEvent ev, float x, float y, int pointer, int button){

			if(agentArriveDestination) {
				selectedPlanet = (Planet)ev.getListenerActor();
				prepareWindow(selectedPlanet);
				window.setVisible(true);
			}

			return true;

		}


	}


	@Override
	public void reachDestination() {
		agentArriveDestination = true;
		clearRoute();
		gamePhase = PHASE_TRAVEL_FINISH;
	}

}
