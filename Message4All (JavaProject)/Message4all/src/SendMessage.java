import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import net.ucanaccess.jdbc.UcanaccessSQLException;

 public class SendMessage extends HttpServlet {
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	   response.setContentType("text/html");
	   HttpSession session = request.getSession();
	   PrintWriter out = response.getWriter();
	   String user = request.getParameter("user");
	   String msg = request.getParameter("msg");
	   String msgFinale = "";
	   int IDchat=-1;
	   String me = (String) session.getAttribute("user");
	   try {
	       Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	     } catch (ClassNotFoundException e) {
	       out.println("Errore: Impossibile caricare il Driver Ucanaccess");
	     } 
	     Connection connection=null;
	     try {
	       connection = DriverManager.getConnection("jdbc:ucanaccess://" + request.getServletContext().getRealPath("/") + "Users.accdb");
	       
	       String query = "Select ID, Messaggio FROM chat WHERE (Utente1 = '" + user + "' OR Utente1 = '" + me + "')AND (Utente2 = '" + me + "' OR Utente2 = '" + user + "');";
	       Statement st = connection.createStatement();
	       ResultSet rs = st.executeQuery(query);
	       if (rs.next()) {
	    	   IDchat = rs.getInt(1);
	    	   msgFinale += rs.getString(2);
	           //response.sendRedirect(request.getContextPath() + "/lista-chat.jsp");
	       }
	       
	       msgFinale += me+":"+msg+";";
	       String query1 = "UPDATE chat SET Messaggio = '" + msgFinale + "' WHERE ID='" + IDchat + "';";
	       Statement st1 = connection.createStatement();
	       st1.executeUpdate(query1);
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