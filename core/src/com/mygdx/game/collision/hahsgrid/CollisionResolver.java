package com.mygdx.game.collision.hahsgrid;

import com.badlogic.gdx.math.Intersector;

public class CollisionResolver {


	public void overlaps(BaseActor actor1, BaseActor actor2) {
		
		System.out.println("enter");
		if(actor1 instanceof Actor1 && actor2 instanceof Actor2) {
			overlaps((Actor1)actor1,(Actor2)actor2);
		}else if(actor1 instanceof Actor2 && actor2 instanceof Actor1) {
			overlaps((Actor1)actor2,(Actor2)actor1);
		}else if(actor1 instanceof Actor1 && actor2 instanceof Actor1) {
			overlaps((Actor1)actor1,(Actor1)actor2);
		}else if(actor1 instanceof Actor2 && actor2 instanceof Actor2) {
			overlaps((Actor2)actor1,(Actor2)actor2);
		}
		

	}
	
	private void overlaps(Actor1 actor1, Actor2 actor2) {
		System.out.println("1 vs 2");
		if(actor1.getBoundingRectangle().overlaps(actor2.getBoundingRectangle())){
			if(Intersector.overlapConvexPolygons(actor1.getBoundingPoligon(), actor2.getBoundingPoligon())){
				actor1.setHit(true);
				actor2.setHit(true);
			}
		}
	}

	private void overlaps(Actor1 actor1, Actor1 actor2) {
		System.out.println("1 vs 1");		
		if(actor1.getBoundingRectangle().overlaps(actor2.getBoundingRectangle())){
			if(Intersector.overlapConvexPolygons(actor1.getBoundingPoligon(), actor2.getBoundingPoligon())){
				actor1.setHit(true);
				actor2.setHit(true);
			}
		}
	}

	private void overlaps(Actor2 actor1, Actor2 actor2) {
		System.out.println("2 vs 2");
		if(actor1.getBoundingRectangle().overlaps(actor2.getBoundingRectangle())){
			if(Intersector.overlapConvexPolygons(actor1.getBoundingPoligon(), actor2.getBoundingPoligon())){
				actor1.setHit(true);
				actor2.setHit(true);
			}
		}	
	}

}
