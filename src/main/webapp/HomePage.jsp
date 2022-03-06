<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>

    <%@ page import="hoppin.*,java.util.List,java.util.ArrayList" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Area Riservata</title>
</head>
<body>

<h2>Benvenuto <c:out value="${username}"/> </h2>

<div>
	"Immagine Hotel" <br>
	Hotel <c:out value="${hotelInfo.name}"/> <br>
	<br>
	Indirizzo: Via <c:out value="${hotelInfo.via}"/>  <c:out value="${hotelInfo.city}"/> <c:out value="${hotelInfo.postcode}"/> <br>
	Stelle: <c:out value="${hotelInfo.stars}"/> <br>
	Descrizione: <c:out value="${hotelInfo.description}"/> <br>
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




