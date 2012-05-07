package com.juanyi.feng;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow.LayoutParams;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RestaurantList extends Activity{
	TableLayout tl;
	String userName="";
	String eventID="";
	ArrayList<String> names;
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
		this.setContentView(R.layout.restaurant);
        Bundle extras = getIntent().getExtras();
        names=new ArrayList<String>();
        if(extras !=null) {
        names = extras.getStringArrayList("names");
        userName = extras.getString("userName");
        eventID=extras.getString("eventID");
        }
        System.out.println(eventID);
        
        tl =(TableLayout)findViewById(R.id.myTableLayout);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/MEgalopolisExtra.otf");
        for(int i=0;i<names.size();i++)
        {
        	if((!(names.get(i).equals(" 	")))&&(!names.get(i).isEmpty()))
        	{
        		TableRow tr = new TableRow(RestaurantList.this);
                tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));			         
                TextView name = new TextView(RestaurantList.this);
                name.setText(names.get(i));
                name.setTextSize(25);
                name.setLayoutParams(new LayoutParams(345,LayoutParams.MATCH_PARENT));
                name.setTypeface(tf);
                name.setTextColor(Color.rgb(205, 102, 29));
                tr.addView(name);
                tl.addView(tr,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
                TableRow tr2 = new TableRow(RestaurantList.this);
                tr2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));			         
                RatingBar rb=new RatingBar(RestaurantList.this);
                rb.setId(i);
                rb.setBackgroundColor(Color.rgb(255, 215, 0));
                tr2.addView(rb);
                tl.addView(tr2,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        	}
        	
        }
        Button submit=new Button(RestaurantList.this);
        submit.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        submit.setText("Submit");
        tl.addView(submit,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ratingString="";
				for(int i=0;i<names.size();i++)
				{
					float currentRate=((RatingBar)findViewById(i)).getRating();
					ratingString+=(currentRate+"#");
					System.out.println(currentRate);
				}
				Client client;
				try {
					client = new Client();
					client.startClient();
		        	client.sendMessage("4;"+userName+";"+eventID+";"+ratingString);
		        	String test=client.recvMessage();
		        	System.out.println(test);
		        	client.sendMessage("end");
		        	client.closeConnection();
		        	
		        	Intent intent = new Intent(RestaurantList.this, GroupInfoActivity.class);
		        	intent.putExtra("names", names);
		        	intent.putExtra("userName",userName);
		        	intent.putExtra("eventID",eventID);
		        	RestaurantList.this.startActivity(intent);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//System.out.println("fail");
				}
	        	
	        	
				
			}
		});
        //getWindow().getDecorView().findViewById(R.id.myTableLayout).invalidate();
	}

}
