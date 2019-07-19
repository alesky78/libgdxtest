package com.mygdx.game.spacerockemitter.data;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Json;

public class ShipData {

	public int ref;
	public String name;
	public String shipTex;
	public float maxSpeed;
	public float acceleration;
	public float deceleration;
	
	
	public ShipData() {
		super();

	}


	public static void main(String[] args) {
		
		List ships = new ArrayList<ShipData>();
		
		ShipData data = new ShipData();
		
		data.ref = 1;
		data.maxSpeed = 1;
		data.acceleration = 1;
		data.deceleration = 1;		
		data.name = "name";
		data.shipTex = "shipTex";

		ships.add(data);
	
		Json json = new Json();
		System.out.println(json.prettyPrint(ships));
		System.out.println(json.toJson(ships));
		
		
	}
	
}
