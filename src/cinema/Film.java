package cinema;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

public class Film {

    private String ime, kratakOpis, trejler, zanr, duzina, dodao, datumDodavanja, IMDBrating;
    private double CenaKarteO, CenaKarteD;
    private int idFilm, aktivan;
    private byte[] posterByte;
    private ImageView poster;
    private List<ReadFromTermin> terminList = new LinkedList<>();
    private List<Film> filmList = new LinkedList<>();

    public Film(int idFilm, String ime, String kratakOpis, String trejler, String zanr, String duzina, String dodao, String datumDodavanja, String IMDBrating, double CenaKarteO, double CenaKarteD, List<ReadFromTermin> terminList, ImageView poster, int aktivan) {
        this.idFilm = idFilm;
        this.ime = ime;
        this.kratakOpis = kratakOpis;
        this.trejler = trejler;
        this.zanr = zanr;
        this.duzina = duzina;
        this.dodao = dodao;
        this.datumDodavanja = datumDodavanja;
        this.IMDBrating = IMDBrating;
        this.CenaKarteD = CenaKarteD;
        this.CenaKarteO = CenaKarteO;
        this.terminList = terminList;
        this.poster = poster;
        this.aktivan = aktivan;
    }

    public Film(List<ReadFromTermin> terminListAllFilms) throws IOException {

        Connection con = SqliteConnection.Connector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            String sql = "SELECT * FROM Film";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                idFilm = rs.getInt("IdFil");
                ime = rs.getString("Ime");
                kratakOpis = rs.getString("KratakOpis");
                trejler = rs.getString("Trejler");
                zanr = rs.getString("Zanr");
                duzina = rs.getString("Duzina");
                dodao = rs.getString("Dodao");
                datumDodavanja = rs.getString("DatumDodavanja");
                IMDBrating = rs.getString("Rating");
                CenaKarteO = rs.getDouble("CenaKarteO");
                CenaKarteD = rs.getDouble("CenaKarteD");
                aktivan = rs.getInt("Aktivan");

                posterByte = rs.getBytes("Poster");
                ByteArrayInputStream bis = new ByteArrayInputStream(posterByte);
                BufferedImage bImage2 = ImageIO.read(bis);
                Image image = SwingFXUtils.toFXImage(bImage2, null);
                poster = new ImageView(image);

                for (int i = 0; i < terminListAllFilms.size(); i++) {
                    if (terminListAllFilms.get(i).getIme().equals(this.ime)) {
                        terminList.add(terminListAllFilms.get(i));
                    }

                }
                List<ReadFromTermin> terminListCopy = new LinkedList<>();
                terminListCopy.addAll(terminList);
                if (aktivan == 1) {
                    filmList.add(new Film(idFilm, ime, kratakOpis, trejler, zanr, duzina, dodao, datumDodavanja, IMDBrating, CenaKarteO, CenaKarteD, terminListCopy, poster, aktivan));
                }

                terminList.clear();

            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error while loading films!");
            alert.setContentText("Try contacting administrator of this application for help.");
            alert.showAndWait();
        }
    }

    public List<Film> getFilmList() {
        return this.filmList;
    }

    public List<ReadFromTermin> getTerminList() {
        return this.terminList;

    }

    public int getIdFilm() {
        return idFilm;
    }

    public String getZanr() {
        return this.zanr;
    }

    public String getKratakOpis() {
        return this.kratakOpis;
    }

    public String getTrejler() {
        return this.trejler;
    }

    public String getDuzina() {
        return this.duzina;
    }

    public String getDatumDodavanja() {
        return this.datumDodavanja;
    }

    public String getIMDBrating() {
        return this.IMDBrating;
    }

    public ImageView getPoster() {
        return this.poster;
    }

    public double getCenaKarteD() {
        return this.CenaKarteD;
    }

    public double getCenaKarteO() {
        return this.CenaKarteO;
    }

    public String getIme() {
        return ime;
    }

    public String getDodao() {
        return dodao;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setKratakOpis(String kratakOpis) {
        this.kratakOpis = kratakOpis;
    }

    public void setTrejler(String trejler) {
        this.trejler = trejler;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public void setDuzina(String duzina) {
        this.duzina = duzina;
    }

    public void setDodao(String dodao) {
        this.dodao = dodao;
    }

    public void setDatumDodavanja(String datumDodavanja) {
        this.datumDodavanja = datumDodavanja;
    }

    public void setIMDBrating(String IMDBrating) {
        this.IMDBrating = IMDBrating;
    }

    public void setCenaKarteO(double CenaKarteO) {
        this.CenaKarteO = CenaKarteO;
    }

    public void setCenaKarteD(double CenaKarteD) {
        this.CenaKarteD = CenaKarteD;
    }

    public void setPosterByte(byte[] posterByte) {
        this.posterByte = posterByte;
    }

    public void setPoster(ImageView poster) {
        this.poster = poster;
    }

    public String toString() {
        return "ImeFilma: " + ime + "\nKratak opis: " + kratakOpis + "\nTerjler: " + trejler + "\nZanr: " + zanr + "\nDuzina: " + duzina + "\nDodao: " + dodao + "\nCena decije karte: " + CenaKarteD + "\nCena karte za odrasle: " + CenaKarteO + "\nTERMINI:\n" + terminList.toString();
    }
}
