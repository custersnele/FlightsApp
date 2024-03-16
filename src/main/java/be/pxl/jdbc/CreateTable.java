package be.pxl.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
	private static final Logger LOGGER = LogManager.getLogger(CreateTable.class);

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/moviedb", "user", "password");
            Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE employee (id INTEGER NOT NULL AUTO_INCREMENT, " +
		            "name TEXT, " +
		            "salary FLOAT, " +
		            "PRIMARY KEY (id))");
            LOGGER.info("Table 'employee' created.");
        } catch (SQLException e) {
            LOGGER.fatal("Something went wrong.", e);
        }
    }
}
