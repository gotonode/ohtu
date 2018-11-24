package ohtu.main;

import ohtu.actions.*;
import ohtu.dao.BlogpostDao;
import ohtu.dao.BookmarkDao;
import ohtu.database.Database;
import ohtu.enums.Commands;
import ohtu.io.IO;
import ohtu.ui.UiController;
import java.util.Arrays;

public class App {

	private UiController uiController;

	private ExitAction exit;
	private ListAction browse;
	private DeleteAction delete;
	private AddAction add;
	private ModifyAction modify;
	private HelpAction help;

	public App(IO io, Database db) {
		BlogpostDao blogpostDao = new BlogpostDao(db);
		BookmarkDao bookmarkDao = new BookmarkDao(db, blogpostDao);

		uiController = new UiController(io); // Either ConsoleIO or StubIO.

		exit = new ExitAction(io, uiController);
		delete = new DeleteAction(io, uiController, bookmarkDao, blogpostDao);
		browse = new ListAction(io, uiController, bookmarkDao);
		add = new AddAction(io, uiController, bookmarkDao, blogpostDao);
		modify = new ModifyAction(io, uiController, bookmarkDao, blogpostDao);
		help = new HelpAction(io, uiController);
	}

	public void run() {

		boolean appRunning = true;

		uiController.printGreeting();
		uiController.printInstructions();

		while (appRunning) {

			char character = uiController.askForCharacter(new char[]{'A', 'L', 'E', 'D', 'M', 'X'}, "Choose a command");

			// I believe there might be a better way to achieve this. Feel free to improve!
			Commands command = Arrays.stream(Commands.values()).filter(a -> a.getCommand() == character).findFirst().get();

			switch (command) {

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
