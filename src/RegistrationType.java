import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationType {
    private final Connection connection;

    public RegistrationType(Connection connection) {
        this.connection = connection;
    }

    /**
     * Create a new registration type
     * @param registrationTypeID The ID of the registration type
     * @param description The description of the registration type
     */
    public void newType(int registrationTypeID, String description) {
        String type = "INSERT INTO RegistrationType"
                + "(registrationtypeid, description) VALUES"
                + "(?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(type);

            stmt.setInt(1, registrationTypeID);
            stmt.setString(2, description);
            stmt.addBatch();

            stmt.execute();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
