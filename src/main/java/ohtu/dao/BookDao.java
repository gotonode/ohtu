package ohtu.dao;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ohtu.database.Database;
import ohtu.domain.Book;

public class BookDao extends ObjectDaoTemplate<Book> {
    private int user_id;

    public BookDao(Database database) {
        super(database);
    }

    @Override
    public Book create(Book book) throws SQLException, ParseException {
        String s1 = "INSERT INTO bookmark (title, type, user_id) VALUES (?, 'K', ?)";
        String s2 = "INSERT INTO book (id, author, isbn) VALUES (?, ?, ?)";

        try (Connection conn = database.getConnection(); PreparedStatement stmt1 = conn.prepareStatement(s1);
                PreparedStatement stmt2 = conn.prepareStatement(s2)) {
            stmt1.setString(1, book.getTitle());
            stmt1.setInt(2, user_id);
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
    
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
