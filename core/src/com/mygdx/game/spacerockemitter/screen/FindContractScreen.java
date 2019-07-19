package com.mygdx.game.spacerockemitter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;
import com.mygdx.game.spacerockemitter.data.ContractData;

public class FindContractScreen extends BaseScreen {

	// activate the graphic DEBUG
	private final boolean MAIN_SCENE_DEBUG = false;
	private final boolean UI_TABLE_DEBUG = true;	

	public FindContractScreen(SpaceRockEmitterGame g) {
		super(g);
	}

	@Override
	protected void create() {

		//table composition

		//COLUMNS 1
		TextButton back = game.uiManager.getTextButon("<<");
		back.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen( new OrbitScreen(game) );
				return true;
			}
		});

		//COLUMNS 2
		Table contracts = new Table();

		Label descContract = game.uiManager.getLabelDefault("available contract");
		descContract.setAlignment(Align.center);
		descContract.setWrap(true);
		
		Table scrollTable = new Table();
		
		
		
		for (ContractData contract : game.dataManager.getActualPlanet().contracts) {
			scrollTable.add(buildContractCard(contract));
			scrollTable.row();				
		}

		
		ScrollPane scroller =  game.uiManager.getScrollPane(scrollTable);
		scroller.setFadeScrollBars(false);

		//the datail of the contract selected
		Table detailContrac = buildContractDetail();
		
		//compose  2
		contracts.row();		
		contracts.add(descContract);
		contracts.add();
		contracts.row();
		contracts.add(scroller).width(350).height(500).padRight(5);
		contracts.add(detailContrac).left().growX();



		uiTable.pad(5);
		uiTable.add(back).top().left().expandX();
		uiTable.row();
		uiTable.add(contracts).left().growX();


		if(MAIN_SCENE_DEBUG) {
			mainStage.setDebugAll(MAIN_SCENE_DEBUG);
		}

		if(UI_TABLE_DEBUG) {
			uiStage.setDebugAll(UI_TABLE_DEBUG);
			uiTable.debugAll();
		}


	}
	
	/**
	 * create the Contract Widget to add in the scroll part
	 * 
	 * @return
	 */
	private Table buildContractCard(ContractData contract) {
		Table contractTable = new Table();
		
		//TODO change the badge using the faction in the contract contract.faction
		Image badgeImage = new Image(new TextureRegionDrawable(new Texture(Gdx.files.internal("spacerockemitter/badge-1.png"))));

		Table title = new Table();
		title.add(game.uiManager.getLabelDefault(contract.name)).left();
		title.row();
		title.add(game.uiManager.getLabelDefault("payment: "+contract.payment)).left();
		
		contractTable.add(badgeImage);
		contractTable.add(title);
		contractTable.add(game.uiManager.getLabelTitle(contract.challenge+""));
		contractTable.pad(5, 0, 5, 0);
		
		//contract selected for detail
		contractTable.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		
		return contractTable;
	}

	/**
	 * create the Contract Widget to add in the scroll part
	 * 
	 * @return
	 */
	private Table buildContractDetail() {
		Table contractTable = new Table();
		
		Image badgeImage = new Image(new TextureRegionDrawable(new Texture(Gdx.files.internal("spacerockemitter/mission-asteroid.png"))));
		
		Table title = new Table();
		title.add(game.uiManager.getLabelDefault("asterodi destroy")).left().expandX().pad(5, 0, 5, 0);
		title.row();
		title.add(game.uiManager.getLabelDefault("payment: 1000k")).left().expandX().pad(5, 0, 5, 0);
		title.row();
		title.add(game.uiManager.getLabelDefault("description")).left().expand().pad(5, 0, 5, 0);

		TextButton accept = game.uiManager.getTextButon("accept");
		accept.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("accept contract");
				return true;
			}
		});
		
		contractTable.add(badgeImage).pad(5, 0, 5, 0).left().expandX();
		contractTable.row();
		contractTable.add(title).left().expandX();
		contractTable.row();		
		contractTable.add(accept).left().expandX();
	
		
		return contractTable;
	}

	
	@Override
	protected void update(float dt) {


	}

}
