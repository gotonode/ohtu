package ohtu.main;

import ohtu.enums.Commands;
import ohtu.ui.UiController;

import java.util.Scanner;

public class Main {

	private static UiController uiController;

	private static boolean appRunning = true;

    public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

    	uiController = new UiController(scanner);

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

		// The main loop has exited, so the program will terminate with error code 0 (success).
		uiController.printGoodbye();
		System.exit(0);
    }
}
