package ohtu.database;

import ohtu.main.Main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Database {

	private String databaseAddress;

	/**
	 * Creates a new Database-object with the given File, which points to the actual SQLite database.
	 * If the given database file does not already exist it is created with the intended database structure
	 * defined as a SQL-script located in "/src/main/resources/sql/createstatements.sql".
	 *
	 * @param file Where the SQLite database file is located.
	 */
	public Database(File file) {
		this.databaseAddress = ("jdbc:sqlite:").concat(file.getAbsolutePath());

		if (!file.exists()) {
			initiateDatabaseTables();
		}
	}

	@Override
	public String toString() {
		return "Database{" +
				"databaseAddress='" + databaseAddress + '\'' +
				'}';
	}

	/**
	 * Get connection to database.
	 *
	 * @return Connection to database.
	 * @throws SQLException In case the connection cannot be retrieved.
	 */
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(databaseAddress);
	}

	/**
	 * Creates the database structure for a new database from a SQL-script file.
	 * Reading the script is performed by SQLReader class.
	 */
	private void initiateDatabaseTables() {
		SQLReader reader = new SQLReader("/sql/createstatements.sql");
		List<String> statements = reader.readSQLStatements();

		try {
			executeSQLStatements(statements);
		} catch (SQLException e) {
			Main.LOG.warning(e.getMessage());
		}

	}

	/**
	 * Executes the SQL-statements given to it in the form of a list.
	 *
	 * @param lines the list containing the SQL-statements to be executed
	 */
	private void executeSQLStatements(List<String> lines) throws SQLException {

		try (Connection connection = getConnection()) {

			Statement statement = connection.createStatement();
			connection.setAutoCommit(false);

			StringBuilder stringBuilder = new StringBuilder();

			for (String line : lines) {
				if (line.equals(("")) || line.startsWith("/*") || line.startsWith("//")) {
					continue;
				}
				stringBuilder.append(line);

				if (line.endsWith(";")) {
					statement.addBatch(stringBuilder.toString());
					stringBuilder = new StringBuilder();
				}
			}

			statement.executeBatch();
			connection.commit();

			statement.close();
		}
	}
}
