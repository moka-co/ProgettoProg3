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
    	<td> 	<p id="${pricer.roomType}Brave"> <c:out value="${pricer.price} "/> </p> 
    			<form action="./FinanceManagement" method="post">
    			<input hidden="hidden" id="${pricer.roomType}Hidden" type="text" name="${pricer.roomType}" value="${pricer.price}"></input> 
    			</form>
    	</td>
    	</tr>
</c:forEach>
</table>
</div>

<div>
<div id="invisibleDiv" hidden="hidden">
	<button id="EditPriceList" name="EditPriceList" value="Conferma Modifica Prezziario">Conferma Modifica Prezziario</button>
</div>
<button id="AddEtoPriceList">Aggiungi elemento</button>
<button id="DeleteEtoPriceList">Elimina elemento</button>
<div id="invisibleDiv2" hidden="hidden">
		<p>Inserisci un nuovo elemento da aggiungere al prezziario che verrà visualizzato dal cliente: </p>
		<form action="./FinanceManagement" method="post">
        	Tipo di Stanza <input type="text" name="roomType"></input> <br>
       		Prezzo <input type="text" name="price"></input> <br>
		<input name="ConfirmAddEtoPriceList" type="submit" value="Conferma aggiunta"></input> <br>
		</form>
	</div>
</div>

<div>

<br>
<h2>Informazioni sui periodi di Alta/Media/Bassa stagione:</h2>
<table>
<tr>
<td> <b> Stagione </b></td>
<td> <b> Periodo di inizio </b> </td>
<td> <b> Periodo di fine </b> </td>
<td> <b> Aumento % sul prezziario </b> </td>
</tr>
<c:forEach items="${seasonlist}" var="season">
	<tr id="${season.start}&${season.end}" class="highlight2">
    	<td> <c:out value="${season.type}"/>  </td>
    	<td> <c:out value="${season.start}"/> </td>
    	<td> <c:out value="${season.end}"/> </td>
    	<td> <c:out value="${season.percIncrease}"/> </td>
    	</tr>
</c:forEach>
</table>

</div>

<div>
<button id="AddEtoSeason">Aggiungi elemento</button>
<button id="EditEtoSeason">Modifica elemento</button>
<button id="DeleteEtoSeason">Elimina elemento</button>
<div id="invisibleDiv3" hidden="hidden">
		<p>Inserisci un nuovo elemento da aggiungere al prezziario che verrà visualizzato dal cliente: </p>
		<form action="./FinanceManagement" method="post">
            Stagione <input type="text" name="season"></input> <br>
            Data inizio <input type="date" name="startDate"></input> <br>
            Data fine <input type="date" name="endDate"></input> <br>
       		Incremento percentuale <input type="text" name="price"></input> <br>
		<input name="ConfirmAddEtoSeason" type="submit" value="Conferma aggiunta"></input> <br>
		</form>
	</div>
</div>

<div id="invisibleDiv4" hidden="hidden">
		<p>Stai modificando l'elemento selezionato in giallo: </p>
		<form action="./FinanceManagement" method="post">
            Stagione <input type="text" name="season"></input> <br>
            Data inizio <input type="date" name="startDate"></input> <br>
            Data fine <input type="date" name="endDate"></input> <br>
       		Incremento percentuale <input type="text" name="price"></input> <br>
		<input name="ConfirmEditEtoSeason" type="submit" value="Conferma modifica"></input> <br>
		</form>
</div>


<script>
var elementForm = "";
$(document).ready(function() {
	RemoveId("");
    $(".highlight").click(function(event) {
    	//Variables declaration
    	var id = $(this).attr("id");
    	let element = document.getElementById("invisibleDiv");
    	let HiddenInput = document.getElementById(id+"Hidden");
		let BravePrice = document.getElementById(id+"Brave");
		//Change color
    	var backColor = $(this).css('background-color');

    	if ( backColor == 'rgba(0, 0, 0, 0)' ){
    		window.elementForm = HiddenInput.getAttribute("name");
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
    	
    	let CurrentHIState = HiddenInput.getAttribute("hidden");
    	if ( backColor == 'rgb(255, 255, 154)' && CurrentHIState == "hidden" ){ 
    		window.elementForm = "";
    		$(this).css('background-color','rgba(0, 0, 0, 0)');
    		HiddenInput.setAttribute("hidden","hidden");
			BravePrice.removeAttribute("hidden");
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
    $("#AddEtoPriceList").click( function() {
			let element = document.getElementById("invisibleDiv2");
			var op = element.getAttribute("hidden");

			if ( op == "hidden"){ 
				element.removeAttribute("hidden");
				$("#AddEtoPriceList").html("Annulla");
			}else{
				element.setAttribute("hidden","hidden");
				$("#AAddEtoPriceList").html("Aggiungi prenotazione");
			}
    });
    $("#DeleteEtoPriceList").click( function() {
    	$.post(
    			"FinanceManagement", {DeleteRoom : window.elementForm}, null 
    	);
    	setTimeout(function() {
			location.replace("FinanceManagement");
		}, 0900);
    	
    	
    });
    $("#AddEtoSeason").click( function() {
    	let element = document.getElementById("invisibleDiv3");
		var op = element.getAttribute("hidden");
		
		if ( op == "hidden"){ 
			element.removeAttribute("hidden");
			$("#AddEtoSeason").html("Annulla");
		}else{
			element.setAttribute("hidden","hidden");
			$("#AddEtoSeason").html("Aggiungi prenotazione");
		}
    });
    

});
function AddId(x) {
    $.post( //manda messaggio al servlet FinanceManagement
        "FinanceManagement", 
       {AddRoomTypeId : x},  null 
       )
};
function RemoveId(x) {
    $.post( //manda messaggio al servlet FinanceManagement
        "FinanceManagement", 
       {RemoveRoomTypeId : x},  null 
       )
};
$(document).ready( 
		function() {
			$("#EditPriceList").click( 
					function(){
						let element = document.getElementById(window.elementForm+"Hidden");
						let ins = $(element).val();
						console.log("Preso l'elemento" + element + ", con il suo attributo value: " + ins);
						$.post(
								"FinanceManagement", {NewPriceToIns: ins}, null
						)
						setTimeout(function() {
							location.replace("FinanceManagement");
						}, 0800);
					}
					
			)
	
		}
		
);
var elementEto = "";
$(document).ready(function() {//highlight
	$(".highlight2").click(
			function(event){
				var id = $(this).attr("id");
				var op = $(this).css("background-color");
				if ( op != "rgba(0, 0, 0, 0)"){ //selected element is yellow, make it white
					$(".highlight2").css("background-color","rgba(0, 0, 0, 0)");
					RemEtoSeasonCache();
					window.elementEto = "";
				}else { //selected element is white, 
					//make everything else white, then colour the select one yellow
					$(".highlight2").css("background-color","rgba(0, 0, 0, 0)");
					$(this).css('background-color','rgb(255, 255, 154)');
					AddEtoSeasonCache(id);
					$("#error1").remove(); //probabilmente da rimuovere
					window.elementEto = id;
				}
				
			}
	);
	
	$("#DeleteEtoSeason").click( 
			function(event){
				var op = confirm("Sei sicuro di voler eliminare l'elemento selezionato? L'operazione è irreversibile");
				if ( op == true){
					$.post("FinanceManagement", {DeleteEtoSeason : "ok"}, null);
					window.elementEto = "";
					setTimeout(function() {
						location.replace("FinanceManagement");
					}, 0800);
				}
			}
			
	);
	$("#EditEtoSeason").click(
			function(event){
				let element = document.getElementById("invisibleDiv4");
				var op = element.getAttribute("hidden");
				if ( window.elementEto == ""){
					alert("Devi prima selezionare un elemento dalla tabella delle stagioni!");
					return;
				}
			
				if ( op == "hidden"){ 
					element.removeAttribute("hidden");
					$("#EditEtoSeason").html("Annulla");
				}else{
					element.setAttribute("hidden","hidden");
					$("#EditEtoSeason").html("Modifica elemento");
				}
			}
	);
	
});

function AddEtoSeasonCache(x) {
   $.post("FinanceManagement", {EtoSeason : x},  null )
};
function RemEtoSeasonCache(){
	$.post( "FinanceManagement", {EtoSeason: ""}, null )
}
</script>

</body>
</html>