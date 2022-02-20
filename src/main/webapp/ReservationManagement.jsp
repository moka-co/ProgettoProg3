<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="hoppin.*,java.util.List,java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestione Prenotazioni</title>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<style>
table {
  font-family: arial, sans-serif;
  width: 50%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
  background-color: 'white';
}

</style>

</head>
<body>

<br>
<div class=".anywhere">
<h2>Prenotazioni:</h2>
<table>
<tr>
<td> <b> Nome Prenotazione </b></td>
<td> <b> Stanza </b> </td>
<td> <b> Check-In </b> </td>
<td> <b> Check-Out </b> </td>
<td> <b> Pacchetto </b> </td>
</tr>
<c:forEach items="${rlist}" var="reservation">
	<tr id="${reservation.id}" class="highlight">
    	<td> <c:out value="${reservation.customerName}"/>  </td>
    	<td> <c:out value="${reservation.roomNum} "/> </td>
    	<td> <c:out value="${reservation.checkIn} "/> </td>
    	<td> <c:out value="${reservation.checkOut} "/> </td>
    	<td> <c:out value="${reservation.pckg} "/> </td>
    	</tr>
</c:forEach>
</table>
</div>
<button id="AddPrenotation">Aggiungi prenotazione</button>
<div class="invisibleDiv" style="opacity: 0">
		<p>Inserisci i dati della nuova prenotazione: </p>
       	 <form action="./ReservationManagement" method="post">
        	Nome Prenotazione <input type="text" name="name"></input> <br>
       	  Stanza <input type="text" name="room"></input> <br>
        	Check-In <input type="date" name="checkin"></input> <br> <br>
        	Check-Out <input type="date" name="checkout"></input> <br> <br>
        	Pacchetto <input type="text" name="package"></input> <br> <br>
		<input name="ConfirmAddReservation" type="submit" value="Conferma Aggiunta Prenotazione"></input> <br>
</div>
	

<script>

$(document).ready(function() {
	$("#AddPrenotation").click(
			function() {
				var op = $(".invisibleDiv").css("opacity");
				if ( op == 0){ //
					$(".invisibleDiv").css("opacity","1");
					$("#AddPrenotation").html("Annulla");
				}else{
					$(".invisibleDiv").css("opacity","0");
					$("#AddPrenotation").html("Aggiungi prenotazione");
				}
			}
	)
});	
</script>

</body>
</html>