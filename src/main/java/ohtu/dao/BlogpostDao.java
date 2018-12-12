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
        Blogpost added = null;
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO bookmark (title, type, user_id) VALUES (?, 'B', ?)");
        stmt.setString(1, blogpost.getTitle());
        stmt.setInt(2, user_id);
        stmt.execute();

        int id = getLatestId();
        if (id != -1) { // Something went wrong when adding the new Bookmark.
            added = createBlogpost(blogpost, id);
        }
        
        database.close(stmt, conn, null);
        return added;
    }
    
    private Blogpost createBlogpost(Blogpost blogpost, int id) throws SQLException, ParseException {
        Blogpost added = null;
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO blogpost (id, author, url) VALUES (?, ?, ?)");
        stmt.setInt(1, id);
        stmt.setString(2, blogpost.getAuthor());
        stmt.setString(3, blogpost.getUrl());
        stmt.execute();
        
        Blogpost newBlogpost = findById(id);
        if (newBlogpost.equals(blogpost)) {
            added = newBlogpost;
        }
        
        database.close(stmt, conn, null);
        return added;
    }

    @Override
    public boolean update(Blogpost blogpost) throws SQLException {
        boolean updated = false;
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE bookmark SET title = ? WHERE id = ?");
        stmt.setString(1, blogpost.getTitle());
        stmt.setInt(2, blogpost.getId());

        if (stmt.executeUpdate() == 1) { /* Blogpost is updated only if the Bookmark was successfully updated */
            updated = updateBlogpost(blogpost);
        }

        database.close(stmt, conn, null);
        return updated;
    }
    
    private boolean updateBlogpost(Blogpost blogpost) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE blogpost SET author=?, url = ? WHERE id = ?");
        stmt.setString(1, blogpost.getAuthor());
        stmt.setString(2, blogpost.getUrl());
        stmt.setInt(3, blogpost.getId());
        
        boolean updated = stmt.executeUpdate() == 1;
        
        database.close(stmt, conn, null);
        return updated;
    }

    @Override
    protected PreparedStatement createStmtWhenFindAll(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, blogpost WHERE bookmark.id = blogpost.id "
                + "AND bookmark.user_id = ?");
        stmt.setInt(1, user_id);
        return stmt;
    }

    @Override
    protected PreparedStatement createStmtWhenDeleteFromCertainTable(Connection conn) throws SQLException {
        return conn.prepareStatement("DELETE FROM blogpost WHERE id = ?");
    }

    @Override
    protected PreparedStatement createStmtWhenFindById(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, blogpost WHERE bookmark.id = ? And blogpost.id=bookmark.id "
                + "AND bookmark.user_id = ?");
        stmt.setInt(2, user_id);
        return stmt;
    }

    @Override
    protected PreparedStatement createStmtWhenFindByTitle(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, blogpost WHERE bookmark.title "
                + "LIKE ? AND bookmark.id = blogpost.id AND bookmark.user_id = ?");
        stmt.setInt(2, user_id);
        return stmt;
    }

    @Override
    protected PreparedStatement createStmtWhenFindByUrl(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, blogpost WHERE blogpost.url LIKE ? "
                + "AND bookmark.id = blogpost.id AND bookmark.user_id = ?");
        stmt.setInt(2, user_id);
        return stmt;
    }

    @Override
    protected PreparedStatement createStmtWhenFindAllOrderByTitle(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, blogpost WHERE bookmark.id = blogpost.id "
                + "AND bookmark.user_id = ? ORDER BY title");
        stmt.setInt(1, user_id);
        return stmt;
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
