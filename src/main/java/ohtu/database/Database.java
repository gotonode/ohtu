package ohtu.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private File databaseFile;
	private String databaseAddress;

	/**
	 * Creates a new Database-object with the given File, which points to the actual SQLite ohtu.database.
	 * @param file Where the SQLite ohtu.database file is located.
	 */
	public Database(File file) {
		this.databaseFile = file;
		this.databaseAddress = ("jdbc:sqlite:").concat(file.getAbsolutePath());
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(databaseAddress);
	}
}
