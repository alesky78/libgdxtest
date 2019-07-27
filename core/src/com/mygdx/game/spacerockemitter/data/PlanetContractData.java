package com.mygdx.game.spacerockemitter.data;

import java.util.ArrayList;
import java.util.List;

public class PlanetContractData {

	//contracts data
	public int planetRef;
	public int contractGenerationDay;
	public List<ContractData> contracts;

	public PlanetContractData(int planetRef) {
		super();
		this.planetRef = planetRef;
		contractGenerationDay = 0;
		contracts = new ArrayList<ContractData>();
	}

	public List<ContractData> getContracts() {
		return contracts;
	}

	public void setContracts(List<ContractData> contracts) {
		this.contracts = contracts;
	}
	

}
