
package ohtu.dao;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import ohtu.database.Database;
import ohtu.domain.Book;


public class BookDao implements ObjectDao<Book, Integer> {

    private final Database database;
    
    /**
    * Creates a new BookDao object. The new BookDao communicates with the given
    * database and adds, edits and removes the data concerning Book-objects stored
    * in the database. 
     * @param database the database to be communicated with
    */
    public BookDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Book> findAll() throws SQLException, ParseException {
        List<Book> books = new ArrayList<>();
        String s = "SELECT * FROM bookmark, book WHERE bookmark.id = book.id";
        
        try (Connection conn = database.getConnection(); ResultSet rs = conn.prepareStatement(s).executeQuery()) {
            while (rs.next()) {
                books.add(constructBookFromResultSet(rs));
            }
        }
        
        return books;
    }

    @Override
    public Book findById(Integer id) throws SQLException, ParseException {
        String s = "SELECT * FROM bookmark, book WHERE bookmark.id = ? AND bookmark.id = book.id";
        
        try (Connection conn = database.getConnection(); PreparedStatement stmt = conn.prepareStatement(s)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Book book = constructBookFromResultSet(rs);
                rs.close();
                return book;
            }
            
            rs.close();
        }
        return null;
    }

    @Override
    public List<Book> findByTitle(String title) throws SQLException, ParseException {
        List<Book> books = new ArrayList<>();
        String s = "SELECT * FROM bookmark, book WHERE bookmark.title LIKE ? AND bookmark.id = book.id";
        
        try (Connection conn = database.getConnection(); PreparedStatement stmt = conn.prepareStatement(s)) {
            stmt.setString(1, "%" + title + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Book book = constructBookFromResultSet(rs);
                books.add(book);
            }
            
            rs.close();
        }
        
        return books;
    }

    @Override
    public Book create(Book book) throws SQLException, ParseException {
        String s1 = "INSERT INTO bookmark (title, type) VALUES (?, 'K')";
        String s2 = "INSERT INTO book (id, author, isbn) VALUES (?, ?, ?)";
        
        try (Connection conn = database.getConnection(); PreparedStatement stmt1 = conn.prepareStatement(s1);
                PreparedStatement stmt2 = conn.prepareStatement(s2)) {
            stmt1.setString(1, book.getTitle());
            stmt1.execute();
            
            int id = getLatestId();
            if (id == -1) {
                return null; // Something went wrong when adding the new Bookmark.
            }
            
            stmt2.setInt(1, id);
            stmt2.setString(2, book.getAuthor());
            stmt2.setString(3, book.getIsbn());
            stmt2.execute();
            
            Book added = findById(id);
            if (added.equals(book)) {
                return added;
            }
        }
        
        return null;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String s1 = "DELETE FROM bookmark WHERE id = ?";
        String s2 = "DELETE FROM book WHERE id = ?";
        
        try (Connection conn = database.getConnection(); PreparedStatement stmt1 = conn.prepareStatement(s1);
                PreparedStatement stmt2 = conn.prepareStatement(s2)) {
            stmt1.setInt(1, id);
            stmt2.setInt(1, id);
            
            /* Book is deleted only if the Bookmark was successfully deleted */ 
            if (stmt1.executeUpdate() == 1) {
                return stmt2.executeUpdate() == 1;
            }
        }
        
        return false;
    }

    @Override
    public boolean update(Book book) throws SQLException {
        String s1 = "UPDATE bookmark SET title = ? WHERE id = ?";
        String s2 = "UPDATE book SET author = ?, isbn = ? WHERE id = ?";
        
        try (Connection conn = database.getConnection(); PreparedStatement stmt1 = conn.prepareStatement(s1);
                PreparedStatement stmt2 = conn.prepareStatement(s2)) {
            stmt1.setString(1, book.getTitle());
            stmt1.setInt(2, book.getId());
            stmt2.setString(1, book.getAuthor());
            stmt2.setString(2, book.getIsbn());
            stmt2.setInt(3, book.getId());
            
            /* Book is updated only if the Bookmark was successfully updated */ 
            if (stmt1.executeUpdate() == 1) {
                return stmt2.executeUpdate() == 1;
            }
        }
        
        return false;
    }
    
    /**
     * Retrieves all information of Book objects stored in the database and creates a list 
     * of corresponding Book objects ordered alphabetically by title.
     * 
     * @return alphabetically ordered (by title) list of Video objects.
     * @throws SQLException
     * @throws ParseException 
     */
    public List<Book> findAllOrderByTitle() throws SQLException, ParseException {
        String s = "SELECT * FROM bookmark, book WHERE bookmark.id = book.id ORDER BY title";
        List<Book> books = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
		ResultSet rs = conn.prepareStatement(s).executeQuery();
            while (rs.next()) {
                Book book = constructBookFromResultSet(rs);
                books.add(book);
            }
        }

        return books;
    }
    
    /**
     * Constructs a Book object from a ResultSet. The fields of the created Book object match
     * the corresponding data found in the ResultSet.
     * 
     * @param rs the ResultSet containing the needed information
     * @return A Book object constructed from the data in the given ResultSet
     * @throws SQLException
     * @throws ParseException 
     */
    private Book constructBookFromResultSet(ResultSet rs) throws SQLException, ParseException {
        Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("addDate")).getTime());
        Book book = new Book(rs.getInt("id"), rs.getString("title"), date, rs.getString("author"), 
                rs.getString("isbn"));
        
        return book;
    }
    
    /**
     * Returns the last id added to the database.
     * 
     * @return latest used id or -1 if unable to retrieve id.
     * @throws SQLException 
     */
    private int getLatestId() throws SQLException{
        String s = "SELECT MAX(id) FROM bookmark";
        
        try (Connection conn = database.getConnection(); ResultSet rs = conn.prepareStatement(s).executeQuery()) {
            if (rs.next()) {
                return rs.getInt("MAX(id)");
            }
        }
        
        return -1;
    } 
}
