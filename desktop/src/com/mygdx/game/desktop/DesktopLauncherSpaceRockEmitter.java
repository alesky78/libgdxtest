package com.mygdx.game.desktop;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;
public class DesktopLauncherSpaceRockEmitter
{
    public static void main (String[] args)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        // change configuration settings
        config.width = 800;
        config.height = 600;
        config.title = "Space Rock";
        
        SpaceRockEmitterGame myProgram = new SpaceRockEmitterGame();
        LwjglApplication launcher = new LwjglApplication( myProgram, config );
    }
}