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
<title>Gestione dipendenti</title>


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

.employee-list {
    display: flex;
    flex-direction: column;
    margin-bottom: 2px;
    align-items: left;
    padding-left: 10px;
    padding-right: 10px;
}

.employee-buttons {
    margin-bottom: 2px;
    padding-top: 12px;
    padding-left: 10px;
    padding-right: 10px;
    width: 400px;
}

.employee-form {
    margin: auto;
    padding-left: 10px;
    margin-top: 10px;
    border: 2px solid black;
    width: 400px;
    height: 225px;
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

.buttons {
    color: white;
    background-color: black;
    text-align: center;
    text-decoration: none;
    font-size: 16px;
    border: 2px solid grey;
}

.DeleteEmployee {
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
    <div class="employee-list">
        <h2>Dipendenti:</h2>
        <table>
            <tr>
                <td> <b> Nome </b></td>
                <td> <b> Email </b> </td>
            </tr>
    
            <c:forEach items="${elist}" var="employee">
                <tr id="${employee.id}" class="highlight">
                    <td> <c:out value="${employee.completeName}"/>  </td>
                    <td> <c:out value="${employee.email} "/> </td>
                    </tr>
            </c:forEach>
    
        </table>
    </div>
    
    
    <br>
    <br>
    
    <div class="employee-buttons">
        <input class="DeleteEmployee"  name="DeleteEmployee" type="submit" value="Elimina Dipendente"></input>
        <button class="buttons" id="AddEmployee">Aggiungi Dipendente</button>
        <div class="employee-form" id="hiddenDiv" hidden="hidden">
         <p>Inserisci i dati dell'account dipendente </p>
                <form class="in-form" action="./EmployeeManagement" method="post">
                    Nome e Cognome <input type="text" name="nome"></input> <br>
                    Email <input type="text" name="email"></input> <br>
                    Password <input type="text" name="password"></input> <br> <br>
                    <input id="buttons-inform" class="buttons" name="ConfirmAddEmployee" type="submit" value="Conferma Aggiunta Dipendente"></input> <br>
                </form>
        </div>
    </div>
</div>


<script>
 $(document).ready(function() {
        $(".highlight").click(function() {
        	var id = $(this).attr("id");
        	var backColor = $(this).css('background-color');
        	
        	
        	if ( backColor == 'rgba(0, 0, 0, 0)' ){
        		$(this).css('background-color','#ffff99');
        		AddId(id);
        	}
        	
        	if ( backColor == 'rgb(255, 255, 153)' ){
        		$(this).css('background-color','rgba(0, 0, 0, 0)');
        		RemoveId(id);
        		}
        });

 });
	function AddId(x) {
        $.post( //manda dati al servlet EmployeeManagement
            "EmployeeManagement", 
           {AddAccId : x},  null 
           )
	};
	function RemoveId(x) {
        $.post( //manda messaggio al servlet EmployeeManagement
            "EmployeeManagement", 
           {RemoveAccId : x},  null 
           )
	};
	
$(document).ready(function() {
	$(".DeleteEmployee").click(function(){
		var res = confirm("Sei sicuro di eliminare gli account dipendente selezionati?");
		if (res == true){
			$.post("EmployeeManagement", {DeleteEmployee: "DeleteEmployee"}, null);
			setTimeout(function() {
				location.replace("EmployeeManagement");
			}, 0800);
		}
	});
});

$(document).ready( function() {
	$("#AddEmployee").click( function(){
		let div = document.getElementById("hiddenDiv");
		let atrb = div.getAttribute("hidden");
		
		if ( atrb == "hidden"){
			div.removeAttribute("hidden");
			$("#AddEmployee").html("Annulla");
		}else{
			div.setAttribute("hidden","hidden");
			$("#AddEmployee").html("Aggiungi Dipendente");
		}
	});
});


</script>

</body>
</html>