<%@ page import="java.sql.*" %>
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
        <th> Orario </th>

        <tr>
            <td> <a href="show-chat.jsp?user=mariorossi"> mariorossi </a> </td>
            <td> Ciao, questo è l'ultimo messaggio che ti ho inviato </td>
            <td> 13:31 </td>
        </tr>
        <tr>
            <td> luigiverdi </td>
            <td> Come va? </td>
            <td> 14:21 </td>
        </tr>
    </table>

    <a href="logout"> Log Out </a>
</body>

</html>