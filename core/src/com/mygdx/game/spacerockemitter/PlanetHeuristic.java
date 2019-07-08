package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.spacerockemitter.actor.Planet;

public class PlanetHeuristic  implements Heuristic<Planet> {

	  @Override
	  public float estimate(Planet current, Planet goal){
	    return Vector2.dst(current.getX(), current.getY(), goal.getX(), goal.getY());
	  }
	}
