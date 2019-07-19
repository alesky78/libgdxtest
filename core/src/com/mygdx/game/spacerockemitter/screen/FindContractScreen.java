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

	
	//contract details widget
	private Label contractType;
	private Label contractPayment;
	private Label contractChalleng;		
	private Image contractMissionImage;
	private TextButton accept; 
	
	
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

		Table detailContrac = createContractDetail();
		
		//compose  2
		contracts.row();		
		contracts.add(descContract);
		contracts.add();
		contracts.row();
		contracts.add(scroller).width(400).height(500).padRight(5);
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
		title.add(game.uiManager.getLabelDefault(contract.type+"")).left();
		title.row();
		title.add(game.uiManager.getLabelDefault("payment: "+contract.payment)).left();
		
		contractTable.add(badgeImage).left();
		contractTable.add(title);
		contractTable.add(game.uiManager.getLabelTitle(contract.challenge+"")).right();
		contractTable.pad(5, 0, 5, 0);
		
		//contract selected for detail
		contractTable.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				refreshContractDetail(contract);
				return true;
			}
		});
		
		return contractTable;
	}

	
	
	private Table createContractDetail() {
		Table contractTable = new Table();
		
		contractType = game.uiManager.getLabelDefault("");
		contractPayment = game.uiManager.getLabelDefault("");	
		contractChalleng = game.uiManager.getLabelDefault("");
		contractMissionImage = new Image();
			
		Table title = new Table();
		title.add(contractType).left().expandX().pad(5, 0, 5, 0);
		title.row();
		title.add(contractPayment).left().expandX().pad(5, 0, 5, 0);
		title.row();
		title.add(contractChalleng).left().expandX().pad(5, 0, 5, 0);
		title.row();		
		

		accept = game.uiManager.getTextButon("accept");
		accept.setVisible(false);
		accept.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		
		contractTable.add(contractMissionImage).pad(5, 0, 5, 0).left().expandX();
		contractTable.row();
		contractTable.add(title).left().expandX();
		contractTable.row();		
		contractTable.add(accept).left().expandX();
	
		
		return contractTable;
	}
	
	/**
	 * create the Contract Widget to add in the scroll part
	 * 
	 * @return
	 */
	private void refreshContractDetail(ContractData contract) {

		//TODO should be created based on the contract types
		Texture mission = new Texture(Gdx.files.internal("spacerockemitter/mission-asteroid.png"));
		contractMissionImage.setDrawable(new TextureRegionDrawable(mission));
		contractMissionImage.setSize(mission.getWidth(), mission.getHeight());

		contractType.setText("type:"+contract.type);
		contractPayment.setText("payment:"+contract.payment);
		contractChalleng.setText("challeng:"+contract.challenge);
		
		accept.setVisible(true);
		
	}

	
	@Override
	protected void update(float dt) {


	}

}
