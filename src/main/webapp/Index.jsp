<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Autenticazione</h2>
	<p>
	<form action="./Authentication" method="post">
	Email <input type="text" name="email"></input>
	<br>
	Password <input type="password" name="password"></input>
	<br> <br>
	<input type="submit" value="Accedi"></input>
	<button name="register" value="register">Registrati</button>
	</form>
</body>
</html>

