package ohtu.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;

public class Database {

	private File databaseFile;
	private String databaseAddress;

	/**
	 * Creates a new Database-object with the given File, which points to the actual SQLite ohtu.database.
	 * If the given database file does not already exist it is created with the intended database structure
	 * defined as a sql-script in /src/main/resources/sql/createstatements.sql
	 *
	 * @param file Where the SQLite ohtu.database file is located.
	 */
	public Database(File file) {
		this.databaseFile = file;
		this.databaseAddress = ("jdbc:sqlite:").concat(file.getAbsolutePath());

		if (!this.databaseFile.exists()) {
			initiateDatabaseTables();
		}
	}

	/**
	 * Get connection to database.
	 *
	 * @return Connection to database.
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(databaseAddress);
	}

	/**
	 * Creates the database structure for a new database from a SQL-script file.
	 */
	private void initiateDatabaseTables() {
		try {
			runSQLScript(System.getProperty("user.dir") + "/src/main/resources/sql/createstatements.sql");
		} catch (SQLException e) {
			// Later switch from printing in console to printing in log?
			System.out.println("Error:" + e.toString());
		}
	}

	/**
	 * Reads and executes the SQL-statements in the file that is found through the
	 * given filepath.
	 *
	 * @param filepath the location of the .sql file containing the required SQL-statements
	 * @throws SQLException
	 */
	private void runSQLScript(String filepath) throws SQLException {
		try (Connection conn = getConnection(); Statement s = conn.createStatement()) {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));

			String executable = "";
			String nextline;

			while ((nextline = reader.readLine()) != null) {
				String line = nextline.trim();

				if (line.equals(("")) || line.startsWith("//") || line.startsWith("/*")) {
					continue;
				}

				executable += line + " ";

				if (line.endsWith(";")) {
					s.executeUpdate(executable);
					executable = "";
				}
			}
		} catch (Exception e) {
			// Later switch from printing in console to printing in log?
			System.out.println("Error: " + e.toString());
		}
	}
}
