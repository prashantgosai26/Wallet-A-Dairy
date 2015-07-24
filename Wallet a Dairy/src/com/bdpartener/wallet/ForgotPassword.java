package com.bdpartener.wallet;

import java.util.ArrayList;
import java.util.HashMap;


import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

@SuppressLint("NewApi")
public class ForgotPassword extends Activity {

	Spinner spQue;
	ActionBar abobj;
	EditText edAnswer, edEmail;
	Button btnSubmit;
	ArrayList<HashMap<String, Object>> arrayquestion;
	// Question array for Sequrity
	String que_name[] = { "Selct Sequerity Question",
			"What was the name of your primary school?",
			"What is the first friend name?",
			"What is the last Name of your Father?" };

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);

//		abobj = getActionBar();
//		ColorDrawable colorDrawable = new ColorDrawable();
//		colorDrawable.setColor(0XffE37225);
//		abobj.setBackgroundDrawable(colorDrawable);
//		abobj.setTitle("FORGOT PASSWORD");
//		abobj.setIcon(new ColorDrawable(getResources().getColor(
//				android.R.color.transparent)));
//		abobj.setHomeAsUpIndicator(R.drawable.menu_for_drawable);
//		// abobj.setHomeButtonEnabled(true);
//		abobj.setDisplayHomeAsUpEnabled(true);
		// abobj.setIcon(R.drawable.cancel);
		// inializa all control
		initcontrol();
	}

	public void initcontrol() {
		spQue = (Spinner) findViewById(R.id.spSequerityQuestion_forgotlink);
		edAnswer = (EditText) findViewById(R.id.etAnswer_forgotlink);
		edEmail = (EditText) findViewById(R.id.etemail_forgotlink);
		btnSubmit = (Button) findViewById(R.id.btnSubmit_forgotlink);
		arrayquestion = new ArrayList<HashMap<String,Object>>();
		setQustionintoSpinner();
	}

	public void setQustionintoSpinner() {
		//Add question name into hash map and add hash map into arraylist
		for (int i = 0; i < que_name.length; ++i) {
			HashMap<String, Object> h_map = new HashMap<String, Object>();
			h_map.put("que_name", que_name[i]);
			arrayquestion.add(h_map);
		}

		String fromm[] = { "que_name" };
		int too[] = { R.id.textView1 };
		
		//simple adpter for display question into spinner
		SimpleAdapter simadp_que = new SimpleAdapter(getApplicationContext(),
				arrayquestion, R.layout.spinner_question_control, fromm, too);
		spQue.setAdapter(simadp_que);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot_password, menu);
		return true;
	}

}
