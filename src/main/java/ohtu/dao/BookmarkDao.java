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

public class BookmarkDao {

    // If possible, this class could return all results, when we don't want to get e.g. just the blog posts.
    private final Database database;
    private BlogpostDao blogpostDao;
    private VideoDao videoDao;

    public BookmarkDao(Database database, BlogpostDao blogpostDao) {
        this.database = database;
        this.blogpostDao = blogpostDao;
    }

    public List<Bookmark> findAll() throws SQLException, ParseException {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn
                .prepareStatement("SELECT id,type FROM bookmark");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            bookmarks.add(findCertainBookmarkByType(rs));
        }
        close(rs,stmt,conn);
        return bookmarks;
    }
    
    public List<Bookmark> findAllOrderByTitle() throws SQLException, ParseException {
        List<Bookmark> bookmarks = new ArrayList<>();
        Connection conn = database.getConnection();

        bookmarks.addAll(blogpostDao.findAllOrderByTitle());
        // Add here in similar manner the new types of Bookmarks (the method FindAllOrderByTitle)
        
        bookmarks.stream().sorted((b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()));
        return bookmarks;
    }
 
    public Bookmark findById(Integer id) throws SQLException, ParseException {
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn
                .prepareStatement("SELECT id,type FROM bookmark where id=?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Bookmark found = findCertainBookmarkByType(rs);
            close(rs,stmt,conn);
            return found;
        }
        close(rs,stmt,conn);
        return null;
    }

   
    public List<Bookmark> findByTitle(String title) throws SQLException, ParseException {
        List<Bookmark>bookmarks=new ArrayList<>();
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn
                .prepareStatement("SELECT id,type FROM bookmark where title=?");
        stmt.setString(1, title);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Bookmark found = findCertainBookmarkByType(rs);
            bookmarks.add(found);
        }
        close(rs,stmt,conn);
        return bookmarks;
    }

    private Bookmark findCertainBookmarkByType(ResultSet rs) throws SQLException, ParseException {
        if (rs.getString("type").equals("B")) {
            return blogpostDao.findById(rs.getInt("id"));
        }

        // other types will be added later
        return null;
    }
    
    //method to close connection to database,PreparesStatement and ResultSet
    private void close(ResultSet rs, PreparedStatement stmt, Connection conn) throws SQLException {
        rs.close();
        stmt.close();
        conn.close();
    }

}
