import java.sql.*;

public class Report {

    private final Connection connection;

    public Report(Connection connection) {
        this.connection = connection;
    }

    /**
     * Print a prefix and then results of a query
     * @param prefix Prefix to output
     * @param query Query to fetch data
     */
    public void printDetail(String prefix, String query) {
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            System.out.print(prefix);
            printResultSet(rs);

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print a result set
     * @param rs Result set to print
     */
    public void printResultSet(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnsNumber = metaData.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(" ");
                    System.out.print(rs.getString(i));
                }
            }
            System.out.println();

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
