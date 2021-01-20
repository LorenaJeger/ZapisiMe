package sample;

import javafx.scene.control.DatePicker;

// Klasa za spremanje željenih atributa
public class Obaveze {
    private int id;
    private String obaveza;
    private String datumObaveze;
    private String vrijemeObaveze;
    private String opisObaveze;

    // Konstruktor
    public Obaveze(int id, String obaveza, String datumObaveze, String vrijemeObaveze, String opisObaveze) {
        this.id = id;
        this.obaveza = obaveza;
        this.datumObaveze = datumObaveze;
        this.vrijemeObaveze = vrijemeObaveze;
        this.opisObaveze = opisObaveze;
    }

    // Getteri
    public int getId() {
        return id;
    }

    public String getObaveza() {
        return obaveza;
    }

    public String getDatumObaveze() {
        return datumObaveze;
    }

    public String getVrijemeObaveze() {
        return vrijemeObaveze;
    }

    public String getOpisObaveze() {
        return opisObaveze;
    }

}