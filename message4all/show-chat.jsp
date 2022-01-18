<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String user = request.getParameter("user");
    String me = (String)session.getAttribute("user");
    Vector<String> mess = new Vector<String>();
    Vector<String> mittente=new Vector<String>();
    
    try {
       Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    } catch (ClassNotFoundException e) {
       out.println("Errore: Impossibile caricare il Driver Ucanaccess");
    } 
    Connection connection=null;
    try {
        connection = DriverManager.getConnection("jdbc:ucanaccess://" + request.getServletContext().getRealPath("/") + "Users.accdb");
        String query = "Select Mittente, Messaggio FROM chat WHERE (Mittente = '" + user + "' OR Mittente = '" + me + "')AND (Destinatario = '" + me + "' OR Destinatario = '" + user + "') ORDER BY TimeStamp ASC;";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()) {
            mittente.add(rs.getString(1));
            mess.add(rs.getString(2));
        }
    }
    catch(Exception e) {
        out.print("");
    }
    finally{
        if(connection != null){
            try{
                connection.close();
            }catch(Exception e){System.out.println(e);}
        }
    }

    
%>
<html>
<head>
    <title>Chat con <%=user%></title>
</head>
<% 
    if(session.getAttribute("nome")==null){
        //RequestDispatcher reqDisp = request.getRequestDispatcher("/index.jsp");
        //reqDisp.forward(request,response);

        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

%>
<body>
    <h1 align=center> Chat con <%=user%></h1>
    

<% 
    try{
        for(int i=0;i<mess.capacity();i++){
            if(mess.get(i).equals("Benvenuti nella vostra chat"))
                out.println("<h1 align=center>"+mess.get(i)+"</h1>");
            else if(mittente.get(i).equals(me))
                out.println("<p align=right>"+mess.get(i)+"</p>");
            else
                out.println("<p align=left>"+mess.get(i)+"</p>");        
        }
    }
    catch(Exception e){
        out.println("");
    }
%>
<form action="send" method="post">
    <input type="text" name="msg" placeholder="Inserisci messaggio" required>
    <input type="hidden" name="user" value="<%=user%>">
    <input type="submit" value="Invia">
</form>
<a href='lista-chat.jsp'>Torna alle tue chat</a>

</body>
</html>