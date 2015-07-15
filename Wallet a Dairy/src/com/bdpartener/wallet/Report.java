package com.bdpartener.wallet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Report extends Activity {

	ListView listView;
	ArrayList<HashMap<String, Object>> arrayList;
	String id[] = new String[5];
	String month[] = new String[5];
	String year[] = new String[5];
	String amount[] = new String[5];
	SQLiteDatabase sqldb;
	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		sqldb = openOrCreateDatabase("MyMoney",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		listView = (ListView) findViewById(R.id.lvreport);
		arrayList = new ArrayList<HashMap<String, Object>>();
		displayRecord();
	}
	private void displayRecord() {
		// TODO Auto-generated method stub
		cursor = sqldb.rawQuery("select * from Total" ,null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					id[0] = cursor.getString(cursor.getColumnIndex("_id"));
					month[0] = cursor.getString(cursor.getColumnIndex("tMonth"));
					year[0] = cursor.getString(cursor.getColumnIndex("tYear"));
					amount[0] = cursor.getString(cursor
							.getColumnIndex("tAmount"));
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					for (int i = 0; i < id[0].length(); i++) {
						hashMap.put("id", id[i]);
						hashMap.put("month", month[i]);
						hashMap.put("year",year[i]);
						hashMap.put("amount", amount[i]);
						arrayList.add(hashMap);
					}
				} while (cursor.moveToNext());
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "No such Record Found.", Toast.LENGTH_LONG).show();
		}

		SimpleAdapter simple = new SimpleAdapter(getApplicationContext(), arrayList,
				R.layout.listviewitemsreport,
				new String[] { "id", "month","year", "amount" }, new int[] {
						R.id.Displayidr, R.id.Displaymonth, R.id.Displayyear, R.id.Displayamount });
		listView.setAdapter(simple);
		// int total=sqldb.rawQuery("select sum(iAmount) from Income",null);
//		Toast.makeText(getApplicationContext(), "Total Amount: " + total, 50)
//				.show();
	}
}
