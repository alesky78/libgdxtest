package com.mygdx.game.spacerockemitter;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.spacerockemitter.data.ContractData;
import com.mygdx.game.spacerockemitter.data.FactionData;
import com.mygdx.game.spacerockemitter.data.HiperSpaceMapData;
import com.mygdx.game.spacerockemitter.data.PlanetData;
import com.mygdx.game.spacerockemitter.data.ShipData;

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
	public int refStartPlanet = 1;
	public float dayUnitDistance = 0.1f;		//used by the navigation to calculate the amount of day passed

	
	//game session data
	public int actualDay;
	public int refActualPlanet;
	
	
	//Model data
	public HiperSpaceMapData hiperSpaceMap;
	public ArrayList<FactionData> factions;
	public ArrayList<ShipData> ships;		
	
	
	public DataManager() {

	}


	/**
	 * intialize here all the data
	 * 
	 */
	public void startNewGame() {

		Json json = new Json();	

		//load data
		hiperSpaceMap = json.fromJson(HiperSpaceMapData.class, Gdx.files.internal("spacerockemitter/data/data_hiperspace.json"));
		factions = json.fromJson(ArrayList.class, Gdx.files.internal("spacerockemitter/data/data_faction.json"));
		ships  = json.fromJson(ArrayList.class, Gdx.files.internal("spacerockemitter/data/data_ship.json"));

		//start the game		
		for (PlanetData planet :  hiperSpaceMap.getPlanets()) {
			planet.faction = findFaction(planet.getFactionRef());	//link the faction
			makeNewContracts(planet);								//generate first contract
		}
		
		refActualPlanet = refStartPlanet;
		
		
	}


	
	/**
	 * get the actual planet
	 * 
	 */
	public PlanetData getActualPlanet() {
		for (PlanetData planet  : hiperSpaceMap.getPlanets()) {
			if(planet.ref == refActualPlanet ) {
				return planet;
			}
		}
		
		return null;
	}

	public void setActualPlanet(PlanetData planet) {
		refActualPlanet = planet.getRef();
	}

	
	public int getDaysOfTrip(int distance) {

		return (int)(distance*dayUnitDistance);
	}
	
	/**
	 * generate the new contract if it is required
	 * 
	 * @param planet
	 */
	public void generateContractsIfNeed(PlanetData planet) {
		if(actualDay-planet.contractGenerationDay > contractRegeneartionDay) {
			makeNewContracts(planet);
		}				
	}

	
	public FactionData findFaction(int ref) {
		for (FactionData factionData : factions) {
			if(factionData.ref == ref) {
				return factionData;
			}
		}
		return null;
	}
	
	private void makeNewContracts(PlanetData planet) {
		
		//remove old if any
		planet.contracts.clear();
		
		//generate it
		int amount = MathUtils.random(minContract, maxContract);
		ContractData contract;
		int index = 0;
		for (int i = 0; i < amount; i++) {
			contract = new ContractData();			
			index = MathUtils.random(1, factions.size()) - 1;
			contract.faction = factions.get(index);
			contract.challenge = MathUtils.random(1, planet.getChallenge());
			
			//TODO i tipi di missioni dovrebbero essere configurati con i loro parametri: pagamenti, tipo etc il pagamento deve risentire della relazione con la fazione
			contract.payment = 1000 * contract.challenge;	
			contract.type = 0;
			
			planet.contracts.add(contract);
			
		}
					
	}
	

}
