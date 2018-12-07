package ohtu.user;

import ohtu.database.Database;
import ohtu.ui.UiController;
import ohtu.io.IO;

public class UserController {

	// Set to true once the user (real or test) has been logged in.
	private static boolean loggedIn = false;

	// ID -1 means no user is logged in.
	private static int userId = -1;

	private UiController uiController;
	private Database database;
	private IO io;

	public UserController(UiController uiController, Database database, IO io) {
		this.uiController = uiController;
		this.database = database;
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
		userId = Integer.MAX_VALUE;
	}
        
        /**
         * Only used in cucumber test, because once the user successfully registered,loggedIn will always be true since it's static.
         * If we want to test other scenario connected to register, we have to set loggedIn as false
         */
        public static void setLogInStatusAsFalse(){
            loggedIn=false;
        }

	public void login() {
		String username = uiController.askForString("Username:", false);
		String password = uiController.askForString("Password:", false);

		// Check that the username and associated password are a match.
		// Return the user's ID, or -1 if the details are invalid.
		int id = database.checkCredentials(username, password);

		if (id == -1) {
			io.println("Could not log in. Please check your username and password.");
		} else {
			io.println("You have been logged in! Your user ID is " + id + ".");
			userId = id;
			loggedIn = true;
		}
	}

	public void registerAndLogin() {
		io.println("Next you'll choose a unique (not taken) username and a password.");

		String username, password;

		while (true) {
			username = uiController.askForString("Username:", false);
			if (username.length() < 3 || username.length() > 10) {
				io.println("Your username must be between 3 and 10 characters.");
				continue;
			}

			// TODO: Check here if the username is taken.

			if (!database.isUsernameAvailable(username)) {
				io.println("That username is already taken. Please try a different one.");
				continue;
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
				continue;
			} else {
				break; // The passwords were a match.
			}
		}

		// At this point, we have a valid username and a valid password.

		// Return -1 if the registration fails, otherwise the user's ID.
		int newUserId = database.registerUser(username, password);

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
}
