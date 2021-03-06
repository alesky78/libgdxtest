package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.spacerockemitter.actor.Planet;
import com.mygdx.game.spacerockemitter.actor.Route;

public class PlanetGraph implements IndexedGraph<Planet> {

	private PlanetHeuristic cityHeuristic = new PlanetHeuristic();
	
	public Array<Planet> planets = new Array<>();
	public Array<Route> routes = new Array<>();

	/**
	 * map of all the routes by any planet
	 */
	ObjectMap<Planet, Array<Connection<Planet>>> routeMap = new ObjectMap<>();

	public void addRoute(Route route){
		connectPlanetDirectional(route.getPlanetA(), route.getPlanetB());
		connectPlanetDirectional(route.getPlanetB(), route.getPlanetA());
		routes.add(route);
	}
	
	
	private void  connectPlanetDirectional(Planet from, Planet to){
		PlanetRoute route = new PlanetRoute(from, to);
		if(!routeMap.containsKey(from)){
			routeMap.put(from, new Array<Connection<Planet>>());
			planets.add(from);
		}
		routeMap.get(from).add(route);
	}
	

	public GraphPath<Planet> findPlanetsPath(Planet start, Planet goal){
				
		GraphPath<Planet> planetPath = new DefaultGraphPath<>();
		new IndexedAStarPathFinder<>(this).searchNodePath(start, goal, cityHeuristic, planetPath);
			
		return planetPath;
	}
	
	public GraphPath<Connection<Planet>> findRoutesPath(Planet start, Planet goal){
		
		GraphPath<Connection<Planet>> routePath = new DefaultGraphPath<>();
		new IndexedAStarPathFinder<>(this).searchConnectionPath(start, goal, cityHeuristic, routePath);
			
		return routePath;
	}	

	/**
	 * extremely important of the algorithm will go in exception
	 * the node index must be sequential 
	 * 
	 */
	@Override
	public int getIndex(Planet node) {
		return node.refIndex;
	}


	@Override
	public Array<Connection<Planet>> getConnections(Planet fromNode) {
		if(routeMap.containsKey(fromNode)){
			return routeMap.get(fromNode);
		}

		return new Array<>(0);
	}


	@Override
	public int getNodeCount() {
		return planets.size;
	}
}
