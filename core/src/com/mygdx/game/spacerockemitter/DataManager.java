package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.spacerockemitter.data.ContractData;
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

	//configuration data
	public int contractRegeneartionDay = 30;
	public int minContract = 1;	
	public int maxContract = 5;	

	
	//game session data
	public int actualDay;
	
	
	//Model data
	public HiperSpaceMapData hiperSpaceMap;
	public PlanetData actualPlanet;	//TODO this must be a reference to the name other way when we save teh game we save this object
	
	
	public DataManager() {

	}


	/**
	 * intialize here all the data
	 * 
	 */
	public void startNewGame() {

		Json json = new Json();	

		//hiperspace data
		hiperSpaceMap = json.fromJson(HiperSpaceMapData.class, Gdx.files.internal("spacerockemitter/data/data_hiperspace.json"));

		//start the game		
		
		//generate first contract
		for (PlanetData planet :  hiperSpaceMap.getPlanets()) {
			makeNewContracts(planet);
		}
		
		//TODO this parameter should be configured in the start new game logic
		actualPlanet = hiperSpaceMap.getPlanets().get(0);
		
		
	}


	/**
	 * generate the new contract if it is required
	 * 
	 * @param planet
	 */
	public void generateContracts(PlanetData planet) {
		if(actualDay-planet.contractGenerationDay > contractRegeneartionDay) {
			makeNewContracts(planet);
		}				
	}
	
	private void makeNewContracts(PlanetData planet) {
		
		//remove old if any
		planet.contracts.clear();
		
		//generate it
		int amount = MathUtils.random(minContract, maxContract);
		ContractData contract;
		for (int i = 0; i < amount; i++) {
			//TODO generate properly the contract and not by static data
			
			contract = new ContractData();
			contract.name = "contract "+i;
			contract.faction = planet.getFaction();
			contract.challenge = MathUtils.random(1, planet.getChallenge());
			contract.payment = 1000 * contract.challenge;
			contract.type = 0;
			
			planet.contracts.add(contract);
			
		}
		
				
	}
	

}
