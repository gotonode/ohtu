/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.dao;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import ohtu.database.Database;
import ohtu.domain.Blogpost;
import ohtu.domain.Bookmark;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author luoyumo
 */
public class BookmarkDaoTest {

    static Database database;
    static File databaseFile;
    static BlogpostDao blogpostDao;
    static BookmarkDao bookmarkDao;
    static Blogpost b1;

    public BookmarkDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws SQLException, ParseException {
        BookmarkDaoTest.databaseFile = new File(System.getProperty("user.dir") + "/test1.db");
        if (databaseFile.exists()) {
            databaseFile.delete();
        }
        BookmarkDaoTest.database = new Database(databaseFile);
        BookmarkDaoTest.blogpostDao = new BlogpostDao(database);
        BookmarkDaoTest.bookmarkDao = new BookmarkDao(database, blogpostDao);
        BookmarkDaoTest.b1 = new Blogpost(-1, "computer science", null, "author1", "url1");
        blogpostDao.create(b1);

    }

    @AfterClass
    public static void tearDownClass() {
        databaseFile.delete();
    }

    @Before
    public void setUp() throws SQLException, ParseException {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void canListAllBookmarks() throws SQLException, ParseException {

        List<Bookmark> bookmarks = bookmarkDao.findAll();
        assertEquals(bookmarks.size(), 1);
    }

    @Test
    public void canFindBookmarkByExistedId() throws SQLException, ParseException {
        Bookmark found = bookmarkDao.findById(1);
        assertTrue(found.isBlogpost());
        assertEquals(found.getId(), 1);
    }

    @Test
    public void canFindBookmarkByNonexistedId() throws SQLException, ParseException {
        Bookmark found = bookmarkDao.findById(100);
        assertNull(found);
    }

    @Test
    public void canFindBookmarkWithSamaTitle() throws SQLException, ParseException {
        List<Bookmark> bookmarks = bookmarkDao.findByTitle(b1.getTitle());
        assertEquals(bookmarks.size(), 1);
    }
}
