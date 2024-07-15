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

public class Food {

    private int idHrana;
    private String ime;
    private byte[] pic;
    private String category;
    private double cena;
    private String opis;
    private String dodao;
    private String datumDodavanja;
    private ImageView slika;

    private List<Food> foodList = new LinkedList<>();

    public Food(int idHrana, String ime, ImageView slika, String category, double cena, String opis, String dodao, String datumDodavanja) {
        this.idHrana = idHrana;
        this.ime = ime;
        this.slika = slika;
        this.category = category;
        this.cena = cena;
        this.opis = opis;
        this.dodao = dodao;
        this.datumDodavanja = datumDodavanja;
    }

    public Food() throws IOException {
        Connection con = SqliteConnection.Connector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            String sql = "SELECT * FROM Hrana";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                idHrana = rs.getInt("IdHra");
                ime = rs.getString("Ime");
                pic = rs.getBytes("Slika");
                category = rs.getString("Kategorija");
                cena = rs.getDouble("Cena");
                opis = rs.getString("Opis");
                dodao = rs.getString("Dodao");
                datumDodavanja = rs.getString("DatumDodavanja");

                ByteArrayInputStream bis = new ByteArrayInputStream(pic);
                BufferedImage bImage2 = ImageIO.read(bis);
                Image image = SwingFXUtils.toFXImage(bImage2, null);
                slika = new ImageView(image);

                foodList.add(new Food(idHrana, ime, slika, category, cena, opis, dodao, datumDodavanja));

            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error while loading food!");
            alert.setContentText("Try contacting administrator of this application for help.");
            alert.showAndWait();
        }
    }

    public int getIdHrana() {
        return idHrana;
    }

    public String getIme() {
        return ime;
    }

    public byte[] getPic() {
        return pic;
    }

    public String getCategory() {
        return category;
    }

    public double getCena() {
        return cena;
    }

    public String getOpis() {
        return opis;
    }

    public String getDodao() {
        return dodao;
    }

    public String getDatumDodavanja() {
        return datumDodavanja;
    }

    public ImageView getSlika() {
        return slika;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setDodao(String dodao) {
        this.dodao = dodao;
    }

    public void setDatumDodavanja(String datumDodavanja) {
        this.datumDodavanja = datumDodavanja;
    }

    public void setSlika(ImageView slika) {
        this.slika = slika;
    }

    //STARA METODA toString
    /*public String toString()
    {
        return "Naziv: "+ ime + "\nKategorija: "+ category + "\nCena: " + cena + "\nOpis: " + opis + "\nDodao: "+ dodao + "\nDatum dodavanja: "+ datumDodavanja;
    }*/
    public String toString() {
        return ime;
    }

}
