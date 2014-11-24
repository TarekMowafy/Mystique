package com.otaku.mystique;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity{
	String languageToLoad;
	TextView title;
	TextView desc;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		
		
		
		Button btn_eng = (Button) findViewById(R.id.btn_eng);
		Button btn_ar = (Button) findViewById(R.id.btn_ar);
		Button btn_franco = (Button) findViewById(R.id.btn_franco);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		btn_ar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				languageToLoad  = "ar"; // your language
				
				Locale locale = new Locale(languageToLoad); 
			    Locale.setDefault(locale);
			    Configuration config = new Configuration();
			    config.locale = locale;
			    getResources().updateConfiguration(config,null);
			    
				
				Intent intent = new Intent(MainActivity.this, Players.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    	startActivity(intent);
				
			}
		});
		btn_eng.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				languageToLoad  = "en"; // your language
				
				Locale locale = new Locale(languageToLoad); 
			    Locale.setDefault(locale);
			    Configuration config = new Configuration();
			    config.locale = locale;
			    getResources().updateConfiguration(config,null);
				
				Intent intent = new Intent(MainActivity.this, Players.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    	startActivity(intent);
				
			}
		});
		
		btn_franco.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						languageToLoad  = "fr"; // your language
						
						Locale locale = new Locale(languageToLoad); 
					    Locale.setDefault(locale);
					    Configuration config = new Configuration();
					    config.locale = locale;
					    getResources().updateConfiguration(config,null);
						
						Intent intent = new Intent(MainActivity.this, Players.class);
		//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    	startActivity(intent);
						
					}
				});
		
		
	    
	    
	    
	    

		
	}

	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onResume();
	}

	
}
