package be.pxl.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ConnectionPoolExample {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolExample.class);

    private static final String URL = "jdbc:mysql://localhost:3307/moviedb";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    public static Optional<Employee> getEmployee(Connection connection, int id) throws SQLException {
        String query = "SELECT * FROM employee WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int retrievedId = resultSet.getInt("id");
            String retrievedName = resultSet.getString("name");
            double retrievedSalary = resultSet.getDouble("salary");
            Employee employee = new Employee(retrievedId, retrievedName);
            employee.setSalary(retrievedSalary);
            return Optional.of(employee);
        } else {
            LOGGER.info("Employee with ID " + id + " not found.");
            return Optional.empty();
        }
    }

    public static void main(String[] args) throws SQLException {
        DataSource dataSource = createDataSource();

        try (Connection connection = dataSource.getConnection()) {
            getEmployee(connection, 2).ifPresent(System.out::println);
        }
    }

    private static DataSource createDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(URL);
        ds.setUsername(USERNAME);
        ds.setPassword(PASSWORD);
        return ds;
    }
}
