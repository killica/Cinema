package cinema;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class SqliteConnection {

    public static Connection Connector() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:User.db");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return conn;
    }

}
