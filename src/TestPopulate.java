import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestPopulate {

    private final Connection connection;
    private final Student student;
    private final Lecturer lecturer;
    private final RegistrationType registrationType;
    private final Titles titles;

    public TestPopulate(Connection connection) {
        this.connection = connection;
        student = new Student(connection);
        lecturer = new Lecturer(connection);
        registrationType = new RegistrationType(connection);
        titles = new Titles(connection);
    }

    /**
     * Add titles to the DB
     */
    public void populateTitles() {
        titles.newTitle(1, "Professor");
        titles.newTitle(2, "Dr");
        titles.newTitle(3, "Mr");
        titles.newTitle(4, "Miss");
        titles.newTitle(5, "Mrs");
    }

    /**
     * Add generated lecturers to the DB
     */
    public void populateLecturers() {
        int lecturerID = 1;
        int firstInitial = 1;
        int secondInitial = 1;

        String lecturer = "INSERT INTO Lecturer "
                + "(lecturerid, titleid, forename, familyname) VALUES "
                + "(?, ?, ?, ?)";


        try {
            PreparedStatement stmt = connection.prepareStatement(lecturer);

            for (int j = 1; j <= 5; j++) {
                String foreName = "Firstname" + firstInitial;
                String familyName = "Lastname" + secondInitial;

                stmt.setInt(1, lecturerID);
                stmt.setInt(2, j);
                stmt.setString(3, String.valueOf(foreName));
                stmt.setString(4, String.valueOf(familyName));
                stmt.addBatch();

                lecturerID++;
                firstInitial++;
                secondInitial++;
            }

            stmt.executeBatch();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add auto generated students to the DB
     */
    @SuppressWarnings("deprecation")
    public void populateStudents() {
        int studentID = 1;
        int firstInitial = 1;
        int secondInitial = 1;
        Date dateOfBirth = Date.valueOf("1995-07-05");

        String student = "INSERT INTO Student "
                + "(studentid, titleid, forename, familyname, dateofbirth) VALUES "
                + "(?, ?, ?, ?, ?)";


        try {
            PreparedStatement stmt = connection.prepareStatement(student);

            for (int i = 0; i < 20; i++) {
                for (int j = 1; j <= 5; j++) {
                    String foreName = "Firstname" + firstInitial;
                    String familyName = "Lastname" + secondInitial;

                    stmt.setInt(1, studentID);
                    stmt.setInt(2, j);
                    stmt.setString(3, String.valueOf(foreName));
                    stmt.setString(4, String.valueOf(familyName));
                    stmt.setDate(5, dateOfBirth);
                    stmt.addBatch();

                    studentID++;
                    firstInitial++;
                    secondInitial++;
                }
            }

            stmt.executeBatch();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add realistic entries to the DB
     */
    public void populateRealistic() {
        lecturer.newLecturer(100, 1, "Diedre", "Parrott");
        lecturer.newContact(100, "G100", "diedreparrot@lecturer.com");

        lecturer.newLecturer(101, 2, "Simon", "Treasure");
        lecturer.newContact(101, "G101", "simontreasure@lecturer.com");

        lecturer.newLecturer(102, 3, "Bob", "Bobbington");
        lecturer.newContact(102, "G200", "bobbobbington@lecturer.com");

        lecturer.newLecturer(103, 4, "Sally", "Silly");
        lecturer.newContact(103, "G103", "sallysilly@lecturer.com");

        lecturer.newLecturer(104, 5, "Harriet", "Lowe");
        lecturer.newContact(104, "G220", "harrietlowe@lecturer.com");


        registrationType.newType(1, "normal");
        registrationType.newType(2, "repeat");
        registrationType.newType(3, "external");


        student.newStudent(200, 1, "John", "Wescott", "2000-01-01");
        student.newContact(200, "johnwescott@email.com", "1 Park Road");
        student.newRegistration(200, 1, 1);
        student.assignTutor(200, 101);
        student.newNextOfKinContact(200, "Ann Wescott", "annwescott@parent.com", "1 Park Road");

        student.newStudent(201, 2, "Emelia", "Friday", "1995-12-01");
        student.newContact(201, "emeliafriday@email.com", "2 Park Road");
        student.newRegistration(201, 2, 2);
        student.assignTutor(201, 101);
        student.newNextOfKinContact(201, "Darren Friday", "darrenfriday@parent.com", "2 Park Road");

        student.newStudent(202, 3, "Fred", "Parv", "1992-06-10");
        student.newContact(202, "fredparv@email.com", "3 Park Road");
        student.newRegistration(202, 1, 3);
        student.assignTutor(202, 101);
        student.newNextOfKinContact(202, "Tina Parv", "tinaparv@parent.com", "3 Park Road");

        student.newStudent(203, 4, "Jenny", "Orange", "1993-01-22");
        student.newContact(203, "jennyorange@email.com", "4 Park Road");
        student.newRegistration(203, 1, 1);
        student.assignTutor(203, 102);
        student.newNextOfKinContact(203, "Dora Explorer", "doraexplorer@parent.com", "4 Park Road");

        student.newStudent(204, 5, "Sarah", "Peters", "1994-03-23");
        student.newContact(204, "sarahpeters@email.com", "5 Park Road");
        student.newRegistration(204, 103, 1);
        student.assignTutor(204, 103);
        student.newNextOfKinContact(204, "Sam Peters", "sampeters@parent.com", "5 Park Road");
    }
}
