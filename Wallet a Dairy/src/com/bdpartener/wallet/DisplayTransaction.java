package com.bdpartener.wallet;

import java.util.ArrayList;
import java.util.HashMap;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayTransaction extends Activity {

	LayoutInflater inflater;
	ArrayList<HashMap<String, Object>> arrayList;
	ListView lvDisplayTransction;
	SimpleAdapter simpleAdapter;
	TextView tvcat_name, tvdate;
	EditText edamount, ednote;
	AlertDialog.Builder builder;
	SQLiteDatabase sqldb;
	Cursor cursor;
	String[] item = { "Edit", "Delete" };
	int[] cat_img = new int[10];
	String[] cat_name = new String[10];
	String[] date = new String[10];
	String[] note = new String[10];
	int[] amount = new int[10];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_transaction_income);
		initComponent();
		combinlistViewWithComponent();
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		sqldb = openOrCreateDatabase("MyMoney",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		arrayList = new ArrayList<HashMap<String, Object>>();
		builder = new AlertDialog.Builder(DisplayTransaction.this);
		cursor = sqldb.rawQuery("select * from AddIncome", null);

	}

	private void combinlistViewWithComponent() {
		// TODO Auto-generated method stub
		if (cursor != null) {
			Toast.makeText(getApplicationContext(), "not null",
					Toast.LENGTH_LONG).show();
			if (cursor.moveToFirst()) {
				do {
					cat_name[0] = cursor.getString(cursor
							.getColumnIndex("category_name"));
					date[0] = cursor.getString(cursor.getColumnIndex("date"));
					 note[0] = cursor.getString(cursor.getColumnIndex("note"));
					amount[0] = cursor.getInt(cursor.getColumnIndex("amount"));

					for (int i = 0; i < cat_name[0].length(); ++i) {
						HashMap<String, Object> hmap = new HashMap<String, Object>();
						hmap.put("cat_name", cat_name[i]);
						hmap.put("date", date[i]);
						hmap.put("note", note[i]);
						hmap.put("amount", amount[i]);
						arrayList.add(hmap);
						break;
					}
				} while (cursor.moveToNext());
			}
			simpleAdapter = new SimpleAdapter(getApplicationContext(),
					arrayList, R.layout.display_transaction_component,
					new String[] { "cat_name", "date", "amount" }, new int[] {
							R.id.tvname_display_transaction_component,
							R.id.tvdate_display_transaction_component,
							R.id.tvmoney_display_transaction_component });

			lvDisplayTransction.setAdapter(simpleAdapter);
			
		}
		cursor.close();
		lvDisplayTransction
		.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(final AdapterView<?> parent,
					 View view, final int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, Object> hashMap = (HashMap<String, Object>) parent.getItemAtPosition(position);
				final String cat_name_get = hashMap.get("cat_name").toString();
				final String date_get = hashMap.get("date").toString();
				final String note_get = hashMap.get("note").toString();
				final String amount_get = hashMap.get("amount").toString();
				Toast.makeText(getApplicationContext(),"catname"+ cat_name_get+ "date"+ note_get+ "amount"+ amount_get,Toast.LENGTH_LONG).show();
				inflater = LayoutInflater.from(getApplicationContext());
				view = inflater.inflate(R.layout.update_transcation,null);
				tvcat_name = (TextView) view.findViewById(R.id.tvcategoryname_upadate_transcation);
				tvdate = (TextView) view.findViewById(R.id.tvdate_update_transaction);
				edamount = (EditText) view.findViewById(R.id.edamount_update_transaction);
				ednote = (EditText) view.findViewById(R.id.ednote_update_transaction);
				
				builder.setTitle("Edit");
				builder.setIcon(R.drawable.pencil);
				builder.setView(view);

				tvcat_name.setText(cat_name_get);
				edamount.setText(amount_get);
				ednote.setText(note_get);
				tvdate.setText(date_get);
				tvcat_name.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(DisplayTransaction.this, CategoryIncome.class);
						startActivity(intent);
					}
				
					
				});
				builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "Update is Call by you", Toast.LENGTH_LONG).show();
					}
				});
				builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "Delete is Call by you", Toast.LENGTH_LONG).show();
					}
				});
				
				Toast.makeText(getApplicationContext(),"Edit form",Toast.LENGTH_LONG).show();
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});

updateTransaction();
deleteTransaction();
	}

	private void updateTransaction() {
		// TODO Auto-generated method stub

	}

	private void deleteTransaction() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_transaction, menu);
		return true;
	}

}
