package ohtu.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import ohtu.database.Database;
import ohtu.domain.Book;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookDaoTest extends AbstractDaoTest {

	private static BookDao bookDao;
	private static Book k1, k2, k3;

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
		bookDao = new BookDao(database);
		k1 = new Book(-1, "Introduction to Algorithms", null, "Thomas H. Cormen", "9-780-262-0338-48");
		k2 = new Book(-1, "Introduction to Java Programming", null, "Y. Daniel Liang", "9-780-134-6709-42");
		k3 = new Book(-1, "Learning Python", null, "Mark Lutz", "9-781-593-2760-34");
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
		Book k4 = new Book(-1, "Python Crash Course", null, "Eric Matthes", "9-781-593-2760-34");
		Book added = bookDao.create(k4);
		assertEquals(k4, added);
	}

	@Test
	public void canListBooksWithSimilarTitle() throws SQLException, ParseException {
		String keyword = "introduction";
		List<Book> books = bookDao.findByTitle(keyword);
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
		Book nonExisted = new Book(100, "Java Base64 URL Safe Encoding ", null, "Billy Yarosh", "1-234-567-8910-11");
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

}
