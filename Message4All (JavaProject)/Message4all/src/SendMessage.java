import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import net.ucanaccess.jdbc.UcanaccessSQLException;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

 public class SendMessage extends HttpServlet {
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	   response.setContentType("text/html");
	   HttpSession session = request.getSession();
	   PrintWriter out = response.getWriter();
	   String user = request.getParameter("user");
	   String msg = request.getParameter("msg");
	   String me = (String) session.getAttribute("user");
	   try {
	       Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	     } catch (ClassNotFoundException e) {
	       out.println("Errore: Impossibile caricare il Driver Ucanaccess");
	     } 
	     Connection connection=null;
	     try {
	       connection = DriverManager.getConnection("jdbc:ucanaccess://" + request.getServletContext().getRealPath("/") + "Users.accdb");
	       SimpleDateFormat form=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	       String data = form.format(new Date());
	       
	       String query = "INSERT INTO chat(Mittente, Destinatario, Messaggio, TimeStamp) VALUES ('"+me+"','"+user+"','"+msg+"', #"+data+"#);";
	       Statement st = connection.createStatement();
	       st.executeUpdate(query);
	       response.sendRedirect(request.getContextPath() + "/show-chat.jsp?user="+user);
	     }
	     catch(Exception e) {
	    	 out.print(e);
	     }
	     finally{
	         if(connection != null){
	             try{
	                 connection.close();
	             }catch(Exception e){
	            	 System.out.println(e);
	             }
	         }
	     }
   
   }
 }