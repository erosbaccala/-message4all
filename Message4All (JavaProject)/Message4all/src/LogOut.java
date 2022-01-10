import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import net.ucanaccess.jdbc.UcanaccessSQLException;
 
 public class LogOut extends HttpServlet {
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
     HttpSession session = request.getSession();
     response.setContentType("text/html");
     response.setCharacterEncoding("UTF-8");
     PrintWriter out = response.getWriter();
     out.println("<html>");
     out.println("<head><title>Redirect</title></head><body>");
     session.setAttribute("nome", null);
     session.setAttribute("cognome", null);
     response.sendRedirect(request.getContextPath() + "/index.jsp");
     out.println("</body>");
     out.println("</html>");
   }
}