<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String user = request.getParameter("user");
    String me = (String)session.getAttribute("user");
    String msg = null;
    String[] mess=null;
    String[][] messaggi=null;
    
    try {
       Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    } catch (ClassNotFoundException e) {
       out.println("Errore: Impossibile caricare il Driver Ucanaccess");
    } 
    Connection connection=null;
    try {
        connection = DriverManager.getConnection("jdbc:ucanaccess://" + request.getServletContext().getRealPath("/") + "Users.accdb");
        String query = "Select Messaggio FROM chat WHERE (Utente1 = '" + user + "' OR Utente1 = '" + me + "')AND (Utente2 = '" + me + "' OR Utente2 = '" + user + "');";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            msg = rs.getString(1);
        } 

        mess = msg.split(";");
        messaggi = new String[mess.length][2];
        for(int i=0;i<mess.length;i++){
            messaggi[i][0] = mess[i].split(":")[0];
            messaggi[i][1] = mess[i].split(":")[1];
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
    <h1> Chat con <%=user%></h1>
<% 
    try{
        for(int i=0;i<messaggi.length;i++){
            if(messaggi[i][0].equals(me))
                out.println("<p align=right>"+messaggi[i][1]+"</p>");
            else
                out.println("<p align=left>"+messaggi[i][1]+"</p");        
        }
    }
    catch(Exception e){
        out.println(e);
    }
%>
    

</body>
</html>