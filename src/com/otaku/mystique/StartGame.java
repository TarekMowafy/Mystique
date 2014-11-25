package com.otaku.mystique;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StartGame extends Activity implements SensorEventListener, SurfaceHolder.Callback {
	
	private SensorManager sensorManager;
	private Sensor sensorAccelerometer;
	List<String> gameContent;
	static int itemIndex = 0;
	
	RelativeLayout layoutBack;
	
	TextView textViewTime ,displayedWord ,touchToStart;
	RelativeLayout background;
	
	CountDownTimer timerStart;
	
	Boolean startGame = false;
	
	int CORRECT_ANSWERS_COUNT = 0;
	int EXTRA_TIME = 5000;
	
	int roundDuration;
	boolean extraTime = false;
	boolean soundEffects  = false;
	
	String word;
	Boolean inGame = false;
	Boolean timerRunning = false;
	HashMap<String, Boolean> itemResult = new HashMap<String, Boolean>();
	
    private static final String TAG = "Recorder";
    public static SurfaceView mSurfaceView;
    public static SurfaceHolder mSurfaceHolder;
    public static Camera mCamera ;
    public static boolean mPreviewRunning;
    
    public Intent intent;
    public String rec= null;
    
    ChallengeGameObj challengeGame;
    
    SharedPreferences settings; 
    public static final String PREFS_NAME = "MyPrefsFile";
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_game);

		layoutBack = (RelativeLayout) findViewById(R.id.relativeLayout_back);
		layoutBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		Intent intent = getIntent();
		intent.getExtras();
		
		CategoryDataObj clickedObj;
		
		settings = getSharedPreferences(PREFS_NAME, 0);
		extraTime = settings.getBoolean("extraTime", false);
		soundEffects = settings.getBoolean("soundEffects", false);
		
		roundDuration = settings.getInt("roundDuration", 60)*1000;
		
		textViewTime= (TextView) findViewById(R.id.textViewtime);
		if(intent.hasExtra("challengeGame")){
		
			challengeGame = (ChallengeGameObj) intent.getSerializableExtra("challengeGame");
			textViewTime.setText("Team " + challengeGame.getCurrentTeam()+" Round "+challengeGame.getCurrentRound());
			clickedObj = challengeGame.getCurrentCategory();
			
		}else{
			clickedObj = (CategoryDataObj) intent.getSerializableExtra("clickedObj");
		}
		
		gameContent = Arrays.asList(clickedObj.dataObj.split(","));
		Collections.shuffle(gameContent);

		displayedWord = (TextView) findViewById(R.id.txt_place_on_forehead);
		background = (RelativeLayout) findViewById(R.id.background_game);

		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}
	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
	}
	protected void onDestroy() {
		super.onDestroy();
		sensorManager.unregisterListener(this);
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {		
		float valueAzimuth = event.values[0];
		float valuePitch = event.values[1];
		float valueRoll = event.values[2];
		
		if(valueRoll >-2 && valueRoll < 2){
			if(!startGame){
				startGame = true;
				if (!timerRunning){
					new CountDownTimer(3000, 1000) {
						@Override
						public void onTick(long millisUntilFinished) {
							displayedWord.setText("" + (millisUntilFinished / 1000  ) ); 
						}
						@Override
						public void onFinish() {
							// TODO Auto-generated method stub
							word = getRandString(gameContent);
							background.setBackgroundResource(R.drawable.background_game);
							displayedWord.setText(word);
							inGame = true;
							
							intent = new Intent(StartGame.this, RecorderService.class);
	     	                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     	                startService(intent);
	     	                createTimer();
						}
					}.start();
				}else{
					word = getRandString(gameContent);
					background.setBackgroundResource(R.drawable.background_game);
					displayedWord.setText(word);
					inGame = true;
				}
			}
		}
		// Correct answer
		if(valueRoll < -7){
			if(inGame){
				CORRECT_ANSWERS_COUNT++;
				itemResult.put(word, true);
				new CountDownTimer(1000,1000) {
					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						displayedWord.setText(R.string.correct);
						background.setBackgroundResource(R.drawable.background_correct);
						onDestroy();	
					}
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						onResume();
					}
				}.start();
				startGame = false;	
			}
		}
		//Pass answer
		if(valueRoll > 7){
			if(inGame){
				itemResult.put(word, false);
				new CountDownTimer(1000,1000) {
					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						displayedWord.setText(R.string.pass);
						background.setBackgroundResource(R.drawable.background_pass);
						onDestroy();
					}
					
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						onResume();
					}
				}.start();
				startGame = false;
				
			}
		}	
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	public String getRandString(List<String> gameContent) {
		String randomStr;
		if(itemIndex == gameContent.size()){
			itemIndex = 0;
			randomStr = gameContent.get(itemIndex);
			itemIndex++;
			
		}else{
			randomStr = gameContent.get(itemIndex);
			itemIndex++;
		}
		return randomStr;
	}

	public void createTimer()
	{	
		timerStart = new CountDownTimer((roundDuration)+2000 , 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				timerRunning = true;
				String ms = String.format("%02d:%02d",
						TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) 
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
	 
						TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))); 
				
				textViewTime.setText(ms); 	
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				if(extraTime){
					new CountDownTimer((CORRECT_ANSWERS_COUNT * 3)*1000,1000) {
						@Override
						public void onTick(long millisUntilFinished) {
							// TODO Auto-generated method stub
							timerRunning = true;
							String ms = String.format("%02d:%02d",
									TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) 
									- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
				 
									TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
									TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))); 
							
							textViewTime.setText("Extra Time \n"+ms);
							textViewTime.setTextColor(Color.RED);
						}
						@Override
						public void onFinish() {
							// TODO Auto-generated method stub
							itemIndex = 0;
							CORRECT_ANSWERS_COUNT = 0;
							textViewTime.setText("Completed."); 
							stopService(new Intent(StartGame.this, RecorderService.class));
							String videoPath = RecorderService.videoPath;
				            rec="Stopped";
				            onDestroy();
				            Intent intent = new Intent(StartGame.this, CalculateScore.class);
				            intent.putExtra("itemResult", itemResult);
				            intent.putExtra("videoPath", videoPath);
				            if(challengeGame != null){
				            	intent.putExtra("challengeGame", challengeGame);
				            }
				            startActivity(intent);
				            finish();
						}
					}.start();
				}else{
					itemIndex = 0;
					textViewTime.setText("Completed."); 
					stopService(new Intent(StartGame.this, RecorderService.class));
					String videoPath = RecorderService.videoPath;
		            rec="Stopped";
		            onDestroy();
		            Intent intent = new Intent(StartGame.this, CalculateScore.class);
		            intent.putExtra("itemResult", itemResult);
		            intent.putExtra("videoPath", videoPath);
		            if(challengeGame != null){
		            	intent.putExtra("challengeGame", challengeGame);
		            }
		            startActivity(intent);
		            finish();
				}
				
			}
		}.start();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		stopService(new Intent(StartGame.this, RecorderService.class));
	}

	
}
