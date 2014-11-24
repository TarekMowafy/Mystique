package com.otaku.mystique;

import java.io.Serializable;

public class RoundObj implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int roundNo;
	//private int roundDuration;
	CategoryDataObj category;
	private int score;
	
	public RoundObj(int roundNo, CategoryDataObj category)
	{
		this.roundNo = roundNo;
		this.category = category;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int score)
	{
		this.score = score  ;
	}
	public CategoryDataObj getCategory(){
		return category;
	}

}
