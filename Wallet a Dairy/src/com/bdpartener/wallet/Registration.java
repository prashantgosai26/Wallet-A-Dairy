package com.bdpartener.wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.R.string;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ShareCompat.IntentBuilder;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends Activity {

	MyDbHelper mHelper;
	EditText edemail, edpassword, edconfpassword, edanswer;
	Spinner spcurrency, spquestion;
	AlertDialog ad;
	ActionBar abobj;
	Button btnSubmit, btnCancel;
	SQLiteDatabase sqldb,mDBw;
	String create, insert, select, update;
	ArrayList<HashMap<String, Object>> arylist_cur, arylist_que;

	int cur_img[] = { R.drawable.dollar98, R.drawable.afghanistan1,
			R.drawable.brazil1, R.drawable.cuba1, R.drawable.denmark1,
			R.drawable.dollar98, R.drawable.euro30, R.drawable.iceland1,
			R.drawable.india1, R.drawable.indonesia1, R.drawable.iran1,
			R.drawable.kazakhstan1, R.drawable.laos1, R.drawable.malaysia1,
			R.drawable.nepal1, R.drawable.netherlands1, R.drawable.norway1,
			R.drawable.pakistan2, R.drawable.peru1, R.drawable.philippines1,
			R.drawable.poland1, R.drawable.pound12, R.drawable.romania1,
			R.drawable.russia1, R.drawable.sri2, R.drawable.sweden2,
			R.drawable.switzerland1, R.drawable.taiwan1, R.drawable.thailand1,
			R.drawable.turkey2, R.drawable.ukraine1, R.drawable.yen19,
			R.drawable.zimbabwe1 };

	String cur_name[] = { "Select Currency", "Afghanistan", "Brazil", "Cuba",
			"Denmark", "United Kingdom", "Europe", "Iceland", "India",
			"Indonesia", "Iran", "Kazakistan", "Laos", "Malaysia", "Nepal",
			"Netherland", "Norway", "Pakistan", "Peru", "Philippines",
			"Poland", "Pound", "Romania", "Russia", "Shri Lanka", "Sweden",
			"Switzerland", "Taiwan", "Thailand", "Turkey", "Ukraine", "Yen",
			"Zimbabwe" };
	String[] que_name = { "Selct Sequerity Question",
			"What was name of your Primary School?",
			"What was name of your first Friend?",
			"What is last Name of your Father?" };

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registartion);

		initControl();
		setCurrency();
		createTable();
		onSubmitButton();

	}

	public void initControl() {
		spquestion = (Spinner) findViewById(R.id.spSequerityQuestion);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		edconfpassword = (EditText) findViewById(R.id.etConformPassword);
		edemail = (EditText) findViewById(R.id.etemail);
		edpassword = (EditText) findViewById(R.id.etPassword);
		edanswer = (EditText) findViewById(R.id.etAnswer);
		arylist_cur = new ArrayList<HashMap<String, Object>>();
		arylist_que = new ArrayList<HashMap<String, Object>>();
		mHelper = new MyDbHelper(this);
		sqldb = openOrCreateDatabase("MyMoney",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);

		spquestion.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent().setClass(Registration.this,
						Login.class);
				startActivity(intent);
			}
		});
		
	}

	private void createTable() {
		// TODO Auto-generated method stub
		create = "create table if not exists registers (email text,pass text,question text,answer text);";
		sqldb.execSQL(create);
	}

	private void insertIntoDatabase(String editEmail, String editPassword,
			String spquestion, String editAnswer) {
		// TODO Auto-generated method stub
//		mDBw = mHelper.getWritableDatabase();
		String email = editEmail;
		String password = editPassword;
		String question = spquestion;
		String answer = editAnswer;
//		
//		ContentValues cv = new ContentValues(4);
//		cv.put(MyDbHelper.COL_ANS, answer);
//		cv.put(MyDbHelper.COL_PASS, password);
//		cv.put(MyDbHelper.COL_EMAIL, email);
//		cv.put(MyDbHelper.COL_QUE, question);
//		
//		mDBw.insert(MyDbHelper.TABLE_REGISTRATION, null, cv);
		insert = "insert into registers values('" + email + "','" + password
				+ "','" + question + "','" + answer + "');";
		sqldb.execSQL(insert);
		Toast.makeText(getApplicationContext(), "Register Successfully.",
				Toast.LENGTH_SHORT).show();
		Intent i = new Intent(getApplicationContext(), Login.class);
		startActivity(i);
//		mDBw.close();
	}

	private void displayRecord() {
		// TODO Auto-generated method stub

	}

	private void updateRecord() {
		// TODO Auto-generated method stub

	}

	// Registration conform onclick Submit button
	public void onSubmitButton() {
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String editEmail = edemail.getText().toString();
				final String editPassword = edpassword.getText().toString();
				final String editConPassword = edconfpassword.getText()
						.toString();
				final String spQuestion = spquestion.getSelectedItem()
						.toString();
				final String editAnswer = edanswer.getText().toString();
				
				if (!isValidEmail(editEmail)) {
					edemail.setError("Invalid Email");
				} else if (!isValidPassword(editPassword)) {
					edpassword.setError("Invalid Password");
				} else if (!editPassword.equals(editConPassword)) {
					edconfpassword.setError("Password Does Not Match");
				} else {
					insertIntoDatabase(editEmail, editPassword, spQuestion,
							editAnswer);
				}
				// Intent i = new Intent(getApplicationContext(),
				// CategoryIncome.class);
				// startActivity(i);
			}

			private boolean isValidEmail(String editEmail) {
				String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

				Pattern pattern = Pattern.compile(EMAIL_PATTERN);
				Matcher matcher = pattern.matcher(editEmail);
				return matcher.matches();
			}

			private boolean isValidPassword(String editPassword) {
				if (editPassword != null && editPassword.length() > 5) {
					return true;
				}
				return false;
			}
		});
	}

	public void setCurrency() {

		// layout inflater for display currency into view
		LayoutInflater li = LayoutInflater.from(getApplicationContext());
		View vi = li.inflate(R.layout.spinner_view, null);
		ListView lv = (ListView) vi.findViewById(R.id.lvCurrencyDisplayView);


		// Add question name into hash map and add hash map into arraylist
		for (int i = 0; i < que_name.length; ++i) {
			HashMap<String, Object> h_map = new HashMap<String, Object>();
			h_map.put("que_name", que_name[i]);
			arylist_que.add(h_map);
		}

		String from_que[] = { "que_name" };
		int to_que[] = { R.id.textView1 };

		// simple adpter for display question into spinner
		SimpleAdapter simadp_que = new SimpleAdapter(getApplicationContext(),
				arylist_que, R.layout.spinner_question_control, from_que,
				to_que);
		spquestion.setAdapter(simadp_que);

	}
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
