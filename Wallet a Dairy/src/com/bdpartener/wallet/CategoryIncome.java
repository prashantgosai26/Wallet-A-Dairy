package com.bdpartener.wallet;

import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.spec.IvParameterSpec;


import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CategoryIncome extends Activity {

	ArrayList<HashMap<String, Object>> arylistIN, arylistEX;
	ListView lvIncome, lvExpense;
	HashMap<String, Object> h_map;
	int income_img[] = { R.drawable.in_event, R.drawable.in_intrest,
			R.drawable.in_salary, R.drawable.in_sale };
	String income_imgg[] = { "in_event", "in_intrest", "in_salary", "in_sale" };
	String income_name[] = { "Event", "Intrest Money", "Salary", "Sale" };

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		initControl();
		DisplayIncomeCategory();
		onClickIncomeView();
		// passValueToAddMoney();
	}

	public void initControl() {


		arylistIN = new ArrayList<HashMap<String, Object>>();
		lvIncome = (ListView) findViewById(R.id.lvincome);
	}

	public void DisplayIncomeCategory() {


		for (int i = 0; i < income_imgg.length; ++i) {
			h_map = new HashMap<String, Object>();
			h_map.put("income_imgg", income_img[i]);
			h_map.put("income_name", income_name[i]);
			arylistIN.add(h_map);
		}

		String fromIN[] = { "income_imgg", "income_name" };
		int toIN[] = { R.id.imageView1, R.id.textView1 };
		SimpleAdapter simadpIN = new SimpleAdapter(getApplicationContext(),
				arylistIN, R.layout.spinner_category_control, fromIN, toIN);
		lvIncome.setAdapter(simadpIN);

	}

	public void onClickIncomeView() {
		lvIncome.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int res_id = getResources().getIdentifier(
						income_imgg[position], "drawable", getPackageName());

				Intent intent = new Intent(CategoryIncome.this, IncomeAdd.class);
				intent.putExtra("name", income_name[position]);
				intent.putExtra("img_id", res_id);
				startActivity(intent);
			}
		});
	}


}
