<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="hoppin.*,java.util.List,java.util.ArrayList" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestione dipendenti</title>
</head>

<!-- c:set var="show" value="questo"/> -->

<body>

<c:if test="${ResultAddEmployee.equals('ok') }"><p style="color:red">Account impiegato aggiunto con successo</p></c:if>
<c:if test="${ResultAddEmployee.equals('no') }"><p style="color:red">Errore nell'inserimento dell'account</p></c:if>

<c:choose>
    <c:when test="${show.equals('questo')}">
       <form action="./EmployeeManagement" method="post">
	<input name="AddEmployee" type="submit" value="Aggiungi Dipendente"></input>
		</form>
    </c:when>
    
        <c:otherwise>
        <c:set var="show" value="questo"/>
        <p>Inserisci i dati dell'account dipendente </p>
        <form action="./EmployeeManagement" method="post">
        Nome e Cognome <input type="text" name="nome"></input> <br>
        Email <input type="text" name="email"></input> <br>
        Password <input type="text" name="password"></input> <br> <br>
	<input name="ConfirmAddEmployee" type="submit" value="Conferma Aggiunta Dipendente"></input> <br>
		</form>
    </c:otherwise>
</c:choose>




<h2>Dipendenti:</h2>

<c:forEach items="${elist}" var="item">
	<br>
    	${item}
    	<br>
</c:forEach>

</body>
</html>