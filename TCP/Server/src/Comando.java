public enum Comando {

    GET_ROW("GET_ROW", 1, "Ottieni il monumento nella riga scelta", ""),
    GET_PER_COMUNE ("GET_PER_COMUNE", 1, "Ottieni i monumenti in quel comune", "Non esiste nessun monumento in questo comune"),
    GET_PER_PROVINCIA ("GET_PER_PROVINCIA", 1, "Ottieni i monumenti in quella provincia", "Non esiste nessun monumento in questa provincia"),
    GET_PER_REGIONE ("GET_PER_REGIONE", 1, "Ottieni i monumenti in quella regione", "Non esiste nessun monumento in questa regione"),
    GET_PER_NOME("GET_PER_NOME", 1, "Ottieni i monumenti con quel nome", "Non esiste nessun monumento con questo nome"),
    GET_PER_NOME_PARZIALE("GET_PER_NOME_PARZIALE", 1, "Ottieni i monumenti che contengono quel nome", "Nessun monumento contiene questo nome"),
    GET_PER_TIPO ("GET_PER_TIPO", 1, "Ottieni i monumeni di quel tipo", "Non esiste nessun monumento di questo tipo"),
    GET_PER_ANNO("GET_PER_ANNO", 1, "Ottieni i monumenti inseriti in quell'anno", "Nessun monumento è stato inserito in quell'anno"),
    GET_TRA_ANNI("GET_TRA_ANNI", 2, "Ottieni i monumenti inseriti tra i due anni (il primo dev'essere minore del secondo)", "Non è stato inserito nessun monumento tra i due anni"),
    GET_TRA_LONGITUDINI("GET_TRA_LONGITUDINI", 2, "Ottieni i monumenti presenti tra le due longitudini (la prima longitudine dev'essere minore della seconda, usare il punto per i decimali)", "Non è presente nessun monumento tra le due longitudini"),
    GET_TRA_LATITUDINI("GET_TRA_LATITUDINI", 2, "Ottieni i monumenti presenti tra le due latitudini (la prima latitudine dev'essere minore della seconda, usare il punto per i decimali)", "Non è presente nessun monumento tra le due latitudini"),
    GET_TRA_LONGITUDINI_E_LATITUDINI("GET_TRA_LONGITUDINI_E_LATITUDINI", 4, "Ottieni i monumenti presenti tra due longitudini e due latitudini (inserire i parametri nell'ordine: lon < lon2, lat1 < lat2, usare il punto per i decimali)", "Non è presente nessun monumento tra le due longitudini e le due latitudini"),
    END("END", 0, "Chiudi la connessione al Server", "");

    String nome;
    int parametriPrevisti;
    String descrizione;
    String errore;

    Comando(String nome, int parametriPrevisti, String descrizione, String errore){
        this.nome = nome;
        this.parametriPrevisti = parametriPrevisti;
        this.descrizione = descrizione;
        this.errore = "ERROR: " + errore;
    }

}
