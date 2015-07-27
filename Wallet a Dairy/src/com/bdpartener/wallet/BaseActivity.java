package com.bdpartener.wallet;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BaseActivity extends Activity {

	public static final String PREFS_NAME = "LoginPrefs";

	protected FrameLayout frameLayout;
	protected ListView mDrawerList;
	protected String[] listArray = { "Home", "Income", "Expense", "Budget", "Expense Record", "Income_ Record","Logout"};
	protected static int position;
	private static boolean isLaunch = true;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle actionBarDrawerToggle;
//	SessionManager manager;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint({ "NewApi", "ResourceAsColor" }) @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer_base_layout);
		
		frameLayout = (FrameLayout)findViewById(R.id.content_frame);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
//		mDrawerList.setBackgroundColor(R.color.mDrawerList);
//		manager = new SessionManager(getApplicationContext());
//		manager.checkLogin();
		// set a custom shadow that overlays the main content when the drawer opens
		//mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, listArray));
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				openActivity(position);
			}
		});
		
		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		// ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
		actionBarDrawerToggle = new ActionBarDrawerToggle(
				this,						/* host Activity */
				mDrawerLayout, 				/* DrawerLayout object */
				R.drawable.ic_drawer,     /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,       /* "open drawer" description for accessibility */
				R.string.drawer_close)      /* "close drawer" description for accessibility */ 
		{ 
			@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
			public void onDrawerClosed(View drawerView) {
				getActionBar().setTitle(listArray[position]);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				super.onDrawerStateChanged(newState);
			}
		};
		mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

		if(isLaunch){
			isLaunch = false;
			openActivity(0);
		}
	}
	
	protected void openActivity(int position) {
//		mDrawerList.setItemChecked(position, true);
//		setTitle(listArray[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
		BaseActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities. 
		
		switch (position) {
		case 0:
			startActivity(new Intent(this, Home.class));
			break;
		case 1:
			startActivity(new Intent(this, IncomeAdd.class));
			break;
		case 2:
			startActivity(new Intent(this, ExpenseAdd.class));
			break;
		case 3:
			startActivity(new Intent(this, BudgetAdd.class));
			break;
		case 4:
//		Toast.makeText(getApplicationContext(), "record", Toast.LENGTH_LONG).show();
			startActivity(new Intent(this, Transaction_Expense.class));
			break;
		case 5:
			startActivity(new Intent(this, Transaction_Income.class));
			break;
		case 6:
			Logout.exitApplication(this);
//			Intent intent = new Intent();
//			startActivity(intent);
//			break;
			
		default:
			break;
		}
		
//		Toast.makeText(this, "Selected Item Position::"+position, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// The action bar home/up action should open or close the drawer. 
		// ActionBarDrawerToggle will take care of this.
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
		
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    
    /* We can override onBackPressed method to toggle navigation drawer*/
	@Override
	public void onBackPressed() {
		if(mDrawerLayout.isDrawerOpen(mDrawerList)){
			mDrawerLayout.closeDrawer(mDrawerList);
		}else {
			mDrawerLayout.openDrawer(mDrawerList);
		}
	}
}
