package com.bdpartener.wallet;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.DateFormat;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Transaction_Budget extends BaseActivity {

	ListView lvbudget;
	SQLiteDatabase sqldb;
	MyDbHelper mHelper;
	Cursor cursor;
	TextView tvTotalBudget;
	SimpleAdapter simple;
	ArrayList<HashMap<String, Object>> arrayList;
	String id[] = new String[5];
	String name[] = new String[5];
	String amount[] = new String[5];
	float total = 0.0f;
	Calendar cal;
	String firstDay, lastDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_display_transaction_budget);
		getLayoutInflater().inflate(R.layout.activity_display_transaction_budget, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);
		
		mHelper = new MyDbHelper(this);
		sqldb = mHelper.getReadableDatabase();
//		sqldb = openOrCreateDatabase("MyMoney",
//				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		lvbudget = (ListView) findViewById(R.id.lvAllBudgetRecord);
		arrayList = new ArrayList<HashMap<String, Object>>();
		cal = Calendar.getInstance();
		tvTotalBudget = (TextView) findViewById(R.id.tvBudgetTotal);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		firstDay = sdf.format(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		lastDay = sdf.format(cal.getTime());
		// Toast.makeText(this, firstDay + " " + lastDay, Toast.LENGTH_LONG)
		// .show();
		// System.out.println("First Day of Month: " +
		// sdf.format(firstDayOfMonth));
		displayIncome();

	}

	private void displayIncome() {
		// TODO Auto-generated method stub
		cursor = sqldb.rawQuery("select * from Budget WHERE bDate BETWEEN '"
				+ firstDay + "' AND '" + lastDay + "'", null);
		// cursor = sqldb.rawQuery("select * from Budget WHERE bName='Food' ",
		// null);
		Toast.makeText(getApplicationContext(), "problem in cursor", 100)
				.show();
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					// id[0] = cursor.getString(cursor.getColumnIndex("_id"));
					name[0] = cursor.getString(cursor.getColumnIndex("bName"));
					amount[0] = cursor.getString(cursor
							.getColumnIndex("bAmount"));
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					for (int i = 0; i < id[0].length(); i++) {
						total = total + Float.parseFloat(amount[i]);
						hashMap.put("id", id[i]);
						hashMap.put("name", name[i]);
						hashMap.put("amount", amount[i]);
						arrayList.add(hashMap);
					}
				} while (cursor.moveToNext());
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "No Such Record", Toast.LENGTH_SHORT).show();
		}

		simple = new SimpleAdapter(getApplicationContext(), arrayList,
				R.layout.listviewitems,
				new String[] { "id", "name", "amount" }, new int[] {
						R.id.Displayid, R.id.Displayname, R.id.Displaydate });
		lvbudget.setAdapter(simple);
		tvTotalBudget.setText(Float.toString(total));
		// if (cursor != null) {
		// if (cursor.moveToFirst()) {
		// do {
		// id[0] = cursor.getString(cursor.getColumnIndex("_id"));
		// name[0] = cursor.getString(cursor.getColumnIndex("bName"));
		// amount[0] = cursor.getString(cursor
		// .getColumnIndex("bAmount"));
		// HashMap<String, Object> hashMap = new HashMap<String, Object>();
		// for (int i = 0; i < id[0].length(); i++) {
		// total = total + Float.parseFloat(amount[i]);
		// hashMap.put("id", id[i]);
		// hashMap.put("name", name[i]);
		// hashMap.put("amount", amount[i]);
		// arrayList.add(hashMap);
		// }
		// } while (cursor.moveToNext());
		// }
		// }
		// else {
		// Toast.makeText(getApplicationContext(), "Cursor is null", 50).show();
		// }
		//
		// simple = new SimpleAdapter(getApplicationContext(), arrayList,
		// R.layout.listviewitems,
		// new String[] { "id", "name", "amount" }, new int[] {
		// R.id.Displayid, R.id.Displayname, R.id.Displaydate });
		// lvbudget.setAdapter(simple);
		// tvTotalIncome.setText(Float.toString(total));
		// // int total=sqldb.rawQuery("select sum(iAmount) from Income",null);
		// Toast.makeText(getApplicationContext(), "Total Amount: " + total, 50)
		// .show();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		Intent intent = new Intent(getApplicationContext(), Home.class);
//		startActivity(intent);
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transc, menu);
		return true;
	}

}
