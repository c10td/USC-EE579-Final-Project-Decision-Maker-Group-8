package com.juanyi.feng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RestaurantActivity extends Activity {
	EditText et;
	Button submitButton;
	String seletedCuisineType;
	ArrayList<String> names;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	StrictMode.ThreadPolicy policy = new StrictMode.
    	ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/MEgalopolisExtra.otf");
        TextView tv = (TextView) findViewById(R.id.title);
        tv.setTypeface(tf);
        TextView tv1 = (TextView) findViewById(R.id.textView1);
        tv1.setTypeface(tf);
        TextView tv2 = (TextView) findViewById(R.id.textView4);
        tv2.setTypeface(tf);
        et=(EditText)findViewById(R.id.editText1);
       
        submitButton=(Button)findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//System.out.println(et.getText().toString());
				//System.out.println(sp.getSelectedItem().toString());
				String zipCode=et.getText().toString();
				try {
					String temp="http://cs-server.usc.edu:1053/cgi-bin/yelpInfo.pl?rrzip="+zipCode;
		        	//URL url = new URL("http://cs-server.usc.edu:1053/cgi-bin/yelpTester.pl?rrzip=90012&cuisine=South%20American");
					URL url = new URL(temp);
					HttpURLConnection con = (HttpURLConnection) url
		        		.openConnection();
		        	readStream(con.getInputStream());
		        	//TextView tempTV=new TextView("apple");
		        	Intent intent = new Intent(RestaurantActivity.this, RestaurantList.class);
		        	intent.putExtra("names", names);
		        	
		        	RestaurantActivity.this.startActivity(intent);

		        	} catch (Exception e) {
		        	e.printStackTrace();
		        }
			}
		});
        //String url="http://cs-server.usc.edu:1053/cgi-bin/yelpTester.pl";
       // URL url;
        
    
    }
    private void readStream(InputStream in) {
    	BufferedReader reader = null;
    	names=new ArrayList<String>();
    	try {
    		reader = new BufferedReader(new InputStreamReader(in));
    		String line = "";
    		while ((line = reader.readLine()) != null) {
    			System.out.println(line);
    			if(!names.contains(line))
    			{
    				names.add(line);
    			}
    			
    		} 
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		if (reader != null) {
    			try {
    			  reader.close();
    			} catch (IOException e) {
    			    e.printStackTrace();
    			  }
    		}
    	}
    }
}