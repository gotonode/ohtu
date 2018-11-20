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
import static org.junit.Assert.*;

public class BlogpostDaoTest {

    Database database;

    File databaseFile;
    BlogpostDao blogpostDao;
    Blogpost b1, b2, b3;

    public BlogpostDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {

    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() throws SQLException, ParseException {

        databaseFile = new File(System.getProperty("user.dir") + "/test.db");
        if (databaseFile.exists()) {
            databaseFile.delete();
        }
        database = new Database(databaseFile);
        blogpostDao = new BlogpostDao(database);
        b1 = new Blogpost(-1, "title", null, "author1", "url1");
        b2 = new Blogpost(-1, "title", null, "author2", "url2");
        b3 = new Blogpost(-1, "titlec", null, "author3", "url3");
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
        Blogpost b4 = new Blogpost(-1, "titled", null, "author4", "url4");
        Blogpost added = blogpostDao.create(b4);
        assertEquals(b4, added);
    }

    @Test
    public void canListBlogpostsWithSameTitle() throws SQLException, ParseException {

        List<Blogpost> blogposts = blogpostDao.findByTitle(b2.getTitle());
        assertEquals(blogposts.size(), 2);

    }

    @Test
    public void canListAllBlogposts() throws SQLException, ParseException {
        List<Blogpost> allBlogposts = blogpostDao.findAll();
        assertEquals(allBlogposts.size(), 3);
    }

    @Test
    public void canFindBlogpostByExistedId() throws SQLException, ParseException {
        Blogpost found = blogpostDao.findById(1);
        assertEquals(found.getId(), 1);
    }

    @Test
    public void cannotFindBlogpostByNonexistedId() throws SQLException, ParseException {
        Blogpost found = blogpostDao.findById(100);
        assertEquals(found, null);
    }

}
