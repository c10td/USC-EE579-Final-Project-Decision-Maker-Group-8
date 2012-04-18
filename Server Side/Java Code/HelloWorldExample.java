import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Hashtable;
import java.util.regex.Pattern;
import org.jdom.*;
import java.text.*;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.File;


public class HelloWorldExample extends HttpServlet{
	
	int count = 0;
	Hashtable table = new Hashtable<String,Hashtable<String,ArrayList<Float>>>();
	Hashtable optionTable = new Hashtable<String, String[]>();
	Hashtable resultTable = new Hashtable<String, Hashtable<String, Integer>>();
	
	
	protected void processRequest(HttpServletRequest request,HttpServletResponse response,String method) throws ServletException,IOException
	{
		
		response.setContentType("application/x-json");
		PrintWriter out=response.getWriter();
		
		
    String reqTypeStr = request.getParameter("type");
    
    int reqType = Integer.parseInt(reqTypeStr);
    
    switch (reqType) {
    	
    	case 1: 
    	  String userName = request.getParameter("name");
    	  count++;
    	  String countStr = Integer.toString(count);
    	  String groupID = coutStr + "-" + userName;
    	  table.put(groupID,new Hashtable<String,ArrayList<Float>>());
    	  resultTable.put(groupID,new Hashtable<String,Integer>());
    	  
    	  String[] options;
    	  String optionStr = request.getParameter("options");
    	  options = optionStr.spilt(";");
    	  optionTable.put(groupID, options);
    	  
    	  out.println(groupID);
    	  break;
    	      	
    	case 2:    	
    	  Set<String> groupSet = table.keySet();
    	  int num = groupSet.size();
    	  String output = "";
    	  if (num==0)
    	  {
    	  	output = "no group";
    	  }
    	  else
    	  {
    	  	for (String x:groupSet)
    	  	{
    	  		output = output + x + ";";
    	  	}
    	  }
    	  out.println(output);
    	  break;
    	
    	  
    	case 3:
    	  String group = request.getParameter("groupid");
    	  String username = request.getParameter("name");
    	  
    	  Hashtable groupTable = new Hashtable<String,Integer>();
    	  groupTable = (Hashtable<String,Integer>)resultTable.get(group);
    	  int i = 0;
    	  groupTable.put(username,i);
    	  
    	  String group_option[];
    	  group_option = (String[])optionTable.get(group);
    	  String output = "";
    	  for (String x:group_option)
    	  {
    	  	output = output + x + ";";
    	  }
    	  out.println(output);
    	  break;
    	     	  
    	  
    	case 4:
    	  String group = request.getParameter("groupid");
    	  String user = request.getParameter("name");
    	  String pListStr = request.getParameter("plist");
    	  String[] pArray = pListStr.split(";");
    	  ArrayList<Float> pList = new ArrayList<Float>();
    	  for (String x:pArray)
    	  {
    	  	float f = Float.parseFloat(x); 
    	  	pList.add(f);
    	  }
    	  	
    	  Hashtable groupTable = new Hashtable<String,ArrayList<Float>>();
    	  groupTable = (Hashtable<String,ArrayList<Float>>)table.get(group);
    	  groupTable.put(user,pList);
    	  break;
    	  
    	case 5:
    	  String group = request.getParameter("groupid");
    	  String user = request.getParameter("name");
    	  Hashtable groupTable = new Hashtable<String,ArrayList<Float>>();
    	  groupTable = (Hashtable<String,ArrayList<Float>>)table.get(group);
    	  
    	  String[] optionsStr;
    	  optionsStr = (String[])optionTable.get(group);
    	  int optionNum = optionsStr.length;
    	  
  			
		}
			
			
			
			
			
	
		  
	  
	  catch (IOException io) 
	  {
		  out.println(io.getMessage());
		  //out.println("error");
	  } 
	  
	  catch (JDOMException jdomex) 
	  {
		  out.println(jdomex.getMessage());
		  //out.println("error1");
	  }
	
	
	//out.println("test");
	out.close();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		processRequest(request,response,"GET");
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		processRequest(request,response,"POST");
	}
	
}