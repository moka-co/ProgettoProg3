<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>

    <%@ page import="hoppin.*,java.util.List,java.util.ArrayList" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Gestione pacchetti</title>


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

.packages-list {
    display: flex;
    flex-direction: column;
    margin-bottom: 2px;
    align-items: left;
    padding-left: 10px;
    padding-right: 10px;
}

.packages-button {
    margin-bottom: 2px;
    padding-left: 10px;
    padding-right: 10px;
    width: 500px;
}

.buttons-container {
    padding-top: 10px;
}

.buttons {
    color: white;
    background-color: black;
    text-align: center;
    text-decoration: none;
    font-size: 16px;
    border: 2px solid grey;
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

<div class="center">
    <a href="HomePage.jsp" class="previous">&#8249; Homepage</a>
<div class="packages-list">
    <h2>Pacchetti:</h2>
    <table>
     <tr>
            <td> <b> Nome Pacchetto </b></td>
            <td> <b> Descrizione </b> </td>
            <td> <b> Prezzo</b> </td>
     </tr>

         <c:forEach items="${PackageList}" var="Package">
	        <tr id="${Package.name}" class="highlight">
            	<td> <c:out value="${Package.name}"/>  </td>
            	<td> <c:out value="${Package.description} "/> </td>
            	<td> <c:out value="${Package.price} "/> </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="packages-button">
    <div class="buttons-container">
        <button class="buttons" id="AddPackage">Aggiungi pacchetto</button>
        <button class="buttons" id="DeletePackage">Elimina pacchetto</button>
        <button class="buttons" id="EditPackage">Modifica pacchetto</button>
    </div>
    
    <div id="invisibleDiv" hidden="hidden">
    		<p>Inserisci i dati del nuovo pacchetto: </p>
           	 <form action="./PackagesManagement" method="post">
               	Nome Pacchetto <input type="text" name="NPack"></input> <br>
           	    Descrizione Pacchetto <input type="text" name="DPack"></input> <br>
            	Prezzo Pacchetto <input type="text" name="PPack"></input> <br> <br>
    		<input name="ConfirmAddPackage" type="submit" value="Conferma Aggiunta Pacchetto"></input> <br>
    		</form>
    </div>

    <div id="invisibleDiv2" hidden="hidden">
    		<p>Inserisci i dati da modificare del pacchetto selezionato: </p>
          	 <form action="./PackagesManagement" method="post">
               	Nome Pacchetto <input type="text" name="NPack"></input> <br>
          	    Descrizione Pacchetto <input type="text" name="DPack"></input> <br>
           	    Prezzo Pacchetto <input type="text" name="PPack"></input> <br> <br>
    		<input name="ConfirmEditPackage" type="submit" value="Conferma Modifica Prenotazione"></input> <br>
    		</form>
    </div>
</div>

</div>

<script>
pckgSelected=""

$(document).ready(function() {
    $("#AddPackage").click(
            function() {
                let element = document.getElementById("invisibleDiv");
                var op = element.getAttribute("hidden");
                //op può avere valore "hidden" oppure "null"
                //se l'attributo è hidden, rimuovilo così che il form venga mostrato
                //se l'attributo è null, allora aggiungi hidden così che il forma venga tolto
                if ( op == "hidden"){ //
                    element.removeAttribute("hidden");
                    $("#AddPackage").html("Annulla");
                }else{
                    element.setAttribute("hidden","hidden");
                    $("#AddPackage").html("Aggiungi pacchetto");
                }
            }
    )
});  


$(document).ready(function() {
	$(".highlight").click(
			function(event){
				var id = $(this).attr("id");
				let color = $(this).css('background-color');
				if ( color == 'rgb(255, 255, 153)') { //Già colorato
					$(".highlight").css("background-color","rgba(0, 0, 0, 0)"); //Metti tutto in bianco
					SetValue("");
				}else {
					$(".highlight").css("background-color","rgba(0, 0, 0, 0)");
					$(this).css('background-color','#ffff99');
					SetValue(id);
				}
				
			}
	)
});

function SetValue(x) {
   $.post( //manda messaggio al servlet EmployeeManagement
       "PackagesManagement", 
      {SetValue : x},  null 
      );
   window.pckgSelected=x;
};


//Elimina pacchetto
$(document).ready(function() {
	$("#DeletePackage").click(
			function(event){
				if ( window.pckgSelected == "") {
					alert("Seleziona prima un elemento cliccando sul pacchetto da eliminare");
					return;
				}
				var res = confirm("Sei sicuro di eliminare la prenotazione selezionata?");
				if (res == true){
					$.post("PackagesManagement", {DeletePackage: "DeletePackage"}, null);
					setTimeout(function() {
						location.replace("PackagesManagement");
					}, 0800);
				}		
			}
	)
});	

//Modifica pacchetto
$(document).ready(function() {
    $("#EditPackage").click(
            function() {
            	if ( window.pckgSelected == "") {
					alert("Seleziona prima un elemento cliccando sul pacchetto da eliminare");
					return;
				}
            	let element = document.getElementById("invisibleDiv2");
            	var op = element.getAttribute("hidden");
				if ( op == "hidden"){ 
					element.removeAttribute("hidden");
					$("#EditPackage").html("Annulla");
				}else{
					element.setAttribute("hidden","hidden");
					$("#EditPackage").html("Modifica pacchettoa");
				}
           }
            	
    )
});  

</script>



</body>
</html>