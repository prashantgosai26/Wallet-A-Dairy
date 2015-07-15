package com.bdpartener.wallet;

import java.util.ArrayList;
import java.util.HashMap;


import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class CategoryExpense extends Activity implements OnItemClickListener {

	ActionBar abobj;
	ArrayList<HashMap<String, Object>> arylistIN, arylistEX;
	ListView lvIncome, lvExpense;
	HashMap<String, Object> h_map;
	String expense_imgg[] = { "ex_baby", "ex_education", "ex_electricity",
			"ex_entertainment", "ex_finance", "ex_food", "ex_helth",
			"ex_internet", "ex_ornament", "ex_transportaion", "ex_trip",
			"ex_vacation" };
	int expense_img[] = { R.drawable.ex_baby, R.drawable.ex_education,
			R.drawable.ex_electricity, R.drawable.ex_entertainment,
			R.drawable.ex_finance, R.drawable.ex_food, R.drawable.ex_helth,
			R.drawable.ex_internet, R.drawable.ex_ornament,
			R.drawable.ex_transportaion, R.drawable.ex_trip,
			R.drawable.ex_vacation };
	String expense_name[] = { "Baby", "Education", "Electricity",
			"Entertainment", "Finance", "Food", "Helth", "Internet",
			"Ornament", "Transportation", "Trip", "Vacation" };

	String expense,budget;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_expense);
		initControl();
		DisplayExpenseCategory();
		
	}

	public void initControl() {

		arylistEX = new ArrayList<HashMap<String, Object>>();
		lvExpense = (ListView) findViewById(R.id.lvexpence);
		lvExpense.setOnItemClickListener(this);
	}

	private void DisplayExpenseCategory() {
		// TODO Auto-generated method stub
		for (int i = 0; i < expense_img.length; ++i) {
			h_map = new HashMap<String, Object>();
			h_map.put("expense_img", expense_img[i]);
			h_map.put("expense_name", expense_name[i]);
			arylistEX.add(h_map);
		}

		String fromEX[] = { "expense_img", "expense_name" };
		int toEX[] = { R.id.imageView1, R.id.textView1 };
		SimpleAdapter simadpEX = new SimpleAdapter(getApplicationContext(),
				arylistEX, R.layout.spinner_category_control, fromEX, toEX);
		lvExpense.setAdapter(simadpEX);
	}


	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		// TODO Auto-generated method stub
		int res_id = getResources().getIdentifier(
				expense_imgg[position], "drawable", getPackageName());

//		Intent intent = getIntent();
//		if("expense".equals(intent.getStringExtra("expense")))
//		{
			Intent in = new Intent(CategoryExpense.this,
					ExpenseAdd.class);
			in.putExtra("name", expense_name[position]);
			in.putExtra("img_id", res_id);
			startActivity(in);
//		}
//		else{
//			Intent in = new Intent(CategoryExpense.this,
//					BudgetAdd.class);
//			in.putExtra("name", expense_name[position]);
//			in.putExtra("img_id", res_id);
//			startActivity(in);
//		}

	}

}
