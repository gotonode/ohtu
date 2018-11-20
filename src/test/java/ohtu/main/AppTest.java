package ohtu.main;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import ohtu.database.Database;
import ohtu.io.StubIO;
import ohtu.ui.UiController;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class AppTest {

	private static TemporaryFolder tempFolder;

	private App app;
	private StubIO stubIo;
	private ArrayList<String> lines;

	private static Database database;
	private static File databaseFile;

	@Before
	public void beforeClass() {
		lines = new ArrayList<>();
		stubIo = new StubIO(lines);
	}

	@BeforeClass
	public static void setUpClass() throws IOException {
		tempFolder = new TemporaryFolder();
		tempFolder.create();

		// Assign a test database into the newly created temporary folder.
		databaseFile = new File(tempFolder.getRoot() + "/test.db");
		if (databaseFile.exists()) {
			databaseFile.delete();
		}
	}

	/**
	 * A sample test which creates a new App with the StubIO, and issues an exit command ('E').
	 * The app should exit with the goodbye message.
	 *
	 * @throws AssertionError
	 */
	@Test
	public void appExitsWhenExitCommandGiven() throws AssertionError, ParseException {

		lines.add("E");

		app = new App(stubIo, database);
		app.run();

		ArrayList<String> prints = stubIo.getPrints(); // When debugging tests, this is an interesting variable to read.

		assertTrue(prints.contains("Thanks for using " + ohtu.main.Main.APP_NAME + "."));
	}
}
