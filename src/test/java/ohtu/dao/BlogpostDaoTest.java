package ohtu.dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import ohtu.database.Database;
import ohtu.domain.Blogpost;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

public class BlogpostDaoTest extends AbstractDaoTest {

    private static BlogpostDao blogpostDao;
    private static Blogpost b1, b2, b3;

    @BeforeClass
    public static void setUpClass() throws IOException {
        initialize();
    }

    @AfterClass
    public static void tearDownClass() {
        databaseFile.delete();
    }

    @Before
    public void setUp() throws SQLException, ParseException, IOException {

        // Can we delete the database after each test and re-create it? Currently the following doesn't work.
        final boolean delete = databaseFile.delete();

        // TODO: This temporary fixes the problem on Windows. But it's ineffective. And creates a lot of temp folders.
        setUpClass();

        database = new Database(databaseFile);
        blogpostDao = new BlogpostDao(database);
        b1 = new Blogpost(-1, "Data Mining", null, "navamani saravanan", "http://notescompsci.blogspot.com/2013/04/data-mining.html");
        b2 = new Blogpost(-1, "Data Mining For Beginners", null, "P.Fournier-Viger", "http://data-mining.philippe-fournier-viger.com/introduction-data-mining");
        b3 = new Blogpost(-1, "Java TWO marks", null, "navamani saravanan", "http://notescompsci.blogspot.com/2013/04/java-two-marks.html");

        blogpostDao.create(b1);
        blogpostDao.create(b2);
        blogpostDao.create(b3);
    }

    @After
    public void tearDown() {
        databaseFile.delete();
    }

    @Test
    public void canCreateNewBlogpost() throws SQLException, ParseException {
        Blogpost b4 = new Blogpost(-1, "How to learn computer science", null, "Karim", "https://www.afternerd.com/blog/learn-computer-science/");
        Blogpost added = blogpostDao.create(b4);
        assertEquals(b4, added);
    }

    @Test
    public void canListBlogpostsWithSimilarTitle() throws SQLException, ParseException {
        String keyword = "data mining";
        List<Blogpost> blogposts = blogpostDao.findByTitle(keyword);
        assertEquals(2, blogposts.size());
    }

    @Test
    public void canListBlogpostsWithSimilarUrl() throws SQLException, ParseException {
        String keyword = "notescompsci";
        List<Blogpost> blogposts = blogpostDao.findByURL(keyword);
        assertEquals(2, blogposts.size());
    }

    @Test
    public void canListAllBlogposts() throws SQLException, ParseException {
        List<Blogpost> allBlogposts = blogpostDao.findAll();
        assertEquals(3, allBlogposts.size());
    }

    @Test
    public void canFindBlogpostByExistedId() throws SQLException, ParseException {
        Blogpost found = blogpostDao.findById(1);
        assertEquals(1, found.getId());
    }

    @Test
    public void cannotFindBlogpostByNonexistedId() throws SQLException, ParseException {
        Blogpost found = blogpostDao.findById(100);
        assertNull(found);
    }

    @Test
    public void canDeleteBlogpostByExistedId() throws SQLException, ParseException {
        int toBeDeleted = 1;
        boolean success = blogpostDao.delete(toBeDeleted);
        assertTrue(success);
        assertNull(blogpostDao.findById(toBeDeleted));
    }

    @Test
    public void cannotDeleteBlogpostByNonexistedId() throws SQLException {
        int nonExisted = 100;
        boolean success = blogpostDao.delete(nonExisted);
        assertFalse(success);
    }

    @Test
    public void canUpdateExistedBlogpost() throws SQLException, ParseException {
        Blogpost toBeUpdated = blogpostDao.findById(1);
        String newTitle = "Data mining for beginners";
        toBeUpdated.setTitle(newTitle);
        boolean success = blogpostDao.update(toBeUpdated);
        assertTrue(success);
        assertEquals(newTitle, blogpostDao.findById(toBeUpdated.getId()).getTitle());
    }

    @Test
    public void cannotUpdateNonexistedBlogpost() throws SQLException {
        Blogpost nonExisted = new Blogpost(100, "Java Base64 URL Safe Encoding ", null, "Billy Yarosh", "https://keaplogik.blogspot.com/2016/01/java-base64-url-safe-encoding.html");
        nonExisted.setTitle("Safe Encoding for beginners");
        boolean success = blogpostDao.update(nonExisted);
        assertFalse(success);
    }

    @Test
    public void canFindAllBlogpostsOrderedByTitle() throws SQLException, ParseException {
        List<Blogpost> allBlogposts = blogpostDao.findAllOrderByTitle();
        assertEquals(b1.getTitle(), allBlogposts.get(0).getTitle());
        assertEquals(b2.getTitle(), allBlogposts.get(1).getTitle());
        assertEquals(b3.getTitle(), allBlogposts.get(2).getTitle());
    }

}
