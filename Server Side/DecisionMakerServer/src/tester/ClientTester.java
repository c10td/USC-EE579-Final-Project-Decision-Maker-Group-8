package tester;

import java.io.IOException;

import myclient.Client;

public class ClientTester {
	public static void main(String args[]) throws Exception{
	    try {
	    	Client client = new Client();
		      client.startClient();
		      String msg1 = "1;testname1;option1;option2";
		      client.sendMessage(msg1);
		      System.out.println("[Client]: " + msg1);
		      System.out.println("[Server]: " + client.recvMessage());
		      client.sendMessage("End");
		      client.closeConnection();
		      
		      Client client1 = new Client();
		      client1.startClient();
		      client1.sendMessage("3;testname2;1-testname1");
		      System.out.println("[Client]: " + "3;testname2;1-testname1");
		      System.out.println("[Server]: " + client1.recvMessage());
		      client1.sendMessage("End");
		      client1.closeConnection();
		      
		      Client client2 = new Client();
		      client2.startClient();
		      client2.sendMessage("3;testname3;1-testname1");
		      System.out.println("[Client]: " + "3;testname3;1-testname1");
		      System.out.println("[Server]: " + client2.recvMessage());
		      client2.sendMessage("End");
		      client2.closeConnection();
		      
		      
		     
		      
		     
		      
		      
		      Client client6 = new Client();
		      client6.startClient();
		      client6.sendMessage("4;testname1;1-testname1;5#0");
		      System.out.println("[Client]: " + "4;testname1;1-testname1;5#0#0");
		      System.out.println("[Server]: " + client6.recvMessage());
		      client6.sendMessage("End");
		      client6.closeConnection();
		      
		      Client client7 = new Client();
		      client7.startClient();
		      client7.sendMessage("4;testname2;1-testname1;0#5");
		      System.out.println("[Client]: " + "4;testname2;1-testname1;0#5#0");
		      System.out.println("[Server]: " + client7.recvMessage());
		      client7.sendMessage("End");
		      client7.closeConnection();
		      
		      Client client8 = new Client();
		      client8.startClient();
		      client8.sendMessage("4;testname3;1-testname1;5#3");
		      System.out.println("[Client]: " + "4;testname3;1-testname1;0#0#5");
		      System.out.println("[Server]: " + client8.recvMessage());
		      client8.sendMessage("End");
		      client8.closeConnection();
		      
		      
		      
		    
		      Client client12 = new Client();
		      client12.startClient();
		      client12.sendMessage("5;1-testname1");
		      //System.out.println(client12.recvMessage());
		      
		      System.out.println("[Client]: " + "5;1-testname1");
		      System.out.println("[Server]: " + client12.recvMessage());
		      client12.sendMessage("End");
		      client12.closeConnection();
		      
	      
	    } catch(IOException e) {
	      e.printStackTrace();
	    }
	  }

}
