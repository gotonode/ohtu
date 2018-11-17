package ohtu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import ohtu.domain.Bookmark;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import ohtu.database.Database;

public class BookmarkDao implements ObjectDao<Bookmark, Integer> {

    // If possible, this class could return all results, when we don't want to get e.g. just the blog posts.
    private final Database database;
    private BlogpostDao blogpostDao;

    public BookmarkDao(Database database, BlogpostDao blogpostDao) {
        this.database = database;
        this.blogpostDao = blogpostDao;
    }

    @Override
    public List<Bookmark> findAll() throws SQLException, ParseException {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn
                .prepareStatement("SELECT id,type FROM bookmark");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            bookmarks.add(findCertainBookmarkByType(rs));
        }
        rs.close();
        stmt.close();
        conn.close();
        return bookmarks;
    }

    @Override
    public Bookmark findById(Integer id) throws SQLException, ParseException {
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn
                .prepareStatement("SELECT id,type FROM bookmark where id=?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Bookmark found = findCertainBookmarkByType(rs);
            rs.close();
            stmt.close();
            conn.close();
            return found;
        }
        return null;
    }

    @Override
    public Bookmark findByTitle(String title) throws SQLException, ParseException {
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn
                .prepareStatement("SELECT id,type FROM bookmark where title=?");
        stmt.setString(1, title);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Bookmark found = findCertainBookmarkByType(rs);
            rs.close();
            stmt.close();
            conn.close();
            return found;
        }
        return null;
    }

    @Override
    public boolean create(Bookmark bookmark) throws SQLException, ParseException {
        return false;
    }

    private Bookmark findCertainBookmarkByType(ResultSet rs) throws SQLException, ParseException {
        if (rs.getString("type").equals("B")) {
            return blogpostDao.findById(rs.getInt("id"));
        }

        // other types will be added later
        return null;
    }

}
