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
    <h1> Area protetta di <%=session.getAttribute("nome")%> <%=session.getAttribute("cognome")%> </h1>
    <a href="logout"> Log Out </a>
</body>

</html>