# Progetto

Applicazione Client-Server per la consultazione da remoto di informazioni relative a Monumenti italiani.

## Requisiti

* L'ambiente di sviluppo integrato **IntelliJ IDEA**, con JDK 23
* Una console per eseguire i comandi

## Requisiti facoltativi

* DockerDesktop
* Sistema operativo Linux, o WSL se si usa Windows
* Abilitare tutte le impostazioni di WSL integration su Docker Desktop: Settings > Resources > WSL integration (può essere necessario il *Refetch distros* ad ogni avvio del dispositivo)

## Installazione

1. Clona la repository:
   ```sh
   git clone https://github.com/LeoF-07/Progetto.git

2. Apri i progetti del Server e del Client (dell'applicazione TCP o UDP) sull'ambiente di sviluppo.

## Esecuzione

1. Avvia il Server
2. Avvia il Client

## Funzionamento

Il Server dispone del file CSV contenente tutti i Monumenti italiani.  
Il Client, munito di interfaccia grafica, comunica con il Server per consultare informazioni relative ai Monumenti.  
  
Il protocollo di comunicazione Client-Server è descritto nel file Relazione.pdf presente nella repository.  

* TCP: Il Server gestisce più connessioni utilizzando i sockets
* UDP: Il Server gestisce una connessione alla volta

## Specifiche e Funzionalità

Il Server mette a disposizione funzionalità di ricerca. Riceve comandi dal Client sottoforma di Stringhe JSON, li interpreta, e restituisce al Client le informazioni che ha richiesto, sempre sottoforma di Stringhe JSON.

## Container Docker con il Server

Eventualmente è possibile utilizzare dei container per ospitare i Server TCP e UDP. Si vedano i **requisiti facoltativi**.  
1. Poiché i server sono muniti di interfaccia grafica è necessario installare i pacchetti X11 essenziali per il funzionamento del server grafico X11 nell'ambiente WSLg o nel sistema Linux:
   ```sh
   sudo apt update && sudo apt install -y x11-apps
   ```  
   <br>
2. Su Docker Hub è pubblicata l'immagine del container che ospita il Server TCP: https://hub.docker.com/repository/docker/leof07/server-tcp/general  
   e l'immagine del container che ospita il Server UDP: https://hub.docker.com/repository/docker/leof07/server-udp/general  
   Per avviare il container del Server TCP è necessario eseguire il seguente comando:
   ```sh
   docker run -p 1050:1050 -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix --name serverTCP leof07/server-tcp
   ```
   Per avviare il container del Server UDP è necessario eseguire il seguente comando:
   ```sh
   docker run -p 1050:1050 -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix --name serverUDP leof07/server-udp
   ```
   <br>

3. Eventualmente si può procedere poi alla creazione dell'immagine e del container (Nell'esempio che segue si presuppone che il Progetto si trovi nella cartella Documents):  
   <br>
   * Spostarsi nella directory del progetto
      ```sh
      cd /mnt/c/Users/<nome_utente>/Documents/Progetto/TCP/Server
      ```
   * Costruire l'immagine
      ```sh
      docker build -t server-tcp .
      ```
   * Avviare il container:
      ```sh
      docker run -p 1050:1050 -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix --name serverTCP server-tcp
      ```
      <br>

4. In seguito il container può essere avviato comodamente con (sostituire `<nome-container>` con serverTCP o con serverUDP in base all'esigenza):
   ```sh
   docker start <nome-container>
   ```
   oppure con:
   ```sh
   docker start -a <nome-container>
   ```

## Autore

Progettato e sviluppato da Leonardo.

## Licenza

Questo progetto è distribuito sotto la GNU License 3.0. Consulta il file LICENSE per maggiori dettagli.