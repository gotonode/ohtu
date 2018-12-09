package ohtu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ohtu.database.Database;
import ohtu.domain.Blogpost;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A DAO object for the Blogposts. This class talks with the database and
 * creates (or updates/deletes) Blogpost objects.
 */
public class BlogpostDao extends ObjectDaoTemplate<Blogpost> {

    public BlogpostDao(Database database) {
        super(database);
    }

    @Override
    public Blogpost create(Blogpost blogpost) throws SQLException, ParseException {
        Connection conn = database.getConnection();
        PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO bookmark (title, type, user_id) VALUES (?, 'B', ?)");
        stmt1.setString(1, blogpost.getTitle());
        stmt1.setInt(2, Integer.MAX_VALUE); // this will be removed soon and replaced with proper user_id
        stmt1.execute();

        int id = getLatestId();
        if (id == -1) {
            database.close(stmt1, conn, null);
            return null; // Something went wrong when adding the new Bookmark.
        }

        PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO blogpost (id, author,url) VALUES (?, ?,?)");
        stmt2.setInt(1, id);
        stmt2.setString(2, blogpost.getAuthor());
        stmt2.setString(3, blogpost.getUrl());
        stmt2.execute();

        Blogpost added = findById(id);
        if (added.equals(blogpost)) {
            return added;
        }

        return null;
    }

    @Override
    public boolean update(Blogpost blogpost) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt1 = conn.prepareStatement("UPDATE bookmark SET title = ? WHERE id = ?");
        PreparedStatement stmt2 = conn.prepareStatement("UPDATE blogpost SET author=?, url = ? WHERE id = ?");
        stmt1.setString(1, blogpost.getTitle());
        stmt1.setInt(2, blogpost.getId());
        stmt2.setString(1, blogpost.getAuthor());
        stmt2.setString(2, blogpost.getUrl());
        stmt2.setInt(3, blogpost.getId());

        int updated = 0;
        if (stmt1.executeUpdate() == 1) {
            /* Blogposy is updated only if the Bookmark was successfully updated */
            updated = stmt2.executeUpdate();
            stmt2.close();
        }

        database.close(stmt1, conn, null);
        return updated == 1;
    }

    @Override
    protected PreparedStatement createStmtWhenFindAll(Connection conn) throws SQLException {
        return conn.prepareStatement("SELECT * FROM bookmark, blogpost WHERE bookmark.id = blogpost.id");
    }

    @Override
    protected PreparedStatement createStmtWhenDeleteFromCertainTable(Connection conn) throws SQLException {
        return conn.prepareStatement("DELETE FROM blogpost WHERE id = ?");
    }

    @Override
    protected PreparedStatement createStmtWhenFindById(Connection conn) throws SQLException {
        return conn.prepareStatement("SELECT * FROM bookmark, blogpost WHERE bookmark.id = ? And blogpost.id=bookmark.id");
    }

    @Override
    protected PreparedStatement createStmtWhenFindByTitle(Connection conn) throws SQLException {
        return conn.prepareStatement("SELECT * FROM bookmark, blogpost WHERE bookmark.title "
                + "LIKE ? AND bookmark.id = blogpost.id");
    }

    @Override
    protected PreparedStatement createStmtWhenFindByUrl(Connection conn) throws SQLException {
        return conn.prepareStatement("SELECT * FROM bookmark, blogpost WHERE blogpost.url LIKE ? "
                + "AND bookmark.id = blogpost.id");
    }

    @Override
    protected PreparedStatement createStmtWhenFindAllOrderByTitle(Connection conn) throws SQLException {
        return conn.prepareStatement("SELECT * FROM bookmark, blogpost WHERE bookmark.id = blogpost.id ORDER BY title");
    }

    @Override
    protected Blogpost constructBookmarkFromResultSet(ResultSet rs) throws SQLException, ParseException {
        String dateString = rs.getString("addDate");
        Date dateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        java.sql.Date date = new java.sql.Date(dateUtil.getTime());
        Blogpost blogpost = new Blogpost(rs.getInt("id"), rs.getString("title"), date, rs.getString("author"),
                rs.getString("url"));
        return blogpost;
    }

}
