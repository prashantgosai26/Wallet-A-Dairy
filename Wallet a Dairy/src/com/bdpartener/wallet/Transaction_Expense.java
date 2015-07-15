package com.bdpartener.wallet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.revmob.RevMob;
import com.revmob.ads.fullscreen.RevMobFullscreen;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Transaction_Expense extends BaseActivity {

	ListView lvExpense;
	SQLiteDatabase sqldb;
	Cursor cursor;
	TextView tvTotalExpense;
	SimpleAdapter simple;
	ArrayList<HashMap<String, Object>> arrayList;
	Calendar cal;
	String firstDay,lastDay;
	String id[] = new String[5];
	String name[] = new String[5];
	String amount[] = new String[5];
	float total=0.0f;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_display_transaction_expense);
		getLayoutInflater().inflate(R.layout.activity_display_transaction_expense, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);
		sqldb = openOrCreateDatabase("MyMoney",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		lvExpense = (ListView) findViewById(R.id.lvAllExpenseRecord);
		arrayList = new ArrayList<HashMap<String,Object>>();
		cal = Calendar.getInstance();

		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		firstDay = sdf.format(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		lastDay = sdf.format(cal.getTime());
		displayExpense();
	}
	private void displayExpense() {
		// TODO Auto-generated method stub

		cursor = sqldb.rawQuery("SELECT  * FROM Expense WHERE eDate BETWEEN '" + firstDay + "' AND '" + lastDay	+ "' ", null);
		tvTotalExpense = (TextView)findViewById(R.id.tvExpenseTotal);
		if(cursor !=null )
		{
			if(cursor.moveToFirst())
			{
				do {
					id[0]=cursor.getString(cursor.getColumnIndex("_id"));
					name[0]=cursor.getString(cursor.getColumnIndex("eName"));
					amount[0]=cursor.getString(cursor.getColumnIndex("eAmount"));
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					for(int i=0;i<id[0].length();i++)
					{
						total=total+Float.parseFloat(amount[i]);
						hashMap.put("id", id[i]);
						hashMap.put("name", name[i]);
						hashMap.put("amount",amount[i]);
						arrayList.add(hashMap);
					}
				} while (cursor.moveToNext());
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "No Such Record", Toast.LENGTH_SHORT).show();
		}
		
		simple = new SimpleAdapter(getApplicationContext(), arrayList, R.layout.listviewitems, new String[] {"id","name","amount"}, new int[] {R.id.Displayid,R.id.Displayname,R.id.Displaydate});
		lvExpense.setAdapter(simple);
		tvTotalExpense.setText(Float.toString(total));
//		int total=sqldb.rawQuery("select sum(iAmount) from Income",null);
		// Toast.makeText(getApplicationContext(), "Total Amount: "+total,
		// 50).show();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	Intent intent = new Intent(getApplicationContext(), Home.class);
	startActivity(intent);
		super.onBackPressed();
	}
	@Override

public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction__expense, menu);
		return true;
	}

}
