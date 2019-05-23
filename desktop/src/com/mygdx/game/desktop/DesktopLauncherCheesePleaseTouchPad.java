package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.cheeseplease.touchpad.CheeseGame;


public class DesktopLauncherCheesePleaseTouchPad {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 600;
		config.height = 800;
		config.title = "Cheese, Please! touchpad";
		
		new LwjglApplication(new CheeseGame(), config);
	}
}
