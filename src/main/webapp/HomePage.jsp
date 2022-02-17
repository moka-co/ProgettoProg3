<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="hoppin.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Area Riservata</title>
</head>
<body>

<%!
public String Firstname;
%>

<% 

Cookie [] cookies = request.getCookies(); 

//Cookie sempre diverso da null, altrimenti AuthFilter se ne accorge e rimanda ad Authentication

for (Cookie aCookie : cookies) {
	String name = aCookie.getName();
	if (name.equals("id")){
		String value = aCookie.getValue();
		int i = Integer.valueOf(value);
		if ( name.equals("id")){
			MySQLConnect db = new MySQLConnect();
			Firstname = db.getNamebyId(i);
		
		}
	}
}
%>

<h2>Benvenuto <% out.print(Firstname); %></h2>

<button type="button">Clicca qui per vedere la parte economica dell'Hotel</button>
<br> <br>
<button onclick="window.location='EmployeeManagement';" value="employeeManagement">Gestione Dipendenti</button>
<br> <br>
<button type="button">Clicca qui per vedere le prenotazioni al tuo Hotel</button> 

</body>

</html>




