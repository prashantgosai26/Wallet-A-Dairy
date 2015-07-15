package com.bdpartener.wallet;

import java.util.HashMap;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Login extends Activity {

	SQLiteDatabase sqldb;
	String semail, spass;
	Cursor c;
	int count = 3;
	String[] email_array = new String[10];
	String[] pass_array = new String[10];
	EditText email, pass;
	ActionBar abobj;
	public static final String PREFS_NAME = "LoginPrefs";
//	SessionManager manager;
	Button btnforgatpass, btnregister, btnlogin;

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);
		email = (EditText) findViewById(R.id.etemail_login);
		pass = (EditText) findViewById(R.id.etPassword_login);
		sqldb = openOrCreateDatabase("MyMoney",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
//		manager = new SessionManager(getApplicationContext());
		btnlogin = (Button) findViewById(R.id.btnSubmit_login);
		btnforgatpass = (Button) findViewById(R.id.btnforgot_login);
		btnregister = (Button) findViewById(R.id.btnRegistration_login);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getString("logged", "").toString().equals("logged")) {
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
        }
		btnforgatpass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						ForgotPassword.class);
				startActivity(i);
			}
		});

		btnlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String email_edittext = email.getText().toString();
				String pass_edittext = pass.getText().toString();
				c = sqldb.rawQuery("select * from registers", null);
				boolean flag = true;
				if (c != null) {
					if (c.moveToFirst()) {

						do {
							email_array[0] = c.getString(c
									.getColumnIndex("email"));
							pass_array[0] = c.getString(c
									.getColumnIndex("pass"));
							if (!c.isLast()) {
								if (email_array[0].equals(email_edittext)
										&& pass_array[0].equals(pass_edittext)) {
									Toast.makeText(getApplicationContext(),
											"Login Successfull",
											Toast.LENGTH_SHORT).show();
									SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			                        SharedPreferences.Editor editor = settings.edit();
			                        editor.putString("logged", "logged");
			                        editor.commit();
//									manager.createLoginSession("name",email_edittext);
									Intent cat = new Intent(getApplicationContext(),
											Transaction_Expense.class);
									startActivity(cat);
									c.close();
									break;
								}
							} else {
								if (email_array[0].equals(email_edittext)
										&& pass_array[0].equals(pass_edittext)) {
									Toast.makeText(getApplicationContext(),
											"Login success", Toast.LENGTH_SHORT)
											.show();
//									manager.createLoginSession("name",email_edittext);
									Intent cat = new Intent(getApplicationContext(),
											IncomeAdd.class);
									startActivity(cat);
									c.close();
									break;
								} else {
									flag = false;
								}
							}

						} while (c.moveToNext());
						if (flag == false) {
							Toast.makeText(getApplicationContext(),
									"Username And Password is Wrong",
									Toast.LENGTH_SHORT).show();

						}
					}
				}

			}
		});
		
		btnregister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						Registration.class);
				startActivity(i);
			}
		});

	}
	@Override
	public void onBackPressed() {
		int backButtonCount = 0;
		// TODO Auto-generated method stub
		if(backButtonCount >= 1)
	    {
	        Intent intent = new Intent(Intent.ACTION_MAIN);
	        intent.addCategory(Intent.CATEGORY_HOME);
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(intent);
	    }
	    else
	    {
	        Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
	        backButtonCount++;
	    }
		super.onBackPressed();
	}
}
