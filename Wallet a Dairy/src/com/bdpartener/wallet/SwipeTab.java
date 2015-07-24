package com.bdpartener.wallet;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class SwipeTab extends BaseActivity {
	static ViewPager viewPager = null;
	static ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		// setContentView(R.layout.swipetab);
		getLayoutInflater().inflate(R.layout.swipetab, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);
		viewPager = (ViewPager) findViewById(R.id.pager);

		ActionTab actionTab = new ActionTab();
		actionTab.callPager();
	}
}

class ActionTab extends FragmentActivity implements TabListener {


	

	public void callPager() {
		// TODO Auto-generated method stub
		SwipeTab.viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		SwipeTab.viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				SwipeTab.actionBar.setSelectedNavigationItem(arg0);
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

		ActionBar.Tab tab1 = SwipeTab.actionBar.newTab();
		tab1.setText("Tab 1");
		tab1.setTabListener(this);

		ActionBar.Tab tab2 = SwipeTab.actionBar.newTab();
		tab2.setText("Tab 2");
		tab2.setTabListener(this);

		SwipeTab.actionBar.addTab(tab1);
		SwipeTab.actionBar.addTab(tab2);
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		SwipeTab.viewPager.setCurrentItem(arg0.getPosition());
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