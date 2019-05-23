package com.mygdx.game.desktop;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.cardpickup.CardPickUpGame;
public class DesktopCardPickUpLauncher
{
    public static void main (String[] args)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        // change configuration settings
        config.width = 800;
        config.height = 600;
        config.title = "card pick-up 52";
        
        CardPickUpGame myProgram = new CardPickUpGame();
        LwjglApplication launcher = new LwjglApplication( myProgram, config );
    }
}