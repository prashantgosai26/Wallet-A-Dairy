package com.bdpartener.wallet;

import java.util.ArrayList;
import java.util.HashMap;


import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ForgotPassword extends Activity {

	Spinner spQue;
	ActionBar abobj;
	SQLiteDatabase mDBr;
	MyDbHelper mHelper;
	Cursor cursor;
	TextView tvAnswer;
	EditText edAnswer, edEmail;
	Button btnSubmit,btnLogin;
	ArrayList<HashMap<String, Object>> arrayquestion;
	// Question array for Sequrity
	String que_name[] = { "Selct Sequerity Question",
			"What was name of your Primary School?",
			"What was name of your first Friend?",
			"What is last Name of your Father?"};

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
		mDBr=openOrCreateDatabase("MyMoney",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		initcontrol();
	}

	public void initcontrol() {
		spQue = (Spinner) findViewById(R.id.spSequerityQuestion_forgotlink);
		edAnswer = (EditText) findViewById(R.id.etAnswer_forgotlink);
		edEmail = (EditText) findViewById(R.id.etemail_forgotlink);
		btnSubmit = (Button) findViewById(R.id.btnSubmit_forgotlink);
		btnLogin = (Button) findViewById(R.id.btnlogin_forgotlink);
		tvAnswer = (TextView) findViewById(R.id.tvAnswer_forgotlink);
		arrayquestion = new ArrayList<HashMap<String,Object>>();
	
		spQue.setVisibility(View.VISIBLE);
		edAnswer.setVisibility(View.VISIBLE);
		edEmail.setVisibility(View.VISIBLE);
		btnSubmit.setVisibility(View.VISIBLE);
		tvAnswer.setVisibility(View.INVISIBLE);			
		btnLogin.setVisibility(View.INVISIBLE);
		setQustionintoSpinner();
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getValue();
			}
		});
	}

	private void getValue() {
		
		final String Que = spQue.getSelectedItem().toString();
		final String Ans = edAnswer.getText().toString();
		final String Email = edEmail.getText().toString();
		checkDataBase(Que,Ans,Email);
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

	public void checkDataBase(String Question,String Answer,String Email){
		String que = Question;
		String ans = Answer;
		String email = Email;
		cursor=mDBr.rawQuery("select * from registers where email='"+email+"'", null);
		
		if (cursor!=null) {
			if(cursor.moveToFirst()){
				do {
					if(que.equals(cursor.getString(cursor.getColumnIndex("question")))){
						if(ans.equals(cursor.getString(cursor.getColumnIndex("answer")))){
							spQue.setVisibility(View.INVISIBLE);
							edAnswer.setVisibility(View.INVISIBLE);
							edEmail.setVisibility(View.INVISIBLE);
							btnSubmit.setVisibility(View.INVISIBLE);
							tvAnswer.setVisibility(View.VISIBLE);			
							btnLogin.setVisibility(View.VISIBLE);
							tvAnswer.setText(cursor.getString(cursor.getColumnIndex("pass")));
							btnLogin.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
								Intent intent = new Intent(ForgotPassword.this,Login.class);
								startActivity(intent);
								}
							});
						}
						else{
							edAnswer.setError("Answer do not Match");
						}
					}
					else{
						Toast.makeText(this, "Selected Question not Match", 100).show();
					}
					
				} while (cursor.moveToNext());
			}
			else{
				Toast.makeText(this, "Email Address Not Found", 100).show();
			}
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot_password, menu);
		return true;
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
