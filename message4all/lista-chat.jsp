<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Area Protetta</title>
</head>
<% 
    if(session.getAttribute("nome")==null){
        //RequestDispatcher reqDisp = request.getRequestDispatcher("/index.jsp");
        //reqDisp.forward(request,response);

        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }
    String me = (String)session.getAttribute("user");
    Vector<String> users = new Vector<String>();
    Vector<String> msg = new Vector<String>();

    try {
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    } catch (ClassNotFoundException e) {
        out.println("Errore: Impossibile caricare il Driver Ucanaccess");
    } 
    Connection connection=null;
    try {
        connection = DriverManager.getConnection("jdbc:ucanaccess://" + request.getServletContext().getRealPath("/") + "Users.accdb");
        String query = "Select Mittente, Destinatario, Messaggio FROM chat WHERE (Mittente = '" + me + "' OR Destinatario = '" + me + "') ORDER BY TimeStamp DESC;";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()) {
            if(users.contains(rs.getString(1)) || users.contains(rs.getString(2)))
                continue;
            
            if(rs.getString(1).equals(me)){
                users.add(rs.getString(2));
            }else
                users.add(rs.getString(1));

            msg.add(rs.getString(1)+"-->"+rs.getString(3));
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
<body>
    <h1> Lista chat di <%=session.getAttribute("nome")%> <%=session.getAttribute("cognome")%> </h1>
    <form action="newchat" method="post">
        <input type="text" name="user" placeholder="Inserisci username" required> <br>
        <input type="submit" value="Avvia nuova chat">
    </form>
    <table border=1>
        <th> Nome utente </th>
        <th> Ultimo Messaggio </th>

        <%
            try{
                for(int i=0;i<users.capacity();i++){
                    out.println("<tr>");
                    out.println("<td> <a href='show-chat.jsp?user="+users.get(i)+"'>"+users.get(i)+"</a></td>");
                    out.println("<td>"+msg.get(i)+"</td>");
                    out.println("</tr>");                
                }
            }
            catch(Exception e){
                out.println("");
            }

        %>
    </table>

    <a href="logout"> Log Out </a>
</body>

</html>