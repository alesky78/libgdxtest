package com.mygdx.game.ballbounce;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GameUtils {

	public static Animation<TextureRegion> parseSpriteSheet(String fileName, int frameCols, int frameRows,float frameDuration, PlayMode mode){

		Texture t = new Texture(Gdx.files.internal(fileName), true);
		t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		int frameWidth = t.getWidth() / frameCols;
		int frameHeight = t.getHeight() / frameRows;
		TextureRegion[][] temp = TextureRegion.split(t, frameWidth, frameHeight);
		TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
		int index = 0;
		for (int i = 0; i < frameRows; i++)
		{
			for (int j = 0; j < frameCols; j++)
			{
				frames[index] = temp[i][j];
				index++;
			}
		}
		Array<TextureRegion> framesArray = new Array<TextureRegion>(frames);
		return new Animation<TextureRegion>(frameDuration, framesArray, mode);
	}


	

}
