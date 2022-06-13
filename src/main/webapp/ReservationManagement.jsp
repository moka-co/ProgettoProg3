<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="hoppin.*,java.util.List,java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Gestione Prenotazioni</title>

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

.center {
    margin: auto;
    width: 1600px;
}

.reservations {
    display: flex;
    flex-direction: column;
    margin-bottom: 2px;
    align-items: left;
    padding-left: 10px;
    padding-right: 10px;
}

.reservations-button {
    margin-bottom: 2px;
    padding-top: 12px;
    padding-left: 10px;
    padding-right: 10px;
    width: 800px;
}

.buttons {
    color: white;
    background-color: black;
    text-align: center;
    text-decoration: none;
    font-size: 16px;
    border: 2px solid grey;
}

.reservation-form {
    margin: auto;
    padding-left: 10px;
    margin-top: 10px;
    border: 2px solid black;
    width: 400px;
    height: 300px;
}

.in-form {
    width: 300px;
    margin: auto;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
}

#buttons-inform {
    margin-top: 10px;
}

.previous {
    background-color: black;
    color: white;
    font-size: 30px;
    text-decoration: none;
    display: inline-block;
    padding: 10px 10px;
    margin-left: 6px;
}

.previous:hover {
    background-color: #f1f1f1;
    color: black;
}

</style>

</head>
<body>

<br>
<div class="center">
    <a href="HomePage.jsp" class="previous">&#8249; Homepage</a>
    <div class="reservations">
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
    
    <div class="reservations-button">
        <button class="buttons" name="AddReservation" id="AddReservation">Aggiungi prenotazione</button>
        <button class="buttons" name="EditReservation" id="EditReservation">Modifica prenotazione</button>
        <button class="buttons" name="DeleteReservation" id="DeleteReservation">Elimina prenotazione</button>
        <div class="reservation-form" id="invisibleDiv1" hidden="hidden">
                <p>Inserisci i dati della nuova prenotazione: </p>
                    <form class="in-form" action="./ReservationManagement" method="post">
                        Nome Prenotazione <input type="text" name="name"></input> <br>
                        Stanza <input type="text" name="room"></input> <br>
                        Check-In <input type="date" name="checkin"></input> <br> <br>
                        Check-Out <input type="date" name="checkout"></input> <br> <br>
                        Pacchetto <input type="text" name="package"></input> <br> <br>
                <input id="buttons-inform" class="buttons" name="ConfirmAddReservation" type="submit" value="Conferma Aggiunta Prenotazione"></input> <br>
                </form>
        </div>
        
        <div class="reservation-form" id="invisibleDiv2" hidden="hidden">
                <p>Inserisci i dati da modificare della prenotazione selezionata: </p>
                    <form class="in-form" action="./ReservationManagement" method="post">
                        Nome Prenotazione <input type="text" name="name"></input> <br>
                        Stanza <input type="text" name="room"></input> <br>
                        Check-In <input type="date" name="checkin"></input> <br> <br>
                        Check-Out <input type="date" name="checkout"></input> <br> <br>
                        Pacchetto <input type="text" name="package"></input> <br> <br>
                <input id="buttons-inform" class="buttons" name="ConfirmEditReservation" type="submit" value="Conferma Modifica Prenotazione"></input> <br>
                </form>
        </div>
    
    </div>
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