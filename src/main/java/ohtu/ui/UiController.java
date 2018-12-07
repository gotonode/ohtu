package ohtu.ui;

import ohtu.io.IO;
import ohtu.tools.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		io.println("Welcome to " + ohtu.main.Main.APP_NAME + "!");
	}

	public void printGoodbye() {
		io.println("Thanks for using " + ohtu.main.Main.APP_NAME + ".");
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
	 * @return An array of Strings, e.g. "String[]". Index 0 contains title, index 1 contains author and index 2 contains url.
	 */
	public String[] askBlogpostData() {

		io.println("Adding a new blogpost.");

		String title = askForString("Title:", false);
		String author = askForString("Author:", false);
		String url = askForValidUrl();

		return new String[]{title, author, url};
	}

	/**
	 * Prompts the user for a title and URL.
	 *
	 * @return An array of Strings, e.g. "String[]". Index 0 contains title, index 1 contains url.
	 */
	public String[] askVideoData() {

		io.println("Adding a new video.");

		String title = askForString("Title:", false);
		String url = askForValidUrl();

		return new String[]{title, url};
	}

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

	public int askForIdToRemove() {
		io.println("Which bookmark to delete? Please see the ID from the bookmark in question.");
		return askForInt(0, Integer.MAX_VALUE, "Bookmark to delete");
	}

	public int askForIdToModify() {
		io.println("Which bookmark to modify? Please see the ID from the bookmark in question.");
		return askForInt(0, Integer.MAX_VALUE, "Bookmark to modify");
	}

	/**
	 * Asks the user for a string. Optionally doesn't allow empty strings.
	 *
	 * @param prompt What to ask (prompt) from the user.
	 * @param allowEmpty If true, the string can be empty. Otherwise it cannot.
	 * @return The string the user typed in.
	 */
	public String askForString(final String prompt, final boolean allowEmpty) {

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

	/**
	 * prints all the available instructions for the user.
	 */
	public void printInstructions() {

		printEmptyLine();

		io.println("L: List all bookmarks");
		io.println("S: Search for a bookmark by title or by URL");
		io.println("A: Add a new bookmark");
		io.println("M: Modify an existing bookmark");
		io.println("D: Delete an existing bookmark");

		printEmptyLine();

		io.println("X: Print all of the available commands (this text)");
		io.println("E: Exit from the app");

		printEmptyLine();
	}

	/**
	 * Continuously ask the user for a character, until a valid one is given.
	 *
	 * @param allowedChars A list of chars that are accepted.
	 * @return A valid uppercase character.
	 */
	public char askForCharacter(final char[] allowedChars, final String prompt) {

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
	private int askForInt(final int min, final int max, final String prompt) {

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
	 * Prints the Blogpost's data into console.
	 *
	 * @param printableData The data IN ORDER.
	 */
	public void printBlogpost(final List<String> printableData) {

		String firstLine = "  ===== " + printableData.get(5) + " =====";

		io.println(firstLine);
		io.println("  Title: " + printableData.get(2));
		io.println("  Type: " + printableData.get(0));
		io.println("  Author: " + printableData.get(3));
		io.println("  URL: " + printableData.get(4));
		io.println("  Date added: " + printableData.get(1));

		String block = "  ";
		while (block.length() < firstLine.length()) {
			block += "=";
		}

		io.println(block);
		printEmptyLine(); // An empty line to tidy things up.
	}

	/**
	 * Prints the Video's data into console.
	 *
	 * @param printableData The data IN ORDER.
	 */
	public void printVideo(final List<String> printableData) {


		String firstLine = "  ===== " + printableData.get(4) + " =====";

		io.println(firstLine);
		io.println("  Title: " + printableData.get(2));
		io.println("  Type: " + printableData.get(0));
		io.println("  URL: " + printableData.get(3));
		io.println("  Date added: " + printableData.get(1));

		String block = "  ";
		while (block.length() < firstLine.length()) {
			block += "=";
		}

		io.println(block);
		printEmptyLine(); // An empty line to tidy things up.
	}

	/**
	 * Prints the Book's data into console.
	 *
	 * @param printableData The data IN ORDER.
	 */
	public void printBook(final ArrayList<String> printableData) {
		String firstLine = "  ===== " + printableData.get(5) + " =====";

		io.println(firstLine);
		io.println("  Title: " + printableData.get(2));
		io.println("  Type: " + printableData.get(0));
		io.println("  Author: " + printableData.get(3));
		io.println("  ISBN: " + printableData.get(4));
		io.println("  Date added: " + printableData.get(1));

		String block = "  ";
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
		io.println("Please confirm that you want to delete this bookmark? Type 'Y' for yes, 'N' for no.");
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

	public String[] askBookData() {
		io.println("Adding a new book.");

		String title = askForString("Title:", false);
		String author = askForString("Author:", false);
		String isbn = askForString("ISBN:", false);

		return new String[]{title, author, isbn};
	}

	public void printLoginInstructions() {
		io.println("Please either log in (command 'L'), register for an account (command 'R'), or exit (command 'E').");
	}

	public void printWhereToGetLatestVersion(String url) {
		io.println("You can always get the latest version of this app from GitHub.");
		io.println(url);
	}

	public void printVersion(int version) {
		io.println("You are running version " + version + ".");
	}
}
