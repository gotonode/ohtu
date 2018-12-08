package ohtu.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import ohtu.database.Database;
import ohtu.domain.Blogpost;
import ohtu.domain.Book;
import ohtu.domain.Bookmark;
import ohtu.domain.Video;
import ohtu.tools.BookmarkBuilder;
import ohtu.tools.DaoBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookmarkDaoTest extends AbstractDaoTest {

    private static BlogpostDao blogpostDao;
    private static BookmarkDao bookmarkDao;
    private static VideoDao videoDao;
    private static BookDao bookDao;
    private static Blogpost b1;
    private static Video v1, v2;
    private static Book k1;

    @BeforeClass
    public static void setUpClass() throws SQLException, ParseException, IOException {
        initialize();
        database = new Database(databaseFile);
        BookmarkDaoTest.blogpostDao = DaoBuilder.buildBlogpostDao(database);
        BookmarkDaoTest.videoDao = DaoBuilder.buildVideoDao(database);
        BookmarkDaoTest.bookDao = DaoBuilder.buildBookDao(database);
        BookmarkDaoTest.bookmarkDao = DaoBuilder.buildBookmarkDao(database, blogpostDao, videoDao, bookDao);
        BookmarkDaoTest.b1 = BookmarkBuilder.buildBlogpost(-1, "Data Mining", "navamani saravanan", "http://notescompsci.blogspot.com/2013/04/data-mining.html", null);
        BookmarkDaoTest.v1 = BookmarkBuilder.buildVideo(-1, "Map of Computer Science", "https://www.youtube.com/watch?v=SzJ46YA_RaA", null);
        BookmarkDaoTest.v2 = BookmarkBuilder.buildVideo(-1, "Big Data Analytics", "https://www.youtube.com/watch?v=LtScY2guZpo", null);
        BookmarkDaoTest.k1 = BookmarkBuilder.buildBook(-1, "Introdiction to Algorithms", "Thomas H. Cormen", "9-780-262-0338-48", null);
        blogpostDao.create(b1);
        videoDao.create(v1);
        videoDao.create(v2);
        bookDao.create(k1);
    }

    @AfterClass
    public static void tearDownClass() {
        databaseFile.delete();
    }

    @Test
    public void canListAllBookmarks() throws SQLException, ParseException {
        List<Bookmark> bookmarks = bookmarkDao.findAll();
        assertEquals(4, bookmarks.size());
    }

    @Test
    public void canFindBookmarkByExistedId() throws SQLException, ParseException {
        Bookmark found = bookmarkDao.findById(1);
        assertTrue(found.isBlogpost());
        assertEquals(1, found.getId());
    }

    @Test
    public void canFindBookmarkByNonexistedId() throws SQLException, ParseException {
        Bookmark found = bookmarkDao.findById(100);
        assertNull(found);
    }

    @Test
    public void canListBookmarkWithSimilarTitle() throws SQLException, ParseException {
        String keyword = "data";
        List<Bookmark> bookmarks = bookmarkDao.findByTitle(keyword);
        assertEquals(2, bookmarks.size());
    }

    @Test
    public void canListBookmarkWithSimilarUrl() throws SQLException, ParseException {
        String keyword = "youtube";
        List<Bookmark> bookmarks = bookmarkDao.findByURL(keyword);
        assertEquals(2, bookmarks.size());
    }

    @Test
    public void canFindAllBookmarksOrderByTitle() throws SQLException, ParseException {
        List<Bookmark> allBookmarks = bookmarkDao.findAllOrderByTitle();
        assertEquals(v2.getTitle(), allBookmarks.get(0).getTitle());
        assertEquals(b1.getTitle(), allBookmarks.get(1).getTitle());
        assertEquals(k1.getTitle(), allBookmarks.get(2).getTitle());
        assertEquals(v1.getTitle(), allBookmarks.get(3).getTitle());
    }
}
