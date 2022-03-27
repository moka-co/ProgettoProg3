<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>

    <%@ page import="hoppin.*,java.util.List,java.util.ArrayList" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<title>Area Riservata</title>

<style>
.center {
    margin:auto;
    width: 800px;
    height: auto;
}

.selected{
   box-shadow:0px 12px 22px 1px #333;
}

.images {
    border: 2px solid;
    display: flex;
    flex-direction: column;
    margin-bottom: 2px;
    align-items: left;
    padding-left: 10px;
    padding-right: 10px;
}

.images-buttons {
    margin: auto;
    margin-top: 20px;
}

.images-file {
    margin: auto;
    display: grid;
}

.images-invisible { 
    margin: auto;
}

.hotel-description {
    border: solid 2px;
    margin: auto;
    padding-left: 10px;
    padding-right: 10px;
    display: flex;
    flex-direction: column;
    
}

.hotel-description-buttons {
    margin: auto auto 20px auto;
}

.buttons-flex {

    margin: auto;
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    
}

.buttons {
    
    color: white;
    background-color: black;
    text-align: center;
    text-decoration: none;
    font-size: 16px;
    border: 2px solid grey;
}

.buttons-top {
    background-color: black;
    color: white;
    font-size: 30px;
    text-decoration: none;
    display: inline-block;
    padding: 10px 10px;
    margin-left: 6px;
}

.buttons:hover {
    background-color: #f1f1f1;
    color: black;
    cursor: pointer; 
}

.buttons-top:hover {
    background-color: #f1f1f1;
    color: black;
    cursor: pointer;
}

</style>

</head>
<body>


<div class="center">

    <br>
    <div class="buttons-flex">
    	<button class="buttons-top" onclick="window.location='FinanceManagement';" type="button">Economia</button>
    	<br> <br>
    	<button class="buttons-top" onclick="window.location='EmployeeManagement';" value="employeeManagement">Dipendenti</button>
    	<br> <br>
    	<button class="buttons-top" onclick="window.location='ReservationManagement'" value="reservationManagement">Prenotazioni</button>
    	<br> <br>
    	<button class="buttons-top" onclick="window.location='PackagesManagement'" value="reservationManagement">Pacchetti</button>
    </div>

    <h2>Benvenuto <c:out value="${username}"/> </h2>

    <div class="images"> 
        <h3> Il tuo Hotel: <c:out value="${hotelInfo.name}"/> </h3> <br> 
        <div class="images-file">
            <c:forEach items="${fns}" var="filename">
        		<img id="${filename}" src="/images/${filename}" alt="${fns}" height="200" width="400">
        	</c:forEach>
        </div>
        <br> <br>
        <div class="images-buttons">
            <button class="buttons" id="AddPhoto">Aggiungi foto</button>
            <button class="buttons" hidden="hidden" id="DeletePhoto">Elimina Foto</button> <br> <br>
        </div>
        <br>
        <div class="images-invisible" id="invisibleDiv" hidden="hidden">

             <form action="./HotelInfoManagement" method="post" enctype="multipart/form-data">
                Aggiungi immagine <br> <input type="file" name="image" ></input> <br> <br>
                Via <input type="text" name="addressVia"></input> <br>
                <input class="buttons" name="AddImage" type="submit" value="Invia"></input> 
            </form>
        </div>
    </div>

    <div class="hotel-description">
        <div id="noHiddenDiv">
        	<h3>Informazioni sull'Hotel</h3>
        	<b>Indirizzo:</b> Via <c:out value="${hotelInfo.via}"/>  <c:out value="${hotelInfo.city}"/> <c:out value="${hotelInfo.postcode}"/> <br>
        	Stelle: <c:out value="${hotelInfo.stars}"/> <br>
        	Descrizione: <c:out value="${hotelInfo.description}"/> <br>
        	<br>
        </div>

        <br>
        <div id="hiddenDiv" hidden="hidden">
            <br> <br>
            <h3>Modifica le informazioni sull'Hotel</h3>
            <form action="./HotelInfoManagement" method="post">
               Via<input id="${hotelInfo.via}Hidden" type="text" name="via" value="${hotelInfo.via}"></input>  <br>
               Città <input id="${hotelInfo.city}Hidden" type="text" name="city" value="${hotelInfo.city}"></input> <br>
               Codice postale <input id="${hotelInfo.postcode}Hidden" type="text" name="postcode" value="${hotelInfo.postcode}"></input> <br>
               Stelle <input id="${hotelInfo.stars}Hidden" type="text" name="stars" value="${hotelInfo.stars}"></input> <br>
              Descrizione <br> <textarea name="description" rows="5" cols="80" id="${hotelInfo.description}"></textarea> <br> <br>
              <input class="buttons" name="ConfirmEditInfo" type="submit" value="Conferma modifica informazioni"></input> <br>
            </form>

        </div>


        <br>
        <div class="hotel-description-buttons">
            <button class="buttons" id="EditInfo">Modifica informazioni</button>            
        </div>
    </div>
    			
    <br>
    <br>
    <br>
    <br>
   
    </div>
</body>

<script>
$(document).ready(function() {
    $("#AddPhoto").click(
            function() {
                let element = document.getElementById("invisibleDiv");
                var op = element.getAttribute("hidden");

                if ( op == "hidden"){
                    element.removeAttribute("hidden");
                    $("#AddPhoto").html("Annulla");
                }else{
                    element.setAttribute("hidden","hidden");
                    $("#AddPhoto").html("Aggiungi foto");
                }
            }
    )
});  



$(document).ready(function() {
	$("#EditInfo").click (
			function() {
				let hide1 = document.getElementById("noHiddenDiv");
                let hide2 = document.getElementById("hiddenDiv");
                var op = hide2.getAttribute("hidden");

                if ( op == "hidden"){
                	hide1.setAttribute("hidden","hidden");
                    hide2.removeAttribute("hidden");
                    
                    $("#EditInfo").html("Annulla");
                }else{
                	hide2.setAttribute("hidden","hidden");
                	hide1.removeAttribute("hidden");
                    $("#EditInfo").html("Modifica informazioni");
                }
            }
	)
	
}); 

ImgtoDelete="";
$(document).ready(function() {
    $("img").click(function(event) {
    	var element = document.getElementById("DeletePhoto");
    	var id = $(this).attr("id");
    	var state = $(this).attr('class');
    	
    	
    	if ( state == null || state == "" ){
    		$("img").removeClass('selected');
    		$(this).addClass('selected');
    		window.ImgtoDelete=id;
    		element.removeAttribute("hidden");
    	}else{
    		$(this).removeClass('selected');
    		window.ImgtoDelete="";
    		element.setAttribute("hidden","hidden");
    	}
    	
    });
    
    $("#DeletePhoto").click( 
			function(event){
				var op = confirm("Sei sicuro di voler eliminare l'elemento selezionato? L'operazione è irreversibile");
				if ( op == true){
					$.post("HotelInfoManagement", {DeletePhoto : window.ImgtoDelete}, null);
					window.ImgtoDelete="";
					setTimeout(function() {
						location.replace("HotelInfoManagement");
					}, 0800);
				}
			});

});




</script>

</html>