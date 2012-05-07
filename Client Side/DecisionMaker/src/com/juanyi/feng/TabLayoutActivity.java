//Author: Juanyi Feng
package com.juanyi.feng;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * An activity use for managing tab menus
 */
@SuppressWarnings("deprecation")
public class TabLayoutActivity extends TabActivity{
	private TabHost mTabHost;
	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.index);
		String userName="";
		Bundle extras = getIntent().getExtras();
		if(extras !=null) {
			 userName = extras.getString("userName");
		}
		
		mTabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		
		//restaurent
		intent = new Intent(this,RestaurantActivity.class);
		intent.putExtra("userName", userName);
		spec = mTabHost.newTabSpec("restaurent")
						.setIndicator("Restaurent")
						.setContent(intent);
		mTabHost.addTab(spec);
		
		//movie
		intent = new Intent(this,MovieActivity.class);
		intent.putExtra("userName", userName);
		spec = mTabHost.newTabSpec("movie")
						.setIndicator("Movie")
						.setContent(intent);
		mTabHost.addTab(spec);
		
		//customize
		intent = new Intent(this,CustomizeActivity.class);
		intent.putExtra("userName", userName);
		spec = mTabHost.newTabSpec("Customize")
						.setIndicator("Customize")
						.setContent(intent);
		mTabHost.addTab(spec);
		
	}
}
