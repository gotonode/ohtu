package io;

import ohtu.io.StubIO;
import ohtu.main.App;
import ohtu.ui.UiController;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class StubIOTest {

	private App app;
	private StubIO stubIo;
	private ArrayList<String> lines;

	@Before
	public void initialize() {
		lines = new ArrayList<>();
		stubIo = new StubIO(lines);
	}

	/**
	 * A sample test which creates a new App with the StubIO, and issues an exit command ('E').
	 * @throws AssertionError
	 */
	@Test
	public void appExitsWhenExitCommandGiven() throws AssertionError  {

		lines.add("E");

		app = new App(stubIo);
		app.run();

		ArrayList<String> prints = stubIo.getPrints(); // When debugging tests, this is an interesting variable to read.

		assertTrue(prints.contains("Thanks for using " + ohtu.main.Main.APP_NAME + ".\n"));
	}

}
