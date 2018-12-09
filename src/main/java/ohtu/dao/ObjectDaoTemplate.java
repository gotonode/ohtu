package ohtu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import ohtu.database.Database;

public abstract class ObjectDaoTemplate<Bookmark> {

    protected final Database database;

    ObjectDaoTemplate(Database database) {
        this.database = database;
    }

    /**
     * Returns all of the Bookmarks, or Bookmarks of a specific type.
     *
     * @return A list containing the results.
     * @throws SQLException Database failure.
     * @throws ParseException Parse failure.
     */
    public List<Bookmark> findAll() throws SQLException, ParseException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = createStmtWhenFindAll(conn);
        List<Bookmark> bookmarks = createAndHandleResultSet(stmt);
        database.close(stmt, conn, null);
        return bookmarks;
    }

    /**
     * Finds a Bookmark by its unique ID.
     *
     * @param id The ID, an integer that is 1 and above.
     * @return The Bookmark with the given ID.
     * @throws SQLException Database failure.
     * @throws ParseException Parse failure.
     */
    public Bookmark findById(Integer id) throws SQLException, ParseException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = createStmtWhenFindById(conn);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Bookmark bookmark = constructBookmarkFromResultSet(rs);
            database.close(stmt, conn, rs);
            return bookmark;
        }

        database.close(stmt, conn, rs);
        return null;

    }

    /**
     * Finds a Bookmark with the given title.
     *
     * @param title The title to look for.
     * @return The Bookmark with the title.
     * @throws SQLException Database failure.
     * @throws ParseException Parse failure.
     */
    public List<Bookmark> findByTitle(String title) throws SQLException, ParseException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = createStmtWhenFindByTitle(conn);
        stmt.setString(1, "%" + title + "%");
        List<Bookmark> bookmarks = createAndHandleResultSet(stmt);
        database.close(stmt, conn, null);
        return bookmarks;
    }

    public List<Bookmark> findByURL(String url) throws SQLException, ParseException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = createStmtWhenFindByUrl(conn);
        stmt.setString(1, "%" + url + "%");

        List<Bookmark> bookmarks = createAndHandleResultSet(stmt);

        database.close(stmt, conn, null);

        return bookmarks;
    }

    public List<Bookmark> findAllOrderByTitle() throws SQLException, ParseException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = createStmtWhenFindAllOrderByTitle(conn);

        List<Bookmark> bookmarks = createAndHandleResultSet(stmt);

        database.close(stmt, conn, null);

        return bookmarks;
    }

    /**
     * Deletes the bookmark with given id
     *
     * @param id.The ID, an integer that is 1 or above
     * @return true if the bookmark was successfully deleted, otherwise false
     * @throws SQLException Database failure
     */
    public boolean delete(Integer id) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM bookmark WHERE id = ?");
        PreparedStatement stmt2 = createStmtWhenDeleteFromCertainTable(conn);
        stmt1.setInt(1, id);
        stmt2.setInt(1, id);

        int updated = 0;
        if (stmt1.executeUpdate() == 1) {
            /* Certain type is deleted only if the Bookmark was successfully deleted */
            updated = stmt2.executeUpdate();
            stmt2.close();
        }

        database.close(stmt1, conn, null);
        return updated == 1;
    }

    protected List<Bookmark> createAndHandleResultSet(PreparedStatement stmt) throws SQLException, ParseException {
        List<Bookmark> bookmarks = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Bookmark bookmark = constructBookmarkFromResultSet(rs);
            bookmarks.add(bookmark);
        }

        rs.close();
        return bookmarks;
    }

    /**
     * Returns the last id added to the database.
     *
     * @return latest used id or -1 if unable to retrieve id.
     * @throws SQLException
     */
    protected int getLatestId() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) FROM bookmark");
        ResultSet rs = stmt.executeQuery();

        int latest = -1;

        if (rs.next()) {
            latest = rs.getInt("MAX(id)");
        }

        database.close(stmt, conn, rs);
        return latest;
    }

    /**
     * Adds the Bookmark into the database. Ignores the Bookmark's ID and
     * addDate, those are assigned by the database. When creating a new Bookmark
     * from user input (not from database), assign -1 as the ID and null as
     * addDate.
     *
     * @param bookmark The Bookmark in question. Cannot pass Bookmark here
     * directly.
     * @return bookmark with id and addDate which were assigned by the database
     * if creation succeeded, otherwise null
     * @throws SQLException Database failure.
     * @throws ParseException Parse failure.
     */
    public abstract Bookmark create(Bookmark bookmark) throws SQLException, ParseException;

    /**
     * updates the information of a bookmark which were save in the database
     *
     * @param bookmark The bookmark with new information(title,author .etc)
     * @return true if the bookmark was successfully updated, otherwise false
     * @throws SQLException Database failure
     */
    public abstract boolean update(Bookmark bookmark) throws SQLException;

    /**
     * creates PreparedStatment which will be used in findAll-method
     *
     * @throws SQLException
     */
    protected abstract PreparedStatement createStmtWhenFindAll(Connection conn) throws SQLException;

    protected abstract PreparedStatement createStmtWhenDeleteFromCertainTable(Connection conn) throws SQLException;

    protected abstract PreparedStatement createStmtWhenFindById(Connection conn) throws SQLException;

    protected abstract PreparedStatement createStmtWhenFindByTitle(Connection conn) throws SQLException;

    protected abstract PreparedStatement createStmtWhenFindByUrl(Connection conn) throws SQLException;

    protected abstract PreparedStatement createStmtWhenFindAllOrderByTitle(Connection conn) throws SQLException;

    /**
     * Constructs a certain type of bookmark object from a ResultSet. The fields
     * of the created bookmark object match the corresponding data found in the
     * ResultSet.
     *
     * @param rs the ResultSet containing the needed information
     * @return A certain type of bookmark object constructed from the data in
     * the given ResultSet
     * @throws SQLException
     * @throws ParseException
     */
    protected abstract Bookmark constructBookmarkFromResultSet(ResultSet rs) throws SQLException, ParseException;

}
