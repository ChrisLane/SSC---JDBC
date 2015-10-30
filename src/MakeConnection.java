import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MakeConnection {
    private String url = "jdbc:postgresql://dbteach2.cs.bham.ac.uk/cml476";
    private String username = "cml476";
    private String pass = "postpass1";
    private Connection connection = null;

    public MakeConnection() {
        try {
            System.setProperty("jdbc.drivers", "org.postgresql.Driver");
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found");
        }

        System.out.println("PostgreSQL driver registered.");

        try {
            connection = DriverManager.getConnection(url, username, pass);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (connection != null) {
            System.out.println("Database accessed!");
        } else {
            System.out.println("Failed to make a connection");
        }
    }

    /**
     * Return the SQL connection
     * @return The SQL connection
     */
    public Connection getConnection() {
        return connection;
    }

}
