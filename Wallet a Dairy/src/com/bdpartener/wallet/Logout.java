package com.bdpartener.wallet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class Logout extends Activity{
	 public static final String PREFS_NAME = "LoginPrefs";

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.logout_file);
	    
	        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("logged");
            editor.commit();
            finish();
	    }

//	    public boolean onCreateOptionsMenu(Menu menu) {
//	        MenuInflater Inflater = getMenuInflater();
//	        Inflater.inflate(R.menu., menu);
//	        return true;
//	    }

//	    @Override
//	    public boolean onOptionsItemSelected(MenuItem item) {
//	        if (item.getItemId() == R.id.logout) {
//	            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//	            SharedPreferences.Editor editor = settings.edit();
//	            editor.remove("logged");
//	            editor.commit();
//	            finish();
//	        }
//	        return super.onOptionsItemSelected(item);
//	    }
	}