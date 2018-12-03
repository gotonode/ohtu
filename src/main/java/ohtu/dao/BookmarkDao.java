package ohtu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import ohtu.domain.Bookmark;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import ohtu.database.Database;

public class BookmarkDao {
    private final Database database;
    private final BlogpostDao blogpostDao;
    private final VideoDao videoDao;
    private final BookDao bookDao;

    public BookmarkDao(Database database, BlogpostDao blogpostDao, VideoDao videoDao, BookDao bookDao) {
        this.database = database;
        this.blogpostDao = blogpostDao;
        this.videoDao = videoDao;
        this.bookDao = bookDao;
    }
    
    /**
     * Retrieves the data of all the Bookmarks stored in the database and creates a
     * corresponding list of Bookmark objects.
     * 
     * @return list of Bookmark objects
     * @throws SQLException
     * @throws ParseException 
     */
    public List<Bookmark> findAll() throws SQLException, ParseException {
        List<Bookmark> bookmarks = new ArrayList<>();
        bookmarks.addAll(blogpostDao.findAll());
        bookmarks.addAll(videoDao.findAll());
        bookmarks.addAll(bookDao.findAll());
        
        bookmarks.sort(Comparator.comparing(b -> b.getId()));
        
        return bookmarks;
    }
    
    /**
     * Creates an alphabetically (by title) ordered list of all the Bookmark objects
     * stored in the database.
     * 
     * @return alphabetically (by title) ordered list of Bookmark objects
     * @throws SQLException
     * @throws ParseException 
     */
    public List<Bookmark> findAllOrderByTitle() throws SQLException, ParseException {
        List<Bookmark> bookmarks = new ArrayList<>();
        bookmarks.addAll(blogpostDao.findAllOrderByTitle());
        bookmarks.addAll(videoDao.findAllOrderByTitle());
        bookmarks.addAll(bookDao.findAllOrderByTitle());
        // Add here in similar manner the new types of Bookmarks (the method FindAllOrderByTitle)
        
        return bookmarks.stream().sorted((b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()))
                .collect(Collectors.toList());
    }
    
    /**
     * Searches the database for a Bookmark with the given id and returs it as a 
     * Bookmark object if one is found.
     * 
     * @param id the id of the wanted Bookmark
     * @return Bookmark object or null if no Bookmark is found.
     * @throws SQLException
     * @throws ParseException 
     */
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
                .prepareStatement("SELECT id,type FROM bookmark where title LiKE ?");
        String pattern="%"+title+"%";
        stmt.setString(1, pattern);
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
        if (rs.getString("type").equals("V")) {
            return videoDao.findById(rs.getInt("id"));
        }
        if (rs.getString("type").equals("K")) {
            return bookDao.findById(rs.getInt("id"));
        }
        return null;
    }
    
    /**
     * Closes the connection to database, PreparedStatement and ResultSet.
    */
    private void close(ResultSet rs, PreparedStatement stmt, Connection conn) throws SQLException {
        rs.close();
        stmt.close();
        conn.close();
    }

}
