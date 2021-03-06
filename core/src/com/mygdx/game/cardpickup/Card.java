package com.mygdx.game.cardpickup;

public class Card extends BaseActor {

	
	protected final static String[] rankNames = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
	
	private String rank;
	private String suit;
	public float offsetX;
	public float offsetY;
	public float originalX;
	public float originalY;
	public boolean dragable;

	public Card(String r, String s){
		super();
		rank = r;
		suit = s;
		dragable = true;
	}

	public String getRank(){ 
		return rank; 
	}

	public String getSuit(){ 
		return suit; 
	}

	public int getRankIndex(){

		for (int i = 0; i < rankNames.length; i++)
		{
			if ( rank.equals( rankNames[i] ) )
				return i;
		}
		return -1;
	}
}
