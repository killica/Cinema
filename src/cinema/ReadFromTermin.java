package cinema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Alert;

public class ReadFromTermin {

    private String imeFilma, datum, pocetak, kraj, dodao;
    private int idTermin, sala, aktivan;
    private List<ReadFromTermin> terminList = new LinkedList<>();

    public ReadFromTermin(int idTermin, String imeFilma, String datum, String pocetak, String kraj, String dodao, int sala, int aktivan) {
        this.idTermin = idTermin;
        this.imeFilma = imeFilma;
        this.datum = datum;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.dodao = dodao;
        this.sala = sala;
        this.aktivan = aktivan;
    }

    public ReadFromTermin() {
        Connection con = SqliteConnection.Connector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Termin";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                idTermin = rs.getInt("IdTerm");
                imeFilma = rs.getString("ImeFilma");
                datum = rs.getString("Datum");
                pocetak = rs.getString("Pocetak");
                kraj = rs.getString("Kraj");
                dodao = rs.getString("Dodao");
                sala = rs.getInt("Sala");
                aktivan = rs.getInt("Aktivan");
                if (aktivan == 1) {
                    terminList.add(new ReadFromTermin(idTermin, imeFilma, datum, pocetak, kraj, dodao, sala, aktivan));
                }

            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error while loading termins!");
            alert.setContentText("Try contacting administrator of this application for help.");
            alert.showAndWait();
        }
    }

    public List<ReadFromTermin> getTerminList() {
        return this.terminList;
    }

    public int getIdTermin() {
        return idTermin;
    }

    public String getIme() {
        return this.imeFilma;
    }

    public int getSala() {
        return this.sala;
    }

    public String getDatum() {
        return this.datum;
    }

    public String getPocetak() {
        return this.pocetak;
    }

    public String getKraj() {
        return this.kraj;
    }

    public void setImeFilma(String imeFilma) {
        this.imeFilma = imeFilma;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setPocetak(String pocetak) {
        this.pocetak = pocetak;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public void setDodao(String dodao) {
        this.dodao = dodao;
    }

    public void setIdTermin(int idTermin) {
        this.idTermin = idTermin;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public String getDodao() {
        return dodao;
    }

    public String toString() {
        return "Ime: " + imeFilma + "\nSala: " + sala + "\nDatum: " + datum + "\nPocetak: " + pocetak + "\nKraj: " + kraj + "\nDodao: " + dodao + "\n";
    }

}
