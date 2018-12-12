package ohtu.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ohtu.database.Database;

public class UserDbController {

    private Database database;
    
    /**
     * Creates a new UserDbController object. The UserDbController communicates with the given 
     * database to add and retrieve data in/from the database table User of the database.
     * 
     * @param database the database to be commnicates with
     */
    public UserDbController(Database database) {
        this.database = database;
    }
    
    /**
     * Retrieves the value of the column id from those rows in the database table User where 
     * the values of the colums username and password match the values given as parameters. 
     * If there are no matches the method returns -1.
     * 
     * @param username the username to be matched in the database
     * @param password the password to be matched in the database
     * @return id of the user that has the given username and password, or -1 if there are no matches
     * @throws SQLException 
     */
    public int checkCredentials(String username, String password) throws SQLException {
        int id = -1;
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM User WHERE username = ? AND password = ?");
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            id = rs.getInt("id");
        }

        database.close(stmt, conn, rs);
        return id;
    }
    
    /**
     * Creates a new row in the database table User with the given values as the values of the
     * corresponding columns username and password.
     * 
     * @param username the username of the new User
     * @param password th password of the new User
     * @return the id of the new User added to the database
     * @throws SQLException 
     */
    public int registerUser(String username, String password) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO User (username, password) VALUES (?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.executeUpdate();

        database.close(stmt, conn, null);
        return getUserId(username);
    }
    
    /**
     * Retrieves the value of the column id from the database from the row that has the 
     * username given as parameter as the value of the column username. 
     * 
     * @param username the username to matched in the database
     * @return the id of the user with the given username
     * @throws SQLException 
     */
    private int getUserId(String username) throws SQLException {
        int id = -1;
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM User WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            id = rs.getInt("id");
        }

        database.close(stmt, conn, rs);
        return id;
    }
    
    /**
     * Checks if the username given as parameter already exists in the database and is
     * thus taken.
     * 
     * @param username the username to be seached for from the database
     * @return true if the username is not taken, false if it is taken
     * @throws SQLException 
     */
    public boolean isUsernameAvailable(String username) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        boolean available = !rs.next();

        database.close(stmt, conn, rs);
        return available;
    }
    
    /**
     * Checks if the current users id matches the user_id of a bookmark. The id of the bookmark 
     * is given as parameter.
     * 
     * @param id the id of the bookmark to be checked
     * @return true if the user_id and current users id match, false otherwise
     * @throws SQLException 
     */
    public boolean userOwnsBookmarkWithId(int id) throws SQLException {
        boolean owns = false;
        int userId = UserController.getUserId();
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Bookmark WHERE id = ? AND user_id = ?");
        stmt.setInt(1, id);
        stmt.setInt(2, userId);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            owns = true;
        }
        
        database.close(stmt, conn, rs);
        return owns;
    }
}