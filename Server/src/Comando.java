public enum Comando {

    GET_ROW("GET_ROW"),
    GET_PER_COMUNE ("GET_PER_COMUNE"),
    GET_PER_PROVINCIA ("GET_PER_PROVINCIA"),
    GET_PER_REGIONE ("GET_PER_REGIONE"),
    GET_PER_NOME("GET_PER_NOME"),
    GET_PER_NOME_PARZIALE("GET_PER_NOME_PARZIALE"),
    GET_PER_TIPO ("GET_PER_TIPO"),
    GET_PER_ANNO("GET_PER_ANNO"),
    GET_PER_ANNI("GET_PER_ANNI"),
    GET_TRA_LONGITUDINI("GET_TRA_LONGITUDINI"),
    GET_TRA_LATITUDINI("GET_TRA_LATITUDINI"),
    GET_TRA_LONGITUDINI_E_LATITUDINI("GET_TRA_LONGITUDINI_E_LATITUDINI"),
    END("END");

    String nome;
    int lunghezzaComando;

    Comando(String nome){
        this.nome = nome;
        lunghezzaComando = nome.length();
    }

}
