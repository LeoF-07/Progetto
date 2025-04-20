public enum Comando {

    GET_ROW("GET_ROW", 1, "Ottieni il monumento nella riga scelta"),
    GET_PER_COMUNE ("GET_PER_COMUNE", 1, "Ottieni i monumenti in quel comune"),
    GET_PER_PROVINCIA ("GET_PER_PROVINCIA", 1, "Ottieni i monumenti in quella provincia"),
    GET_PER_REGIONE ("GET_PER_REGIONE", 1, "Ottieni i monumenti in quella regione"),
    GET_PER_NOME("GET_PER_NOME", 1, "Ottieni i monumenti con quel nome"),
    GET_PER_NOME_PARZIALE("GET_PER_NOME_PARZIALE", 1, "Ottieni i monumenti che contengono quel nome"),
    GET_PER_TIPO ("GET_PER_TIPO", 1, "Ottieni i monumeni di quel tipo"),
    GET_PER_ANNO("GET_PER_ANNO", 1, "Ottieni i monumenti inseriti in quell'anno"),
    GET_PER_ANNI("GET_PER_ANNI", 2, "Ottieni i monumenti inseriti tra i due anni (il primo dev'essere minore del secondo)"),
    GET_TRA_LONGITUDINI("GET_TRA_LONGITUDINI", 2, "Ottieni i monumenti presenti tra le due longitudini (la prima longitudine dev'essere minore della seconda)"),
    GET_TRA_LATITUDINI("GET_TRA_LATITUDINI", 2, "Ottieni i monumenti presenti tra le due latitudini (la prima latitudine dev'essere minore della seconda)"),
    GET_TRA_LONGITUDINI_E_LATITUDINI("GET_TRA_LONGITUDINI_E_LATITUDINI", 4, "Ottieni i monumenti presenti tra due longitudini e due latitudini (inserisci i parametri nell'ordine: longitudine1 < longitudine2, latitudine1 < latitudine2)"),
    END("END", 0, "Chiudi la connessione al Server");

    String nome;
    int parametriPrevisti;
    String descrizione;

    Comando(String nome, int parametriPrevisti, String descrizione){
        this.nome = nome;
        this.parametriPrevisti = parametriPrevisti;
        this.descrizione = descrizione;
    }

}
