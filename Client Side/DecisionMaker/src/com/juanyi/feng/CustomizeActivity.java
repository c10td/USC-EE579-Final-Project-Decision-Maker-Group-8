package com.juanyi.feng;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CustomizeActivity  extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		textView.setText("Customize");
		setContentView(textView);
		
	}
}
