package com.bdpartener.wallet;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.revmob.RevMob;
import com.revmob.ads.fullscreen.RevMobFullscreen;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class BudgetAdd extends BaseActivity implements OnClickListener {
	Button btnonsubmit, btncancel;
	ImageView imgviewsetcatagory_image;
	TextView tvdate, tvcategory;
	EditText edamount, ednote;

	DatePickerDialog dialog;
	int mday, mmonth, myear;
	String str, create, name, insert;
	Calendar calendar = Calendar.getInstance();
	private boolean exit = false;
	SimpleDateFormat dateFormat;
	MyDbHelper mHelper;
	SQLiteDatabase mDbW, mDbR;
	Cursor mCursor;
	String date;
	SimpleCursorAdapter mAdapter;
	int Total, Amt_Income;

	RevMobFullscreen fullscreen;
	RevMob mob;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_add_budget);
		getLayoutInflater().inflate(R.layout.activity_add_budget, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);
		mHelper = new MyDbHelper(this);
		edamount = (EditText) findViewById(R.id.edamount_addmoneybd);
		ednote = (EditText) findViewById(R.id.ednote_addmoneybd);
		btnonsubmit = (Button) findViewById(R.id.btnSubmit_addmoneybd);
		btnonsubmit.setOnClickListener(this);
		btncancel = (Button) findViewById(R.id.btncancel_budget_add);
		btncancel.setOnClickListener(this);
		tvcategory = (TextView) findViewById(R.id.tvcategoryname_addbudget);
		tvcategory.setOnClickListener(this);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		date = dateFormat.format(calendar.getTime());
		imgviewsetcatagory_image = (ImageView) findViewById(R.id.imgcategory_addmoneybd);
		mob = RevMob.start(this);
		fullscreen = mob.createFullscreen(this, null);
		setcategory();

	}

	private void setcategory() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		int image = intent.getIntExtra("img_id", 000000);
		str = intent.getStringExtra("img_id");
		name = intent.getStringExtra("name");
		tvcategory.setText(name);
		imgviewsetcatagory_image.setImageResource(image);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(getApplicationContext(), Transaction_Expense.class);
        startActivity(intent);
	}
	@Override
	public void onClick(View view) {
		if (view == btnonsubmit) {
			mDbW = mHelper.getWritableDatabase();
			ContentValues cv = new ContentValues(4);
			cv.put(MyDbHelper.COL_NAME_B, tvcategory.getText().toString());
			cv.put(MyDbHelper.COL_NOTE_B, ednote.getText().toString());
			cv.put(MyDbHelper.COL_DATE_B,date);
			cv.put(MyDbHelper.COL_AMOUNT_B,
					Float.parseFloat(edamount.getText().toString()));
			mDbW.insert(MyDbHelper.TABLE_BUDGET, null, cv);

			// mDbW.insert(cat_table, null, cvC);
			edamount.setText(null);
			ednote.setText(null);
			tvcategory.setText(null);
//			mCursor.close();
//			mDbR.close();
			mDbW.close();
			fullscreen.show();
//			Intent intent = new Intent(BudgetAdd.this, TabBar_Display.class);
//			intent.putExtra("budget", "budget");
//			startActivity(intent);


		} else if (view == tvcategory) {
			Intent intent = new Intent(BudgetAdd.this, CategoryBudget.class);
			intent.putExtra("budget", "budget");
			startActivity(intent);
			BudgetAdd.this.finish();
		} else if (view == btncancel) {
			Intent intent = new Intent().setClass(BudgetAdd.this, Transaction_Expense.class);
			startActivity(intent);
		} else {
		}
	}
}
