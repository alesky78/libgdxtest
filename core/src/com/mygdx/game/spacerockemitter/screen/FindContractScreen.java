package com.mygdx.game.spacerockemitter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.spacerockemitter.AssetCatalog;
import com.mygdx.game.spacerockemitter.SpaceRockEmitterGame;
import com.mygdx.game.spacerockemitter.data.ContractData;

public class FindContractScreen extends BaseScreen {

	// activate the graphic DEBUG
	private final boolean MAIN_SCENE_DEBUG = false;
	private final boolean UI_TABLE_DEBUG = false;	

	
	//contract details widget
	private Label contractType;
	private Label contractPayment;
	private Label contractChalleng;
	private Label contractFactionName;			
	private Image contractFactionBadge;
	private Image contractMissionImage;
	private TextButton accept; 
	
	
	public FindContractScreen(SpaceRockEmitterGame g) {
		super(g);
	}

	@Override
	protected void create() {

		//regenerate the data
		game.dataManager.generateContractsIfNeed(game.dataManager.refActualPlanet);
		
		
		//table composition

		TextButton back = game.uiManager.getTextButon("<<");
		back.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen( new OrbitScreen(game) );
				return true;
			}
		});


		Label descContract = game.uiManager.getLabelDefault("available contract");
		descContract.setAlignment(Align.center);
		descContract.setWrap(true);
		
		//scrolling part
		Table scrollTable = new Table();
		ScrollPane scroller =  game.uiManager.getScrollPane(scrollTable);
		scroller.setFadeScrollBars(false);
		
		
		for (ContractData contract : game.dataManager.findPlanetContractDataActualPlanet().contracts) {
			scrollTable.add(buildContractCard(contract)).left().pad(5, 0, 5, 0);
			scrollTable.row();				
		}
		

		//detail contract part
		Table detailContractTable = createContractDetail();
		
		//full contract table
		Table contracts = new Table();		
		contracts.row();		
		contracts.add(descContract);
		contracts.add();
		contracts.row();
		contracts.add(scroller).width(350).height(500).padRight(5);
		contracts.add(detailContractTable).left().growX();

		
		uiTable.pad(5);
		uiTable.add(back).top().left().expandX();
		uiTable.row();
		uiTable.add(contracts).left().expandX();


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
		contractTable.setTouchable(Touchable.enabled); 
		
		TextureAtlas texture = game.assetManager.get(AssetCatalog.TEXTURE_ATLAS_FACTION_BADGE);
		Image badgeImage =  new Image(new TextureRegionDrawable(texture.findRegion(contract.faction.imageBadge) ));

		Table title = new Table();
		title.row();		
		title.add(game.uiManager.getLabelDefault(contract.type+"")).left();
		title.row();
		title.add(game.uiManager.getLabelDefault("payment: "+contract.payment)).left();
		
		contractTable.add(badgeImage);
		contractTable.add(title);
		contractTable.add(game.uiManager.getLabelTitle(contract.challenge+"")).expandX().right();
		
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
		contractFactionName = game.uiManager.getLabelDefault("");
		contractFactionBadge = new Image();
		contractMissionImage = new Image();

		
			
		Table title = new Table();
		title.add(contractType).left().expandX().pad(5, 0, 5, 0);
		title.row();
		title.add(contractPayment).left().expandX().pad(5, 0, 5, 0);
		title.row();
		title.add(contractChalleng).left().expandX().pad(5, 0, 5, 0);
		title.row();		
		title.add(contractFactionName).left().expandX().pad(5, 0, 5, 0);
		title.row();
		title.add(contractFactionBadge).left().expandX().pad(5, 0, 5, 0);
		title.row();				
		
		accept = game.uiManager.getTextButon("accept");
		accept.setVisible(false);
		accept.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen( new ChooseShipScreen(game) );	
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

		Texture mission = new Texture(Gdx.files.internal("spacerockemitter/mission-asteroid.png"));			//TODO the mission immage should be created based on the contract types
		contractMissionImage.setDrawable(new TextureRegionDrawable(mission));
		contractMissionImage.setSize(mission.getWidth(), mission.getHeight());

		TextureRegion badge =  game.assetManager.get(AssetCatalog.TEXTURE_ATLAS_FACTION_BADGE).findRegion(contract.faction.imageBadge);
		contractFactionBadge.setDrawable(new TextureRegionDrawable(badge));
		
		contractType.setText("type:"+contract.type);
		contractPayment.setText("payment:"+contract.payment);
		contractChalleng.setText("challeng:"+contract.challenge);
		
		accept.setVisible(true);
		
	}

	
	@Override
	protected void update(float dt) {


	}

}
