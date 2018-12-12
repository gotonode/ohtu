package ohtu.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import ohtu.database.Database;
import ohtu.domain.Book;
import ohtu.tools.BookmarkBuilder;
import ohtu.tools.DaoBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookDaoTest extends AbstractDaoTest {

    private static BookDao bookDao;
    private static Book k1, k2, k3;
    private static final String KEY_TITLE = "introduction";

    @BeforeClass
    public static void setUpClass() throws IOException {
        initialize();
    }

    @AfterClass
    public static void tearDownClass() {
        databaseFile.delete();
    }

    @Before
    public void setUp() throws IOException, SQLException, ParseException {
        final boolean delete = databaseFile.delete();

        setUpClass();

        database = new Database(databaseFile);
        bookDao = DaoBuilder.buildBookDao(database);
        k1 = BookmarkBuilder.buildBook(-1, "Introduction to Algorithms", "Thomas H. Cormen", "9-780-262-0338-48", null);
        k2 = BookmarkBuilder.buildBook(-1, "Introduction to Java Programming", "Y. Daniel Liang", "9-780-134-6709-42", null);
        k3 = BookmarkBuilder.buildBook(-1, "Learning Python", "Mark Lutz", "9-781-593-2760-34", null);

        ObjectDaoTemplate.setUser_id(1);

        bookDao.create(k1);
        bookDao.create(k2);
        bookDao.create(k3);
    }

    @After
    public void tearDown() {
        databaseFile.delete();
    }

    @Test
    public void canCreateNewBook() throws SQLException, ParseException {
        Book k4 = BookmarkBuilder.buildBook(-1, "Python Crash Course", "Eric Matthes", "9-781-593-2760-34", null);
        Book added = bookDao.create(k4);
        assertEquals(k4, added);
    }

    @Test
    public void canListBooksWithSimilarTitle() throws SQLException, ParseException {
        List<Book> books = bookDao.findByTitle(KEY_TITLE);
        assertEquals(2, books.size());
    }

    @Test
    public void canListAllBooks() throws SQLException, ParseException {
        List<Book> allBooks = bookDao.findAll();
        assertEquals(3, allBooks.size());
    }

    @Test
    public void canFindBooksByExistedId() throws SQLException, ParseException {
        Book found = bookDao.findById(1);
        assertEquals(1, found.getId());
    }

    @Test
    public void cannotFindBookByNonexistedId() throws SQLException, ParseException {
        Book found = bookDao.findById(100);
        assertNull(found);
    }

    @Test
    public void canDeleteBookByExistedId() throws SQLException, ParseException {
        int toBeDeleted = 1;
        boolean success = bookDao.delete(toBeDeleted);
        assertTrue(success);
        assertNull(bookDao.findById(toBeDeleted));
    }

    @Test
    public void cannotDeleteBookByNonexistedId() throws SQLException {
        int nonExisted = 100;
        boolean success = bookDao.delete(nonExisted);
        assertFalse(success);
    }

    @Test
    public void canUpdateExistedBook() throws SQLException, ParseException {
        Book toBeUpdated = bookDao.findById(1);
        String newTitle = "The magic of algorithms";
        toBeUpdated.setTitle(newTitle);
        boolean success = bookDao.update(toBeUpdated);
        assertTrue(success);
        assertEquals(newTitle, bookDao.findById(toBeUpdated.getId()).getTitle());
    }

    @Test
    public void cannotUpdateNonexistedBook() throws SQLException {
        Book nonExisted = BookmarkBuilder.buildBook(100, "Java Base64 URL Safe Encoding ", "Billy Yarosh", "1-234-567-8910-11", null);
        nonExisted.setTitle("Safe Encoding for beginners");
        boolean success = bookDao.update(nonExisted);
        assertFalse(success);
    }

    @Test
    public void canFindAllBooksOrderedByTitle() throws SQLException, ParseException {
        List<Book> allBooks = bookDao.findAllOrderByTitle();
        assertEquals(k1.getTitle(), allBooks.get(0).getTitle());
        assertEquals(k2.getTitle(), allBooks.get(1).getTitle());
        assertEquals(k3.getTitle(), allBooks.get(2).getTitle());
    }

    @Test
    public void cannotListbookWhichBelongsToDifferentUser() throws SQLException, ParseException {
        ObjectDaoTemplate.setUser_id(3);
        assertEquals(0, bookDao.findAll().size());
        assertEquals(0, bookDao.findAllOrderByTitle().size());
    }

    @Test
    public void cannotFindByIdWhenBookBelongsToDifferentUSer() throws SQLException, ParseException {
        ObjectDaoTemplate.setUser_id(3);
        assertNull(bookDao.findById(1));
    }

    @Test
    public void cannotFindByTitleWhenBookBelongsToDifferentUSer() throws SQLException, ParseException {
        ObjectDaoTemplate.setUser_id(3);
        assertEquals(0, bookDao.findByTitle(KEY_TITLE).size());
    }

}
