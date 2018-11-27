package ohtu.database;

import ohtu.main.Main;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class Database {

	private String databaseAddress;

    /**
     * Creates a new Database-object with the given File, which points to the actual SQLite ohtu.database.
     * If the given database file does not already exist it is created with the intended database structure
     * defined as a sql-script located in /src/main/resources/sql/createstatements.sql
     *
     * @param file Where the SQLite ohtu.database file is located.
     */
    public Database(File file) throws IOException {
        this.databaseAddress = ("jdbc:sqlite:").concat(file.getAbsolutePath());

        if (!file.exists()) {
            initiateDatabaseTables();
        }
    }

	@Override
	public String toString() {
    	// This is here to ease debugging.
		return "Database{" +
				"databaseAddress='" + databaseAddress + '\'' +
				'}';
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
     * Reading the scriptfile is performed by SQLReader class.
     */
    private void initiateDatabaseTables() throws IOException {
        SQLReader reader =  new SQLReader(System.getProperty("user.dir") + "/src/main/resources/sql/createstatements.sql");
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
     * @param statements the list containing the SQL-statements to be executed
     * @throws SQLException
     */
    private void executeSQLStatements(List<String> statements) throws SQLException {
        try (Connection conn = getConnection(); Statement s = conn.createStatement()) {

            for (String statement : statements) {
                    s.executeUpdate(statement);
            }

        } catch (Exception e) {
            Main.LOG.warning(e.getMessage());
        }
    }
}
