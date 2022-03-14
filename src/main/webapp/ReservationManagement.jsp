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
<div>
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
<button name="AddReservation" id="AddReservation">Aggiungi prenotazione</button>
<button name="EditReservation" id="EditReservation">Modifica prenotazione</button>
<button name="DeleteReservation" id="DeleteReservation">Elimina prenotazione</button>
<div id="invisibleDiv1" hidden="hidden">
		<p>Inserisci i dati della nuova prenotazione: </p>
       	 <form action="./ReservationManagement" method="post">
        	Nome Prenotazione <input type="text" name="name"></input> <br>
       	  Stanza <input type="text" name="room"></input> <br>
        	Check-In <input type="date" name="checkin"></input> <br> <br>
        	Check-Out <input type="date" name="checkout"></input> <br> <br>
        	Pacchetto <input type="text" name="package"></input> <br> <br>
		<input name="ConfirmAddReservation" type="submit" value="Conferma Aggiunta Prenotazione"></input> <br>
		</form>
</div>

<div id="invisibleDiv2" hidden="hidden">
		<p>Inserisci i dati da modificare della prenotazione selezionata: </p>
       	 <form action="./ReservationManagement" method="post">
        	Nome Prenotazione <input type="text" name="name"></input> <br>
       	  Stanza <input type="text" name="room"></input> <br>
        	Check-In <input type="date" name="checkin"></input> <br> <br>
        	Check-Out <input type="date" name="checkout"></input> <br> <br>
        	Pacchetto <input type="text" name="package"></input> <br> <br>
		<input name="ConfirmEditReservation" type="submit" value="Conferma Modifica Prenotazione"></input> <br>
		</form>
</div>

<div id="errorDiv" style="color:red">
</div>

	

<script>

$(document).ready(function() {
	$("#AddReservation").click(
			function() {
				let element = document.getElementById("invisibleDiv1");
				var op = element.getAttribute("hidden");
				//op può avere valore "hidden" oppure "null"
				//se l'attributo è hidden, rimuovilo così che il form venga mostrato
				//se l'attributo è null, allora aggiungi hidden così che il forma venga tolto
				if ( op == "hidden"){ //
					element.removeAttribute("hidden");
					$("#AddReservation").html("Annulla");
				}else{
					element.setAttribute("hidden","hidden");
					$("#AddReservation").html("Aggiungi prenotazione");
				}
			}
	)
});	

var trId = 0;
$(document).ready(function() {
	$("#EditReservation").click(
			function() {
				$(".highlight").each( function() {
					let iterId = $(this).attr("id");
					let element = document.getElementById(iterId);
					let styles = getComputedStyle(document.getElementById(iterId));
					let bgColor = styles.getPropertyValue("background-color");
					if ( bgColor == "rgb(255, 255, 153)"  ){
						console.log("Trovato elemento colorato");
						trId = $(this).attr("id");
					}
					
				});
				console.log("trId: " + trId);
				let element = document.getElementById("invisibleDiv2");
				if ( trId != 0){
					$("Stai modificando la prenotazione selezionata in giallo").appendTo("#errorDiv");
				}else {
					$("<p id='error1'>Devi prima selezionare una prenotazione da modificare</p>").appendTo("#errorDiv");
					return;
				}
				var op = element.getAttribute("hidden");
				if ( op == "hidden"){ 
					element.removeAttribute("hidden");
					$("#EditReservation").html("Annulla");
				}else{
					element.setAttribute("hidden","hidden");
					$("#EditReservation").html("Modifica prenotazione");
				}
			}
	)
});	

$(document).ready(function() { //highlight
			$(".highlight").click(
					function(event){
						var id = $(this).attr("id");
						$(".highlight").css("background-color","rgba(0, 0, 0, 0)");
						$(this).css('background-color','#ffff99');
						$("#error1").remove();
						AddValue(id);
						
					}
			)
		});

       function AddValue(x) {
           $.post( //manda messaggio al servlet EmployeeManagement
               "ReservationManagement", 
              {AddValue : x},  null 
              )
   	};
   	
$(document).ready(function() {
	$("#DeleteReservation").click(
			function(event){
				var res = confirm("Sei sicuro di eliminare la prenotazione selezionata?");
				if (res == true){
					$.post("ReservationManagement", {DeleteReservation: "DeleteReservation"}, null);
					setTimeout(function() {
						location.replace("ReservationManagement");
					}, 0800);
				}		
			}
	)
});
</script>

</body>
</html>