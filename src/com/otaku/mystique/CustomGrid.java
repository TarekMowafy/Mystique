package com.otaku.mystique;

import java.util.ArrayList;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class CustomGrid extends BaseAdapter{
	

    private Context mContext;
    
    SharedPreferences pref;
    
    
//    private final int[] categ;
//    private final int[] Imageid;
    private ArrayList<CategoryDataObj> ArrayCatDataObj;
    
    public CustomGrid(Context c,ArrayList<CategoryDataObj> ArrayCatDataObj) {
    	mContext = c;
    	this.ArrayCatDataObj = ArrayCatDataObj;
    	pref = mContext.getSharedPreferences("MyPref", 0);
	}
	
//    public CustomGrid(Context c,int[] categ,int[] Imageid ) {
//    	mContext = c;
//    	this.Imageid = Imageid;
//    	this.categ = categ;
//	}
    @Override
    public int getCount() {
      // TODO Auto-generated method stub
      return ArrayCatDataObj.size();
    }
    @Override
    public Object getItem(int position) {
      // TODO Auto-generated method stub
      return ArrayCatDataObj.get(position);
    }
    @Override
    public long getItemId(int position) {
      // TODO Auto-generated method stub
      return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      // TODO Auto-generated method stub
      View grid;
      LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      
//      if (convertView == null) {
    	  
        grid = new View(mContext);
        grid = inflater.inflate(R.layout.onecategory, null);
        
//      } else {
//        grid = (View) convertView;
//      }
      TextView textView = (TextView) grid.findViewById(R.id.categoryName);
      ImageView imageView = (ImageView)grid.findViewById(R.id.categoryImage);
      textView.setText(ArrayCatDataObj.get(position).name);
      imageView.setImageResource(ArrayCatDataObj.get(position).imagelocation);
      
      if (ArrayCatDataObj.get(position).isFree.equals("purchase")){
    	  ImageView purchImg = (ImageView) grid.findViewById(R.id.imagePurchase);
    	  purchImg.setImageResource(R.drawable.img_purchase);
//    	  if(pref.getBoolean(ArrayCatDataObj.get(position).name, true)){
//    		  ArrayCatDataObj.get(position).setIsFree();
//    	  }else{
//    		  ImageView purchImg = (ImageView) grid.findViewById(R.id.imagePurchase);
//        	  purchImg.setImageResource(R.drawable.img_purchase);
//    	  }
    	  
    	  
      }
      
      return grid;
    }
}