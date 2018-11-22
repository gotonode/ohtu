package ohtu.main;

import ohtu.dao.BlogpostDao;
import ohtu.dao.BookmarkDao;
import ohtu.database.Database;
import ohtu.domain.Blogpost;
import ohtu.domain.Bookmark;
import ohtu.enums.Commands;
import ohtu.io.IO;
import ohtu.tools.Builder;
import ohtu.ui.UiController;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class App {

	private boolean appRunning;
	private BlogpostDao blogpostDao;
	private BookmarkDao bookmarkDao;
	private IO io;
	private Database database;
	private UiController uiController;

	public App(IO io, Database db) {
		this.io = io;
		this.database = db;
		blogpostDao = new BlogpostDao(database);
		bookmarkDao = new BookmarkDao(database, blogpostDao);
	}

	public void run() {

		appRunning = true;

		uiController = new UiController(io); // Either ConsoleIO or StubIO.

		uiController.printGreeting();
		uiController.printInstructions();

		while (appRunning) {

			char character = uiController.askForCharacter(new char[]{'A', 'L', 'E', 'D', 'M', 'X'});

			// I believe there might be a better way to achieve this. Feel free to improve!
			Commands command = Arrays.stream(Commands.values()).filter(a -> a.getCommand() == character).findFirst().get();

			switch (command) {

				case LIST:
					listAll();
					break;

				case ADD:
					addBlogpost();
					break;

				case DELETE:
					deleteBookmark();
					break;

				case MODIFY:
					modifyBookmark();
					break;

				case HELP:
					uiController.printInstructions();
					break;

				case EXIT:
					appRunning = false;
					break;

				default:
					throw new IllegalArgumentException("This method was called with an illegal argument.");
			}
		}

		// The main loop has exited, so the program will terminate.
		uiController.printGoodbye();
	}

	private void modifyBookmark() {
		int id = uiController.askForIdToModify();
		Bookmark bookmark;
		try {
			bookmark = bookmarkDao.findById(id);
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
			return;
		}

		io.println("Enter new values. Leave blank to use current value.");

		io.println("Current title: " + bookmark.getTitle());
		String newTitle = uiController.askForString("New title: ", true);
		if (!newTitle.isEmpty()) {
			bookmark.setTitle(newTitle);
		}

		boolean success = false;

		if (bookmark instanceof Blogpost) {
			try {
				success = blogpostDao.update((Blogpost)bookmark);
			} catch (SQLException e) {
				Main.LOG.warning(e.getMessage());
				return;
			}
		}

		if (success) {
			io.println("Successfully updated your bookmark.");
		} else {
			io.println("Couldn't update your bookmark. Please try again.");
		}
	}

	private void deleteBookmark() {

		try {
			if (bookmarkDao.findAll().isEmpty()) {
				uiController.printNoBookmarks();
				return;
			}

		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
			return;
		}

		int id = uiController.askForIdToRemove();
		Bookmark bookmark;
		try {
			bookmark = bookmarkDao.findById(id);
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
			return;
		}

		uiController.printDeleteConfirmation(bookmark.getId(), bookmark.getTitle(), bookmark.getClass().getSimpleName());

		char c = uiController.askForCharacter(new char[]{'Y', 'N'});

		if (c == 'Y') {
			if (bookmark instanceof Blogpost) {
				try {
					blogpostDao.delete(id);
					uiController.printDeleteSuccessful(id);
				} catch (SQLException e) {
					Main.LOG.warning(e.getMessage());
					return;
				}
			}
		} else {
			uiController.printAbortDelete();
		}
	}

	private void listAll() {

		io.println("Listing all bookmarks...");
		uiController.printEmptyLine(); // Tidy.

		try {
			if (bookmarkDao.findAll().isEmpty()) {
				uiController.printNoBookmarks();
				return;
			}
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
		}

		try {
			List<Bookmark> bookmarks = bookmarkDao.findAll();
			for (Bookmark bookmark : bookmarks) {
				if (bookmark instanceof Blogpost) {
					Blogpost blogpost = (Blogpost) bookmark;

					// Create an ArrayList of Strings that contains the printable data IN ORDER.
					ArrayList<String> printableData = new ArrayList<>();
					printableData.add(blogpost.getClass().getSimpleName());
					printableData.add(blogpost.getAddDate().toString());
					printableData.add(blogpost.getTitle());
					printableData.add(blogpost.getAuthor());
					printableData.add(blogpost.getUrl());
					printableData.add(String.valueOf(blogpost.getId()));

					// Ask the controller to print the Bookmark's data to console.
					uiController.printBlogpost(printableData);
				}
			}
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
		}

	}

	/**
	 * Asks the user for title, author and url, and creates a new Blogpost by using Builder.
	 * Then tries to add the new Blogpost into the database. Tells the user if the operation succeeded or not.
	 */
	private void addBlogpost() {

		String[] values = uiController.askBlogpostData();
		String title = values[0]; // Care should be taken so as not to mix these indices up.
		String author = values[1];
		String url = values[2];

		// Database will handle assigning correct ID to the object, and decides the addDate value.
		Date date = null; // This is here just as a reference that it is null.
		int id = -1; // When creating new Bookmarks from user input, assign -1 as the ID.

		Blogpost newBlogpost = Builder.buildBlogpost(id, title, author, url, date);
		try {
			final Blogpost success = blogpostDao.create(newBlogpost);
			if (success != null) {
				uiController.addSuccess(newBlogpost.getClass().getSimpleName());
			} else {
				uiController.addFailure();
			}
			uiController.printEmptyLine();
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
		}

	}
}
