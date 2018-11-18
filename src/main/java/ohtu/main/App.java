package ohtu.main;

import ohtu.dao.BlogpostDao;
import ohtu.dao.BookmarkDao;
import ohtu.database.Database;
import ohtu.domain.Blogpost;
import ohtu.domain.Bookmark;
import ohtu.io.IO;
import ohtu.tools.Builder;
import ohtu.ui.UiController;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
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

	// TODO: Remove/fix this constructor later. Now here for the sake of Test files continuing to work.
	public App(IO io) {
		this.io = io;
		this.database = new Database(new File(""));
		blogpostDao = new BlogpostDao(database);

	}

	public App(IO io, Database db) {
		this.io = io;
		this.database = db;
		blogpostDao = new BlogpostDao(database);
		bookmarkDao = new BookmarkDao(database, blogpostDao);
	}


	public void run() throws ParseException {

		appRunning = true;

		uiController = new UiController(io); // Either ConsoleIO or StubIO.

		uiController.printGreeting();
		uiController.printInstructions();

		while (appRunning) {

			char command = uiController.askForCharacter(new char[]{'A', 'L', 'E'});

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

				case 'E':
					appRunning = false;
					break;
			}
		}

		// The main loop has exited, so the program will terminate.
		uiController.printGoodbye();
	}

	private void listAll() {
		io.println("Listing all bookmarks...");
		try {
			List<Bookmark> bookmarks = bookmarkDao.findAll();
			for (Bookmark bookmark : bookmarks) {
				if (bookmark instanceof Blogpost) {
					Blogpost blogpost = (Blogpost) bookmark;
					io.println(blogpost.getClass().getSimpleName()
							+ " "
							+ blogpost.getAddDate()
							+ ": "
							+ blogpost.getTitle()
							+ " (" + blogpost.getAuthor()
							+ ") " + blogpost.getUrl());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Asks the user for title, author and url, and creates a new Blogpost by using Builder.
	 * Then tries to add the new Blogpost into the database. Tells the user if the operation succeeded or not.
	 */
	private void addBlogpost() {

		String[] values = uiController.askBlogpostData();
		String title = values[0];
		String author = values[1];
		String url = values[2];

		// Database will handle assigning correct ID to the object, and decides the addDate value.
		Date date = null; // This is here just as a reference that it is null.
		int id = -1; // When creating new Bookmarks from user input, assign -1 as the ID.

		Blogpost newBlogpost = Builder.buildBlogpost(id, title, author, url, date);
		try {
			final boolean success = blogpostDao.create(newBlogpost);
			if (success) {
				uiController.addSuccess(newBlogpost.getClass().getSimpleName());
			} else {
				uiController.addFailure();
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
