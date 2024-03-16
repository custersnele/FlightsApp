package be.pxl.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlInjection {

	private static final Logger LOGGER = LogManager.getLogger(SqlInjection.class);

	public static void main(String[] args) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/moviedb", "user", "password")) {
			Statement statement = conn.createStatement();
			String movieName = "A CLOCKWORK ORANGE";
			String movieName2 = "A CLOCKWORK ORANGE' UNION SELECT *, '1', '1' FROM DIRECTOR where '1=1";
			String query = "SELECT * FROM MOVIES where MOV_TITLE = '" + movieName2 + "'";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			final ResultSetMetaData meta = resultSet.getMetaData();
			final int columnCount = meta.getColumnCount();
			System.out.println("Column count: " + columnCount);
			while (resultSet.next()) {
				for (int column = 1; column <= columnCount; column++) {
					Object value = resultSet.getObject(column);
					System.out.print(value + " / ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			LOGGER.fatal("Something went wrong.", e);
		}
	}
}
