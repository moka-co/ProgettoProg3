<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hoppin</title>
    <style>
        .center {
            margin:auto;
            width: 300px;
        }
        .form {
            display:flex;
            width: auto;
        }
        .flex {
            margin-top: 1px;
        }

        .buttons {
            color: cadetblue;
        }

        #button1 {
            margin-right: 25px;
            background-color: black;
            color: white;
        }

        #button2 {
            margin-left: 25px;
            background-color: black;
            color: white;
        }

        p {
            margin-bottom: 6px;
        }
    </style>
</head>
<body>
    <div class="center">
    <h2>Autenticazione</h2>
    <div class="form">
        <div class="flex">
        <form action="./Authentication" method="post">
            <p> Email </p> 
        <input type="text" name="email"></input>
        <br>
        <p>Password</p>
         <input type="password" name="password"></input>
        <br> <br>
        <div class="buttons">
        <input id="button1" type="submit" value="Accedi"></input>
        <button id="button2" name="register" value="register">Registrati</button>
        </div>
        </form>
        </div>
    </div>
    </div>
</body>
</html>
