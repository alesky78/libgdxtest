package com.mygdx.game.spacerockemitter.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;

public class FindContractScreen extends BaseScreen {


	public FindContractScreen(SpaceRockEmitterGame g) {
		super(g);
	}

	@Override
	protected void create() {

		//label to user later
		Label text2 = game.uiManager.getLabelDefault("This is a short string!");
		text2.setAlignment(Align.center);
		text2.setWrap(true);

		TextButton button1 = game.uiManager.getTextButon("button");
		TextButton button2 = game.uiManager.getTextButon("button");
		TextButton button3 = game.uiManager.getTextButon("button");
		TextButton button4 = game.uiManager.getTextButon("button");		
		
		Table scrollTable = new Table();
		
		scrollTable.add(button1);
		scrollTable.row();
		scrollTable.add(button2);
		scrollTable.row();		
		scrollTable.add(button3);
		scrollTable.row();
		scrollTable.add(button4);
		scrollTable.row();
		
		ScrollPane scroller =  game.uiManager.getScrollPane(scrollTable);
		scroller.setFadeScrollBars(false);


		uiTable.pad(5);
		uiTable.add(scroller).width(200);
		uiTable.debugAll();


	}

	@Override
	protected void update(float dt) {
		// TODO Auto-generated method stub

	}

}
