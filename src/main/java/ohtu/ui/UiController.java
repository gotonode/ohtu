package ohtu.ui;

import java.util.Scanner;

public class UiController {

	// I'll implement the mock UI component later (Riku).

	private static final String APP_NAME = "Bookmarks Database";

	private Scanner scanner;

	public UiController(Scanner scanner) {
		this.scanner = scanner;
	}

	public void showGreeting() {
		System.out.println("Welcome to " + APP_NAME + "!");
	}
}
