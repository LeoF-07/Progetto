import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class Monumento {

    private String comune;
    private String provincia;
    private String regione;
    private String nome;
    private String tipo;
    private Year annoInserimento;
    private LocalDateTime dataEOraInserimento;
    private String identificatoreOpenStreetMap;
    private double longitudine;
    private double latitudine;

    public String getParametri(){
        return "comune;provincia;regione;nome;tipo;annoInserimento;dataEOraInserimento;identificatoreOpenStreetMap;longitudine;latitudine";
    }

    public Monumento(String comune, String provincia, String regione, String nome, String tipo, Year annoInserimento, LocalDateTime dataEOraInserimento, String identificatoreOpenStreetMap, double longitudine, double latitudine) {
        this.comune = comune;
        this.provincia = provincia;
        this.regione = regione;
        this.nome = nome;
        this.tipo = tipo;
        this.annoInserimento = annoInserimento;
        this.dataEOraInserimento = dataEOraInserimento;
        this.identificatoreOpenStreetMap = identificatoreOpenStreetMap;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Year getAnnoInserimento() {
        return annoInserimento;
    }

    public void setAnnoInserimento(Year annoInserimento) {
        this.annoInserimento = annoInserimento;
    }

    public LocalDateTime getDataEOraInserimento() {
        return dataEOraInserimento;
    }

    public void setDataEOraInserimento(LocalDateTime dataEOraInserimento) {
        this.dataEOraInserimento = dataEOraInserimento;
    }

    public String getIdentificatoreOpenStreetMap() {
        return identificatoreOpenStreetMap;
    }

    public void setIdentificatoreOpenStreetMap(String identificatoreOpenStreetMap) {
        this.identificatoreOpenStreetMap = identificatoreOpenStreetMap;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm:ss");
        String dataFormattata = dataEOraInserimento.format(formatter);
        return "Monumento: " + comune + " " + provincia + " " + regione + " " + nome + " " + tipo + " "
                + annoInserimento + " " + dataFormattata + " " + identificatoreOpenStreetMap + " " + longitudine + " " + latitudine;
    }

}
