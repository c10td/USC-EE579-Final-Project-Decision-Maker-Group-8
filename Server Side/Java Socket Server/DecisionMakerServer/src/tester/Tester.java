package tester;

import java.io.IOException;

import myclient.Client;

public class Tester {
	public static void main(String args[]) throws Exception{
	    try {
	      Client client = new Client();
	      client.startClient();
	      String msg1 = "1;testname1;option1;option2;option3";
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
	      
	      
	      Client client3 = new Client();
	      client3.startClient();
	      client3.sendMessage("3;testname4;1-testname1");
	      System.out.println("[Client]: " + "3;testname4;1-testname1");
	      System.out.println("[Server]: " + client3.recvMessage());
	      client3.sendMessage("End");
	      client3.closeConnection();
	      
	      Client client4 = new Client();
	      client4.startClient();
	      client4.sendMessage("3;testname5;1-testname1");
	      System.out.println("[Client]: " + "3;testname5;1-testname1");
	      System.out.println("[Server]: " + client4.recvMessage());
	      client4.sendMessage("End");
	      client4.closeConnection();
	      
	      Client client5 = new Client();
	      client5.startClient();
	      client5.sendMessage("3;testname6;1-testname1");
	      System.out.println("[Client]: " + "3;testname6;1-testname1");
	      System.out.println("[Server]: " + client5.recvMessage());
	      client5.sendMessage("End");
	      client5.closeConnection();
	      
	      Client client6 = new Client();
	      client6.startClient();
	      client6.sendMessage("4;testname1;1-testname1;0#0#5");
	      System.out.println("[Client]: " + "4;testname1;1-testname1;0#0#5");
	      System.out.println("[Server]: " + client6.recvMessage());
	      client6.sendMessage("End");
	      client6.closeConnection();
	      
	      Client client7 = new Client();
	      client7.startClient();
	      client7.sendMessage("4;testname2;1-testname1;5#0#5");
	      System.out.println("[Client]: " + "4;testname2;1-testname1;5#0#5");
	      System.out.println("[Server]: " + client7.recvMessage());
	      client7.sendMessage("End");
	      client7.closeConnection();
	      
	      Client client8 = new Client();
	      client8.startClient();
	      client8.sendMessage("4;testname3;1-testname1;1#3#2");
	      System.out.println("[Client]: " + "4;testname3;1-testname1;1#3#2");
	      System.out.println("[Server]: " + client8.recvMessage());
	      client8.sendMessage("End");
	      client8.closeConnection();
	      
	      Client client9 = new Client();
	      client9.startClient();
	      client9.sendMessage("4;testname4;1-testname1;5#1#3");
	      System.out.println("[Client]: " + "4;testname4;1-testname1;5#1#3");
	      System.out.println("[Server]: " + client9.recvMessage());
	      client9.sendMessage("End");
	      client9.closeConnection();
	      
	      Client client10 = new Client();
	      client10.startClient();
	      client10.sendMessage("4;testname5;1-testname1;2#5#4");
	      System.out.println("[Client]: " + "4;testname5;1-testname1;2#5#4");
	      System.out.println("[Server]: " + client10.recvMessage());
	      client10.sendMessage("End");
	      client10.closeConnection();
	      
	      Client client11 = new Client();
	      client11.startClient();
	      client11.sendMessage("4;testname6;1-testname1;4#5#1");
	      System.out.println("[Client]: " + "4;testname6;1-testname1;4#5#1");
	      System.out.println("[Server]: " + client11.recvMessage());
	      client11.sendMessage("End");
	      client11.closeConnection();
	      
	      Client client12 = new Client();
	      client12.startClient();
	      client12.sendMessage("5;1-testname1");
	      //System.out.println(client12.recvMessage());
	      
	      System.out.println("[Client]: " + "5;1-testname1");
	      System.out.println("[Server]: " + client12.recvMessage());
	      client12.sendMessage("End");
	      client12.closeConnection();
	      
	      Client client13 = new Client();
	      client13.startClient();
	      client13.sendMessage("5;1-testname1");
	      //System.out.println(client12.recvMessage());
	      
	      System.out.println("[Client]: " + "5;1-testname1");
	      System.out.println("[Server]: " + client13.recvMessage());
	      client13.sendMessage("End");
	      client13.closeConnection();
	      
	      
	     
	      
	      
	    } catch(IOException e) {
	      e.printStackTrace();
	    }
	  }

}

