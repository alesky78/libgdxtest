package com.mygdx.game.desktop;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.starfield.UIGame;
public class DesktopLauncherStarfield
{
    public static void main (String[] args)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        // change configuration settings
        config.width = 1000;
        config.height = 800;
        config.title = "UI test";
        
        UIGame myProgram = new UIGame();
        LwjglApplication launcher = new LwjglApplication( myProgram, config );
    }
}