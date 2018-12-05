package ohtu.main;

import ohtu.actions.*;
import ohtu.dao.BlogpostDao;
import ohtu.dao.BookDao;
import ohtu.dao.BookmarkDao;
import ohtu.dao.VideoDao;
import ohtu.database.Database;
import ohtu.enums.Commands;
import ohtu.io.IO;
import ohtu.ui.UiController;
import ohtu.user.UserController;

import java.util.Arrays;

public class App {

	private UiController uiController;

	private final ExitAction exit;
	private final ListAction browse;
	private final DeleteAction delete;
	private final AddAction add;
	private final ModifyAction modify;
	private final HelpAction help;

	private boolean hasPrintedInitialInstructions = false;
	private IO io;
	private Database database;
	private final boolean requireLogin;

	public App(IO io, Database db, boolean requireLogin) {
		this.io = io;
		database = db;
		this.requireLogin = requireLogin;

		BlogpostDao blogpostDao = new BlogpostDao(db);
		VideoDao videoDao = new VideoDao(db);
		BookDao bookDao = new BookDao(db);

		BookmarkDao bookmarkDao = new BookmarkDao(db, blogpostDao, videoDao, bookDao);

		uiController = new UiController(io); // Either ConsoleIO or StubIO.

		exit = new ExitAction(io, uiController);
		delete = new DeleteAction(io, uiController, bookmarkDao, blogpostDao, videoDao, bookDao);
		browse = new ListAction(io, uiController, bookmarkDao);
		add = new AddAction(io, uiController, blogpostDao, videoDao, bookDao);
		modify = new ModifyAction(io, uiController, bookmarkDao, blogpostDao, videoDao, bookDao);
		help = new HelpAction(io, uiController);
	}

	public void run() {

		boolean appRunning = true;

		uiController.printGreeting();

		outer:
		while (appRunning) {

			// Only if this has been set, we'll require the user to log in.
			if (requireLogin) {

				inner:
				while (!UserController.isLoggedIn()) {
					// Loop this until the user has logged in or exited the app.
					// Will never enter this section again. I just wanted to include this in the main logic loop.

					uiController.printEmptyLine();
					uiController.printLoginInstructions();

					char code = uiController.askForCharacter(new char[]{'L', 'R', 'E'}, "Your choice");

					if (code == 'E') {
						appRunning = false;
						exit.act();
						break outer;
					}

					UserController userController = new UserController(uiController, database, io);

					if (code == 'L') {
						// Login functionality.
						userController.login();
					} else if (code == 'R') {
						// Registration and immediate login.
						userController.registerAndLogin();
					}

					// Once the user is logged in, you can use "UserController.getUserId()" to get their ID.
					// Notice the uppercase 'U'. It's a static method.
					int id = UserController.getUserId(); // Like this.
				}

			} else {
				// This will log in the user with ID of Integer.MAX_VALUE.
				// Use that user for tests.
				UserController.autoLoginDefaultUser();
			}

			if (!hasPrintedInitialInstructions) {
				// We'll only print these once, at the beginning. User can manually print them again.
				uiController.printInstructions();
				hasPrintedInitialInstructions = true;
			}

			char character = uiController.askForCharacter(new char[]{'A', 'L', 'E', 'D', 'M', 'X', 'S'}, "Choose a command ('X' lists them)");

			Commands command = Arrays.stream(Commands.values()).filter(a -> a.getCommand() == character).findFirst().get();

			switch (command) {

				case SEARCH:
					browse.search();
					break;

				case LIST:
					browse.act();
					break;

				case ADD:
					add.act();
					break;

				case DELETE:
					delete.act();
					break;

				case MODIFY:
					modify.act();
					break;

				case HELP:
					help.act();
					break;

				case EXIT:
					appRunning = false;
					exit.act();
					break;

				default:
					throw new IllegalArgumentException("This method was called with an illegal argument.");
			}
		}

		// The main loop has exited, so the program will terminate.
	}
}
