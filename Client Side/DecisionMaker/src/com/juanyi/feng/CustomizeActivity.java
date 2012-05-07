//Author: Juanyi Feng
package com.juanyi.feng;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * An activity use for inputing customize options
 */
public class CustomizeActivity  extends Activity {
	String userName="";
	Button submit ;
	EditText optionOne;
	EditText optionTwo;
	EditText optionThree;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.
    	ThreadPolicy.Builder().permitAll().build(); 
    	StrictMode.setThreadPolicy(policy);
		
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		if(extras !=null) {
			 userName = extras.getString("userName");
		}
		;
		setContentView(R.layout.customize);
		
		optionOne= (EditText)findViewById(R.id.optionOne);
		optionTwo=(EditText)findViewById(R.id.optionTwo);
		optionThree=(EditText)findViewById(R.id.optionThree);
		submit =(Button)findViewById(R.id.submitCustomize); 
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//get options from EditTexts
				String one=optionOne.getText().toString();
				String two=optionTwo.getText().toString();
				String three=optionThree.getText().toString();
				ArrayList<String> names=new ArrayList<String>();
				names.add(one);
				names.add(two);
				names.add(three);
				Client client;
				try {
					client = new Client();
					client.startClient();
		        	client.sendMessage("1;"+userName+";"+one+";"+two+";"+three+";");
		        	
		        	String eventID=client.recvMessage();
		        	client.sendMessage("end");
		        	client.closeConnection();
		        	
		        	
		        	Intent intent = new Intent(CustomizeActivity.this, RestaurantList.class);
		        	intent.putExtra("names", names);
		        	intent.putExtra("userName",userName);
		        	intent.putExtra("eventID",eventID);
		        	
		        	
		        	CustomizeActivity.this.startActivity(intent);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
				
				
			}
		});

		
	}
}
