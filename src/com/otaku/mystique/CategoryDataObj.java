package com.otaku.mystique;

import java.io.Serializable;

public class CategoryDataObj implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String name;
	String description;
	String dataObj;
	int location;
	String ArrayName;
	String isFree;
	int imagelocation;
	int thumblocation;
	
	
	public void setIsFree(){
		isFree = "purchased";
	}

}
