package com.mygdx.game.desktop;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.jumpingjack.JumpingJackGane;

public class DesktopJumpingJack
{
    public static void main (String[] args)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        // change configuration settings
        config.width = 1000;
        config.height = 800;
        config.title = "Jumping Jack";
        
        LwjglApplication launcher = new LwjglApplication( new JumpingJackGane(), config );
    }
}