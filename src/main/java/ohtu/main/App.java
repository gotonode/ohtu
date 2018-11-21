package ohtu.main;

import ohtu.dao.BlogpostDao;
import ohtu.dao.BookmarkDao;
import ohtu.database.Database;
import ohtu.domain.Blogpost;
import ohtu.domain.Bookmark;
import ohtu.io.IO;
import ohtu.tools.Builder;
import ohtu.ui.UiController;

import java.util.ArrayList;
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

			char command = uiController.askForCharacter(new char[]{'A', 'L', 'E', 'D', 'M'});

			if (command == 'X') {
				uiController.printInstructions();
				continue;
			}

			// I'll be using the enums from enums.Commands-package soon.
			switch (command) {

				case 'L':
					listAll();
					break;

				case 'A':
					addBlogpost();
					break;

				case 'D':
					deleteBookmark();
					break;

				case 'M':
					modifyBookmark();
					break;

				case 'E':
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
		int id = uiController.askForIdToRemove();
		// TODO: Work in progress.
		io.println("Modifying bookmark with ID " + id);
	}

	private void deleteBookmark() {
		int id = uiController.askForIdToRemove();
		// TODO: Work in progress.
		io.println("Removing bookmark with ID " + id);
	}

	private void listAll() {
		io.println("Listing all bookmarks...");
		uiController.printEmptyLine(); // Tidy.
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
