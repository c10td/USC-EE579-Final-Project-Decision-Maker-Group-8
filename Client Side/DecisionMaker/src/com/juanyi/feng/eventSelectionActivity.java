//Author: Juanyi Feng
package com.juanyi.feng;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

/**
 * An activity use for event seletion
 */
public class eventSelectionActivity   extends Activity {
	String eventIDs;
	TableLayout tl;
	String userName="";
	String eventID="";
	ArrayList<String> names;
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.
    	ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
		Bundle extras = getIntent().getExtras();
		if(extras !=null) {
			eventIDs = extras.getString("eventIDs");
		}
		System.out.println(eventIDs);
		userName = extras.getString("userName");
		
		String[] events=eventIDs.split(";");
		this.setContentView(R.layout.restaurant);
		 tl =(TableLayout)findViewById(R.id.myTableLayout);
	        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/MEgalopolisExtra.otf");
	        
	        //show all events programatically
	        for(int i=0;i<events.length;i++)
	        {
	        	
	        		TableRow tr = new TableRow(eventSelectionActivity.this);
	                tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));			         
	                TextView name = new TextView(eventSelectionActivity.this);
	                name.setText(events[i]);
	                name.setTextSize(25);
	                name.setLayoutParams(new LayoutParams(345,LayoutParams.MATCH_PARENT));
	                name.setTypeface(tf);
	                name.setTextColor(Color.rgb(205, 102, 29));
	                tr.addView(name);
	                tl.addView(tr,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
	               
	        	
	        	
	        }
	        final EditText result=new EditText(eventSelectionActivity.this);
	        result.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	        tl.addView(result,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
	        Button submit=new Button(eventSelectionActivity.this);
	        submit.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	        submit.setText("Submit");
	        tl.addView(submit,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
	        submit.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String selectedGroup=result.getText().toString();
					Client client;
					try {
						client = new Client();
						client.startClient();
			        	client.sendMessage("3;"+userName+";"+selectedGroup+";");
			        	String options=client.recvMessage();
			        	String[] optionsArray=options.split(";");
			        	ArrayList<String> names=new ArrayList<String>();
			        	for(String current: optionsArray)
			        	{
			        		names.add(current);
			        	}
			        	Intent intent = new Intent(eventSelectionActivity.this, RestaurantList.class);
			        	intent.putExtra("names", names);
			        	intent.putExtra("userName",userName);
			        	intent.putExtra("eventID",selectedGroup);
			        	
			        	
			        	
			        	client.sendMessage("end");
			        	client.closeConnection();
			        	eventSelectionActivity.this.startActivity(intent);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
			});
	}

}
