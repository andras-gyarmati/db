package xyz.codingmentor.hellojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author brianelete
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException {
        final String URL = "jdbc:postgresql://localhost/course";
        final String USER = "postgres";
        final String PASSWORD = "postgres";
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        String selectSQL = "SELECT * FROM student WHERE student_name LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            statement.setString(1, "Ca%");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                System.out.println(result.getString("student_name"));
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }
    }
}
