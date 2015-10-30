import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Titles {
    private final Connection connection;

    public Titles(Connection connection) {
        this.connection = connection;
    }

    /**
     * Add a new title to the database
     * @param titleID The ID of the title
     * @param titleString The title
     */
    public void newTitle(int titleID, String titleString) {
        String title = "INSERT INTO Titles"
                + "(titleid, titlestring) VALUES"
                + "(?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(title);

            stmt.setInt(1, titleID);
            stmt.setString(2, titleString);
            stmt.addBatch();

            stmt.execute();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
