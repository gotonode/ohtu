import ui.UiController;

import java.util.Scanner;

public class Main {

	private static UiController uiController;

    public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

    	uiController = new UiController(scanner);

    	uiController.showGreeting();
    }
}
