package ohtu.io;

import java.util.Scanner;

public class ConsoleIO implements IO {

	private Scanner scanner;

	public ConsoleIO(Scanner scanner) {
		this.scanner = scanner;
	}

	@Override
	public void print(String input) {
		System.out.print(input);
	}

	@Override
	public void println(String input) {
		System.out.println(input);
	}

	@Override
	public String readLine(String prompt) {
		print(prompt.trim() + " "); // Trim is here just in case it already has a trailing space.
		return scanner.nextLine();
	}
}
