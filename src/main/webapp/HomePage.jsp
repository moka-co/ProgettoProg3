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
		MySQLConnect db = new MySQLConnect();
		Firstname = db.getNamebyId(i);
		db.disconnect();
		
	}
}
%>

<h2>Benvenuto <% out.print(Firstname); %></h2>

<div>
	"Immagine Hotel" <br>
	"Descrizione Hotel" <br>
	<br>
</div>
<br>
<div>
	<button onclick="window.location='FinanceManagement';" type="button">Clicca qui per vedere la parte economica dell'Hotel</button>
	<br> <br>
	<button onclick="window.location='EmployeeManagement';" value="employeeManagement">Clicca qui per gestire l'account dei tuoi dipendenti</button>
	<br> <br>
	<button onclick="window.location='ReservationManagement'" value="reservationManagement">Clicca qui per gestire le prenotazioni al tuo hotel</button>
	<br> <br>
	<button onclick="window.location='PackagesManagement'" value="reservationManagement">Clicca qui per gestire i pacchetti del tuo Hotel</button>
</div>

</body>

</html>




