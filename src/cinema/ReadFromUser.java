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
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class ReadFromUser {

    private List<Film> filmList = new LinkedList<>();
    private List<Food> foodList = new LinkedList<>();
    private String fullname, username, gender, city, dateofbirth, country, email, securityquestion, answer;
    private ImageView iv;
    private int idUser, admin;
    private List<ReadFromUser> users = new LinkedList<>();

    public void loadUsers() throws IOException {
        Connection con = SqliteConnection.Connector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM User";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int idUser = rs.getInt("IdUser");
                int admin = rs.getInt("Admin");
                String fullname = rs.getString("FullName");
                String username = rs.getString("UserName");
                String gender = rs.getString("Gender");
                String city = rs.getString("City");
                String dateofbirth = rs.getString("DateOfBirth");
                String country = rs.getString("Country");
                String email = rs.getString("Email");
                String securityquestion = rs.getString("SecurityQuestion");
                String answer = rs.getString("Answer");
                byte[] slika = rs.getBytes("ProfilePicture");
                ByteArrayInputStream bis = new ByteArrayInputStream(slika);
                BufferedImage bImage2 = ImageIO.read(bis);
                Image image = SwingFXUtils.toFXImage(bImage2, null);
                ImageView iv = new ImageView(image);
                users.add(new ReadFromUser(idUser, fullname, username, gender, city, dateofbirth, country, email, securityquestion, answer, iv, admin));

            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Username not found!");
            alert.setContentText("Try another username or create a new account.");
            alert.showAndWait();
        }
    }

    public ReadFromUser(int idUser, String fullname, String username, String gender, String city, String dateofbirth, String country, String email, String securityquestion, String answer, ImageView iv, int admin) {
        this.idUser = idUser;
        this.fullname = fullname;
        this.username = username;
        this.gender = gender;
        this.city = city;
        this.dateofbirth = dateofbirth;
        this.country = country;
        this.email = email;
        this.securityquestion = securityquestion;
        this.answer = answer;
        this.iv = iv;
        this.admin = admin;
    }

    public ReadFromUser(String username, String password, Stage primaryStage, int adminInd, List<Film> filmList, List<Food> foodList) throws IOException {
        this.filmList = filmList;
        this.foodList = foodList;
        //loadUsers();

        Connection con = SqliteConnection.Connector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM User WHERE UserName = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            MessageDigest md = new MessageDigest(password);
            String password1 = md.getSHA();
            if(rs.next());
            String realPassword = rs.getString("Password");
            admin = rs.getInt("Admin");
            if (!realPassword.equals(password1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Wrong password!");
                alert.showAndWait();
            } else if (admin != adminInd) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                if (admin == 0) {
                    alert.setContentText("You can not login as an administrator.");
                }
                if (admin == 1) {
                    alert.setContentText("You can not login as a user.");
                }
                alert.showAndWait();
            } else {

                this.fullname = rs.getString("FullName");
                this.gender = rs.getString("Gender");
                this.city = rs.getString("City");
                this.dateofbirth = rs.getString("DateOfBirth");
                this.country = rs.getString("Country");
                this.email = rs.getString("Email");
                this.securityquestion = rs.getString("SecurityQuestion");
                this.answer = rs.getString("Answer");

                byte[] slika = rs.getBytes("ProfilePicture");
                rs.close();
                ps.close();
                con.close();

                ByteArrayInputStream bis = new ByteArrayInputStream(slika);
                BufferedImage bImage2 = ImageIO.read(bis);
                Image image = SwingFXUtils.toFXImage(bImage2, null);
                this.iv = new ImageView(image);
                con.close();
                primaryStage.close();
                if (admin == 0) {
                    User u = new User(fullname, username, password1, gender, city, dateofbirth, country, 
                            email, securityquestion, answer, iv, filmList, foodList);
                } else {
                    Admin a = new Admin(fullname, username, password1, gender, city, dateofbirth, country, 
                            email, securityquestion, answer, iv, filmList, foodList, users);
                }
            }     
        }catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Username not found!");
            alert.setContentText("Try another username or create a new account.");
            alert.showAndWait();
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public List<Film> getFilmList() {
        return filmList;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getSecurityquestion() {
        return securityquestion;
    }

    public String getAnswer() {
        return answer;
    }

    public ImageView getIv() {
        return iv;
    }

    public int getAdmin() {
        return admin;
    }

    public List<ReadFromUser> getUsers() {
        return users;
    }

}
