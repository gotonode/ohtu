package ohtu.database;

import ohtu.main.Main;
import ohtu.user.UserController;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Database {

    private String databaseAddress;

    /**
     * Creates a new Database-object with the given File, which points to the
     * actual SQLite database. If the given database file does not already exist
     * it is created with the intended database structure defined as a
     * SQL-script located in "/src/main/resources/sql/createstatements.sql".
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
        return "Database{"
                + "databaseAddress='" + databaseAddress + '\''
                + '}';
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
     * close statement,connection and resultset that were used in dao classes
     * @param stmt The Statement to be closed
     * @param conn The Connection to be closed
     * @param rs The ResultSet to be closed
     * @throws java.sql.SQLException
     */
    public void close(Statement stmt, Connection conn, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        stmt.close();
        conn.close();
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

    public int checkCredentials(String username, String password) {
    	return Integer.MAX_VALUE; // TODO: Fake user always logs in now.
    }

    public int registerUser(String username, String password) throws SQLException {
        // TODO: Remove this when real registration is completed
        createDefaultUser();
        
    	return Integer.MAX_VALUE; // TODO: Fake user always registers successfully.
    }
    
    public boolean isUsernameAvailable(String username) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
    	boolean available = !rs.next();
        
        close(stmt, conn, rs);
        return available;
    }

    public boolean userOwnsBookmarkWithId(int id) {

    	int userId = UserController.getUserId();

    	return true; // TODO: Either implement this here or somewhere else.
		// Only pass the ID to this function, as the user ID we already know.
    }
    
    // TODO: REMOVE THIS method later, when daos can handle user_id checking and such
    public boolean createDefaultUser() throws SQLException {
        boolean created = false;
        Connection conn = getConnection();
        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM User WHERE id = ?");
        stmt1.setInt(1, Integer.MAX_VALUE);
        ResultSet rs = stmt1.executeQuery();
        
        if (!rs.next()) {
            PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO User (id) VALUES ( ? )");
            stmt2.setInt(1, Integer.MAX_VALUE);
            created = stmt2.executeUpdate() == 1;
            stmt2.close();
        }
        
        close(stmt1, conn, rs);
        return created;
    }
}
