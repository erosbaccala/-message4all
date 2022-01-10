import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import net.ucanaccess.jdbc.UcanaccessSQLException;

public class SignIn extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
     response.setContentType("text/html");
     response.setCharacterEncoding("UTF-8");
     String username = request.getParameter("username");
     String psw = request.getParameter("password");
     String nome = request.getParameter("nome");
     String cognome = request.getParameter("cognome");
     PrintWriter out = response.getWriter();
     out.println("<html>");
     out.println("<head><title>DataBase</title></head><body>");
     out.println("<!DOCTYPE html><html>");
     out.println("<head>");
     out.println("<meta charset=\"UTF-8\" />");
     out.println("<title> Registrazione </title>");
     out.println("</head>");
     out.println("<body>");
     try {
       Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
     } catch (ClassNotFoundException e) {
       out.println("Errore: Impossibile caricare il Driver Ucanaccess");
     } 
     try {
       Connection connection = DriverManager.getConnection("jdbc:ucanaccess://" + request.getServletContext().getRealPath("/") + "Users.accdb");
       try {
         Statement st = connection.createStatement();
         String query = "INSERT INTO users VALUES ('" + username + "','" + psw + "', '" + nome + "', '" + cognome + "');";
         st.executeUpdate(query);
         out.println("<h1> Registrazione effettuata con successo </h1>");
         out.println("<a href='index.jsp'>Vai alla pagina di login</a>");
         if (connection != null)
           connection.close(); 
       } catch (Throwable throwable) {
         if (connection != null)
           try {
             connection.close();
           } catch (Throwable throwable1) {
             throwable.addSuppressed(throwable1);
           }  
         throw throwable;
       } 
     } catch (UcanaccessSQLException ex) {
       out.println("<h1> Nome utente già  in uso </h1>");
       out.println("<a href='signin.html'>Torna alla pagina di registrazione</a>");
     } catch (SQLException ex) {
       out.println(ex);
     } 
     out.println("</body>");
     out.println("</html>");
   }
}