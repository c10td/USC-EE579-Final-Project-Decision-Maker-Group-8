
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * A chatting server
 *
 * @author Juanyi Feng
 */

public class Apple extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
    	String fileName= request.getParameter("fileName");
		String type=request.getParameter("type");
		
		//if the request is getting chatting history
		if(type.equals("get"))
		{
			response.setContentType("text/html");

			String filePath = "/WEB-INF/"+fileName+".txt";
			try{
				ServletContext context = getServletContext();

				InputStream is = context.getResourceAsStream(filePath);
				if (is != null) {
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader reader = new BufferedReader(isr);
					PrintWriter writer = response.getWriter();
					String text = "";

					while ((text = reader.readLine()) != null) {
						writer.println(text);
					}
				}
			}
			catch(Exception e)  
			{  
				File file = new File(filePath);
				BufferedWriter output = new BufferedWriter(new FileWriter(file));
				output.write("");
				output.close();
			}  
		
		
		}
		
		//if the request is posting the chatting histoty
		else
		{
		
			String filePath = getServletContext().getRealPath("/WEB-INF/"+fileName+".txt");
			String message=request.getParameter("message");
			String userName=request.getParameter("userName");

		try{
			FileWriter out = new FileWriter(filePath, true);
			out.write(userName+":"+message+"\n ");
			out.close();
		}
		catch(Exception e)  
		{  
			File file = new File(filePath);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(userName+":"+message+"\n ");
			output.close();
		}  
		
		
		}
	    
		
       
    }
}