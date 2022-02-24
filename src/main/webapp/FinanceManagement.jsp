<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="hoppin.*,java.util.List,java.util.ArrayList" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Finanze</title>
</head>
<body>
Counter di tutte le prenotazioni:  <c:out value="${allReservation}"/>
Profitti totali: <c:out value="${sumAllPrice}"/> <br>
</body>
</html>