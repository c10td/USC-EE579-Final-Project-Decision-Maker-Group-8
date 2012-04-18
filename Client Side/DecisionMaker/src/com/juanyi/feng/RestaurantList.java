package com.juanyi.feng;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableRow.LayoutParams;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RestaurantList extends Activity{
	TableLayout tl;
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
		this.setContentView(R.layout.restaurant);
        Bundle extras = getIntent().getExtras();
        ArrayList<String> names=new ArrayList<String>();
        if(extras !=null) {
        names = extras.getStringArrayList("names");
        }
        //System.out.println(names);
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
                rb.setBackgroundColor(Color.rgb(255, 215, 0));
                tr2.addView(rb);
                tl.addView(tr2,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        	}
        	
        }
        Button submit=new Button(RestaurantList.this);
        submit.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        submit.setText("Submit");
        tl.addView(submit,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        //getWindow().getDecorView().findViewById(R.id.myTableLayout).invalidate();
	}

}
