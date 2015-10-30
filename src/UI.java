import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class UI {
    private final Connection connection;
    private final Student student;
    private final Lecturer lecturer;
    private final Scanner in = new Scanner(System.in);

    public UI() {
        MakeConnection makeConnection = new MakeConnection();
        connection = makeConnection.getConnection();

        student = new Student(connection);
        lecturer = new Lecturer(connection);
    }

    public static void main(String[] args) {
        UI ui = new UI();

        ui.menu();

        try {
            ui.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Outputs a menu interface with selectable options
     */
    private void menu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("============================");
            System.out.println("|      University DB       |");
            System.out.println("============================");
            System.out.println("| Options:                 |");
            System.out.println("|    1. Register Student   |");
            System.out.println("|    2. Assign Tutor       |");
            System.out.println("|    3. Student Report     |");
            System.out.println("|    4. Tutees Report      |");
            System.out.println("|    5. Exit               |");
            System.out.println("============================");
            System.out.println("Please enter your option number (1-4)...");
            int option;
            while ((option = in.nextInt()) > 4) {
                System.out.println("Your selection must be between 1 and 4");
            }

            switch (option) {
                case 1:
                    registerStudent();
                    break;
                case 2:
                    assignTutor();
                    break;
                case 3:
                    studentReport();
                    break;
                case 4:
                    tuteesReport();
                    break;
                case 5:
                    exit = true;
            }
        }
    }

    /**
     * Prompts for a lecturer ID and then produces a report of tutees
     */
    private void tuteesReport() {
        System.out.println("Please enter a lecturer ID");
        int lecturerID;
        while ((lecturerID = in.nextInt()) < 0) {
            System.out.println("Please enter a lecturer ID greater than 0");
        }

        lecturer.tuteesReport(lecturerID);
    }

    /**
     * Prompts for a student and lecturer ID and then assigns that lecturer to the student as a tutor
     */
    private void assignTutor() {
        System.out.println("Please enter the ID of the student you wish to assign the tutor to");
        int studentID;
        while ((studentID = in.nextInt()) < 0) {
            System.out.println("Please enter a student ID greater than 0");
        }

        System.out.println("Please enter ID of the lecturer you are assigning");
        int lecturerID;
        while ((lecturerID = in.nextInt()) < 0) {
            System.out.println("Please enter a lecturer ID greater than 0");
        }

        student.assignTutor(studentID, lecturerID);
    }

    /**
     * Prompts for all information required for a new student and then enters it into the database
     */
    private void registerStudent() {
        System.out.println("Please enter a student ID");
        int studentID;
        while ((studentID = in.nextInt()) < 0) {
            System.out.println("Please enter a student ID greater than 0");
        }

        System.out.println("Please enter a title ID 1-5 (Professor, Dr, Mr, Miss, Mrs)");
        int titleID;
        while ((titleID = in.nextInt()) < 0 || titleID > 5) {
            System.out.println("Title ID must be 1-5 (Professor, Dr, Mr, Miss, Mrs)");
        }

        System.out.println("Please enter student's first name");
        String foreName;
        while ((foreName = in.next()).length() > 20) {
            System.out.println("First name must be 20 characters or less");
        }

        System.out.println("Please enter student's last name");
        String familyName;
        while ((familyName = in.next()).length() > 20) {
            System.out.println("Last name must be 20 characters or less");
        }

        System.out.println("Please enter student's date of birth (YYYY-MM-DD)");
        String dateOfBirth = in.next();

        student.newStudent(studentID, titleID, foreName, familyName, dateOfBirth);
    }

    /**
     * Prompts for a student ID and then prints a report of the student's information
     */
    private void studentReport() {
        System.out.println("Please enter a student ID");
        int studentID;
        while ((studentID = in.nextInt()) < 0) {
            System.out.println("Please enter a student ID greater than 0");
        }

        student.studentReport(studentID);
    }
}
