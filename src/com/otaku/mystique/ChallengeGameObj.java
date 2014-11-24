package com.otaku.mystique;

import java.io.Serializable;
import java.lang.reflect.Constructor;

import org.apache.http.entity.SerializableEntity;

public class ChallengeGameObj implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//private TeamObj[] teams ;
	private RoundObj[][] rounds;
	private int roundDuration;
	private int currentRound = 0;
	private int currentTeam = 0;
	private CategoryDataObj category;
	
	public ChallengeGameObj(int noOfTeams, int noOfRounds, int roundDuration) {
		//teams = new TeamObj[noOfTeams];
		rounds = new RoundObj[noOfTeams][noOfRounds];
		this.roundDuration = roundDuration;
	} 
	public int getNoOfTeams()
	{
		return rounds.length;
	}
	public void setScore(int score){
		rounds[currentTeam][currentRound].setScore(score);
	}
	public int getTotalTeamScore(int teamIndex)
	{
		int score = 0;
		for(int i = 0; i < rounds[teamIndex].length; i++)
			score += rounds[teamIndex][i].getScore();
		return score;
	}
	
	public void progressGame()
	{
		
		
		if(currentTeam == rounds.length-1)
		{
			currentRound++;
			currentTeam = 0;
		}
		else
			currentTeam++;
	}
	
	public void populateCurrentRound(CategoryDataObj category)
	{
		for(int i = 0; i < rounds.length; i++)
			rounds[i][currentRound] = new RoundObj(currentRound, category);
	}
	public int getRoundDuration(){
		
		return roundDuration*1000;
	}
	public int getCurrentTeam(){
		return currentTeam + 1;
	}
	public int getCurrentRound(){
		return currentRound + 1;
	}
	public CategoryDataObj getCurrentCategory(){
		
		return rounds[0][currentRound].getCategory();
	}
	public boolean isRoundEnded(){
		
		return currentTeam == rounds.length-1;
	}
	public boolean isGameEnded(){
		
		return currentTeam == rounds.length-1 && currentRound == rounds[0].length-1;
		
	}
	
}