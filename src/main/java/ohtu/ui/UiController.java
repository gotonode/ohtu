package ohtu.ui;

import ohtu.io.IO;

import java.util.Arrays;
import java.util.List;

public class UiController {

	private IO io;

	public UiController(IO io) {
		this.io = io;
	}

	public void addSuccess(String type) {
		io.println("A new " + type.trim() + " has been created and saved to the database.");
	}

	public void addFailure() {
		io.println("Could not add your new bookmark to the database. Please try again.");
	}

	public void printGreeting() {
		io.println("Welcome to " + ohtu.main.Main.APP_NAME + "!\n");
	}

	public void printGoodbye() {
		io.println("Thanks for using " + ohtu.main.Main.APP_NAME + ".");
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
		String url = askForString("URL:", false);

		return new String[]{title, author, url};
	}

	public int askForIdToRemove() {
		io.println("Which bookmark to delete? Please see the ID from the bookmark in question.");
		int id = askForInt(0, Integer.MAX_VALUE);
		return id;
	}

	public int askForIdToModify() {
		io.println("Which bookmark to modify? Please see the ID from the bookmark in question.");
		int id = askForInt(0, Integer.MAX_VALUE);
		return id;
	}

	/**
	 * Asks the user for a string. Optionally doesn't allow empty strings.
	 *
	 * @param prompt     What to ask (prompt) from the user.
	 * @param allowEmpty If true, the string can be empty. Otherwise it cannot.
	 * @return The string the user typed in.
	 */
	public String askForString(String prompt, boolean allowEmpty) {

		String data = "";

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
		io.println("L: List all blogposts");
		io.println("A: Add a new blogpost");
		io.println("M: Modify a new blogpost");
		io.println("D: Delete a bookmark");
		io.println("");
		io.println("X: Print all of the available commands (this text)");
		io.println("E: Exit from the app");
		io.println("");
	}

	/**
	 * Continuously ask the user for a character, until a valid one is given.
	 *
	 * @param allowedChars A list of chars that are accepted.
	 * @return A valid uppercase character.
	 */
	public char askForCharacter(char[] allowedChars) {

		while (true) {

			String next = io.readLine("Your choice:");

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
	public int askForInt(int min, int max) {

		int value = 0;

		while (true) {

			String next = io.readLine("Your choice:");

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
	public void printBlogpost(List<String> printableData) {
		io.println("===== " + printableData.get(5) + " =====");
		io.println("Title: " + printableData.get(2));
		io.println("Type: " + printableData.get(0));
		io.println("Author: " + printableData.get(3));
		io.println("URL: " + printableData.get(4));
		io.println("Date added: " + printableData.get(1));
		io.println(""); // An empty line to tidy things up.
	}

	public void printEmptyLine() {
		io.println("");
	}

	public void printDeleteConfirmation(int id, String title, String type) {
		io.println("Bookmark with ID " + id + " has a title of '" + title + "' and is of type " + type + ".");
		io.println("Please confirm that you want to delete this bookmark? Type 'Y' for yes, 'N' for no.");
	}

	public void printAbortDelete() {
		io.println("Bookmark will not be deleted.");
	}

	public void printDeleteSuccessful(int id) {
		io.println("Successfully deleted bookmark with ID " + id + ".");
	}

	public void printNoBookmarks() {
		io.println("No bookmarks have been saved in the database.");
	}
}
