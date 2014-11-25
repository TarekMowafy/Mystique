package com.otaku.mystique;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Locale;

import mp.MpUtils;
import mp.PaymentRequest;
import mp.PaymentResponse;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Categories extends Activity {
	
		SharedPreferences lang, settings; 
		SharedPreferences.Editor editor ;
		public static final String PREFS_NAME = "MyPrefsFile";
	
		 ArrayList<NavDrawerItem> dataList;
		 GridView grid;
		 private PopupWindow pwindo;
	     DrawerLayout dLayout;
	     ListView dList;
	     NavDrawerListAdapter adapter;
	     DisplayMetrics metrics = new DisplayMetrics();
	     ChallengeGameObj challengeGame;
	     
	     CategoryDataObj ClickedObj;
	     
	     String language = " ";
	     
	     private static String SERVICE_ID = "9023be583d1320ef78ec22083700f51e";
	 	 private static String APP_SECRET = "a922538f3551a8f8756c418959e97237";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categories);
		
		
		lang = getSharedPreferences(PREFS_NAME, 0);
		language = lang.getString("language", "fr");
		
		
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Intent intent = getIntent();
		intent.getExtras();
		if(intent.hasExtra("challengeGame")){
			challengeGame = (ChallengeGameObj) intent.getSerializableExtra("challengeGame");
		}
			
		

		
		ArrayList<CategoryObj> CategoryArray = new ArrayList<CategoryObj>();
		CategoryObj catObj;
	    String name;
	    int id;
		Field[] fields = R.array.class.getFields();
		
		for(final Field field : fields) {
			catObj = new CategoryObj();
		      
		       try{
		    	   name = field.getName(); //name of string
		           id = field.getInt(R.string.class); //id of string
		           catObj.name = name;
		           catObj.location = id;
		       }
		       catch (Exception ex)
		       {
		           
		       }
		       
		       CategoryArray.add(catObj);
		    }
		
		Resources res = getResources();
		ArrayList<CategoryDataObj> CategoryDataArray = new ArrayList<CategoryDataObj>();
		CategoryDataObj catDataObj;
		String[] DataStrArr;
		String imageName;
		String thumbName;
		 
		for(CategoryObj co : CategoryArray)
		{
			catDataObj = new CategoryDataObj();
			DataStrArr = res.getStringArray(co.location);
			
			catDataObj.ArrayName = co.name;
			catDataObj.location = co.location;
			catDataObj.name = DataStrArr[0];
			catDataObj.description = DataStrArr[1];
			catDataObj.dataObj = DataStrArr[2];
			catDataObj.isFree = DataStrArr[3];
			
			thumbName = "thumb_" + co.name;
			catDataObj.thumblocation =  getResources().getIdentifier(thumbName , "drawable", getPackageName());
			
			imageName = "img_" + co.name;
			catDataObj.imagelocation =  getResources().getIdentifier(imageName , "drawable", getPackageName());
			
			CategoryDataArray.add(catDataObj);
		}
		
		/* Categories View */
		CustomGrid gridadapter = new CustomGrid(Categories.this, CategoryDataArray);
		
	    grid=(GridView)findViewById(R.id.gridView);
        grid.setAdapter(gridadapter);
        grid.setSelector(android.R.color.transparent);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                	ClickedObj = (CategoryDataObj)grid.getItemAtPosition(position);
                	initiatePopupWindow(ClickedObj);
                	
                }
            });
  
		
		/* Navigation Drawer items and actions */

	        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        dList = (ListView) findViewById(R.id.left_drawer);
	        
	        dataList = new ArrayList<NavDrawerItem>();
	        dataList.add(new NavDrawerItem(R.string.home, R.drawable.nav_home));
	        dataList.add(new NavDrawerItem(R.string.all, R.drawable.nav_all));
	        dataList.add(new NavDrawerItem(R.string.adjust, R.drawable.nav_adjust));
	        dataList.add(new NavDrawerItem(R.string.favorite, R.drawable.nav_favorite));
	        dataList.add(new NavDrawerItem(R.string.settings, R.drawable.nav_settings));
	        
	        
	        
	        
	        ImageView btn_menu = (ImageView) findViewById(R.id.btn_menu);
	        
	        btn_menu.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dLayout.openDrawer(Gravity.LEFT);
				}
			});

		    adapter = new NavDrawerListAdapter(this, dataList);
		    

	        
	        dList.setAdapter(adapter);
			dList.setSelector(android.R.color.darker_gray);
	        dList.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
					
					dLayout.closeDrawers();					
					switch (position) {
					case 0:
						Intent intent  = new Intent(Categories.this, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
						
						break;

					case 1:
						
						break;
					case 2:
							// TODO Auto-generated method stub
							LayoutInflater inflater = (LayoutInflater) Categories.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							final View layout = inflater.inflate(R.layout.settings_popup,(ViewGroup) findViewById(R.id.popup_layout));
							
							DisplayMetrics metrics = new DisplayMetrics();
							getWindowManager().getDefaultDisplay().getMetrics(metrics);
							
							int x = metrics.widthPixels - metrics.widthPixels /8;
							int y = metrics.heightPixels - metrics.widthPixels /10;
							
							pwindo = new PopupWindow(layout, x, y,false);		
							pwindo.setAnimationStyle(R.style.PopupWindowAnimation);
							pwindo.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0,0);
							
							settings = getSharedPreferences(PREFS_NAME, 0);
							
							CheckBox CB_extraTime = (CheckBox) layout.findViewById(R.id.CB_extraTime);
							
							CB_extraTime.setChecked(settings.getBoolean("extraTime", false));
							
							CheckBox CB_soundEffects = (CheckBox) layout.findViewById(R.id.CB_soundEffects);
							CB_soundEffects.setChecked(settings.getBoolean("soundEffects", false));
							
							RadioButton RB_sixty = (RadioButton) layout.findViewById(R.id.rb_sixty);
							RadioButton RB_ninty = (RadioButton) layout.findViewById(R.id.rb_ninty);
							RadioButton RB_oneTwenty = (RadioButton) layout.findViewById(R.id.rb_oneTwenty);

							
							switch (settings.getInt("roundDuration", 60)) {
							case 60:
								RB_sixty.setChecked(true);
								
								break;
							case 90:
								RB_ninty.setChecked(true);
								
								break;
							case 120:
								RB_oneTwenty.setChecked(true);
								
								break;

							default:
								break;
							}

							
							Button continue_btn = (Button) layout.findViewById(R.id.continue_btn);
							
							continue_btn.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									RadioGroup RG_timeOfRound = (RadioGroup) layout.findViewById(R.id.radioGroupTimeOfRound);
									RadioButton RB_timeOfRound = (RadioButton) layout.findViewById(RG_timeOfRound.getCheckedRadioButtonId());
									
									CheckBox CB_extraTime = (CheckBox) layout.findViewById(R.id.CB_extraTime);
									CheckBox CB_soundEffects = (CheckBox) layout.findViewById(R.id.CB_soundEffects);
									
									 boolean extraTime = CB_extraTime.isChecked();
									 boolean soundEffects = CB_soundEffects.isChecked();
									 int roundDuration = Integer.parseInt(String.valueOf(RB_timeOfRound.getText()));
									settings = getSharedPreferences(PREFS_NAME, 0);
									editor = settings.edit();
									editor.putBoolean("extraTime", extraTime);
									editor.putBoolean("soundEffects", soundEffects);
									editor.putInt("roundDuration", roundDuration);
									editor.commit();
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
							
						
						break;
					case 3:
	
						break;
	
					case 4:
	
						break;
					}
					
				}
	        });
	}
	protected void initiatePopupWindow(final CategoryDataObj clickedObj) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) Categories.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.category_popup,(ViewGroup) findViewById(R.id.popup_category));
		
		
		
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		int x = metrics.widthPixels - metrics.widthPixels /6;
		int y = metrics.heightPixels - metrics.heightPixels/5;
		
		pwindo = new PopupWindow(layout, x, y,false);				
		pwindo.setAnimationStyle(R.style.PopupWindowAnimation);
		pwindo.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0,0);
		
		TextView title = (TextView) layout.findViewById(R.id.catName);
		TextView description = (TextView) layout.findViewById(R.id.catDescription);
		ImageView thumbnail = (ImageView) layout.findViewById(R.id.thumbView);
		Button startgame_btn = (Button) layout.findViewById(R.id.startGame);
		
		title.setText(clickedObj.name);
		description.setText(clickedObj.description);
		thumbnail.setImageResource(clickedObj.thumblocation);
		
		if(clickedObj.isFree.equals("free") || clickedObj.isFree.equals("purchased")){
			startgame_btn.setText(R.string.startGame);
			 startgame_btn.setOnClickListener(new View.OnClickListener() {
				 
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startTheGame(clickedObj);
				}
			});
		}else{
			startgame_btn.setText(R.string.purchase);
			  startgame_btn.setBackgroundColor(Color.parseColor("#ec1e79"));
			  startgame_btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					PaymentRequest.PaymentRequestBuilder builder = new PaymentRequest.PaymentRequestBuilder();
	                builder.setService(SERVICE_ID, APP_SECRET);
	                builder.setDisplayString(clickedObj.ArrayName);     
	                builder.setProductName("Categories_"+clickedObj.ArrayName);  // non-consumable purchases are restored using this value
	                builder.setType(MpUtils.PRODUCT_TYPE_NON_CONSUMABLE);        // non-consumable items can be later restored
	                builder.setIcon(clickedObj.thumblocation);
	                PaymentRequest pr = builder.build();
	                
	                if(language.equals("fr")){
	                	Locale locale = new Locale("en"); 
					    Locale.setDefault(locale);
					    Configuration config = new Configuration();
					    config.locale = locale;
					    getResources().updateConfiguration(config,null);
	                }
	                makePayment(pr);

				}
			});
		}


		

		ImageView dismiss_popup = (ImageView) layout.findViewById(R.id.close_popup);
		dismiss_popup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pwindo.dismiss();
				
			}
		});
		
	}
	public void onBackPressed() {
	    if (pwindo != null && pwindo.isShowing()) {
	        pwindo.dismiss();
	    } else {
	        super.onBackPressed();
	    }
	    
	}
	
	
	public void startTheGame(CategoryDataObj clickedObj) {
		if( language.equals("fr")){
        	Locale locale = new Locale(language); 
		    Locale.setDefault(locale);
		    Configuration config = new Configuration();
		    config.locale = locale;
		    getResources().updateConfiguration(config,null);
        }
		Intent intent= new Intent(Categories.this,StartGame.class);
		intent.putExtra("clickedObj", clickedObj);
		
		if(challengeGame != null){
		
			challengeGame.populateCurrentRound(clickedObj);
			intent.putExtra("challengeGame", challengeGame);
		}
		
		startActivity(intent);
		pwindo.dismiss();	
		
	}
	
	private static final int REQUEST_CODE = 1234; // Can be anything
	
	protected final void makePayment(PaymentRequest payment) {
		startActivityForResult(payment.toIntent(this), REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			if(data == null) {
				return;
			}
			
			// OK
			if (resultCode == RESULT_OK) {
				PaymentResponse response = new PaymentResponse(data);
				
				
				switch (response.getBillingStatus()) {
					case MpUtils.MESSAGE_STATUS_BILLED:
						
						
						startTheGame(ClickedObj);
						
					break;
				case MpUtils.MESSAGE_STATUS_FAILED:
					// ...
					break;
				case MpUtils.MESSAGE_STATUS_PENDING:
					// ...
					break;	
				}
			// Cancel
			} else {
				// ..
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

}
