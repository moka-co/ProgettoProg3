<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="hoppin.*,java.util.List,java.util.ArrayList" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Finanze</title>
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

<div>
Counter di tutte le prenotazioni:  <c:out value="${allReservation}"/>
Profitti totali: <c:out value="${sumAllPrice}"/> <br>
</div>

<div>
<br>
<h2>Prezziario del tuo Hotel:</h2>
<table>
<tr>
<td> <b> Tipo di Stanza </b></td>
<td> <b> Prezzo </b> </td>
</tr>
<c:forEach items="${pricelist}" var="pricer">
	<tr id="${pricer.roomType}" class="highlight">
    	<td> <c:out value="${pricer.roomType}"/>  </td>
    	<td> <p id="${pricer.roomType}Brave"> <c:out value="${pricer.price} "/> </p> <input hidden=hidden id="${pricer.roomType}Hidden" type="text" value="${pricer.price}"></input> </td>
    	</tr>
</c:forEach>
</table>
</div>

<div id="invisibleDiv" hidden="hidden">
<input name="EditPriceList" type="submit" value="Conferma Modifica Prezziario"></input>
</div>


<script>
$(document).ready(function() {
    $(".highlight").click(function(event) {
    	//Variables declaration
    	var id = $(this).attr("id");
    	let element = document.getElementById("invisibleDiv");
    	let HiddenInput = document.getElementById(id+"Hidden");
		let BravePrice = document.getElementById(id+"Brave");
		console.log("element: " + element);
		console.log("HiddenInput: " + HiddenInput);
		console.log("BravePrice: " + BravePrice);
		
		//Change color
    	var backColor = $(this).css('background-color');

    	if ( backColor == 'rgba(0, 0, 0, 0)' ){
    		$(".highlight").css("background-color","rgba(0, 0, 0, 0)"); //metti tutti gli altri colori in bianco
    		element.setAttribute("hidden","hidden"); //nascondi il bottone in basso
    		$(this).css('background-color','#ffff99'); //cambia colore
    		AddId(id); //passa il tipo di stanza selezionata al back-end
    		
    		
    		$(".highlight").each( function() {
				let iterId = $(this).attr("id");
				let i = document.getElementById(iterId+"Brave");
				i.removeAttribute("hidden");
				let j = document.getElementById(iterId+"Hidden");
				j.setAttribute("hidden","hidden");

			});
    	}
    	
    	if ( backColor == 'rgb(255, 255, 153)' ){ 
    		//Simulazione di un doppio click modificando in modo non visibile il colore
    		$(this).css('background-color','rgba(255, 255, 154 )');
    		element.removeAttribute("hidden");
    		
    		if ( HiddenInput != null){
    			HiddenInput.removeAttribute("hidden");
    			BravePrice.setAttribute("hidden","hidden");
    		}
    		AddId(id);
    		}
    	
    	if ( backColor == 'rgb(255, 255, 154)' ){ 
    		$(this).css('background-color','rgba(0, 0, 0, 0)');
    		element.setAttribute("hidden","hidden");
    		RemoveId(id);
    		
    		
    		$(".highlight").each( function() {
				let iterId = $(this).attr("id");
				let i = document.getElementById(iterId+"Brave");
				i.removeAttribute("hidden");
				let j = document.getElementById(iterId+"Hidden");
				j.setAttribute("hidden","hidden");

			});

    		}
    	
    });

});
function AddId(x) {
    $.post( //manda messaggio al servlet EmployeeManagement
        "FinanceManagement", 
       {AddRoomTypeId : x},  null 
       )
};
function RemoveId(x) {
    $.post( //manda messaggio al servlet EmployeeManagement
        "FinanceManagement", 
       {RemoveRoomTypeId : x},  null 
       )
};
</script>

</body>
</html>