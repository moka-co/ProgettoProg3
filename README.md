## Installazione
### Con Docker
1. `git clone https://github.com/mokassino/ProgettoProg3 && cd ProgettoProg3`
2. `docker build -t mokassino/hoppin .`
3. `docker run -d -it -p 8080:8080 mokassino/hoppin`
4. `docker exec mokassino/hoppin bash hostname -I`
4. Dentro al contenitore, fare `hostname -I` e prendere l'indirizzo ip, dovrebbe essere qualcosa del tipo `172.17.0.2`
5. In un altro terminale il database mysql e inizializzarlo con le tabelle presenti nella cartella ProgettoProg3/MySQL
6. Crea un utente con lo stesso ip del contenitore docker e con un username e password a scelta
7. Dentro al file di configurazione, inserire username, password, e host scelti, più l'absolute path ad una directory utilizzata per salvare le immagini.
8. `docker run -d -it -p 8080:8080 mokassino/hoppin`
9. `docker exec mokassino/hoppin "bash startup.sh"
10. Collegarsi da browser a `localhost:8080` oppure `IP_DEL_CONTAINER:8080`
