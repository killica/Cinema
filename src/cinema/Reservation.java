package cinema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reservation {

    private int idRez, screen, aktivan;
    private String username, sifra, pocetak, kraj, datum;
    private ArrayList<Reservation> list = new ArrayList<>();

    public Reservation() {
        try {
            loadReservations();
        } catch (SQLException ex) {
            Logger.getLogger(Reservation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Reservation(int idRez, String username, String sifra, int screen, String pocetak, String kraj, String datum, int aktivan) {
        this.idRez = idRez;
        this.username = username;
        this.sifra = sifra;
        this.screen = screen;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.datum = datum;
        this.aktivan = aktivan;
    }

    public void loadReservations() throws SQLException {
        Connection con = SqliteConnection.Connector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Rezervacija WHERE Aktivan = 1";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Reservation(rs.getInt("IdRez"), rs.getString("Username"), rs.getString("Sifra"), rs.getInt("Screen"), rs.getString("Pocetak"), rs.getString("Kraj"), rs.getString("Datum"), rs.getInt("Aktivan")));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Reservation> getList() {
        return list;
    }

    public int getIdRez() {
        return idRez;
    }

    public int getScreen() {
        return screen;
    }

    public int getAktivan() {
        return aktivan;
    }

    public String getUsername() {
        return username;
    }

    public String getSifra() {
        return sifra;
    }

    public String getPocetak() {
        return pocetak;
    }

    public String getKraj() {
        return kraj;
    }

    public String getDatum() {
        return datum;
    }

    public void setScreen(int screen) {
        this.screen = screen;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public void setPocetak(String pocetak) {
        this.pocetak = pocetak;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String toString() {
        return "\nID rezervacije: " + idRez + "\nUsername: " + username + "\nSifra: " + sifra + "\nScreen: " + screen + "\nPocetak: " + pocetak + "\nKraj: " + kraj + "\nDatum: " + datum + "Aktivan: " + aktivan;
    }

}
