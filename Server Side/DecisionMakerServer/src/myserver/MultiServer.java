package myserver;

import java.net.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import java.io.*;

public class MultiServer 
{         
    private ServerSocket m_ServerSocket; 
    private int serverPort = 1099;	
	private int count = 0;
	private Hashtable<String,Hashtable<String,ArrayList<Float>>> table = new Hashtable<String,Hashtable<String,ArrayList<Float>>>();
	private Hashtable<String, String[]> optionTable = new Hashtable<String, String[]>();
	private Hashtable<String, Hashtable<String, Integer>> resultTable = new Hashtable<String, Hashtable<String, Integer>>();
	private Hashtable<String, ArrayList<ArrayList<String>>> groupingTable = new Hashtable<String, ArrayList<ArrayList<String>>>();
     
     
    public MultiServer()  
    { 
        try 
        { 
            // Create the server socket. 
            m_ServerSocket = new ServerSocket(serverPort); 
        } 
        catch(IOException ioe) 
        { 
            System.out.println("Could not listen on port:" + serverPort); 
            System.exit(-1); 
        } 
         
        System.out.println("Listening for clients on " + serverPort); 
         
        // Successfully created Server Socket. Now wait for connections. 
        int id = 0; 
        while(true) 
        {                         
            try 
            { 
                // Accept incoming connections. 
                Socket clientSocket = m_ServerSocket.accept(); 
             
                // accept() will block until a client connects to the server. 
                // If execution reaches this point, then it means that a client 
                // socket has been accepted. 
                 
                // For each client, we will start a service thread to 
                // service the client requests. Starting a thread also lets our 
                // Server accept multiple connections simultaneously. 
                 
                // Start a service thread 
                 
                ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++); 
                cliThread.start(); 
            } 
            catch(IOException ioe) 
            { 
                System.out.println("Exception encountered on accept. Ignoring. Stack Trace :"); 
                ioe.printStackTrace(); 
            } 
        } 
    } 
     
    public static void main (String[] args) 
    { 
        new MultiServer();     
    } 
     
     
    class ClientServiceThread extends Thread 
    { 
        Socket m_clientSocket;         
        int m_clientID = -1; 
        boolean m_bRunThread = true; 
         
        ClientServiceThread(Socket s, int clientID) 
        { 
            m_clientSocket = s; 
            m_clientID = clientID; 
        } 
        
        public void run() 
        {             
            // Obtain the input stream and the output stream for the socket 
            // Encapsulate them with a BufferedReader and a PrintWriter as shown below. 
            BufferedReader in = null;  
            PrintWriter out = null; 
             
            // Print out details of this connection 
            System.out.println("Accepted Client : ID - " + m_clientID + " : Address - " +  
                             m_clientSocket.getInetAddress().getHostName()); 
                 
            try 
            {                                 
                in = new BufferedReader(new InputStreamReader(m_clientSocket.getInputStream())); 
                out = new PrintWriter(new OutputStreamWriter(m_clientSocket.getOutputStream())); 
                                 
                // Run in a loop until m_bRunThread is set to false.                
                while(m_bRunThread) 
                {                     
                    // Read incoming stream. Print the client's message into console
                    String clientCommand = in.readLine(); 
                     
                    System.out.println("[Client Says]: " + clientCommand); 
                                        
                    if(clientCommand.equalsIgnoreCase("END")) 
                    { 
                        // Special command. Quit this thread 
                        m_bRunThread = false;    
                        System.out.print("Stopping client thread for client : " + m_clientID); 
                    } 
                    else 
                    { 
                    	// Print the server's response message into console
                    	String output = processInput(clientCommand);
                    	System.out.println("[Server Says]: " + output); 
                        out.println(output); 
                        out.flush(); 
                    } 
                } 
            } 
            catch(Exception e) 
            { 
                e.printStackTrace(); 
            } 
            finally 
            { 
                // Clean up 
                try 
                {                     
                    in.close(); 
                    out.close(); 
                    m_clientSocket.close(); 
                    System.out.println("...Stopped"); 
                } 
                catch(IOException ioe) 
                { 
                    ioe.printStackTrace(); 
                } 
            } 
        }
        
        // Process received request message from the client
        private String processInput(String msg){
      	  String[] inputArray = msg.split(";");
      	  int type = Integer.parseInt(inputArray[0]);
      	  
      	  switch (type){
      	  
      	  // Event host creates a event. Server returns eventID.
      	  case 1: 
      		  int arrayLength = inputArray.length;
      		  String userName = inputArray[1];
      		  count++;
          	  String countStr = Integer.toString(count);
          	  String eventID = countStr + "-" + userName;
          	  table.put(eventID,new Hashtable<String,ArrayList<Float>>());
          	    	  
          	  groupingTable.put(eventID, new ArrayList<ArrayList<String>>());
              resultTable.put(eventID,new Hashtable<String,Integer>());
          	  
          	  String[] options = new String[arrayLength-2];
          	  for (int i = 2; i<arrayLength; i++){
          		  options[i-2] = inputArray[i];
          	  }
          		  
          	  optionTable.put(eventID, options);
          	  
          	  Hashtable<String, Integer> groupTable1 = new Hashtable<String,Integer>();
         	  groupTable1 = (Hashtable<String,Integer>)resultTable.get(eventID);
         	  int ii = -1;
         	  groupTable1.put(userName,ii);
         	           	  
          	  return eventID;
          	  
          // Client browses all the existing events stored on the server.
          // Returns existing events' eventID.
      	  case 2:    	
          	  Set<String> groupSet = table.keySet();
          	  int num = groupSet.size();
          	  String output2 = "";
          	  if (num==0)
          	  {
          	  	output2 = "no group";
          	  }
          	  else
          	  {
          	  	for (String x:groupSet)
          	  	{
          	  		output2 = output2 + x + ";";
          	  	}
          	  }
          	  return output2;
          	  
          	  
          // Client wants to join an event. Server returns all the options of this event.	  
      	  case 3:
          	  String event = inputArray[2];
          	  String username = inputArray[1];
          	  
          	  Hashtable<String, Integer> groupTable = new Hashtable<String,Integer>();
          	  groupTable = (Hashtable<String,Integer>)resultTable.get(event);
          	  int i = -1;
          	  groupTable.put(username,i);
          	  
          	  String group_option[];
          	  group_option = (String[])optionTable.get(event);
          	  String output3 = "";
          	  for (String x:group_option)
          	  {
          	  	output3 = output3 + x + ";";
          	  }
          	  return output3;
          	  
          	  
          // Client sends his preference list to server.	  
      	  case 4:
          	  String event4 = inputArray[2];
          	  String user = inputArray[1];
          	  String pListStr = inputArray[3];
          	  String[] pArray = pListStr.split("#");
          	  ArrayList<Float> pList = new ArrayList<Float>();
          	  for (String x:pArray)
          	  {
          	  	float f = Float.parseFloat(x); 
          	  	pList.add(f);
          	  }
          	  	
          	  Hashtable<String, ArrayList<Float>> groupTable4 = new Hashtable<String,ArrayList<Float>>();
          	  groupTable4 = (Hashtable<String,ArrayList<Float>>)table.get(event4);
          	  groupTable4.put(user,pList);
          	  String output4 = "Get " + user + "'s list!";
          	  return output4;
      	  
          	// Generate Grouping Result for certain event.  The Server returns the grouping result.
            case 5:
        	  String event5 = inputArray[1];
          	  String[] optionsStr;
          	  optionsStr = (String[])optionTable.get(event5);
          	  int optionNum = optionsStr.length;
          	  Hashtable<Integer, float[]> decisionPoint = new Hashtable<Integer, float[]>();
          	  
          	  for (int k = 0; k < optionNum; k++){
          		  float[] decisionPointTemp = new float[optionNum];
          		  decisionPointTemp[k] = 5;
          		  decisionPoint.put(k, decisionPointTemp);
          	  }
          	  disHash(decisionPoint); 
          	 
          	  Hashtable<Integer, float[]> newDecisionPoints = new Hashtable<Integer, float[]>();
          	  Hashtable<Integer, float[]> oldDecisionPoints = new Hashtable<Integer, float[]>();
          	  oldDecisionPoints = kMeans(event5, decisionPoint);
          	  disHash(oldDecisionPoints);    	  
          	  
          	  int count = 0;
          	  do{
          		  newDecisionPoints = kMeans(event5, oldDecisionPoints);
          		  disHash(newDecisionPoints);
          		  oldDecisionPoints = kMeans(event5, newDecisionPoints);
          		  disHash(oldDecisionPoints); 
          		  count++;
          		  System.out.println("Loop Num: " + count);
          	  }while(!(compareHash(newDecisionPoints, oldDecisionPoints)));
          	
          	  Hashtable<String, Integer> result = new Hashtable<String, Integer>();
          	  result = resultTable.get(event5);
          	  String output5 = "";         	  
          	  for (String x:result.keySet()){
          		  output5 = output5 + x + ": " + result.get(x) + "; ";
          	  }
          	  
          	  String[] optionList = optionTable.get(event5);
          	  String output51="";
          	  ArrayList<ArrayList<String>> resultlist = new ArrayList<ArrayList<String>>();
          	  resultlist = groupingTable.get(event5);
          	  
          	  for (int x=0; x<optionNum; x++){
          		  if(resultlist.get(x).size() != 0){
          			  output51+="#" + optionList[x] + "   # : "; 
          			  for (String s:resultlist.get(x)){
          				  output51+=" [" + s + "] ";
          			  }
          			  output51+="; ";
          		  }
          	  }
          	  
          	  return output51;
          	  
          		  
          	// The client looks up which sub-group he is divided into.
            case 6:
            	String event6 = inputArray[1];
            	String userName6 = inputArray[2];
            	Hashtable<String, Integer> eventResult = new Hashtable<String, Integer>();
            	eventResult = resultTable.get(event6);
            	int groupNum = eventResult.get(userName6);
            	String output6 ="";
            	output6 += event6 + groupNum;
            	
            	return output6;
            	           	
          	      
            default:
              	return null;	
      	    }
      } 
        
      //Helper function to perform the K-Means Clustering Algorithm.  
      private Hashtable<Integer, float[]> kMeans(String event, Hashtable<Integer, float[]> decisionPoints){
    	  Hashtable<String, ArrayList<Float>> groupTable = new Hashtable<String,ArrayList<Float>>();
      	  groupTable = (Hashtable<String,ArrayList<Float>>)table.get(event);
      	  Hashtable<String, Integer> groupResult = new Hashtable<String, Integer>();
      	  groupResult = resultTable.get(event);
      	  
      	  String[] optionsStr;
      	  optionsStr = (String[])optionTable.get(event);
      	  int optionNum = optionsStr.length;
      	  
      	  ArrayList<ArrayList<String>> group = new ArrayList<ArrayList<String>>();
    	  group = groupingTable.get(event);
    	  group.clear();
    	  
    	  for (int i = 0; i<optionNum; i++){
    		  ArrayList<String> templist = new ArrayList<String>();
    		  group.add(templist);
    	  }
    	  
      	        	 
      	  for (String x : groupTable.keySet()){
      		  float[] tempArray;
      		  tempArray = list2Array(groupTable.get(x));
      		  float[] distanceArray = new float[optionNum];
      		  for (int m=0; m<optionNum; m++){
      			  float[] decisionLoc = decisionPoints.get(m);
      			  distanceArray[m] = calculateDis(tempArray, decisionLoc);
      		  }
      		  int minLoc = findMin(distanceArray);
      		  groupResult.put(x, minLoc);
      		  group.get(minLoc).add(x);
      	  }
      	
      	  Hashtable<Integer,float[]> newDecisionPoints = new Hashtable<Integer,float[]>();
      	  
      	  //Calculate the new Means(Decision Points)
      	  for (int i = 0; i<optionNum;i++){
      		  ArrayList<String> subgroup = group.get(i);
      		  if (subgroup.size() != 0){
      			  ListIterator<String> iter = subgroup.listIterator();
      			  float[] tempSum = new float[optionNum];
      			  for (int k = 0; k<optionNum; k++){
      				  tempSum[k] = 0;
      			  }
      			  while (iter.hasNext()){
      				  String name = iter.next();
      				  float[] tempArray1;
      				  tempArray1 = list2Array(groupTable.get(name));
      				  tempSum = arraySum(tempSum, tempArray1);
      			  }
      			  for (int k = 0; k<optionNum; k++){
    				  tempSum[k] = tempSum[k]/subgroup.size();
    			  }
      			  
      			  newDecisionPoints.put(i, tempSum);
      		  }
      		  else{
      			  float tempArr[] = new float[optionNum];
      			  for (int n=0; n<optionNum; n++){
      				  if (n == i){
      					  tempArr[n] = 5;
      				  }
      				  else{
      					  tempArr[n] = 0;
      				  }
      			  }
      			  newDecisionPoints.put(i, tempArr);
      		  }
      	  } 	  
      	  
      	  return newDecisionPoints;
    	  
      }
        
      // Helper function to convert an ArrayList of float into array.  
      private float[] list2Array(ArrayList<Float> list){
    	  Iterator<Float> iterator = list.iterator();
    	  int length = list.size();
    	  float[] array = new float[length];
    	  for (int i = 0; i<length; i++){
    		  array[i] = iterator.next();
    	  }
    	  return array;
      }
      
      // Helper function to calculate distance between two points.
      private float calculateDis(float[] a, float[] b){
    	  int length = a.length;
    	  float result = 0;
    	  for (int i = 0; i < length; i++)
    	  {
    		  result = result + (a[i] - b[i]) * (a[i] - b[i]);
    	  } 
    	  return result;
      }
      
      // Helper function to find the minimum element in an array of float.
      private int findMin(float[] array){
    	  int length = array.length;
    	  float min = 100;
    	  int loc = 0;
    	  for (int i = 0; i<length; i++){
    		  if (array[i]<min){
    			  min = array[i];
    			  loc = i;
    		  }    		  
    	  }
    	  return loc;
      }
      
      // Helper function to calculate the sum of two float array.
      private float[] arraySum(float[] a, float[] b){
    	  int length = a.length;
    	  float result[] = new float[length];
    	  for (int i = 0; i<length; i++){
    		  result[i] = a[i] + b[i];
    	  }
    	  return result;	 
      }
      
      // Helper function to compare two HashTable.
      private boolean compareHash(Hashtable<Integer,float[]> hashTableA, Hashtable<Integer,float[]> hashTableB){
    	  
    	  int sizeA = hashTableA.size();
    	  int sizeB = hashTableB.size();
    	  if (sizeA != sizeB){
    		  return false;
    	  }
    	  
    	  for (int x : hashTableA.keySet()){
    		  if (!(compareArray(hashTableB.get(x), hashTableA.get(x)))){
    			  return false;
    		  }
    	  }
    	  return true;
    	  
      }
      
      
      // Helper function to compare two float array.
      private boolean compareArray(float[] a, float[] b){
    	  int size = a.length;
    	  for (int i = 0; i<size; i++){
    		  if (Math.abs(a[i] - b[i]) > 0.001 ){
    			  //System.out.println(a[i] + " ------- " +b[i]);
    			  return false;
    		  }
    	  }
    	  return true;
      }
      
      
      // Helper function to convert an array of float into string.
      private String arry2string(float[] a){
    	  String output="";
    	  for (float f:a){
    		  output+=f+"; ";
    	  }
    	  return output;
      }
      
      // Helper function to display the location of decision point into console.
      private void disHash(Hashtable<Integer, float[]> table){
    	  for (int i:table.keySet()){
    		  System.out.println("##DecisionPoint[" + i + "]## :" + arry2string(table.get(i)));
    	  }
    	  System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      }
     
    
   }
}
