package com.mygdx.game.spacerockemitter;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class RockGenerator {

	private Texture[] rockTexture;
	
	private static final int MAX_SIZE = 4; 
	
	public RockGenerator( AssetManager assetManager) {
	
		rockTexture = new Texture[4];
		rockTexture[0] = assetManager.get(AssetCatalog.TEXTURE_ROCK_0);
		rockTexture[1] = assetManager.get(AssetCatalog.TEXTURE_ROCK_1);
		rockTexture[2] = assetManager.get(AssetCatalog.TEXTURE_ROCK_2);
		rockTexture[3] = assetManager.get(AssetCatalog.TEXTURE_ROCK_3);	
	}
	
	public void generateNewRocks(int numRocks, List<Rock> rockList,Stage mainStage ) {
		for (int n = 0; n < numRocks; n++){
			Rock rock = new Rock(2,MAX_SIZE);
			Texture rockTex = rockTexture[n%4];
			rock.storeAnimation( "default", rockTex );
			rock.setPosition(800 * MathUtils.random(), 600 * MathUtils.random() );
			rock.setOriginCenter();
			rock.setEllipseBoundary();
			rock.setAutoAngle(false);
			float speedUp = MathUtils.random(0.0f, 1.0f);
			rock.setVelocityAS( 360 * MathUtils.random(), 75 + 50*speedUp );
			rock.addAction( Actions.forever( Actions.rotateBy(360, 2 - speedUp) ) );
			mainStage.addActor(rock);
			rockList.add(rock);
			rock.setParentList(rockList);
		}
	}


	public void generateSplitRock(Rock rock, List<Rock> newRocks) {
		Rock littleRock;
		int distance = 5;
		int amount = rock.getSize()-1;	//the rock generated are deduced by the size of the original one
		
		int actualSize = 0;
		int iteration = 0;
		while(amount>0) {
			actualSize = MathUtils.random(1, amount);
			amount -= actualSize;
			littleRock = new Rock(actualSize, actualSize);	//energy equal the size
			littleRock.setPosition(rock.getX()+ distance*MathUtils.cosDeg(360/2*iteration), rock.getY()+ distance*MathUtils.sinDeg(360/2*iteration));
			littleRock.storeAnimation( "default",rockTexture[MathUtils.random(0, rockTexture.length-1)] );	
			littleRock.setScale((float)actualSize/MAX_SIZE, (float)actualSize/MAX_SIZE);
			littleRock.setOriginCenter();			
			littleRock.setEllipseBoundary();
			littleRock.setAutoAngle(false);
			float speedUp = MathUtils.random(0.0f, 1.0f);
			littleRock.setVelocityAS( 360 * MathUtils.random(), 75 + 50*speedUp );
			littleRock.addAction( Actions.forever( Actions.rotateBy(360, 2 - speedUp) ) );
			newRocks.add(littleRock);					
			
			iteration++;
		}

	}
	
}
