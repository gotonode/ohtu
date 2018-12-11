/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.user;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import ohtu.dao.BlogpostDao;
import ohtu.database.Database;
import ohtu.domain.Blogpost;
import ohtu.tools.BookmarkBuilder;
import ohtu.tools.DaoBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;

public class UserDbControllerTest {

    private File databaseFile;
    private Database db;
    private UserDbController userDbController;
    private final String USERNAME = "user";
    private final String PASSWORD = "password";

    @Before
    public void setUp() throws IOException, SQLException {
        TemporaryFolder tempFolder = new TemporaryFolder();
        tempFolder.create();
        databaseFile = new File(tempFolder.getRoot() + "/test.db");
        db = new Database(databaseFile);
        userDbController = new UserDbController(db);
        userDbController.registerUser(USERNAME, PASSWORD);
    }

    @After
    public void tearDown() {
        databaseFile.delete();
    }

    @Test
    public void canRegisterUser() throws SQLException {
        String username = "newUser";
        String pwd = "newPassword";
        userDbController.registerUser(username, pwd);
        assertEquals(2, userDbController.checkCredentials(username, pwd));
    }

    @Test
    public void returnMinusOneWhenUsernameOrPasswordNotCorrect() throws SQLException {
        String wrongUsername = "wrongname";
        String wrongPassword = "wrongpassword";
        assertEquals(-1, userDbController.checkCredentials(wrongUsername, PASSWORD));
        assertEquals(-1, userDbController.checkCredentials(USERNAME, wrongPassword));
    }

    @Test
    public void returnUserIdWhenUsernameAndPasswordCorrect() throws SQLException {
        assertEquals(1, userDbController.checkCredentials(USERNAME, PASSWORD));
    }

    @Test
    public void acceptNonExistedUsername() throws SQLException {
        String newUsername = "nonExisted";
        assertTrue(userDbController.isUsernameAvailable(newUsername));
    }

    @Test
    public void rejectExistedUsername() throws SQLException {
        assertFalse(userDbController.isUsernameAvailable(USERNAME));
    }

    @Test
    public void canFigureOutWhetherUserOwnsBookmark() throws SQLException, ParseException {
        BlogpostDao blogpostDao = DaoBuilder.buildBlogpostDao(db);
        Blogpost blogpost = BookmarkBuilder.buildBlogpost(-1, "Data Mining", "navamani saravanan", "http://notescompsci.blogspot.com/2013/04/data-mining.html", null);
        blogpostDao.setUser_id(userDbController.checkCredentials(USERNAME, PASSWORD));
        Blogpost added = blogpostDao.create(blogpost);

        UserController.setUserId(1);
        assertTrue(userDbController.userOwnsBookmarkWithId(added.getId()));

        UserController.setUserId(2);
        assertFalse(userDbController.userOwnsBookmarkWithId(added.getId()));
    }

}
