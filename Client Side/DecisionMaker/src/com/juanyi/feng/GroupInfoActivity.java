package com.juanyi.feng;

//import usc.ee579.hw1.R;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class GroupInfoActivity extends Activity {
	
	protected Button getInfoButton;
	protected Button chatButton;
	private TextView group_info;
	String eventIDs;
	TableLayout tl;
	String userName="";
	String eventID="";
	ArrayList<String> names;
	
	public void onCreate(Bundle savedInstanceState) {
		
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.groupinfo);
        Bundle extras = getIntent().getExtras();
        names=new ArrayList<String>();
        if(extras !=null) {
        names = extras.getStringArrayList("names");
        userName = extras.getString("userName");
        eventID=extras.getString("eventID");
        }
        
        getInfoButton = (Button) findViewById(R.id.getInfo);
        chatButton = (Button) findViewById(R.id.chat);
        group_info = (TextView) findViewById(R.id.textView_Groupinfo);
        
        
        
        
        
        group_info.setText("");
        getInfoButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Client client;
				try {
					client = new Client();
					client.startClient();
		        	client.sendMessage("5;"+eventID+";");
		        	String test=client.recvMessage();  
		        	String[] content=test.split(";");
		        	System.out.println(test);
		        	if(content.length!=0);
		        	{  
		        		group_info.setText(content[0]); 
		        		group_info.append("\n"); 
		        		for(int i=1;i<content.length;i++)
		        		{
		        			group_info.append(content[i]);
		        			group_info.append("\n");
		        		}
		        	}
		        	
		        	client.sendMessage("end");
		        	client.closeConnection();
		        	
		        	//Intent intent = new Intent(RestaurantList.this, GroupInfoActivity.class);
		        	//intent.putExtra("names", names);
		        	//intent.putExtra("userName",userName);
		        	//intent.putExtra("eventID",eventID);
		        	//RestaurantList.this.startActivity(intent);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//System.out.println("fail");
				}
	        	
			}
		});
        
        chatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
				Client client;
				try {
					client = new Client();
					client.startClient();
		        	client.sendMessage("6;"+eventID+";"+userName+";");
		        	String test=client.recvMessage();
		        	System.out.println(test);
		        	group_info.setText(test);
		        	client.sendMessage("end");
		        	client.closeConnection();
		        	
		        	//Intent intent = new Intent(RestaurantList.this, GroupInfoActivity.class);
		        	//intent.putExtra("names", names);
		        	//intent.putExtra("userName",userName);
		        	//intent.putExtra("eventID",eventID);
		        	//RestaurantList.this.startActivity(intent);
		        	Intent intent = new Intent(GroupInfoActivity.this, ChatTesterActivity.class);
		        	intent.putExtra("names", names);
		        	intent.putExtra("userName",userName);
		        	intent.putExtra("eventID",eventID);
		        	intent.putExtra("fileName",test);
		        	GroupInfoActivity.this.startActivity(intent);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
					//System.out.println("fail");
				}
				
			}
		});
        
	}
        

}
