package ohtu.dao;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ohtu.database.Database;
import ohtu.domain.Book;

public class BookDao extends ObjectDaoTemplate<Book> {

    public BookDao(Database database) {
        super(database);
    }

    @Override
    public Book create(Book book) throws SQLException, ParseException {
        Book added = null;
        Connection conn = database.getConnection(); 
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO bookmark (title, type, user_id) VALUES (?, 'K', ?)");
        stmt.setString(1, book.getTitle());
        stmt.setInt(2, user_id);
        stmt.execute();

        int id = getLatestId();
        if (id != -1) { // Something went wrong when adding the new Bookmark.
            added = createBook(book, id);
        }
        
        database.close(stmt, conn, null);
        return added;
    }
    
    private Book createBook(Book book, int id) throws SQLException, ParseException {
        Book added = null;
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO book (id, author, isbn) VALUES (?, ?, ?)");
        stmt.setInt(1, id);
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getIsbn());
        stmt.execute();

        Book newBook = findById(id);
        if (newBook.equals(book)) {
            added = newBook;
        }
        
        database.close(stmt, conn, null);
        return added;
    }

    @Override
    public boolean update(Book book) throws SQLException {
        boolean updated = false;
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE bookmark SET title = ? WHERE id = ?");
        stmt.setString(1, book.getTitle());
        stmt.setInt(2, book.getId());
        
        /* Book is updated only if the Bookmark was successfully updated */
        if (stmt.executeUpdate() == 1) {
            updated = updateBook(book);
        }
        
        database.close(stmt, conn, null);
        return updated;
    }
    
    private boolean updateBook(Book book) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE book SET author = ?, isbn = ? WHERE id = ?");
        stmt.setString(1, book.getAuthor());
        stmt.setString(2, book.getIsbn());
        stmt.setInt(3, book.getId());
        boolean updated = stmt.executeUpdate() == 1;
        
        database.close(stmt, conn, null);
        return updated;
    }

    @Override
    protected PreparedStatement createStmtWhenFindAll(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, book WHERE bookmark.id = book.id "
                + "AND bookmark.user_id = ?");
        stmt.setInt(1, user_id);
        return stmt;
    }

    @Override
    protected PreparedStatement createStmtWhenDeleteFromCertainTable(Connection conn) throws SQLException {
        return conn.prepareStatement("DELETE FROM book WHERE id = ?");
    }

    @Override
    protected PreparedStatement createStmtWhenFindById(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, book WHERE bookmark.id = ? And book.id=bookmark.id "
                + "AND bookmark.user_id = ?");
        stmt.setInt(2, user_id);
        return stmt;
    }

    @Override
    protected PreparedStatement createStmtWhenFindByTitle(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, book WHERE bookmark.title "
                + "LIKE ? AND bookmark.id = book.id AND bookmark.user_id = ?");
        stmt.setInt(2, user_id);
        return stmt;
    }

    @Override
    protected PreparedStatement createStmtWhenFindByUrl(Connection conn) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement createStmtWhenFindAllOrderByTitle(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, book WHERE bookmark.id = book.id "
                + "AND bookmark.user_id = ? ORDER BY title");
        stmt.setInt(1, user_id);
        return stmt;
    }

    @Override
    protected Book constructBookmarkFromResultSet(ResultSet rs) throws SQLException, ParseException {
        Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("addDate")).getTime());
        Book book = new Book(rs.getInt("id"), rs.getString("title"), date, rs.getString("author"),
                rs.getString("isbn"));

        return book;
    }
    
}
