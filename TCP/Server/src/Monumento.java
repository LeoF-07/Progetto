import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;

/**
 * Classe Monumento
 * Rappresenta un monumento con tutti i suoi attributi corrispondenti ai campi del file CSV
 */
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

    public static String getAttributi(){
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

    public String getProvincia() {
        return provincia;
    }

    public String getRegione() {
        return regione;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public Year getAnnoInserimento() {
        return annoInserimento;
    }

    public LocalDateTime getDataEOraInserimento() {
        return dataEOraInserimento;
    }

    public String getIdentificatoreOpenStreetMap() {
        return identificatoreOpenStreetMap;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm:ss");
        String dataFormattata = dataEOraInserimento.format(formatter);
        return "Monumento: " + comune + " " + provincia + " " + regione + " " + nome + " " + tipo + " "
                + annoInserimento + " " + dataFormattata + " " + identificatoreOpenStreetMap + " " + longitudine + " " + latitudine;
    }

}