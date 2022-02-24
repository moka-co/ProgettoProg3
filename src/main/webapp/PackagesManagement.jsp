<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="hoppin.*,java.util.List,java.util.ArrayList" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
    
<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>


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

<div class=".anywhere">
<h2>Pacchetti:</h2>
<table>
<tr>
<td> <b> Nome Pacchetto </b></td>
<td> <b> Descrizione </b> </td>
<td> <b> Prezzo</b> </td>
</tr>

<c:forEach items="${PackageList}" var="Package">
	<tr class="highlight">
    	<td> <c:out value="${Package.name}"/>  </td>
    	<td> <c:out value="${Package.description} "/> </td>
    	<td> <c:out value="${Package.price} "/> </td>
    	</tr>
</c:forEach>
</table>
</div>

</body>
<button id="AddPackage">Aggiungi pacchetto</button>
<div id="invisibleDiv" hidden="hidden">
		<p>Inserisci i dati del nuovo pacchetto: </p>
       	 <form action="./PackagesManagement" method="post">
        	Nome Pacchetto <input type="text" name="NPack"></input> <br>
       	    Descrizione Pacchetto <input type="text" name="DPack"></input> <br>
        	Prezzo Pacchetto <input type="int" name="PPack"></input> <br> <br>
		<input name="ConfirmAddPackage" type="submit" value="Conferma Aggiunta Pacchetto"></input> <br>
</div>

<script >
	
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

</script>
</html>