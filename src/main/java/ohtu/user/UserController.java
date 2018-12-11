package ohtu.user;

import java.sql.SQLException;
import ohtu.tools.HashGenerator;
import ohtu.ui.UiController;
import ohtu.io.IO;

public class UserController {

    // Set to true once the user (real or test) has been logged in.
    private static boolean loggedIn = false;

    // ID -1 means no user is logged in.
    private static int userId = -1;

    private UiController uiController;
    private UserDbController userDbController;
    private IO io;

    public UserController(UiController uiController, UserDbController userDbController, IO io) {
        this.uiController = uiController;
        this.userDbController = userDbController;
        this.io = io;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static int getUserId() {
        return userId;
    }

    public static void autoLoginDefaultUser() {
        loggedIn = true;
        userId = 0; // ID 0 is for the testing user. ID's 1 and above are for real users. ID -1 means no user or a failure.
    }

    public void login() throws SQLException {
        String username = uiController.askForString("Username:", false);
        String password = uiController.askForString("Password:", false);

        String hashedPassword = HashGenerator.getHashedString(password);

        // Check that the username and associated password are a match.
        // Return the user's ID, or -1 if the details are invalid.
        int id = userDbController.checkCredentials(username, hashedPassword);

        if (id == -1) {
            io.println("Could not log in. Please check your username and password.");
        } else {
            io.println("You have been logged in! Your user ID is " + id + ".");
            userId = id;
            loggedIn = true;
        }
    }

    public void registerAndLogin() throws SQLException {
        io.println("Next you'll choose a unique (not taken) username and a password.");

        String username, password;

        while (true) {
            username = uiController.askForString("Username:", false);
            if (username.length() < 3 || username.length() > 10) {
                io.println("Your username must be between 3 and 10 characters.");
                continue;
            }

            if (!userDbController.isUsernameAvailable(username)) {
                io.println("That username is already taken. Please try a different one.");
            } else {
                break; // We got everything.
            }
        }

        while (true) {
            password = uiController.askForString("Password:", false);
            if (password.length() < 6 || password.length() > 32) {
                io.println("Your password must be between 6 and 32 characters.");
                continue;
            }

            String passwordAgain = uiController.askForString("Password (again):", false);
            if (!password.equals(passwordAgain)) {
                io.println("The passwords didn't match. Please try again.");
            } else {
                break; // The passwords were a match.
            }
        }

        // At this point, we have a valid username and a valid password.
        String hashedPassword = HashGenerator.getHashedString(password);

        // Return -1 if the registration fails, otherwise the user's ID.
        int newUserId = userDbController.registerUser(username, hashedPassword);

        // I'll hash the password here later. For now, you can use plaintext.
        if (newUserId != -1) {
            io.println("Your new account was created and you have been logged in! Your user ID is " + newUserId + ".");
            loggedIn = true;
            userId = newUserId;
            // New user was created and logged in.

        } else {
            io.println("Could not create your account. Please try again later.");
            // User has not been logged in.
        }

    }

    /**
     * Only used in UserDbControllerTest
     *
     * @param id the testing userId
     */
    public static void setUserId(int id) {
        userId = id;
    }

    /**
     * Only used in cucumber test, because once the user successfully
     * registered,loggedIn will always be true since it's static. If we want to
     * test other scenario connected to register, we have to set loggedIn as
     * false
     */
    public static void setLogInStatusAsFalse() {
        loggedIn = false;
    }

}
