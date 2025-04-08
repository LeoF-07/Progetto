public enum Comando {

    GET_ROW("GET_ROW"),
    GET_PER_COMUNE("GET_PER_COMUNE");

    String nome;
    int lunghezzaComando;

    Comando(String nome){
        this.nome = nome;
        lunghezzaComando = nome.length();
    }

}
