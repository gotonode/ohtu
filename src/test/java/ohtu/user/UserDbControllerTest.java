/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.user;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ohtu.database.Database;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author luoyumo
 */
public class UserDbControllerTest {

    private File databaseFile;
    private Database db;
    private UserDbController userDbController;
    private final String username = "user";
    private final String password = "password";

    public UserDbControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException, SQLException {
        TemporaryFolder tempFolder = new TemporaryFolder();
        tempFolder.create();
        databaseFile = new File(tempFolder.getRoot() + "/test.db");
        db = new Database(databaseFile);
        userDbController = new UserDbController(db);
        userDbController.registerUser(username, password);
    }

    @After
    public void tearDown() {
        databaseFile.delete();
    }

    @Test
    public void canRegisterUser() throws SQLException {
        assertEquals(1, userDbController.checkCredentials(username, password));
    }

    @Test
    public void returnMinusOneWhenUsernameOrPasswordNotCorrect() throws SQLException {
        String wrongUsername = "wrongname";
        String wrongPassword = "wrongpassword";
        assertEquals(-1, userDbController.checkCredentials(wrongUsername, password));
        assertEquals(-1, userDbController.checkCredentials(username, wrongPassword));
    }

    @Test
    public void returnUserIdWhenUsernameAndPasswordCorrect() throws SQLException {
        assertEquals(1, userDbController.checkCredentials(username, password));
    }

    @Test
    public void acceptNonExistedUsername() throws SQLException {
        String newUsername = "nonExisted";
        assertTrue(userDbController.isUsernameAvailable(newUsername));
    }

    @Test
    public void rejectExistedUsername() throws SQLException {
        assertFalse(userDbController.isUsernameAvailable(username));
    }
    
    //TODO: test userOwnsBookmarkWithId-method

}
