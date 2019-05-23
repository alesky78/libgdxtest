package com.mygdx.game.uitest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class UIScreen extends AbstractScreen {

	public final int viewWidth  = 800;
	public final int viewHeight = 600;	

	private Stage uiStage;	
	private Table uiTable; 	

	public UIScreen(UIGame g) {
		super(g);

		//create the stage and the processor
		uiStage = new Stage( new FitViewport(viewWidth, viewHeight) );
		InputMultiplexer im = new InputMultiplexer(uiStage);
		Gdx.input.setInputProcessor( im );


		//prepare the table
		uiTable = new Table();
		uiTable.setDebug(true);	//SET THE DEBUG FOR THE TABLE
		uiTable.setFillParent(true);
		uiStage.addActor(uiTable);
		
		//add background to the table
		game.skin.add("white", new Texture( Gdx.files.internal("uitest/white4px.png")) );
		uiTable.background( game.skin.newDrawable("white", new Color(1,0,0.4f,0.4f) ));
		
		//ui components button
		TextButton defaultButton = new TextButton("default", game.skin, "default");
		TextButton toggleButton = new TextButton("toggleButton", game.skin, "toggle");	
		ImageTextButton imageButton = new ImageTextButton("imageButton", game.skin,"default");
	
		//ui components labels
		Label titleLabel = new Label("Title Label", game.skin, "title");
		Label defaultLabel = new Label("default label", game.skin, "default");		
		
		//Progress Bar
		ProgressBar progressBarHorizontal = new ProgressBar(0, 1, 0.1f, false, game.skin, "default-horizontal");
		ProgressBar progressBarVertical = new ProgressBar(0, 1, 0.1f, true, game.skin, "default-vertical");		
		ProgressBar progressBarBattery = new ProgressBar(0, 1, 0.1f, false, game.skin, "battery");		
		ProgressBar progressBarSignal1 = new ProgressBar(0, 1, 0.1f, false, game.skin, "signal1");
		ProgressBar progressBarSignal2 = new ProgressBar(0, 1, 0.1f, false, game.skin, "signal2");		
		ProgressBar progressBarSignal3 = new ProgressBar(0, 1, 0.1f, false, game.skin, "signal3");	
		
		//Slider
		Slider sliderHorizontal = new Slider(0, 1, 0.1f, false, game.skin, "default-horizontal");
		Slider sliderVertical = new Slider(0, 1, 0.1f, false, game.skin, "default-vertical");		
		
		//other components
		Window window = new Window("", game.skin, "default");
		window.add(defaultButton);	//add components in the window and call pack later to let it work correctly
		window.pack(); 
		 
		Touchpad touchpad = new Touchpad(2, game.skin, "default");
		TextTooltip toltip = new TextTooltip("tolti[", game.skin, "default");
		
		//checkbox
		CheckBox  checkBox = new CheckBox("checkbox", game.skin,"default");
		


		//add components to the table buttons
		uiTable.pad(5);
		//uiTable.add(new Label("buttons", game.skin, "title")).center().colspan(3).expandX();
		uiTable.row();
		uiTable.add(defaultButton);		
		uiTable.add(toggleButton);
		uiTable.add(imageButton);		

		//add components to the table labels
		uiTable.row();		
		//uiTable.add(new Label("Labels", game.skin, "title")).center().colspan(3).expandX();		
		uiTable.row();
		uiTable.add(titleLabel);
		uiTable.add(defaultLabel);		
		

		//add components to the table progress bar
		uiTable.row();		
		//uiTable.add(new Label("Progress Bar", game.skin, "title")).center().colspan(3).expandX();				
		uiTable.row();
		uiTable.add(progressBarHorizontal);		
		uiTable.add(progressBarVertical);		
		uiTable.add(progressBarBattery).width(15);
		uiTable.row();
		uiTable.add(progressBarSignal1);		
		uiTable.add(progressBarSignal2);		
		uiTable.add(progressBarSignal3);		
		
		//add components to the table slider
		uiTable.row();		
		//uiTable.add(new Label("Progress Bar", game.skin, "title")).center().colspan(3).expandX();				
		uiTable.row();
		uiTable.add(sliderHorizontal);				
		uiTable.add(sliderVertical);			
		uiTable.add(checkBox);	
		
		//add other components
		uiTable.row();		
		//uiTable.add(new Label("Other Components", game.skin, "title")).center().colspan(3).expandX();				
		uiTable.row();
		uiTable.add(window);				
		uiTable.add(touchpad);			


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
