/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import ohtu.database.Database;
import ohtu.domain.Blogpost;
import ohtu.domain.Bookmark;
import ohtu.domain.Video;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

/**
 *
 * @author luoyumo
 */
public class BookmarkDaoTest {

    private static TemporaryFolder tempFolder;

    private static Database database;
    private static File databaseFile;
    private static BlogpostDao blogpostDao;
    private static BookmarkDao bookmarkDao;
    private static VideoDao videoDao;
    private static Blogpost b1;
    private static Video v1;

    @BeforeClass
    public static void setUpClass() throws SQLException, ParseException, IOException {

        tempFolder = new TemporaryFolder();
        tempFolder.create();

        // Assign a test database into the newly created temporary folder.
        databaseFile = new File(tempFolder.getRoot() + "/test.db");

        if (databaseFile.exists()) {
            databaseFile.delete();
        }
        BookmarkDaoTest.database = new Database(databaseFile);
        BookmarkDaoTest.blogpostDao = new BlogpostDao(database);
        BookmarkDaoTest.videoDao = new VideoDao(database);
        BookmarkDaoTest.bookmarkDao = new BookmarkDao(database, blogpostDao, videoDao);
        BookmarkDaoTest.b1 = new Blogpost(-1, "computer science", null, "author1", "url1");
        BookmarkDaoTest.v1 = new Video(-1, "algorithms", null, "url2");
        blogpostDao.create(b1);
        videoDao.create(v1);

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
        assertEquals(2, bookmarks.size());
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
    public void canFindBookmarkWithSamaTitle() throws SQLException, ParseException {
        List<Bookmark> bookmarks = bookmarkDao.findByTitle(b1.getTitle());
        assertEquals(1, bookmarks.size());
    }
    
    @Test
    public void canFindAllBookmarksOrderByTitle() throws SQLException, ParseException{
        List<Bookmark>allBookmarks=bookmarkDao.findAllOrderByTitle();
        assertEquals(v1.getTitle(),allBookmarks.get(0).getTitle());
        assertEquals(b1.getTitle(),allBookmarks.get(1).getTitle());
    }
}
