import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Lecturer {
    private final Connection connection;

    public Lecturer(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates a new lecturer in the database
     * @param lecturerID The lecturer's ID
     * @param titleID The title ID of the lecturer's title
     * @param foreName The lecturer's first name
     * @param familyName The lecturer's last name
     */
    public void newLecturer(int lecturerID, int titleID, String foreName, String familyName) {
        String lecturer = "INSERT INTO Lecturer"
                + "(lecturerid, titleid, forename, familyname) VALUES"
                + "(?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(lecturer);

            stmt.setInt(1, lecturerID);
            stmt.setInt(2, titleID);
            stmt.setString(3, foreName);
            stmt.setString(4, familyName);
            stmt.addBatch();

            stmt.execute();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add new contact details for a lecturer
     * @param lecturerID The lecturer's ID
     * @param office The lecturer's office
     * @param emailAddress The lecturer's email address
     */
    public void newContact(int lecturerID, String office, String emailAddress) {
        String contact = "INSERT INTO LecturerContact "
                + "(lecturerid, office, emailaddress) VALUES"
                + "(?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(contact);

            stmt.setInt(1, lecturerID);
            stmt.setString(2, office);
            stmt.setString(3, emailAddress);

            stmt.execute();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Print a report of a lecturer's tutees
     * @param lecturerID The lecturer's ID
     */
    public void tuteesReport(int lecturerID) {
        Student student = new Student(connection);
        String tutees = "SELECT studentID FROM Tutor WHERE lecturerID = " + lecturerID;

        try {
            PreparedStatement stmt = connection.prepareStatement(tutees);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                student.printName(rs.getInt(1));
                student.printID(rs.getInt(1));
                student.printRegistrationType(rs.getInt(1));
                student.printDOB(rs.getInt(1));
                student.printContact(rs.getInt(1));
                student.printEmergencyContact(rs.getInt(1));
                System.out.println();
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
