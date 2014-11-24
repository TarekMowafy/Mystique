package com.otaku.mystique;


import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalculateScoreChallenge extends Activity {

	ChallengeGameObj challengeGame;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculate_score_challenge);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Intent intent = getIntent();
		intent.getExtras();
		challengeGame = (ChallengeGameObj) intent.getSerializableExtra("challengeGame");
		
		LinearLayout myLayout = (LinearLayout) findViewById(R.id.tv_scores);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		TextView[] pairs = new TextView[challengeGame.getNoOfTeams()];
		 for(int l=0; l<challengeGame.getNoOfTeams(); l++)
	        {
	            pairs[l] = new TextView(this);
	            pairs[l].setTextSize(30);
	            pairs[l].setLayoutParams(lp);
	            pairs[l].setId(l);
	            pairs[l].setText("Team "+ (l+1) +":	\u0009 \u0009"+ challengeGame.getTotalTeamScore(l));
	            pairs[l].setTextColor(Color.WHITE);
	            myLayout.addView(pairs[l]);
	        }
	}
}
