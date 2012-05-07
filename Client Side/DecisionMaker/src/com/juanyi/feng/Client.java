package com.juanyi.feng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
  private OutputStream outToServer = null;
  private BufferedReader inFromServer = null;
  private Socket clientSocket = null;
  private final String defaultServerIP = "207.151.66.29";
  private final int defaultServerPort = 1099; 
  private PrintWriter out = null;
  private String fromClient = null;
  

  public Client() throws IOException {
    initializeClient(defaultServerIP, defaultServerPort);
  }
  
  public Client(String serverIP, int serverPort) {    
    initializeClient(serverIP, serverPort);
  }
  
  //Initialize the client, set up the sockets
  private void initializeClient(String serverIP, int serverPort) {
	try {
	  clientSocket = new Socket(serverIP, serverPort);
    } catch (UnknownHostException e) {
        System.err.println("Don't know about host:" + serverIP);
        System.exit(1);
    } catch (IOException e) {
        System.err.println("Couldn't get I/O for the connection to:" + serverIP);
        System.exit(1);
    }
  }
  
  //Start the client, communicates with the server
  public void startClient() throws IOException{  
	outToServer = clientSocket.getOutputStream();
	out = new PrintWriter(outToServer, true);
	inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }
  
  //Send message to the server
  public void sendMessage(String msg) throws IOException{
    //Packet packet = new Packet(msg);
    //fromClient = msg;
    out.println(msg);  
  }

  //Receive message from the server, return the current state
  public String recvMessage() throws IOException {
	
	//Packet packet = new Packet();    
    String fromServer = null;
    fromServer = inFromServer.readLine();
    String recvMsg = fromServer;
 
    return recvMsg;
  }
  

  //Close connection between client and server
  public boolean closeConnection() {
    try {
      clientSocket.close();
      return true;
    }catch(IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}