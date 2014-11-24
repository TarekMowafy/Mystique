package com.otaku.mystique;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalculateScore extends Activity {

	ChallengeGameObj challengeGame;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculate_score);
		int scoreCount = 0;
		Intent intent = getIntent();
		intent.getExtras();
		if (intent.hasExtra("challengeGame")) {
			challengeGame = (ChallengeGameObj) intent
					.getSerializableExtra("challengeGame");
		}
		RelativeLayout layoutPlay = (RelativeLayout) findViewById(R.id.relativeLayout_play);
		layoutPlay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (challengeGame != null) {
					if (challengeGame.isGameEnded()) {
						
						Intent intent = new Intent(CalculateScore.this,CalculateScoreChallenge.class);
						intent.putExtra("challengeGame", challengeGame);
						startActivity(intent);
						finish();
						
					} else {

						if (challengeGame.isRoundEnded()) {
							challengeGame.progressGame();
							Intent intent = new Intent(CalculateScore.this,
									Categories.class);
							intent.putExtra("challengeGame", challengeGame);
							
							startActivity(intent);
							

						} else {
							challengeGame.progressGame();
							Intent intent = new Intent(CalculateScore.this,
									StartGame.class);
							intent.putExtra("challengeGame", challengeGame);
							
							startActivity(intent);
							
						}

					}

				} else {

					Intent intent = new Intent(CalculateScore.this,
							Categories.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				}
			}
		});

		RelativeLayout layoutBack = (RelativeLayout) findViewById(R.id.relativeLayout_back);
		layoutBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		HashMap<String, Boolean> itemResult = (HashMap<String, Boolean>) intent
				.getSerializableExtra("itemResult");
		final String videoPath = intent.getStringExtra("videoPath");

		ImageView play_video = (ImageView) findViewById(R.id.btn_watchVideo);

		TextView scoreTxt = (TextView) findViewById(R.id.textViewScore);
		LinearLayout myLayout = (LinearLayout) findViewById(R.id.textView_Layout);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		TextView[] pairs = new TextView[itemResult.size()];

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		int i = 0;
		for (HashMap.Entry<String, Boolean> entry : itemResult.entrySet()) {
			pairs[i] = new TextView(this);
			pairs[i].setTextSize(20);
			pairs[i].setGravity(Gravity.CENTER);
			pairs[i].setLayoutParams(lp);
			pairs[i].setId(i);
			pairs[i].setText(entry.getKey());
			if (!entry.getValue()) {
				pairs[i].setTextColor(Color.RED);
			} else {
				pairs[i].setTextColor(Color.WHITE);
			}
			myLayout.addView(pairs[i]);
			i++;
			if (entry.getValue()) {
				scoreCount++;
			}

		}
		scoreTxt.setText(String.valueOf(scoreCount));
		if(challengeGame != null){
			challengeGame.setScore(scoreCount);
		}
		

		play_video.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CalculateScore.this,
						WatchVideo.class);
				intent.putExtra("videoPath", videoPath);
				startActivity(intent);
			}
		});

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onResume();
	}
}
