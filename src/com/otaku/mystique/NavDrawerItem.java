package com.otaku.mystique;

public class NavDrawerItem {

	    private int title;
	    private int icon;

	    public NavDrawerItem(){}
	    public NavDrawerItem(int title, int icon){
	        this.title = title;
	        this.icon = icon;
	    }
	    public int getTitle(){
	        return this.title;
	    }
	    public int getIcon(){
	        return this.icon;
	    }
	    public void setTitle(int title){
	        this.title = title;
	    }
	    public void setIcon(int icon){
	        this.icon = icon;
	    }
}
