package be.pxl.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryRaise {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final Logger LOGGER = LogManager.getLogger(EmployeeCRUD.class);
	private static final String URL = "jdbc:mysql://localhost:3307/moviedb";
	private static final String USERNAME = "user";
	private static final String PASSWORD = "password";

	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			// Turn off auto-commit to manage transactions manually
			connection.setAutoCommit(false);
			try {
				Statement statement = connection.createStatement();
				statement.executeUpdate("UPDATE employee SET salary = salary * 1.1 WHERE id = 2");
				statement.executeUpdate("INSERT INTO salary_history (employee_id, salary_raise, date) VALUES (2, 10, '" + FORMAT.format(LocalDate.now()) + "')");
				// If both updates succeed, commit the transaction
				connection.commit();
				System.out.println("Salary raise successful!");
			} catch (SQLException e) {
				// If any error occurs, rollback the transaction (undo changes)
				connection.rollback();
				System.out.println("Salary raise failed: " + e.getMessage());
			}
		} catch (SQLException e) {
			LOGGER.error("A database error occurred. " + e.getMessage());
		}

	}
}
