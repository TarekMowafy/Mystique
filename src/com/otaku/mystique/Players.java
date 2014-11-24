package com.otaku.mystique;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ViewSwitcher.ViewFactory;

public class Players extends ActionBarActivity {
	int mPosition = 0;
	Integer[] imageIDs = { 
			R.drawable.how_to_play_1, 
			R.drawable.how_to_play_2,
	        R.drawable.how_to_play_3,
	        R.drawable.how_to_play_4,
	        R.drawable.how_to_play_5
	        };
	ImageSwitcher switcher;
	
	private PopupWindow pwindo,howtoplayWindow;
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.players);
		
		//forces notification bar to disappear 
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		ImageView quick_btn = (ImageView) findViewById(R.id.quick_game);
		quick_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Players.this, Categories.class);
				startActivity(intent);
			}
		});
		ImageView challenge_btn = (ImageView) findViewById(R.id.challenge_game);
		challenge_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initiatePopupWindow();
			}
		});	
		
		Button howToPlay_btn = (Button) findViewById(R.id.btn_howtoplay);
		howToPlay_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initiateHowToPlayPopup();
			}
		});
	}

	protected void initiatePopupWindow() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) Players.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.option_popup,(ViewGroup) findViewById(R.id.popup_layout));

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		int x = metrics.widthPixels - metrics.widthPixels /6;
		int y = metrics.heightPixels - metrics.heightPixels/5;
		
		pwindo = new PopupWindow(layout, x, y,false);		
		pwindo.setAnimationStyle(R.style.PopupWindowAnimation);
		pwindo.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0,0);
		
		Button play_btn = (Button) layout.findViewById(R.id.kaml_btn);
		play_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RadioGroup RG_teams = (RadioGroup) layout.findViewById(R.id.radioGroupTeams);
				RadioGroup RG_rounds = (RadioGroup) layout.findViewById(R.id.radioGroupRounds);
				RadioGroup RG_timeOfRound = (RadioGroup) layout.findViewById(R.id.radioGroupTimeOfRound);
				
				RadioButton RB_teams = (RadioButton) layout.findViewById(RG_teams.getCheckedRadioButtonId());
				RadioButton RB_rounds = (RadioButton) layout.findViewById(RG_rounds.getCheckedRadioButtonId());
				RadioButton RB_timeOfRound = (RadioButton) layout.findViewById(RG_timeOfRound.getCheckedRadioButtonId());

				int teams = Integer.parseInt(String.valueOf(RB_teams.getText()));
				int rounds = Integer.parseInt(String.valueOf(RB_rounds.getText()));
				int timeofrounds = Integer.parseInt(String.valueOf(RB_timeOfRound.getText()));
				ChallengeGameObj challengeGame = new ChallengeGameObj(teams, rounds, timeofrounds);
				
				Intent intent = new Intent(Players.this, Categories.class);
				intent.putExtra("challengeGame", challengeGame);
				startActivity(intent);
				pwindo.dismiss();
			}
		});
		
		ImageView dismiss_popup = (ImageView) layout.findViewById(R.id.close_popup);
		dismiss_popup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pwindo.dismiss();
				
			}
		});
		
	}
	protected void initiateHowToPlayPopup(){
		LayoutInflater inflater = (LayoutInflater) Players.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.howtoplay_popup,(ViewGroup) findViewById(R.id.popup_layout_howtoplay));
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		int x = metrics.widthPixels - metrics.widthPixels /6;
		int y = metrics.heightPixels - metrics.heightPixels/5;
		
		howtoplayWindow = new PopupWindow(layout, x, y,false);		
		howtoplayWindow.setAnimationStyle(R.style.PopupWindowAnimation);
		howtoplayWindow.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0,0);
		
		
		
		
	    switcher = (ImageSwitcher) layout.findViewById(R.id.imageSwitcher1);
	    
	    switcher.setFactory(new ViewFactory() {
			
			@Override
			public View makeView() {
				// TODO Auto-generated method stub
				ImageView imageView = new ImageView(Players.this);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.MATCH_PARENT,ImageSwitcher.LayoutParams.MATCH_PARENT));
				return imageView;
			}
		});
	    switcher.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
	    switcher.setOutAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_out));
	    
	    
	    
	    
	    switcher.setImageResource(imageIDs[mPosition]);
	    ImageView right_btn = (ImageView) layout.findViewById(R.id.imgbtn_right);
	    right_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSwitch(null);
			}
		});
	    ImageView left_btn = (ImageView) layout.findViewById(R.id.imgbtn_left);
	    left_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLeftSwitch(null);
			}
		});
	    ImageView dismiss_popup = (ImageView) layout.findViewById(R.id.close_popup);
		dismiss_popup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				howtoplayWindow.dismiss();
				
			}
		});
	    
	
	}
	public void onSwitch(View view) {
		if(mPosition < 4){
			mPosition = mPosition + 1;

			switcher.setImageResource(imageIDs[mPosition]);
		}
	}
	public void onLeftSwitch(View view) {
		if(mPosition > 0){
			mPosition = mPosition - 1;

			switcher.setImageResource(imageIDs[mPosition]);
			
		}
	}
	public void onBackPressed() {
	    if (pwindo != null && pwindo.isShowing()) {
	        pwindo.dismiss();
	    } 
	    if (howtoplayWindow != null && howtoplayWindow.isShowing()) {
	        howtoplayWindow.dismiss();
	    }else {
	        super.onBackPressed();
	    }
	}
	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onResume();
	}

	

}
