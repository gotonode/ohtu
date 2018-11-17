package ohtu.main;

import ohtu.dao.BlogpostDao;
import ohtu.database.Database;
import ohtu.domain.Blogpost;
import ohtu.io.IO;
import ohtu.tools.Builder;
import ohtu.ui.UiController;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class App {

	private boolean appRunning;
	private BlogpostDao blogpostDao;
	private IO io;
        private Database database;

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
	}

	public void run() throws ParseException {

		appRunning = true;

		UiController uiController = new UiController(io); // Either ConsoleIO or StubIO.

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
				case 'A':
					Object[] values = uiController.askBlogpostData();
					String title = values[0].toString();
					String author = values[1].toString();
					String url = values[2].toString();
					Date date = (Date)values[3];
					Blogpost newBlogpost = Builder.buildBlogpost(-1, title, author, url, date);
					try {
						final boolean success = blogpostDao.create(newBlogpost);
						if (success) {
							uiController.addSuccess(newBlogpost.getClass().getSimpleName());
						} else {
							uiController.addFailure();
						}
					} catch (SQLException e) {
						System.out.println(e);
					}
					break;
				case 'E':
					appRunning = false;
					break;
			}
		}

		// The main loop has exited, so the program will terminate.
		uiController.printGoodbye();
	}
}
