package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.spacerockemitter.data.HiperSpaceMapData;
import com.mygdx.game.spacerockemitter.data.PlanetData;

/**
 * this class collect all the data of the game
 * 
 * 
 * 
 * @author Alessandro D'Ottavio
 *
 */
public class DataManager {


	public PlanetData actualPlanet;
	public HiperSpaceMapData hiperSpaceMap;

	public DataManager() {

	}


	/**
	 * intialize here all the data
	 * 
	 */
	public void load() {

		Json json = new Json();	

		//hiperspace data
		hiperSpaceMap = json.fromJson(HiperSpaceMapData.class, Gdx.files.internal("spacerockemitter/data/data_hiperspace.json"));

		//planet wher to start the game
		actualPlanet = hiperSpaceMap.getPlanets().get(0);
		
		
	}



}