//Author: Juanyi Feng
package com.juanyi.feng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * An activity use for chat room of a group
 */
public class ChatTesterActivity extends Activity {
	EditText message;
	TextView chat;
	String currentMessage;
	String eventIDs;
	String userName="";
	String eventID="";
	String fileName="";
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        
        //get information
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
        userName = extras.getString("userName");
        eventID=extras.getString("eventID");
        fileName=extras.getString("fileName");
        
        }
        chat=(TextView)findViewById(R.id.chat);
        chat.setMovementMethod(new ScrollingMovementMethod());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        final Button submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				currentMessage=message.getText().toString();
				try {
					currentMessage=java.net.URLEncoder.encode(currentMessage, "UTF-8");
					System.out.println(currentMessage);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			    //send message
				URL url;
				try {
					url = new URL("http://cs-server.usc.edu:1054/examples/servlet/Apple?type=post&fileName="+fileName+"&userName="+userName+"&message="+currentMessage);
					HttpURLConnection con = (HttpURLConnection)url.openConnection();
					con.getInputStream();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        message=(EditText)findViewById(R.id.message);
        new RetreiveFeedTask().execute();
    }
    
    /**
     * use to refresh chatting history every 10s
     */
    private class RetreiveFeedTask extends AsyncTask<Void,String,Void> {

    	private Exception exception;

    	@Override
        protected Void doInBackground(Void... params) {
            try {
            	URL url = new URL("http://cs-server.usc.edu:1054/examples/servlet/Apple?type=get&fileName="+fileName);
            	String[] lines;
            	 while (!isCancelled()) {
                     SystemClock.sleep(10000);   
                     HttpURLConnection con = (HttpURLConnection)url.openConnection();
                     lines=readStream(con.getInputStream());
         			 publishProgress(lines);

                 }
            } catch (Exception e) {
                this.exception = e;
                return null;
            } 
    		return null;
        }
    	 protected void onProgressUpdate(String... lines) {
    		 if(lines[0]!=null)
    		 {
    			 chat.setText(lines[0]);
    		 }
    		 
    		 for( int i = 1; i < lines.length; i++){
    		       chat.append("\n "+lines[i]);
    		    }


    		
         }

        protected void onPostExecute() {
            // TODO: check this.exception 
            // TODO: do something with the feed
        }

    	/*
    	 * use to read data from perl server
    	 */
    	private String[] readStream(InputStream in) {
        	BufferedReader reader = null;
        	ArrayList<String> lineList=new ArrayList<String>();
        	
        	try {
        		reader = new BufferedReader(new InputStreamReader(in));
        		String line = "";
        		while ((line = reader.readLine()) != null) {
        			lineList.add(line);
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
        	String []lineArray = new String[lineList.size()];
        	lineList.toArray(lineArray);
        	return lineArray;
        }

    	

     }

   
    
}
