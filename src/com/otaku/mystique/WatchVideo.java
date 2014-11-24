package com.otaku.mystique;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Video;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class WatchVideo extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_watch_video);
		
		RelativeLayout layoutPlay = (RelativeLayout) findViewById(R.id.relativeLayout_play);
		layoutPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(WatchVideo.this,Categories.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		
		RelativeLayout layoutBack = (RelativeLayout) findViewById(R.id.relativeLayout_back_to_score);
		layoutBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Intent intent = getIntent();
		intent.getExtras();
		
		final String videoPath = intent.getStringExtra("videoPath");
		
		VideoView vid = (VideoView) findViewById(R.id.videoView);
		vid.setMediaController(new MediaController(this));
		vid.setVideoPath(videoPath);
		vid.start();
		vid.requestFocus();
		
		Button btn_share = (Button) findViewById(R.id.btn_share);
		btn_share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				ContentValues content = new ContentValues(4);
				content.put(Video.VideoColumns.DATE_ADDED,
				System.currentTimeMillis() / 1000);
				content.put(Video.Media.MIME_TYPE, "video/mp4");
				content.put(MediaStore.Video.Media.DATA, videoPath);
				ContentResolver resolver = getBaseContext().getContentResolver();
				Uri uri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, content);

				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("video/*");
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Title");
				sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM,uri);
				startActivity(Intent.createChooser(sharingIntent,"Share the video on..")); 
				
			}
		});
	}


}
