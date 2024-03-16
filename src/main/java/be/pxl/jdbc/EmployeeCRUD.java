package be.pxl.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EmployeeCRUD {

	private static final Logger LOGGER = LogManager.getLogger(EmployeeCRUD.class);

	private static final String url = "jdbc:mysql://localhost:3307/moviedb";
	private static final String username = "user";
	private static final String password = "password";

	public static void createEmployee(Connection connection, String name, float salary) throws SQLException {
		String query = "INSERT INTO employee (name, salary) VALUES (?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, name);
		preparedStatement.setFloat(2, salary);
		preparedStatement.executeUpdate();
		LOGGER.info("Employee '" + name + "' created.");
	}

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

	public static void updateEmployee(Connection connection, int id, String name, float salary) throws SQLException {
		String query = "UPDATE employee SET name = ?, salary = ? WHERE id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, name);
		preparedStatement.setFloat(2, salary);
		preparedStatement.setInt(3, id);
		int rowsUpdated = preparedStatement.executeUpdate();
		if (rowsUpdated > 0) {
			LOGGER.info("Employee with ID " + id + " updated.");
		} else {
			LOGGER.info("Employee with ID " + id + " not found (update failed).");
		}
	}

	public static void deleteEmployee(Connection connection, int id) throws SQLException {
		String query = "DELETE FROM employee WHERE id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		int rowsDeleted = preparedStatement.executeUpdate();
		if (rowsDeleted > 0) {
			LOGGER.info("Employee with ID " + id + " deleted.");
		} else {
			LOGGER.info("Employee with ID " + id + " not found (delete failed).");
		}
	}

	public static int countEmployees(Connection connection) throws SQLException {
		String query = "SELECT COUNT(*) FROM employee";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet result = preparedStatement.executeQuery();
		if (result.next()) {
			return result.getInt(1);
		}
		return 0;
	}

	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			createEmployee(connection, "John Doe", 75000.0f);
			createEmployee(connection, "Jane Smith", 82000.5f);

			getEmployee(connection, 1).ifPresent(System.out::println);
			updateEmployee(connection, 2, "Jane Doe", 85000.0f);
			getEmployee(connection, 2).ifPresent(System.out::println);
			System.out.println("Number of employees: " + countEmployees(connection));
			deleteEmployee(connection, 1);
			System.out.println("Number of employees: " + countEmployees(connection));

		} catch (SQLException e) {
			System.err.println("An error occurred. " + e.getMessage());
			e.printStackTrace();
		}
	}
}

