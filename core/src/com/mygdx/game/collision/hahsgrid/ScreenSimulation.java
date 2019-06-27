package com.mygdx.game.collision.hahsgrid;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ScreenSimulation implements Screen {

	private static final int NEARBY_COLLISION = 1;
	private static final int POTENTIAL_COLLISION = 2;	
	private int COLLISION_ALGHORITM = 2;	
	private int AMOUNT_ENTITY = 100;
	
	// game world dimensions
	final int worldWidth = 800;
	final int worldHeight = 600;
	
	final int maxsize = 50;
	
	private Stage mainStage;
	private Stage uiStage;	
	
	private List<BaseActor> entitites;
	
	private SpatialHashGrid grid;
	
	private Label checkCollisionLabel;
	
	private CollisionResolver colliderResolver;
	
	public ScreenSimulation() {
		
		mainStage = new Stage(new FitViewport(worldWidth, worldHeight));
		uiStage = new Stage(new FitViewport(worldWidth, worldHeight));
		grid = new SpatialHashGrid(maxsize);
		
		colliderResolver = new CollisionResolver();
		
		BitmapFont font = new BitmapFont();
		LabelStyle style = new LabelStyle( font, Color.RED );
		checkCollisionLabel = new Label( "Collision checked: 0", style );
		checkCollisionLabel.setFontScale(2);
		checkCollisionLabel.setPosition(500, 560);
		uiStage.addActor( checkCollisionLabel );
		
		//create rectangles
		Pixmap pixmap = new Pixmap( 64, 64, Format.RGBA8888 );
		pixmap.setColor( 1, 1, 1, 1 );
        pixmap.fill();		
		Texture pixmaptex = new Texture( pixmap );
		
		entitites = new ArrayList<>();
		BaseActor entity;

		for (int i = 0; i < AMOUNT_ENTITY; i++) {
			
			float width = MathUtils.random(10, maxsize);
			float height = MathUtils.random(10, maxsize);			
			float speedx = MathUtils.random(-180, 180);	
			float speedy = MathUtils.random(-180, 180);			
			
			if(i%2==0) {
				entity = new Actor1( width, height, pixmaptex);				
			}else {
				entity = new Actor2( width, height, pixmaptex);
			}

			entity.velocityX = speedx;
			entity.velocityY = speedy;
			entity.setX(MathUtils.random(0, worldWidth)); 
			entity.setY(MathUtils.random(0, worldHeight));
			
			entity.setGrid(grid);
			
			mainStage.addActor(entity);
			entitites.add(entity);
		}

		
	}
	
	@Override
	public void show() {


	}

	@Override
	public void render(float delta) {

		//uso il reset della grigli perche ce il wraparound dopo che ho ricalcolato gli indici
		//se voglio togliere il reset:
		// ogni entita prima dell update dovrebbe deregistrarsi dalla griglia 
		// ogni movimento deve essere all'interno del metodo act 
		//		-- il wrap around nel act del actor
		//		-- i movimenti dovuti alle collisioni dovrebbero essere qui!!!! come gestirli questi
		//
		grid.resetGrid();
		
		//draw main stage and centre the camera		
        mainStage.act(delta);		
        uiStage.act(delta);

        int hitChecked = 0;
        
        //manage collision
        switch (COLLISION_ALGHORITM) {
        	case NEARBY_COLLISION:
                List<BaseActor> colliders;
                for (BaseActor baseActor : entitites) {
                	colliders = grid.getNearby(baseActor);
                	for (BaseActor baseActor2 : colliders) {
                		// con questso algoritmo le colissioni sono controllate bidirezionalmente a --> b e dopo b --> a
                		baseActor.overlaps(baseActor2);
                		hitChecked++;
        			}
        		}
				break;
			case POTENTIAL_COLLISION:
				for (List<BaseActor> actors : grid.getPotentialCollision()) {
					for (int a = 0; a < actors.size()-1; a++) {
						for (int b = a+1; b < actors.size(); b++) {
							// con questso algoritmo le colissioni sono controllate una sola volta quindi bisogna implementera un overlap che risolva in una sola volta a --> b come b --> a
							colliderResolver.overlaps( actors.get(a), actors.get(b));
	                		hitChecked++;
						}
					}
				}
				
				break;	
			default:
				break;
		}
        

        
        checkCollisionLabel.setText("check effettuati:"+hitChecked);
        
        
		for (BaseActor baseActor : entitites) {
			wraparound(baseActor);
		}

		// clear screen and draw graphics
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mainStage.draw();
        uiStage.draw();
		

	}
	
	public void wraparound(Actor ba){
		if ( ba.getX() + ba.getWidth() < 0 )
			ba.setX( worldWidth );
		if ( ba.getX() > worldWidth )
			ba.setX( -ba.getWidth() );
		if ( ba.getY() + ba.getHeight() < 0 )
			ba.setY( worldHeight );
		if ( ba.getY() > worldHeight )
			ba.setY( -ba.getHeight() );
	}
	

	@Override
	public void resize(int width, int height) {
        mainStage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {


	}

	@Override
	public void resume() {


	}

	@Override
	public void hide() {


	}

	@Override
	public void dispose() {


	}

}
