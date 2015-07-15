package com.bdpartener.wallet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.revmob.RevMob;
import com.revmob.ads.fullscreen.RevMobFullscreen;

import android.R.drawable;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
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

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ExpenseAdd extends BaseActivity implements View.OnClickListener {

	Button btnonsubmit, btncancel;
	ImageView imgviewsetcatagory_image;
	// SQLiteDatabase sqldb;
	TextView tvdate, tvcategory;
	EditText edamount, ednote;
	DatePickerDialog dialog;
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
	RevMobFullscreen fullscreen;
	SimpleCursorAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_add_money_expense);
		getLayoutInflater().inflate(R.layout.activity_add_money_expense, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);

		mHelper = new MyDbHelper(this);
		edamount = (EditText) findViewById(R.id.edamount_addmoneyex);
		ednote = (EditText) findViewById(R.id.ednote_addmoneyex);
		btnonsubmit = (Button) findViewById(R.id.btnSubmit_addmoney);
		btncancel = (Button) findViewById(R.id.btncancel_expense_add);
		btnonsubmit.setOnClickListener(this);
		tvcategory = (TextView) findViewById(R.id.tvcategoryname_addmoneyex);
		tvcategory.setOnClickListener(this);
		tvdate = (TextView) findViewById(R.id.tvdate);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		tvdate.setOnClickListener(this);
		tvdate.setText(dateFormat.format(calendar.getTime()));
		imgviewsetcatagory_image = (ImageView) findViewById(R.id.imgcategory_addmoneyex);
		calendar = Calendar.getInstance();
		mday = calendar.get(Calendar.DAY_OF_MONTH);
		mmonth = calendar.get(Calendar.MONTH);
		myear = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		firstDay = dateFormat.format(calendar.getTime());
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		lastDay = dateFormat.format(calendar.getTime());
		sqldb = openOrCreateDatabase("MyMoney",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		RevMob mob = RevMob.start(this);
		fullscreen = mob.createFullscreen(this, null);
		setdate();
		setcategory();

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

	public void setcategory() {
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
		Intent intent = new Intent(getApplicationContext(), Home.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		if (view == btnonsubmit) {
			mDbW = mHelper.getWritableDatabase();
			mDbR = mHelper.getReadableDatabase();
			insertData();
			TotalExpense();
			Finish();
		} else if (view == tvcategory) {
			Intent intent = new Intent(ExpenseAdd.this, CategoryExpense.class);
			intent.putExtra("expense", "expense");
			startActivity(intent);
			ExpenseAdd.this.finish();
		} else if (view == tvdate) {
			dialog.show();
		} else {
			btncancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent().setClass(ExpenseAdd.this,
							Home.class);
					startActivity(intent);
				}
			});
		}
	}

	private void insertData() {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues(4);
		cv.put(MyDbHelper.COL_NAME_E, tvcategory.getText().toString());
		cv.put(MyDbHelper.COL_NOTE_E, ednote.getText().toString());
		cv.put(MyDbHelper.COL_DATE_E, tvdate.getText().toString());
		cv.put(MyDbHelper.COL_AMOUNT_E,
				Float.parseFloat(edamount.getText().toString()));
		mDbW.insert(MyDbHelper.TABLE_EXPENSE, null, cv);
	}

	private void TotalExpense() {
		// TODO Auto-generated method stub
		String selectQuery = "SELECT  * FROM " + MyDbHelper.TABLE_EXPENSE
				+ " WHERE eDate BETWEEN '" + firstDay + "' AND '" + lastDay
				+ "' AND " + MyDbHelper.COL_NAME_E + " = '"
				+ tvcategory.getText().toString() + "' ";
		mCursor = mDbR.rawQuery(selectQuery, null);
		Total = 0.0f;
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					cat_name[0] = mCursor.getString(mCursor
							.getColumnIndex("eName"));
					amount[0] = mCursor.getFloat(mCursor
							.getColumnIndex("eAmount"));
					Total = Total + amount[0];
				} while (mCursor.moveToNext());
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "No Such Record", Toast.LENGTH_SHORT).show();
		}
		Notification(Total);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void Notification(float total) {
		float cat_total = total;
		String selectQuery = "SELECT  * FROM " + MyDbHelper.TABLE_BUDGET
				+ " WHERE bDate BETWEEN '" + firstDay + "' AND '" + lastDay
				+ "' AND " + MyDbHelper.COL_NAME_B + " = '"
				+ tvcategory.getText().toString() + "' ";
		mCursor = mDbR.rawQuery(selectQuery, null);
		Total = 0.0f;
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					cat_name[0] = mCursor.getString(mCursor
							.getColumnIndex("bName"));
					amount[0] = mCursor.getFloat(mCursor
							.getColumnIndex("bAmount"));
					Total = Total + amount[0];
				} while (mCursor.moveToNext());
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "No Such Record", Toast.LENGTH_SHORT).show();
		}

		if (Total == 0.0f) {
		} else if (cat_total > Total) {
			float remain = cat_total - Total;
			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					this);
			// Notification builder = new Notification.Builder(this);
			builder.setContentTitle("Overspent Rs." + remain);
			builder.setContentText("Budget extension recomanded "
					+ tvcategory.getText().toString());
			builder.setSmallIcon(R.drawable.ic_launcher);
			builder.setAutoCancel(true);
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			manager.notify(0, builder.build());
		} else if (cat_total == Total) {
			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					this);
			// Notification builder = new Notification.Builder(this);
			builder.setContentTitle("Budget");
			builder.setContentText("Budget extension recomanded "
					+ tvcategory.getText().toString());
			builder.setSmallIcon(R.drawable.ic_launcher);
			builder.setAutoCancel(true);
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			manager.notify(0, builder.build());
		}

	}

	private void Finish() {
		// TODO Auto-generated method stub
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		firstDay = dateFormat.format(calendar.getTime());
		String current = (dateFormat.format(calendar.getTime()));
		calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DATE, 1);
		String prvFirstDay = dateFormat.format(calendar.getTime());
		calendar.set(Calendar.DATE,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String prvLastDay = dateFormat.format(calendar.getTime());
		SimpleDateFormat mon = new SimpleDateFormat("MMM");
		String month = mon.format(calendar.getTime());
		SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
		String year = yyyy.format(calendar.getTime());

		if (current.equals(firstDay)) {

			String selectQueryEx = "SELECT  * FROM " + MyDbHelper.TABLE_EXPENSE
					+ " WHERE eDate BETWEEN '" + prvFirstDay + "' AND '"
					+ prvLastDay + "' ";
			mCursor = mDbR.rawQuery(selectQueryEx, null);
			eTotal = 0.0f;
			if (mCursor != null) {
				if (mCursor.moveToFirst()) {
					do {
						amount[0] = mCursor.getFloat(mCursor
								.getColumnIndex("eAmount"));
						eTotal = eTotal + amount[0];
					} while (mCursor.moveToNext());
				}
			} else {
			}

			String selectQueryIn = "SELECT  * FROM " + MyDbHelper.TABLE_INCOME
					+ " WHERE iDate BETWEEN '" + prvFirstDay + "' AND '"
					+ prvLastDay + "' ";
			mCursor = mDbR.rawQuery(selectQueryIn, null);
			iTotal = 0.0f;
			if (mCursor != null) {
				if (mCursor.moveToFirst()) {
					do {
						amount[0] = mCursor.getFloat(mCursor
								.getColumnIndex("iAmount"));
						iTotal = iTotal + amount[0];
					} while (mCursor.moveToNext());
				}
			} else {
			}

			Remain = iTotal - eTotal;
			ContentValues cv = new ContentValues(3);
			cv.put(MyDbHelper.COL_MONTH, month);
			cv.put(MyDbHelper.COL_YEAR, year);
			cv.put(MyDbHelper.COL_TOTAL, Remain);
			mDbW.insert(MyDbHelper.TABLE_TOTAL, null, cv);
		}

		fullscreen.show();
//		Intent intent = new Intent(ExpenseAdd.this, TabBar_Display.class);
//		intent.putExtra("total", Total);
//		intent.putExtra("expense", "expense");
//		startActivity(intent);
		edamount.setText(null);
		ednote.setText(null);
		tvcategory.setText(null);
		tvdate.setText(null);
		mCursor.close();
		mDbR.close();
		mDbW.close();

	}

}
