package com.mygdx.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

public class CityHeuristic  implements Heuristic<City> {

	  @Override
	  public float estimate(City currentCity, City goalCity) {
	    return Vector2.dst(currentCity.getX(), currentCity.getY(), goalCity.getX(), goalCity.getY());
	  }
	}
