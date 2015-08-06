package com.bdpartener.wallet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.ads.fullscreen.RevMobFullscreen;
import com.revmob.ads.popup.RevMobPopup;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class IncomeAdd extends BaseActivity  implements View.OnClickListener{

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
	SimpleCursorAdapter mAdapter;
	int Total, Amt_Income;
	private RevMob revMob;
	private RevMobFullscreen fullscreen;
    	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_add_money_income);
		getLayoutInflater().inflate(R.layout.activity_add_money_income, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);
		mHelper = new MyDbHelper(this);
		edamount = (EditText) findViewById(R.id.edamount_addmoneyin);
		ednote = (EditText) findViewById(R.id.ednote_addmoneyin);
		btnonsubmit = (Button) findViewById(R.id.btnSubmit_addmoneyin);
		btnonsubmit.setOnClickListener(this);
		btncancel = (Button) findViewById(R.id.btncancel_income_add);
		tvcategory = (TextView) findViewById(R.id.tvcategoryname_addmoneyin);
		tvdate = (TextView) findViewById(R.id.tvdatein);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		tvdate.setOnClickListener(this);
		tvdate.setText(dateFormat.format(calendar.getTime())); // Insert 'now'
		
		revMob = RevMob.start(this);
		fullscreen = revMob.createFullscreen(this, null);
		
		// date
		imgviewsetcatagory_image = (ImageView) findViewById(R.id.imgcategory_addmoneyin);

		setdate();
		selectCategory();
		setcategory();
	}

	

	@Override
	public void onBackPressed() {
	        super.onBackPressed();
//	        Intent intent = new Intent(getApplicationContext(), Transaction_Expense.class);
//	        startActivity(intent);
	}
	
	private void setdate() {
		// TODO Auto-generated method stub
		dialog = new DatePickerDialog(this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int selectedYear,
					int selectedMonth, int selectedDay) {
				// TODO Auto-generated method stub
				Calendar newDate = Calendar.getInstance();
				newDate.set(selectedYear, selectedMonth, selectedDay);
				tvdate.setText(dateFormat.format(newDate.getTime()));
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));

	}

	public void selectCategory() {
		// TODO Auto-generated method stub
		tvcategory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(IncomeAdd.this, CategoryIncome.class);
				intent.putExtra("income", "income");
				startActivity(intent);
				IncomeAdd.this.finish();
			}
		});
	}

	public void setcategory() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		int image = intent.getIntExtra("img_id", 000000);
		str = intent.getStringExtra("img_id");
		name = intent.getStringExtra("name");
		tvcategory.setText(name);
		imgviewsetcatagory_image.setImageResource(image);
		btncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent().setClass(IncomeAdd.this,
						Home.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View view) {
		if (view == btnonsubmit) {
//			onLoadButtonClick(view);
			mDbW = mHelper.getWritableDatabase();
			ContentValues cv = new ContentValues(4);
			cv.put(MyDbHelper.COL_NAME_I, tvcategory.getText().toString());
			cv.put(MyDbHelper.COL_NOTE_I, ednote.getText().toString());
			cv.put(MyDbHelper.COL_DATE_I, tvdate.getText().toString());
			cv.put(MyDbHelper.COL_AMOUNT_I,
					Float.parseFloat(edamount.getText().toString()));

			mDbW.insert(MyDbHelper.TABLE_INCOME, null, cv);

			// insert into Total account
			mDbR = mHelper.getReadableDatabase();
			String selectQuery = "SELECT  * FROM " + MyDbHelper.TABLE_TOTAL;
			mCursor = mDbR.rawQuery(selectQuery, null);
			ContentValues cvT = new ContentValues();
			if (mCursor != null) {
				if (mCursor.moveToLast()) {
					int test = mCursor
							.getInt(mCursor.getColumnIndex("tAmount"));

					Total = Integer.parseInt(mCursor.getString(mCursor
							.getColumnIndex("tAmount")));
					Total = Total
							+ Integer.parseInt(edamount.getText().toString());

					cvT.put(MyDbHelper.COL_TOTAL, Total);
					mDbW.insert(MyDbHelper.TABLE_TOTAL, null, cvT);

				}
			}else{}
			   fullscreen.show(); // call it wherever you want to show the fullscreen ad
			// mDbW.insert(cat_table, null, cvC);
			edamount.setText(null);
			ednote.setText(null);
			tvcategory.setText(null);
			tvdate.setText(null);
			mCursor.close();
			mDbR.close();
			mDbW.close();

					
			
		} else if (view == tvdate) {
			dialog.show();
		} else {
		}
	}

}
