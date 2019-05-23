package com.mygdx.game.balloonboster;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BirdInputListner extends InputListener {

	Bird bird;
	LevelScreen level;
	
	public BirdInputListner(Bird b, LevelScreen l){
		bird = b;
		level = l;
	}
	
	public boolean touchDown (InputEvent ev, float x, float y, int pointer, int button){
		bird.hit();
		return true;
	}
	
}
