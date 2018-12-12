package ohtu.ui;

import ohtu.enums.Color;
import ohtu.enums.Keys;
import ohtu.io.IO;
import ohtu.tools.Validator;

import java.util.Arrays;
import java.util.HashMap;

import static ohtu.enums.Color.commandText;

/**
 * This class handles the UI-facing aspects of the app. It asks
 * the user for data via the console, and outputs data to the console as well.
 */
public class UiController {

	private final IO io;

	/**
	 * Creates a new UiController.
	 *
	 * @param io The IO object to be used (either production or testing version).
	 */
	public UiController(final IO io) {
		this.io = io;
	}

	public void printGreeting() {
		io.println("Welcome to " + Color.stringWithColor(ohtu.main.Main.APP_NAME, Color.Blue) + "!");
	}

	public void printGoodbye() {
		io.println("Thanks for using " + Color.stringWithColor(ohtu.main.Main.APP_NAME, Color.Blue) + ".");
	}

	public void printVersion(int version) {
		io.println("You are running version " + version + ".");
	}

	public void printWhereToGetLatestVersion(String url) {
		io.println("You can always get the latest version of this app from GitHub.");
		io.println(url);
	}

	public void addSuccess(final String type) {
		io.println("A new " + type.trim() + " has been created and saved to the database.");
	}

	public void addFailure() {
		io.println("Could not add your new bookmark to the database. Please try again.");
	}

	/**
	 * Prompts the user for a title, author and URL.
	 *
	 * @return A dictionary of key-value pairs.
	 */
	public HashMap<Keys, String> askBlogpostData() {

		HashMap<Keys, String> output = new HashMap<>();

		io.println("Adding a new blogpost.");

		String title = askForString("Title:", false);
		String author = askForString("Author:", false);
		String url = askForValidUrl();

		output.put(Keys.Title, title);
		output.put(Keys.Author, author);
		output.put(Keys.URL, url);

		return output;
	}

	/**
	 * Prompts the user for a title and URL.
	 *
	 * @return A dictionary of key-value pairs.
	 */
	public HashMap<Keys, String> askVideoData() {

		HashMap<Keys, String> output = new HashMap<>();

		io.println("Adding a new video.");

		String title = askForString("Title:", false);
		String url = askForValidUrl();

		output.put(Keys.Title, title);
		output.put(Keys.URL, url);

		return output;
	}

	/**
	 * Prompts the user for a title, author and ISBN.
	 *
	 * @return A dictionary of key-value pairs.
	 */
	public HashMap<Keys, String> askBookData() {

		HashMap<Keys, String> output = new HashMap<>();

		io.println("Adding a new book.");

		String title = askForString("Title:", false);
		String author = askForString("Author:", false);
		String isbn = askForString("ISBN:", false);

		output.put(Keys.Title, title);
		output.put(Keys.Author, author);
		output.put(Keys.ISBN, isbn);

		return output;
	}

	public int askForIdToRemove() {
		io.println("Which bookmark to delete? Please see the ID from the bookmark in question.");
		return askForInt(0, Integer.MAX_VALUE, "Bookmark to delete");
	}

	public int askForIdToModify() {
		io.println("Which bookmark to modify? Please see the ID from the bookmark in question.");
		return askForInt(0, Integer.MAX_VALUE, "Bookmark to modify");
	}

	/**
	 * prints all the available instructions for the user.
	 */
	public void printInstructions() {

		printEmptyLine();

		io.println(commandText('L') + ": List all bookmarks");
		io.println(commandText('S') + ": Search for a bookmark by title or by URL");
		io.println(commandText('A') + ": Add a new bookmark");
		io.println(commandText('M') + ": Modify an existing bookmark");
		io.println(commandText('D') + ": Delete an existing bookmark");

		printEmptyLine();

		io.println(commandText('X') + ": Print all of the available commands (this text)");
		io.println(commandText('E') + ": Exit from the app (logout)");

		printEmptyLine();
	}



	/**
	 * Prints the Bookmark's data into console.
	 *
	 * @param printableData The data in a dictionary.
	 */
	public void printBook(HashMap<Keys, String> printableData) {
		final String spacer = " " + " ";
		String firstLine = spacer + "===== " + printableData.get(Keys.ID) + " =====";

		io.println(firstLine);

		io.println(spacer + "Title: " + printableData.get(Keys.Title));
		io.println(spacer + "Type: " + printableData.get(Keys.Class));

		if (printableData.containsKey(Keys.Author)) {
			io.println(spacer + "Author: " + printableData.get(Keys.Author));
		}

		if (printableData.containsKey(Keys.ISBN)) {
			io.println(spacer + "ISBN: " + printableData.get(Keys.ISBN));
		}

		if (printableData.containsKey(Keys.URL)) {
			io.println(spacer + "URL: " + printableData.get(Keys.URL));
		}

		io.println(spacer + "Date added: " + printableData.get(Keys.Date));

		String block = spacer;
		while (block.length() < firstLine.length()) {
			block += "=";
		}

		io.println(block);
		printEmptyLine(); // An empty line to tidy things up.
	}

	public void printEmptyLine() {
		io.println("");
	}

	public void printDeleteConfirmation(final int id, final String title, final String type) {
		io.println("Bookmark with ID " + id + " has a title of '" + title + "' and is of type " + type + ".");
		io.println("Please confirm that you want to delete this bookmark? Type '"
				+ commandText('Y') + "' for yes, '" + commandText('N') + "' for no.");
	}

	public void printAbortDelete() {
		io.println("Bookmark will not be deleted.");
	}

	public void printDeleteSuccessful(final int id) {
		io.println("Successfully deleted bookmark with ID " + id + ".");
	}

	public void printNoBookmarks() {
		io.println("No bookmarks have been saved in the database.");
	}

	public void printBookmarkNotFound() {
		io.println("Could not find a bookmark with that ID. Please re-check it.");
	}

	public void printDeleteFailed(final int id) {
		io.println("Could not remove bookmark with ID " + id + ". Please try again.");
	}

	public void printUrlNotValid() {
		io.println("The URL is not valid. Please remember to add 'http://' at the beginning of it.");
	}



	public void printLoginInstructions() {
		io.println("Please either log in (command '" + commandText('L') + "'), "
				+ "register for an account (command '" + commandText('R') + "'), "
				+ "or exit (command '" + commandText('E') + "').");
	}

	public void printAccessDenied() {
		io.println("This Bookmark belongs to a different user. Please try again with a Bookmark that you own.");
	}

	/**
	 * Continuously ask the user for a character, until a valid one is given.
	 *
	 * @param allowedChars A list of chars that are accepted.
	 * @return A valid uppercase character.
	 */
	public char askForCharacter(char[] allowedChars, String prompt) {

		while (true) {

			String next = io.readLine(prompt.trim() + ":");

			if (next.isEmpty()) {
				io.println("Please enter something.");
				continue;
			}

			if (next.length() > 1) {
				io.println("Please only enter 1 character.");
				continue;
			}

			char input = next.toUpperCase().charAt(0);

			boolean found = false;

			// This should use Java's streams.
			for (char c : allowedChars) {
				if (input == c) {
					found = true;
					break;
				}
			}

			if (!found) {
				io.println("Please enter a character from the following: " + Arrays.toString(allowedChars));
			} else {
				return input;
			}
		}

	}

	/**
	 * Continuously ask the user for an integer, until a valid one is given.
	 *
	 * @param min Min value for the integer.
	 * @param max Max value for the integer.
	 * @return A valid integer between min and max.
	 */
	private int askForInt(int min, int max, String prompt) {

		int value;

		while (true) {

			String next = io.readLine(prompt + ":");

			if (next.isEmpty()) {
				io.println("Please enter something.");
				continue;
			}

			try {
				value = Integer.parseInt(next);
			} catch (Exception e) {
				io.println("Please enter an integer.");
				continue;
			}

			if (value < min) {
				io.println("Please enter an integer that is greater than " + min + ".");
				continue;
			} else if (value > max) {
				io.println("Please enter an integer that is less than " + min + ".");
				continue;
			}

			break;
		}

		return value;
	}

	/**
	 * Continuously asks the user for a valid URL, until one is given.
	 * @return A valid URL in a String format.
	 */
	private String askForValidUrl() {

		String url;

		while (true) {
			url = askForString("URL: ", false);
			boolean isValidUrl = Validator.isValidUrl(url);

			if (url.isEmpty()) {
				break;
			}

			if (!isValidUrl) {
				printUrlNotValid();
				continue;
			}

			break;
		}

		return url;
	}

	/**
	 * Asks the user for a string. Optionally doesn't allow empty strings.
	 *
	 * @param prompt What to ask (prompt) from the user.
	 * @param allowEmpty If true, the string can be empty. Otherwise it cannot.
	 * @return The string the user typed in.
	 */
	public String askForString(String prompt, boolean allowEmpty) {

		String data;

		while (true) {
			data = io.readLine(prompt);
			if (data.isEmpty()) {
				if (allowEmpty) {
					break;
				} else {
					io.println("Please write something.");
				}
			} else {
				break;
			}
		}
		return data;
	}
}
