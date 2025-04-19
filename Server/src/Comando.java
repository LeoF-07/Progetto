public enum Comando {

    GET_ROW("GET_ROW", 1),
    GET_PER_COMUNE ("GET_PER_COMUNE", 1),
    GET_PER_PROVINCIA ("GET_PER_PROVINCIA", 1),
    GET_PER_REGIONE ("GET_PER_REGIONE", 1),
    GET_PER_NOME("GET_PER_NOME", 1),
    GET_PER_NOME_PARZIALE("GET_PER_NOME_PARZIALE", 1),
    GET_PER_TIPO ("GET_PER_TIPO", 1),
    GET_PER_ANNO("GET_PER_ANNO", 1),
    GET_PER_ANNI("GET_PER_ANNI", 2),
    GET_TRA_LONGITUDINI("GET_TRA_LONGITUDINI", 2),
    GET_TRA_LATITUDINI("GET_TRA_LATITUDINI", 2),
    GET_TRA_LONGITUDINI_E_LATITUDINI("GET_TRA_LONGITUDINI_E_LATITUDINI", 4),
    END("END", 0);

    String nome;
    int parametriPrevisti;

    Comando(String nome, int parametriPrevisti){
        this.nome = nome;
        this.parametriPrevisti = parametriPrevisti;
    }

}
