package com.example.admin.mytripcart;

import android.content.Context;
import android.content.SharedPreferences;

public class Shreadpreference {

	private final String DBNAME = "Location";
	private static SharedPreferences sharedPreferences;
	private Context context;
	private static SharedPreferences.Editor spEditor;
	private final String URLRUNONETIME = "run";
	

	public Shreadpreference(Context contex) {
		this.context = contex;
		sharedPreferences = this.context.getSharedPreferences(DBNAME,
				Context.MODE_PRIVATE);
	}
	
	public boolean getURLRUNONETIME() {

		return sharedPreferences.getBoolean(URLRUNONETIME,false);
	}

	public void setURLRUNONETIME(boolean value) {
		spEditor = sharedPreferences.edit();
		spEditor.putBoolean(URLRUNONETIME, value);
		spEditor.commit();
	}
	

}
