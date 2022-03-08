## Installazione
1. Inserire nel file config.properties i dati necessari: url, utente e password del database, nell'ultimo campo invece inserire l'absolute path ad una cartella che conterrà le immagini caricate dal software
2. (Temporaneo) andare ad inserire il path dove si trova il file config.properties nei file `java/hoppin/sql/MySQLConnect.java` e `java/HotelInfoManagement.java`
3. Su Apache Tomcat, andare a modificare il file `server.xml` e inserire dentro `<Host ...> ... </Host>` (SOSTITUISCI path/to/images con la cartella dove andranno messe le immagini)

```xml
<Context docBase="path/to/images" path="/images/"/>
```
