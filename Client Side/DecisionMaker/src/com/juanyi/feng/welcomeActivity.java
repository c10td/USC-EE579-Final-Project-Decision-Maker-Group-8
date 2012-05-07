package com.juanyi.feng;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class welcomeActivity extends Activity {
	 EditText userName;
	String userNameString;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.
    	ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.start); 
        userName=(EditText)findViewById(R.id.userName);
        
        Button create=(Button)findViewById(R.id.createButton);
        
        create.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userNameString=userName.getText().toString();
				Intent intent = new Intent(welcomeActivity.this, TabLayoutActivity.class);
	        	intent.putExtra("userName", userNameString);
	        	
				welcomeActivity.this.startActivity(intent);
				
			}
		});
		Button join=(Button)findViewById(R.id.joinButton);
		join.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Client client;
				try {
					userNameString=userName.getText().toString();
					client = new Client();
					client.startClient();
		        	client.sendMessage("2;");
		        	String eventIDs=client.recvMessage();
		        	Intent intent = new Intent(welcomeActivity.this, eventSelectionActivity.class);
		        	intent.putExtra("eventIDs", eventIDs);
		        	intent.putExtra("userName", userNameString);
		        	
		        	
		        	client.sendMessage("end");
		        	client.closeConnection();
		        	welcomeActivity.this.startActivity(intent);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
			}
		});
	}
	

}
