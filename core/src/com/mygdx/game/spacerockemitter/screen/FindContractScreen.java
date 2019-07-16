package com.mygdx.game.spacerockemitter.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;

public class FindContractScreen extends BaseScreen {


	public FindContractScreen(SpaceRockEmitterGame g) {
		super(g);
	}

	@Override
	protected void create() {

		String reallyLongString = "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
				+ "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
				+ "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n";


		Label text = game.uiManager.getLabelDefault(reallyLongString);
		text.setAlignment(Align.center);
		text.setWrap(true);
		Label text2 = game.uiManager.getLabelDefault("This is a short string!");
		text2.setAlignment(Align.center);
		text2.setWrap(true);
		Label text3 = game.uiManager.getLabelDefault(reallyLongString);
		text3.setAlignment(Align.center);
		text3.setWrap(true);

		Table scrollTable = new Table();
		scrollTable.add(text);
		scrollTable.row();
		scrollTable.add(text2);
		scrollTable.row();
		scrollTable.add(text3);

		ScrollPane scroller =  game.uiManager.getScrollPane(scrollTable);
		//scroller.setFillParent(true);


//		uiTable.pad(5);
//		uiTable.add(scroller);
//		
//		uiTable.debugAll();
		uiStage.addActor(scroller);

	}

	@Override
	protected void update(float dt) {
		// TODO Auto-generated method stub

	}

}
