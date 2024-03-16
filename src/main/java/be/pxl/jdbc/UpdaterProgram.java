package be.pxl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdaterProgram {

	static final String DB_URL = "jdbc:mysql://localhost:3307/moviedb";
	static final String USER = "user";
	static final String PASS = "password";

	public static void main(String[] args) {

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
			conn.setAutoCommit(false);

			try (Statement stmt = conn.createStatement()) {
				String sqlUpdate = "UPDATE employee SET salary = salary + 1000 WHERE id = " + args[0];
				stmt.executeUpdate(sqlUpdate);
				System.out.println("Data updated but not committed yet. Sleeping for 10 seconds...");
				// Sleep to allow time for the ReaderProgram to run and attempt to read the uncommitted data.
				Thread.sleep(10000);

				if ("commit".equals(args[1])) {
					conn.commit();
					System.out.println("Commit");
				} else {
					conn.rollback();
					System.out.println("Rollback");
				}
			}
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
