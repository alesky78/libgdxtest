package com.mygdx.game.spacerockemitter.actor;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

public class PlanetAgent extends BaseActor {

	private float speed = 80f;

	private boolean reachGoal;
	Queue<Planet> pathQueue;

	public PlanetAgent() {
		super();
		pathQueue = new Queue<>();
		reachGoal = true;
	}

	public void setPosition(Planet planet) {
		setPosition(planet.getX(), planet.getY());
	}

	public void setPath(GraphPath<Planet> path) {
		reachGoal = false;
		pathQueue.clear();

		for (int i = 1; i < path.getCount(); i++) {
			pathQueue.addLast(path.get(i));
		}

	}


	public void act(float dt){

		if(!reachGoal){

			Vector2 position = new Vector2(getX(), getY());

			Planet next = pathQueue.first();
			Vector2 target = new Vector2(next.getX(), next.getY());

			target.sub(position);
			
			if(target.len()< speed * dt) {
				pathQueue.removeFirst();
				if(pathQueue.isEmpty()) {
					reachGoal = true;
				}
				moveBy( target.x,  target.y);
			}else {
				target.setLength(speed);
				moveBy( target.x * dt,  target.y * dt);
			}


			
		}

		super.act( dt );


	}

}
