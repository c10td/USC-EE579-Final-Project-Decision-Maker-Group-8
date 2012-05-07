//author:Juanyi Feng
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

/**
 * An activity use for requesting movie information from perl server
 */
public class MovieActivity  extends Activity {
	EditText et;
	Button submitButton;
	String seletedCuisineType;
	ArrayList<String> names;
	String userName="";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	StrictMode.ThreadPolicy policy = new StrictMode.
    	ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  
        Bundle extras = getIntent().getExtras();
		if(extras !=null) {
			 userName = extras.getString("userName");
		}
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
			
				String zipCode=et.getText().toString();
				try {
					String temp="http://cs-server.usc.edu:1053/cgi-bin/movieInfo.pl?search="+zipCode;
		        	
					URL url = new URL(temp);
					HttpURLConnection con = (HttpURLConnection) url
		        		.openConnection();
		        	readStream(con.getInputStream());
		        	
		        	String optionString="";
		        	for(int i=0;i<names.size();i++)
		        	{
		        		optionString+=names.get(i)+";";
		        	}
		        	Client client=new Client();
		        	client.startClient();
		        	client.sendMessage("1;"+userName+";"+optionString);
		        	String eventID=client.recvMessage();
		        	client.sendMessage("end");
		        	client.closeConnection();
		        	
		        	Intent intent = new Intent(MovieActivity.this, RestaurantList.class);
		        	intent.putExtra("names", names);
		        	intent.putExtra("userName",userName);
		        	intent.putExtra("eventID",eventID);
		        	MovieActivity.this.startActivity(intent);

		        	} catch (Exception e) {
		        	e.printStackTrace();
		        }
			}
		});
 
        
    
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
