package com.mygdx.game.balloonboster;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BalloonInputListner extends InputListener {

	Balloon balloon;
	LevelScreen level;
	
	public BalloonInputListner(Balloon b, LevelScreen l){
		balloon = b;
		level = l;
	}
	
	public boolean touchDown (InputEvent ev, float x, float y, int pointer, int button){
		level.addPopped();
		balloon.hit();
		return true;
	}
	
}
