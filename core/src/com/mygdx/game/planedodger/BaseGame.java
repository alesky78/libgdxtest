package com.mygdx.game.planedodger;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class BaseGame extends Game {


	// used to store resources common to multiple screens
	public Skin skin;
	
	public BaseGame(){
		skin = new Skin();
	}
	
	
	public void dispose () {
		super.dispose();
		skin.dispose();
	}	

}
