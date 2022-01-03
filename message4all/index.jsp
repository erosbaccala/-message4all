<html>
    <head>
        <title>
            Log In Page
        </title>
    </head>

    <% 
    if(session.getAttribute("nome") != null){
        response.sendRedirect(request.getContextPath()+"/lista-chat.jsp");
    }
    
    %>
    <body>
		<form action="login" method="post">
            <input type="text" name="username" placeholder="Username" required> <br>
            <input type="password" name="password" placeholder="Password" required> <br>
            <input type="submit" value="Log In">
        </form>
		<a href="signin.html"> Sei un nuovo utente? Registrati </a>
    </body>
</html>