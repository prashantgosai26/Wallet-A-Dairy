package com.bdpartener.wallet;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends BaseActivity {


	int mday, mmonth, myear;
	String str, create, name, insert;
	Calendar calendar = Calendar.getInstance();
	SimpleDateFormat dateFormat;
	MyDbHelper mHelper;
	SQLiteDatabase mDbR, mDbW, sqldb;
	Cursor mCursor;
	
	float Total = 0.0f, eTotal = 0.0f, iTotal = 0.0f, Remain = 0.0f;
	String cat_name[] = new String[5];
	float amount[] = new float[5];
	String firstDay, lastDay;

	SimpleCursorAdapter mAdapter;

	ImageButton income, expense, transaction, budget, report;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_home);
		getLayoutInflater().inflate(R.layout.activity_home, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);
		mHelper=new MyDbHelper(this);
		initControl();
		
//		countRemain();
	}


	

	@Override
	public void onBackPressed() {
	}
	


	private void initControl() {
		// TODO Auto-generated method stub
		income = (ImageButton) findViewById(R.id.ibtnincome_home);
		expense = (ImageButton) findViewById(R.id.ibtnexpense_home);
		transaction = (ImageButton) findViewById(R.id.ibtntransaction_home);
		budget = (ImageButton) findViewById(R.id.ibtnbudget_home);
		report = (ImageButton) findViewById(R.id.ibtnreport_home);

		onButtonClick();
	}

	private void onButtonClick() {
		// TODO Auto-generated method stub
		transaction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(Home.this, TabBar_Display.class);
//				startActivity(intent);
			}
		});
		income.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				Intent intent = new Intent(Home.this, TabBar_Insert.class);
//				intent.putExtra("income", "income");
//				startActivity(intent);
			}
		});
		expense.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(Home.this, TabBar_Insert.class);
//				intent.putExtra("expense", "expense");
//				startActivity(intent);
			}
		});
		budget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(Home.this, TabBar_Insert.class);
//				intent.putExtra("budget", "budget");
//				startActivity(intent);
				// Toast.makeText(getApplicationContext(), "Set Your budget",
				// Toast.LENGTH_LONG).show();
			}
		});
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(Home.this, Report.class);
				 startActivity(intent);
//				Toast.makeText(getApplicationContext(), "Get Full Report",
//						Toast.LENGTH_LONG).show();
			}
		});
	}
	private void countRemain() {
		// TODO Auto-generated method stub
//		mDbW = mHelper.getWritableDatabase();
//		mDbR = mHelper.getReadableDatabase();
//		
//		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
//		firstDay = dateFormat.format(calendar.getTime());
//		String current = (dateFormat.format(calendar.getTime()));
//		calendar = Calendar.getInstance();
//		calendar.add(Calendar.MONTH, -1);
//		calendar.set(Calendar.DATE, 1);
//		String prvFirstDay = dateFormat.format(calendar.getTime());
//		calendar.set(Calendar.DATE,
//				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//		String prvLastDay = dateFormat.format(calendar.getTime());
//		int year = Calendar.YEAR;
//		SimpleDateFormat mon = new SimpleDateFormat("MMM");
//		String month = mon.format(calendar.getTime());

//		if (current.equals(firstDay)) {
//
//			String selectQueryEx = "SELECT  * FROM " + MyDbHelper.TABLE_EXPENSE
//					+ " WHERE eDate BETWEEN '" + prvFirstDay + "' AND '"
//					+ prvLastDay + "' ";
//			mCursor = mDbR.rawQuery(selectQueryEx, null);
//			eTotal = 0.0f;
//			if (mCursor != null) {
//				if (mCursor.moveToFirst()) {
//					do {
//						amount[0] = mCursor.getFloat(mCursor
//								.getColumnIndex("eAmount"));
//						eTotal = eTotal + amount[0];
//					} while (mCursor.moveToNext());
//				}
//			} else {
//			}
//
//			String selectQueryIn = "SELECT  * FROM " + MyDbHelper.TABLE_INCOME
//					+ " WHERE eDate BETWEEN '" + prvFirstDay + "' AND '"
//					+ prvLastDay + "' ";
//			mCursor = mDbR.rawQuery(selectQueryIn, null);
//			iTotal = 0.0f;
//			if (mCursor != null) {
//				if (mCursor.moveToFirst()) {
//					do {
//						amount[0] = mCursor.getFloat(mCursor
//								.getColumnIndex("iAmount"));
//						iTotal = iTotal + amount[0];
//					} while (mCursor.moveToNext());
//				}
//			} else {
//			}
//
//			Remain = iTotal - eTotal;
//			ContentValues cv = new ContentValues(1);
//			cv.put(MyDbHelper.COL_MONTH, month);
//			cv.put(MyDbHelper.COL_YEAR, year);
//			cv.put(MyDbHelper.COL_TOTAL, Remain);
//			mDbW.insert(MyDbHelper.TABLE_TOTAL, null, cv);
//		}
//		mCursor.close();
//		mDbR.close();
//		mDbW.close();

	}


}
