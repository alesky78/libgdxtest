package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Disposable;

/**
 * this class encapsulate the skin in this way it can be substitute easily just changing this class
 * wihtout touch the external class
 * 
 * @author Alessandro
 *
 */
public class UIManager implements Disposable {

	private Skin skin;
	private AssetManager assetManager;


	public UIManager(AssetManager assetManager) {
		this.assetManager = assetManager;
		this.skin = assetManager.get(AssetCatalog.UI_JSON, Skin.class);
	}

	public BitmapFont getBitmapFont() {
		return skin.getFont("font");
	}
	
	public Label getLabelDefault(String text) {
		return new Label(text, skin, "default");	
	}
	
	public Label getLabelTitle(String text) {
		return new Label(text, skin, "title");	
	}
	
	public TextButton getTextButon(String text) {
		return new TextButton(text, skin, "default");
	}
	
	public ProgressBar getProgressBar(float min, float max, float stepSize) {
		return new ProgressBar(0, 1, 0.1f, false, skin, "default-horizontal");
	}	

	public ScrollPane getScrollPane(Actor widget) {
		return new ScrollPane(widget, skin,  "default");
	}	
	
	public Window getWindow() {
		return 	new Window("", skin, "default");
	}	
	
	@Override
	public void dispose() {
		skin.dispose();
	}


}
