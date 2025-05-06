# Progetto

Applicazione Client-Server per la consultazione da remoto di informazioni relative a Monumenti italiani

## Requisiti

* Un ambiente di sviluppo come **IntelliJ IDEA**
* Una console per eseguire i comandi

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
  
Il protocollo di comunicazione Client-Server è descritto nel file Relazione.docx presente nella repository

* TCP: Il Server gestisce più connessioni utilizzando i sockets
* UDP: Il Server gestisce una connessione alla volta

## Specifiche e Funzionalità

Il Server mette a disposizione funzionalità di ricerca. Riceve comandi dal Client sottoforma di Stringhe JSON, li interpreta, e restituisce al Client le informazioni che ha richiesto, sempre sottoforma di Stringhe JSON.

## Autore

Progetto e sviluppato da Leonardo.

## Licenza

Questo progetto è distribuito sotto la GNU License 3.0. Consulta il file LICENSE per maggiori dettagli.