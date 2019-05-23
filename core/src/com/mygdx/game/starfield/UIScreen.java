package com.mygdx.game.starfield;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class UIScreen extends AbstractScreen {

	public final int viewWidth  = 800;
	public final int viewHeight = 600;	

	private Stage uiStage;	 	

	//shaders
	String vertexShader;
    String fragmentShader;
    ShaderProgram shader;
    
	
	public UIScreen(UIGame g) {
		super(g);

		//create the stage and the processor
		uiStage = new Stage( new FitViewport(viewWidth, viewHeight) );
		InputMultiplexer im = new InputMultiplexer(uiStage);
		Gdx.input.setInputProcessor( im );

		
		//create all the shaders
        vertexShader = Gdx.files.internal("shader/passthrough.vrtx").readString();        
        fragmentShader = Gdx.files.internal("shader/starfiled.frgm").readString();      

        shader = new ShaderProgram(vertexShader,fragmentShader);	
        
        if (!shader.isCompiled()) {
            System.err.println(shader.getLog());
            System.exit(0);
        }

        if (shader.getLog().length()!=0){
            System.out.println(shader.getLog());
        }
        
        
		StarfieldActor actor = new StarfieldActor();
		actor.setTexture( new Texture(Gdx.files.internal("starfield/space.png")));
//		actor.setTexture( new Texture(Gdx.files.internal("starfield/spaceship.png")));	
//		actor.setScale(4);
		actor.setPosition(viewWidth/2-actor.getWidth()/2, viewHeight/2-actor.getHeight()/2);
	
		
		actor.setShaderProgram(shader);
		
		actor.enableShader();
		
		uiStage.addActor(actor);

	}
	
	

	@Override
	public void render(float delta) {
		uiStage.act(delta);

		// render
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		uiStage.draw();

	}	


	public void resize(int screenWidth, int screenHeight) {
		uiStage.getViewport().update(screenWidth, screenHeight);
	}	



}
