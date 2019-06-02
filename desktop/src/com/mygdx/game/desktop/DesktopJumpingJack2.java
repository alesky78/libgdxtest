package com.mygdx.game.desktop;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.jumpingjack2.JumpingJack2Gane;

public class DesktopJumpingJack2
{
    public static void main (String[] args)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        // change configuration settings
        config.width = 1000;
        config.height = 800;
        config.title = "Jumping Jack 2";
        
        LwjglApplication launcher = new LwjglApplication( new JumpingJack2Gane(), config );
    }
}