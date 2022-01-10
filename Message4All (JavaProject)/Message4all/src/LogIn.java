import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import net.ucanaccess.jdbc.UcanaccessSQLException;

 public class LogIn extends HttpServlet {
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
     response.setContentType("text/html");
     PrintWriter out = response.getWriter();
     String username = request.getParameter("username");
     String psw = request.getParameter("password");
     out.println("<html>");
     out.println("<head><title>DataBase</title></head><body>");
     try {
       Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
     } catch (ClassNotFoundException e) {
       out.println("Errore: Impossibile caricare il Driver Ucanaccess");
     } 
     Connection connection=null;
     try {
       connection = DriverManager.getConnection("jdbc:ucanaccess://" + request.getServletContext().getRealPath("/") + "Users.accdb");
       
         String query = "Select Username, Password, Nome, Cognome FROM users WHERE Username = '" + username + "' AND Password = '" + psw + "';";
         Statement st = connection.createStatement();
         ResultSet rs = st.executeQuery(query);
         if (rs.next()) {
           out.println("<h1> Login effettuato con successo </h1>");
           HttpSession session = request.getSession();
           session.setAttribute("user", rs.getString(1));
           session.setAttribute("nome", rs.getString(3));
           session.setAttribute("cognome", rs.getString(4));
           out.println("<p> Bentornato " + session.getAttribute("cognome") + " " + session.getAttribute("cognome") + " </p>");
           response.sendRedirect(request.getContextPath() + "/lista-chat.jsp");
         } else {
           out.println("<h1> Nessun utente registrato con queste credenziali</h1>");
         } 
     }
     catch(Exception e) {
    	 out.print(e);
     }
     finally{
         if(connection != null){
             try{
                 connection.close();
             }catch(Exception e){System.out.println(e);}
         }
     }
   }
 }