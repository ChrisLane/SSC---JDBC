import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Student {
    private final Connection connection;

    public Student(Connection connection) {
        this.connection = connection;
    }

    /**
     * Enters a new student into the database
     *
     * @param studentID   The student's ID
     * @param titleID     The ID of the student's title
     * @param foreName    The student's first name
     * @param familyName  The student's last name
     * @param dateOfBirth The student's date of birth
     */
    public void newStudent(int studentID, int titleID, String foreName, String familyName, String dateOfBirth) {
        String student = "INSERT INTO Student "
                + "(studentid, titleid, forename, familyname, dateofbirth) VALUES"
                + "(?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(student)) {

            stmt.setInt(1, studentID);
            stmt.setInt(2, titleID);
            stmt.setString(3, foreName);
            stmt.setString(4, familyName);
            stmt.setDate(5, Date.valueOf(dateOfBirth));

            stmt.execute();

            stmt.close();
        } catch (SQLException e) {
            /*System.err.println(e.getErrorCode());
            if (e.getErrorCode() == 0) {
                System.err.println("Duplicate Key: This studentID already exists!");
            }*/
            e.printStackTrace();
        }
    }

    /**
     * Enters contact information for a student to the database
     * @param studentID The student's ID
     * @param emailAddress The student's email address
     * @param postalAddress The student's postal address
     */
    public void newContact(int studentID, String emailAddress, String postalAddress) {
        String contact = "INSERT INTO StudentContact "
                + "(studentid, emailaddress, postaladdress) VALUES"
                + "(?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(contact)) {

            stmt.setInt(1, studentID);
            stmt.setString(2, emailAddress);
            stmt.setString(3, postalAddress);

            stmt.execute();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enters contact information for a student's next of kin to the database
     * @param studentID The student's ID
     * @param name The name of the next of kin
     * @param emailAddress The email address of the next of kin
     * @param postalAddress The postal address of the next of kin
     */
    public void newNextOfKinContact(int studentID, String name, String emailAddress, String postalAddress) {
        String nextOfKinContact = "INSERT INTO NextOfKinContact "
                + "(studentid, name, emailaddress, postaladdress) VALUES"
                + "(?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(nextOfKinContact)) {

            stmt.setInt(1, studentID);
            stmt.setString(2, name);
            stmt.setString(3, emailAddress);
            stmt.setString(4, postalAddress);

            stmt.execute();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enters new registration details for a student to the database
     * @param studentID The student's ID
     * @param yearOfStudy The student's year of study
     * @param registrationTypeID The ID of the student's registration type
     */
    public void newRegistration(int studentID, int yearOfStudy, int registrationTypeID) {
        String student = "INSERT INTO StudentRegistration "
                + "(studentid, yearOfStudy, registrationTypeID) VALUES"
                + "(?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(student)) {

            stmt.setInt(1, studentID);
            stmt.setInt(2, yearOfStudy);
            stmt.setInt(3, registrationTypeID);

            stmt.execute();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Assigns a tutor to a student
     * @param studentID The student's ID
     * @param lecturerID The tutor's lecturer ID
     */
    public void assignTutor(int studentID, int lecturerID) {
        String tutor = "INSERT INTO Tutor "
                + "(studentid, lecturerid) VALUES"
                + "(?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(tutor)) {

            stmt.setInt(1, studentID);
            stmt.setInt(2, lecturerID);

            stmt.execute();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print a report of a student's information
     * @param studentID The student's ID
     */
    public void studentReport(int studentID) {
        printName(studentID);
        printDOB(studentID);
        printID(studentID);
        printYearOfStudy(studentID);
        printRegistrationType(studentID);
        printContact(studentID);
        printEmergencyContact(studentID);
        printTutor(studentID);
    }

    /**
     * Print a student's full name
     * @param studentID The student's ID
     */
    public void printName(int studentID) {
        String name = "SELECT titleString, foreName, familyName " +
                "FROM Student, Titles " +
                "WHERE studentID = " + studentID + "AND Titles.titleID = " +
                "(SELECT titleID FROM Student WHERE studentID = " + studentID + ")";

        Report report = new Report(connection);
        report.printDetail("Name: ", name);
    }

    /**
     * Print a student's date of birth
     * @param studentID The student's ID
     */
    public void printDOB(int studentID) {
        String dob = "SELECT dateOfBirth " +
                "FROM Student " +
                "WHERE studentID = " + studentID;

        Report report = new Report(connection);
        report.printDetail("DOB: ", dob);
    }

    /**
     * Print a student's ID
     * @param studentID The student's ID
     */
    public void printID(int studentID) {
        System.out.println("StudentID: " + studentID);
    }

    /**
     * Print a student's year of study
     * @param studentID The student's ID
     */
    public void printYearOfStudy(int studentID) {
        String yearOfStudy = "SELECT yearOfStudy " +
                "FROM StudentRegistration " +
                "WHERE studentID = " + studentID;

        Report report = new Report(connection);
        report.printDetail("Year Of Study: ", yearOfStudy);
    }

    /**
     * Print a student's registration type
     * @param studentID The student's ID
     */
    public void printRegistrationType(int studentID) {
        String registrationType = "SELECT description " +
                "FROM RegistrationType " +
                "WHERE registrationTypeID = " +
                "(SELECT registrationTypeID FROM StudentRegistration WHERE studentID = " + studentID + ")";

        Report report = new Report(connection);
        report.printDetail("Registration Type: ", registrationType);
    }

    /**
     * Print a student's contact details
     * @param studentID The student's ID
     */
    public void printContact(int studentID) {
        String contact = "SELECT emailAddress, postalAddress " +
                "FROM StudentContact " +
                "WHERE studentID = " + studentID;

        Report report = new Report(connection);
        report.printDetail("Contact: ", contact);
    }

    /**
     * Print a student's emergency contact details
     * @param studentID The student's ID
     */
    public void printEmergencyContact(int studentID) {
        String emergencyContact = "SELECT name, emailAddress, postalAddress " +
                "FROM NextOfKinContact " +
                "WHERE studentID = " + studentID;

        Report report = new Report(connection);
        report.printDetail("Emergency Contact: ", emergencyContact);
    }

    /**
     * Print a student's personal tutor
     * @param studentID The student's ID
     */
    public void printTutor(int studentID) {
        String tutor = "SELECT foreName, familyName " +
                "FROM Lecturer " +
                "WHERE lecturerID = " +
                "(SELECT lecturerID FROM Tutor WHERE studentID = " + studentID + ")";

        Report report = new Report(connection);
        report.printDetail("Tutor: ", tutor);
    }
}
