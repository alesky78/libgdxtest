package com.mygdx.game.pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
	private int width; 
	private int height;

	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	private CityGraph cityGraph;
	private GraphPath<City> cityPath;

	public PathFindingScreen(PathFindingGame game) {
		super();
		this.game = game;
		create();
	}


	private void create() {
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();

		cityGraph = new CityGraph();

		City startCity = new City(300, 250, "S");
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
		City goalCity = new City(400, 150, "Z");

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

		cityPath = cityGraph.findPath(startCity, goalCity);	

	}


	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {

		// render
		Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//implement render logic here
		for (Street street : cityGraph.streets) {
			street.render(shapeRenderer);
		}

		// Draw all cities blue
		for (City city : cityGraph.cities) {
			city.render(shapeRenderer, batch, font, false);
		}

		// Draw cities in path green
		for (City city : cityPath) {
			city.render(shapeRenderer, batch, font, true);
		}		

	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
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
		// TODO PUT the logic here to button press
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
