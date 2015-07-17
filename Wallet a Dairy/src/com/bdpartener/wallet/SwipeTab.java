package com.bdpartener.wallet;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class SwipeTab extends FragmentActivity implements TabListener {

	ViewPager viewPager;
	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.swipetab);

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.Tab tab1 = actionBar.newTab();
		tab1.setText("Tab 1");
		tab1.setTabListener(this);

		ActionBar.Tab tab2 = actionBar.newTab();
		tab2.setText("Tab 2");
		tab2.setTabListener(this);

		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
}

class MyAdapter extends FragmentPagerAdapter {

	public MyAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;
		if (arg0 == 0) {
			fragment = new BlankFragmentA();
		}
		if (arg0 == 1) {
			fragment = new BlankFragmentB();
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
}