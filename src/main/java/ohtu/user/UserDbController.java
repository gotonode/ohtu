package ohtu.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ohtu.database.Database;

public class UserDbController {

    private Database database;

    public UserDbController(Database database) {
        this.database = database;
    }

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

    public int registerUser(String username, String password) throws SQLException {
        // TODO: Remove this line when real user_id of logged user is used in Daos.
        createDefaultUser();

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO User (username, password) VALUES (?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.executeUpdate();

        database.close(stmt, conn, null);
        return getUserId(username);
        // TODO: Fake user always registers successfully. ?
    }

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

    public boolean isUsernameAvailable(String username) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        boolean available = !rs.next();

        database.close(stmt, conn, rs);
        return available;
    }

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

    // TODO: REMOVE THIS method later, when daos can handle user_id checking and such
    public boolean createDefaultUser() throws SQLException {
        boolean created = false;
        Connection conn = database.getConnection();
        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM User WHERE id = 0");
        ResultSet rs = stmt1.executeQuery();

        if (!rs.next()) {
            PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO User (id) VALUES (0)");
            created = stmt2.executeUpdate() == 1;
            stmt2.close();
        }

        database.close(stmt1, conn, rs);
        return created;
    }
    
}
