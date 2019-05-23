package com.mygdx.game.example7;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class MenuScreen implements Screen {

	private Game game;

	private BaseActor background;
	private BaseActor text;

	private Stage uiStage;

	public MenuScreen(Game g){
		game = g;

		uiStage = new Stage();

		background = new BaseActor();
		background.setTexture(new Texture(Gdx.files.internal("tiles-menu.jpg")));
		background.setPosition( 0, 0 );
		uiStage.addActor(background);

		text = new BaseActor();
		text.setTexture(new Texture(Gdx.files.internal("cheese-please.png")));
		text.setPosition( 20, 100 );
		uiStage.addActor(text);

		BitmapFont font = new BitmapFont();
		String text = " Press S to start, M for main menu ";
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
		uiStage.addActor( instructions );
	}




	@Override
	public void render(float delta) {

		// process input
		if (Gdx.input.isKeyPressed(Keys.S))
			game.setScreen( new LevelScreen(game) );

		// update
		uiStage.act(delta);
		
		// draw graphics
		Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		uiStage.draw();
		
	}



	@Override
	public void show() {}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
}
