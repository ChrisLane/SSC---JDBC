import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Tables {
    private final Connection connection;

    public Tables(Connection connection) {
        this.connection = connection;
    }

    /**
     * Drop all tables
     */
    public void dropAllTables() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("DROP TABLE Student, Lecturer, StudentRegistration, StudentContact, NextOfKinContact," +
                    "LecturerContact, Tutor, Titles, RegistrationType");

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clear all tables
     */
    private void clearAllTables() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("TRUNCATE lecturer, lecturercontact, nextofkincontact, registrationtype, student, studentcontact, studentregistration, titles, tutor");

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create all tables
     */
    public void createAllTables() {
        try {
            Statement stmt = connection.createStatement();

            stmt.addBatch(
                    "CREATE TABLE Student ("
                            + "studentID INT PRIMARY KEY,"
                            + "titleID INT NOT NULL,"
                            + "foreName CHAR(20) NOT NULL,"
                            + "familyName CHAR(20),"
                            + "dateOfBirth DATE NOT NULL"
                            + ")"
            );
            stmt.addBatch(
                    "CREATE TABLE Lecturer ("
                            + "lecturerID INT PRIMARY KEY,"
                            + "titleID INT NOT NULL,"
                            + "foreName CHAR(20) NOT NULL,"
                            + "familyName CHAR(20)"
                            + ")"
            );
            stmt.addBatch(
                    "CREATE TABLE StudentRegistration ("
                            + "studentID INT NOT NULL UNIQUE,"
                            + "yearOfStudy INT NOT NULL,"
                            + "registrationTypeID INT NOT NULL"
                            + ")"
            );
            stmt.addBatch(
                    "CREATE TABLE StudentContact ("
                            + "studentID INT,"
                            + "eMailAddress CHAR(30) NOT NULL,"
                            + "postalAddress CHAR(40) NOT NULL"
                            + ")"
            );
            stmt.addBatch(
                    "CREATE TABLE NextOfKinContact ("
                            + "studentID INT,"
                            + "name CHAR(20) NOT NULL,"
                            + "eMailAddress CHAR(30),"
                            + "postalAddress CHAR(40) NOT NULL"
                            + ")"
            );
            stmt.addBatch(
                    "CREATE TABLE LecturerContact ("
                            + "lecturerID INT UNIQUE,"
                            + "office CHAR(5) NOT NULL,"
                            + "eMailAddress CHAR(30) NOT NULL"
                            + ")"
            );
            stmt.addBatch(
                    "CREATE TABLE Tutor ("
                            + "studentID INT,"
                            + "lecturerID INT"
                            + ")"
            );
            stmt.addBatch(
                    "CREATE TABLE Titles ("
                            + "titleID INT PRIMARY KEY,"
                            + "titleString CHAR(10) UNIQUE NOT NULL"
                            + ")"
            );
            stmt.addBatch(
                    "CREATE TABLE RegistrationType ("
                            + "registrationTypeID INT PRIMARY KEY,"
                            + "description CHAR(10) UNIQUE NOT NULL"
                            + ")"
            );
            stmt.executeBatch();
            stmt.clearBatch();

            stmt.addBatch(
                    "ALTER TABLE Student "
                            + "ADD FOREIGN KEY (titleID) REFERENCES Titles(titleID),"
                            + "ADD CHECK (dateofbirth < current_date),"
                            + "ADD CHECK (dateOfBirth > '1900-01-01')"
            );
            stmt.addBatch(
                    "ALTER TABLE Lecturer "
                            + "ADD FOREIGN KEY (titleID) REFERENCES Titles(titleID)"
            );
            stmt.addBatch(
                    "ALTER TABLE StudentRegistration "
                            + "ADD FOREIGN KEY (studentID) REFERENCES Student(studentID),"
                            + "ADD FOREIGN KEY (registrationTypeID) REFERENCES RegistrationType(registrationTypeID),"
                            + "ADD CHECK (yearOfStudy > 0)"
            );
            stmt.addBatch(
                    "ALTER TABLE StudentContact "
                            + "ADD FOREIGN KEY (studentID) REFERENCES Student(studentID)"
            );
            stmt.addBatch(
                    "ALTER TABLE NextOfKinContact "
                            + "ADD FOREIGN KEY (studentID) REFERENCES Student(studentID)"
            );
            stmt.addBatch(
                    "ALTER TABLE LecturerContact "
                            + "ADD FOREIGN KEY (lecturerID) REFERENCES Lecturer(lecturerID)"
            );
            stmt.addBatch(
                    "ALTER TABLE Tutor "
                            + "ADD FOREIGN KEY (studentID) REFERENCES Student(studentID),"
                            + "ADD FOREIGN KEY (lecturerID) REFERENCES Lecturer(lecturerID)"
            );

            stmt.executeBatch();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
