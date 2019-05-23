package com.mygdx.game.cheeseplease.touchpad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MenuScreen extends BaseScreen {



	private Table uiTable;
	
	public MenuScreen(CheeseGame g){
		super(g);		
	}
	
	
	@Override
	protected void create() {

		BitmapFont font = new BitmapFont();
		String text = " Press S to start, M for main menu, R restart ";
		LabelStyle style = new LabelStyle( font, Color.YELLOW );
		Label instructions = new Label( text, style );
		instructions.setFontScale(2);
		instructions.setPosition(100, 50);

		instructions.addAction(
				Actions.forever(
						Actions.sequence(
								Actions.color( new Color(1, 1, 0, 1), 0.5f ),
								Actions.delay( 0.5f ),
								Actions.color( new Color(0.5f, 0.5f, 0, 1), 0.5f )
								)
						)
				);

		uiTable = new Table();
		uiStage.addActor(uiTable);
		uiTable.setFillParent(true);
		uiTable.setDebug(false);
		game.skin.add( "background", new Texture(Gdx.files.internal("cheeseplease/touchpad/tiles-menu.jpg")) );
		uiTable.background( game.skin.getTiledDrawable("background") );		
		uiTable.add(new Image(new Texture(Gdx.files.internal("cheeseplease/touchpad/cheese-please.png"))));
		uiTable.row();
		uiTable.add(instructions);
		 
		
		
	}

	@Override
	protected void update(float dt) {
		
	}

	public boolean keyDown(int keycode){
		if (keycode == Keys.S)
			game.setScreen( new LevelScreen(game) );
		
		return false;
		
	}


	@Override
	public void show() {}


	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}





}
