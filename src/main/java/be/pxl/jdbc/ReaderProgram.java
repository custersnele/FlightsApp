package be.pxl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReaderProgram {

	static final String URL = "jdbc:mysql://localhost:3307/moviedb";
	static final String USER = "user";
	static final String PASS = "password";

	public static void main(String[] args) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			// Set the isolation level here, for example:
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

			try (Statement stmt = conn.createStatement()) {
				String sql = "SELECT * FROM employee WHERE id = " + args[0];
				ResultSet rs = stmt.executeQuery(sql);

				while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					double salary = rs.getDouble("salary");

					System.out.println("ID: " + id + ", Name: " + name + ", Salary: " + salary);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
