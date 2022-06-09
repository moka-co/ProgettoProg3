## Installazione
### Con Docker
1. `git clone https://github.com/mokassino/ProgettoProg3 && cd ProgettoProg3`
2. `docker build -t mokassino/hoppin .`
3. `docker start -d -it -p 8080:8080 mokassino/hoppin`
4. Dentro al contenitore, fare `hostname -I` e prendere l'indirizzo ip, dovrebbe essere qualcosa del tipo `172.17.0.2`
5. In un altro terminale il database mysql e inizializzarlo con le tabelle presenti nella cartella ProgettoProg3/MySQL
6. Crea un utente con lo stesso ip del contenitore docker e con un username e password a scelta
7. Dentro al file di configurazione, inserire username, password, e host scelti, più l'absolute path ad una directory utilizzata per salvare le immagini.
8. `docker start -d -it -p 8080:8080 mokassino/hoppin`
9. `docker exec mokassino/hoppin "bash startup.sh"
10. Collegarsi da browser a `localhost:8080` oppure `IP_DEL_CONTAINER:8080`


### Manuale
1. Inserire nel file config.properties i dati necessari: url, utente e password del database, nell'ultimo campo invece inserire l'absolute path ad una cartella che conterrà le immagini caricate dal software
2. (Temporaneo) andare ad inserire il path dove si trova il file config.properties nei file `java/hoppin/util/factory/PropertyFactory.java`
3. Su Apache Tomcat, andare a modificare il file `server.xml` e inserire dentro `<Host ...> ... </Host>` (SOSTITUISCI path/to/images con la cartella dove andranno messe le immagini)

```xml
<Context docBase="path/to/images" path="/images/"/>
```
