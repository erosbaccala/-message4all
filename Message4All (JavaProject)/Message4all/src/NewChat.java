import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import net.ucanaccess.jdbc.UcanaccessSQLException;

 public class NewChat extends HttpServlet {
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	   PrintWriter out = response.getWriter();
	   response.setContentType("text/html");
	   HttpSession session = request.getSession();
	   String me = (String) session.getAttribute("user");
	   String user = request.getParameter("user");
	   
	   try {
	       Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	     } catch (ClassNotFoundException e) {
	       out.println("Errore: Impossibile caricare il Driver Ucanaccess");
	     } 
	     Connection connection=null;
	     try {
	       connection = DriverManager.getConnection("jdbc:ucanaccess://" + request.getServletContext().getRealPath("/") + "Users.accdb");
	       
	       if(user.equals(me)) {
	    	   out.println("<p> Impossibile creare chat con se stessi </p>");
	    	   out.println("<a href='lista-chat.jsp'>Torna alle tue chat</a>");
	       }else {
	    	   String query = "Select Username FROM users WHERE Username = '" + user + "';";
	    	   Statement st = connection.createStatement();
	    	   ResultSet rs = st.executeQuery(query);
	    	   if (rs.next()) {
	    		   String query1 = "Select Mittente, Destinatario FROM chat WHERE (Mittente = '" + user + "' OR Mittente = '" + me + "')AND (Destinatario = '" + me + "' OR Destinatario= '" + user + "');";
	    		   Statement st1 = connection.createStatement();
	    		   ResultSet rs1 = st1.executeQuery(query1);
	    		   if(rs1.next()) {
	    			   out.println("<h1>Hai già una chat con questo utente </h1> <br>");
	    			   out.println("<a href='show-chat.jsp?user="+user+"'>Vai alla chat con "+user+"</a><br>");
	    			   out.println("<a href='lista-chat.jsp'>Torna alle tue chat</a>");		    	   
	    		   }else {
	    			   SimpleDateFormat form=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		       String data = form.format(new Date());
	    			   String query2 = "INSERT INTO chat (Mittente, Destinatario, Messaggio, TimeStamp) VALUES ('" + me + "', '"+ user +"', 'Benvenuti nella vostra chat', #"+data+"#);";
	    			   Statement st2 = connection.createStatement();
	    			   st2.executeUpdate(query2);
	    			   response.sendRedirect(request.getContextPath() + "/show-chat.jsp?user="+user);
	    		   }
	    		   
	    	   }else {
	    		   out.println("<h1>Questo utente non esiste, inserire un nome utente valido</h1><br>");
	    		   out.println("<a href='lista-chat.jsp'>Torna alle tue chat</a>");
	    	   }	    	   
	       }
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