package com.mygdx.game.spacerockemitter;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.spacerockemitter.data.ContractData;
import com.mygdx.game.spacerockemitter.data.FactionData;
import com.mygdx.game.spacerockemitter.data.HiperSpaceMapData;
import com.mygdx.game.spacerockemitter.data.PlanetContractData;
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
	public float dayPerUnitDistance = 0.1f;		//used by the navigation to calculate the amount of day passed
	public float maxSpeed = 250;
	public float maxAcceleration = 250; 	
	public float maxDeceleration = 250;	
	
	//game session data
	public int actualDay;
	public int refActualPlanet;
	public List<PlanetContractData> planetContracts;
	
	
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
	@SuppressWarnings("unchecked")
	public void startNewGame() {

		Json json = new Json();	

		//load data
		hiperSpaceMap = json.fromJson(HiperSpaceMapData.class, Gdx.files.internal("spacerockemitter/data/data_hiperspace.json"));
		factions = json.fromJson(ArrayList.class, Gdx.files.internal("spacerockemitter/data/data_faction.json"));
		ships  = json.fromJson(ArrayList.class, Gdx.files.internal("spacerockemitter/data/data_ship.json"));

		//start the game		
		planetContracts = new ArrayList<PlanetContractData>();
		PlanetContractData contract;
		for (PlanetData planet :  hiperSpaceMap.getPlanets()) {
			planet.setFaction(findFaction(planet.getFactionRef()));	//link the faction to the planet
			contract = new PlanetContractData(planet.ref);			//link the planet contract to the planet
			makeNewContracts(contract);								//generate first contract	
			planetContracts.add(contract);
		}
		
		refActualPlanet = refStartPlanet;
		
		
	}

	
	public void setActualPlanet(PlanetData planet) {
		refActualPlanet = planet.getRef();
	}

	
	
	/**
	 * get the actual planet
	 */
	public PlanetData findActualPlanet() {
		return findPlanet(refActualPlanet);
	}
	
	/**
	 * get the planet by its ref
	 */	
	public PlanetData findPlanet(int planetRef) {
		for (PlanetData planet  : hiperSpaceMap.getPlanets()) {
			if(planet.ref == planetRef ) {
				return planet;
			}
		}
		
		return null;
	}	
	
	/**
	 * get the faction by its ref
	 */	
	public FactionData findFaction(int ref) {
		for (FactionData factionData : factions) {
			if(factionData.ref == ref) {
				return factionData;
			}
		}
		return null;
	}
	
	/**
	 * get the PlanetContractData by the planet ref
	 */	
	public PlanetContractData findPlanetContractDataActualPlanet() {
		return findPlanetContractData(refActualPlanet);
	}		
	
	/**
	 * get the PlanetContractData by the planet ref
	 */	
	public PlanetContractData findPlanetContractData(int planetRef) {
		for (PlanetContractData contact : planetContracts) {
			if(contact.planetRef == planetRef) {
				return contact;
			}
		}
		return null;
	}	

	
	
	public int getDaysOfTrip(int distance) {

		return (int)(distance*dayPerUnitDistance);
	}
	
	/**
	 * generate the new contract if it is required checking the last time have been generated
	 * 
	 * @param planet
	 */
	public void generateContractsIfNeed(int planetRef) {
		PlanetContractData planetContract = findPlanetContractData(planetRef);
		if(actualDay-planetContract.contractGenerationDay > contractRegeneartionDay) {
			makeNewContracts(planetContract);
		}				
	}
	
	private void makeNewContracts(PlanetContractData planetContracts) {
				
		//remove old if any
		planetContracts.getContracts().clear();
		
		//generate it
		int amount = MathUtils.random(minContract, maxContract);
		ContractData contract;
		int index = 0;
		for (int i = 0; i < amount; i++) {
			contract = new ContractData();			
			index = MathUtils.random(0, factions.size()- 1);
			contract.faction = factions.get(index);
			contract.challenge = MathUtils.random(1, findPlanet(planetContracts.planetRef).getChallenge());
			
			//TODO i tipi di missioni dovrebbero essere configurati con i loro parametri: pagamenti, tipo etc il pagamento deve risentire della relazione con la fazione
			contract.payment = 1000 * contract.challenge;	
			contract.type = 0;
			
			planetContracts.getContracts().add(contract);
			
		}
					
	}
	

	public float getSpeedRatio(float speed){
		return speed/maxSpeed;
	}

	public float getAccelerationRatio(float acceleration){
		return acceleration/maxAcceleration;
	}

	public float getDecelerationRatio(float deceleration){
		return deceleration/maxDeceleration;
	}	
	
	
}
