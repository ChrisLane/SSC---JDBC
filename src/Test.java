import java.sql.Connection;
import java.sql.SQLException;

public class Test {

    public static void main(String[] args) {
        MakeConnection makeConnection = new MakeConnection();
        Connection connection = makeConnection.getConnection();

        Tables tables = new Tables(connection);
        tables.dropAllTables();
        tables.createAllTables();

        TestPopulate pop = new TestPopulate(connection);
        pop.populateTitles();
        pop.populateLecturers();
        pop.populateStudents();

        pop.populateRealistic();

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
