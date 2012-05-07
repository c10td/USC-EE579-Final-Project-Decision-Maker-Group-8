package com.juanyi.feng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RestaurantActivity extends Activity {
	EditText et;
	Button submitButton;
	Button getZipCodeButton;
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
        getZipCodeButton=(Button)findViewById(R.id.getZipCode);
        getZipCodeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new ZipCodeHolder();
				
			}
		});
       
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
		        	
		        	Intent intent = new Intent(RestaurantActivity.this, RestaurantList.class);
		        	intent.putExtra("names", names);
		        	intent.putExtra("userName",userName);
		        	intent.putExtra("eventID",eventID);
		        	
		        	
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
    public class ZipCodeHolder {


    	public ZipCodeHolder()
    	{
    		  LocationManager locationManager;
    	        String serviceName = Context.LOCATION_SERVICE;
    	        locationManager = (LocationManager)getSystemService(serviceName);
    	        //String provider = LocationManager.GPS_PROVIDER;
    	        
    	        Criteria criteria = new Criteria();
    	        criteria.setAccuracy(Criteria.ACCURACY_FINE);
    	        criteria.setAltitudeRequired(false);
    	        criteria.setBearingRequired(false);
    	        criteria.setCostAllowed(true);
    	        criteria.setPowerRequirement(Criteria.POWER_LOW);
    	        String provider = locationManager.getBestProvider(criteria, true);
    	        
    	        Location location = locationManager.getLastKnownLocation(provider);
    	        updateWithNewLocation(location);
    	        locationManager.requestLocationUpdates(provider, 2000, 10,
    	                        locationListener);
    	}
    	private final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
            }
            public void onProviderDisabled(String provider){
            updateWithNewLocation(null);
            }
            public void onProviderEnabled(String provider){ }
            public void onStatusChanged(String provider, int status,
            Bundle extras){ }
    };
    	
    	private void updateWithNewLocation(Location location) {
            String latLongString;
            EditText myLocationText;
            myLocationText = (EditText)findViewById(R.id.editText1);
            
             
            if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
           
            } 
            String currentZipCode=reverseGeocode(location);   
            myLocationText.setText(currentZipCode);
    }
    	 public String reverseGeocode(Location location)
    		{
    		 String PostalCode = null;	
    		 try 
    		    {
    		        // build the URL using the latitude & longitude you want to lookup
    		        String url = "http://maps.google.com/maps/geo?q=" + Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude()) +"&output=json";
    		        //String url = "http://maps.google.com/maps/geo?q=" + 37.422066 + "," + -122.084095 +"&output=json";
    		       
    		        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    		        StrictMode.setThreadPolicy(policy);
    		        
    		        //set up out communications stuff
    		       
    		        String response = null;
    				HttpClient httpclient = new DefaultHttpClient();
    				HttpGet httpGet = new HttpGet(url);				  
    				HttpResponse httpResponse = httpclient.execute(httpGet);
    				HttpEntity entity = httpResponse.getEntity();
    				if (entity != null) 
    				{  
    					response = EntityUtils.toString(entity);
    				}
    				
    				JSONObject myJsObj = new JSONObject(response);
    				String Placemark = myJsObj.getString("Placemark");
    				if (Placemark.contains("PostalCodeNumber"))
    				{
    					int i = Placemark.indexOf("PostalCodeNumber") + 19;
    					int j = Placemark.indexOf("PostalCodeNumber") + 24;
    					PostalCode = Placemark.substring(i, j);
    				}
    						
    				TextView myZipCode;
    	          //  myZipCode = (TextView)findViewById(R.id.myZipCode);
    	          //  myZipCode.setText("Your zip code is:\n" + PostalCode);   
    				
    		    }
    		    catch (Exception ex)
    		    {
    		        ex.printStackTrace();
    		    }
    		    return PostalCode;
    		}

    }

}