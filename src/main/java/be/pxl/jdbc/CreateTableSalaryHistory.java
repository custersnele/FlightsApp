package be.pxl.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableSalaryHistory {
	private static final Logger LOGGER = LogManager.getLogger(CreateTableSalaryHistory.class);

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/moviedb", "user", "password");
            Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE salary_history (employee_id INTEGER, " +
		            "salary_raise FLOAT, " +
		            "date DATE, " +
		            "FOREIGN KEY (employee_id) REFERENCES employee(id))");
            LOGGER.info("Table 'salary_history' created.");
        } catch (SQLException e) {
            LOGGER.fatal("Something went wrong.", e);
        }
    }
}
