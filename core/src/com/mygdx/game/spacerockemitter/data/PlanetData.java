package com.mygdx.game.spacerockemitter.data;

import java.util.ArrayList;
import java.util.List;

public class PlanetData {

	public String name;
	public float x,y;
	public int ref;
	public String faction;
	public int challenge;
	public String summary;
	public String image;	

	//contracts data
	public int contractGenerationDay;
	public List<ContractData> contracts; 
	
	public PlanetData() {
		super();
		contractGenerationDay = 0;
		contracts = new ArrayList<ContractData>();
	}
	

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public int getChallenge() {
		return challenge;
	}

	public void setChallenge(int challenge) {
		this.challenge = challenge;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<ContractData> getContracts() {
		return contracts;
	}

	public void setContracts(List<ContractData> contracts) {
		this.contracts = contracts;
	}


	public int getContractGenerationDay() {
		return contractGenerationDay;
	}


	public void setContractGenerationDay(int contractGenerationDay) {
		this.contractGenerationDay = contractGenerationDay;
	}
	
		
}
