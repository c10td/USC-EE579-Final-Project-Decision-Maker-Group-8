package com.juanyi.feng;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class TabLayoutActivity extends TabActivity{
	private TabHost mTabHost;
	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.index);
		mTabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		
		//restaurent
		intent = new Intent(this,RestaurantActivity.class);
		spec = mTabHost.newTabSpec("restaurent")
						.setIndicator("Restaurent")
						.setContent(intent);
		mTabHost.addTab(spec);
		
		//movie
		intent = new Intent(this,MovieActivity.class);
		spec = mTabHost.newTabSpec("movie")
						.setIndicator("Movie")
						.setContent(intent);
		mTabHost.addTab(spec);
		
		//customize
		intent = new Intent(this,CustomizeActivity.class);
		spec = mTabHost.newTabSpec("Customize")
						.setIndicator("Customize")
						.setContent(intent);
		mTabHost.addTab(spec);
		
	}
}
