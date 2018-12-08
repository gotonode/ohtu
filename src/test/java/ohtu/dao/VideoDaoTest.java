package ohtu.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import ohtu.database.Database;
import ohtu.domain.Video;
import ohtu.tools.BookmarkBuilder;
import ohtu.tools.DaoBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class VideoDaoTest extends AbstractDaoTest {

    private static VideoDao videoDao;
    private static Video v1, v2, v3;

    public VideoDaoTest() {
    }

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
        databaseFile.delete();
        setUpClass();
        database = new Database(databaseFile);
        videoDao = DaoBuilder.buildVideoDao(database);
        v1 = BookmarkBuilder.buildVideo(-1, "Map of computer science", "https://www.youtube.com/watch?v=SzJ46YA_RaA", null);
        v2 = BookmarkBuilder.buildVideo(-1, "Computer science for intermediate learner", "https://www.youtube.com/watch?v=ohyai6GIRZg", null);
        v3 = BookmarkBuilder.buildVideo(-1, "Java programming", "https://www.youtube.com/watch?v=WPvGqX-TXP0", null);
        videoDao.create(v1);
        videoDao.create(v2);
        videoDao.create(v3);
    }

    @After
    public void tearDown() {
        databaseFile.delete();
    }

    @Test
    public void canCreateNewVideo() throws SQLException, ParseException {
        Video v4 = BookmarkBuilder.buildVideo(-1, "C programming", "https://www.youtube.com/watch?v=KJgsSFOSQv0", null);
        Video added = videoDao.create(v4);
        assertEquals(v4, added);
    }

    @Test
    public void canListVideosWithSimilarTitle() throws SQLException, ParseException {
        String keyword = "computer science";
        List<Video> videos = videoDao.findByTitle(keyword);
        assertEquals(2, videos.size());
    }

    @Test
    public void canListVideosWithSimilarUrl() throws SQLException, ParseException {
        String keyword = "youtube";
        List<Video> videos = videoDao.findByURL(keyword);
        assertEquals(3, videos.size());
    }

    @Test
    public void canListAllVideos() throws SQLException, ParseException {
        List<Video> allVideos = videoDao.findAll();
        assertEquals(3, allVideos.size());
    }

    @Test
    public void canFindVideoByExistedId() throws SQLException, ParseException {
        Video found = videoDao.findById(1);
        assertEquals(1, found.getId());
    }

    @Test
    public void cannotFindVideoByNonexistedId() throws SQLException, ParseException {
        Video found = videoDao.findById(100);
        assertNull(found);
    }

    @Test
    public void canDeleteVideoByExistedId() throws SQLException, ParseException {
        int toBeDeleted = 1;
        boolean success = videoDao.delete(toBeDeleted);
        assertTrue(success);
        assertNull(videoDao.findById(toBeDeleted));
    }

    @Test
    public void cannotDeleteVideoByNonexistedId() throws SQLException {
        int nonExisted = 100;
        boolean success = videoDao.delete(nonExisted);
        assertFalse(success);
    }

    @Test
    public void canUpdateExistedVideo() throws SQLException, ParseException {
        Video toBeUpdated = videoDao.findById(1);
        String newTitle = "Oritentation to computer science";
        toBeUpdated.setTitle(newTitle);
        boolean success = videoDao.update(toBeUpdated);
        assertTrue(success);
        assertEquals(newTitle, videoDao.findById(toBeUpdated.getId()).getTitle());
    }

    @Test
    public void cannotUpdateNonexistedVideo() throws SQLException {
        Video nonExisted = BookmarkBuilder.buildVideo(100, "Ruby programming", "https://www.youtube.com/watch?v=t_ispmWmdjY", null);
        nonExisted.setTitle("Ruby tutorial");
        boolean success = videoDao.update(nonExisted);
        assertFalse(success);
    }

    @Test
    public void canFindAllVideosOrderedByTitle() throws SQLException, ParseException {
        List<Video> allVideos = videoDao.findAllOrderByTitle();
        assertEquals(v2.getTitle(), allVideos.get(0).getTitle());
        assertEquals(v3.getTitle(), allVideos.get(1).getTitle());
        assertEquals(v1.getTitle(), allVideos.get(2).getTitle());
    }

}
