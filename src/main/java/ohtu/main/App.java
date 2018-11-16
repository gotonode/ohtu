package ohtu.main;

import ohtu.io.IO;
import ohtu.ui.UiController;

public class App {

	private boolean appRunning;

	private IO io;

	public App(IO io) {
		this.io = io;
	}

	public void run() {

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
					uiController.addBlogpost();
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
