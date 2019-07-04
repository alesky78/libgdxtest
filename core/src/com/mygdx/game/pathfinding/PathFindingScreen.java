package com.mygdx.game.pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * this implementation is takend from the example 
 * https://happycoding.io/tutorials/libgdx/pathfinding
 * 
 * 
 * @author Alessandro
 *
 */
public class PathFindingScreen implements Screen, InputProcessor {

	private PathFindingGame game;
	private int width = 640; 
	private int height = 480;

	private Stage mainStage;

	public static BitmapFont font;
	
	private static City startCity;
	private static City goalCity;	
	private CityGraph cityGraph;
	private GraphPath<City> cityPath;

	private static Label startCityLabel;
	private static Label goalCityLabel;
	
	public PathFindingScreen(PathFindingGame game) {
		super();
		this.game = game;
		create();
	}
	
	
	private void create() {
		
		mainStage = new Stage( new FitViewport(width, height) );
		
		InputMultiplexer im = new InputMultiplexer(this, mainStage);
		Gdx.input.setInputProcessor( im );
		
		font = new BitmapFont();

		LabelStyle style = new LabelStyle( new BitmapFont(), Color.NAVY );
		startCityLabel = new Label("start:", style);
		goalCityLabel = new Label("goal:", style);		
		
		cityGraph = new CityGraph();

		startCity = new City(300, 250, "S");
		City bCity = new City(300, 350, "B");
		City aCity = new City(200, 350, "A");
		City cCity = new City(400, 350, "C");
		City dCity = new City(200, 250, "D");
		City fCity = new City(100, 250, "F");
		City eCity = new City(400, 250, "E");
		City hCity = new City(300, 150, "H");
		City gCity = new City(200, 150, "G");
		City iCity = new City(200, 50, "I");
		City jCity = new City(300, 50, "J");
		City kCity = new City(400, 50, "K");
		goalCity = new City(400, 150, "Z");

		cityGraph.addCity(startCity);
		cityGraph.addCity(bCity);
		cityGraph.addCity(aCity);
		cityGraph.addCity(cCity);
		cityGraph.addCity(dCity);
		cityGraph.addCity(fCity);
		cityGraph.addCity(eCity);
		cityGraph.addCity(hCity);
		cityGraph.addCity(gCity);
		cityGraph.addCity(iCity);
		cityGraph.addCity(jCity);
		cityGraph.addCity(kCity);
		cityGraph.addCity(goalCity);

		cityGraph.connectCities(startCity, bCity);
		cityGraph.connectCities(bCity, aCity);
		cityGraph.connectCities(bCity, cCity);
		cityGraph.connectCities(startCity, dCity);
		cityGraph.connectCities(dCity, fCity);
		cityGraph.connectCities(startCity, hCity);
		cityGraph.connectCities(hCity, gCity);
		cityGraph.connectCities(gCity, iCity);
		cityGraph.connectCities(iCity, jCity);
		cityGraph.connectCities(jCity, kCity);
		cityGraph.connectCities(kCity, goalCity);
		cityGraph.connectCities(startCity, eCity);
		cityGraph.connectCities(eCity, goalCity);
		
		for (Street street : cityGraph.streets) {
			mainStage.addActor(street);
		}
		
		for (City city : cityGraph.cities) {
			mainStage.addActor(city);
		}
		
		startCityLabel.setPosition(20, 20);
		mainStage.addActor( startCityLabel );
		
		goalCityLabel.setPosition(120, 20);
		mainStage.addActor( goalCityLabel );
		

	}
	
	
	public static void setStartCity(City startCity) {
		PathFindingScreen.startCity = startCity;
		startCityLabel.setText("start:"+startCity.name);

	}

	public static void setGoalCity(City goalCity) {
		PathFindingScreen.goalCity = goalCity;
		goalCityLabel.setText("goal:"+goalCity.name);
	}


	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {

		mainStage.act(delta);
		
		// render
		Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mainStage.draw();	

	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void dispose() {
		font.dispose();
	}
	

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}



	@Override
	public boolean keyDown(int keycode) {
		
		if(keycode == Keys.R){	//repeat the simulation
			System.out.println("RESET SIMULATION");			
			game.setScreen(new PathFindingScreen(game));
		} else if(keycode == Keys.C){
			System.out.println("RECALCULATE");
			//clean all the city
			for (City city : cityGraph.cities) {
				city.setInPath(false);
			}
			//recalculate the path			
			cityPath = cityGraph.findPath(startCity, goalCity);
			//highlights the new one			
			for (City city : cityPath) {
				city.setInPath(true);
			}
		}
		
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}


}
