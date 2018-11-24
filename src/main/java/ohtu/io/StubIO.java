package ohtu.io;

import java.util.ArrayList;
import java.util.List;

public class StubIO implements IO {

	private List<String> lines;
	private ArrayList<String> prints;

	private int i;

	public StubIO(List<String> values) {
		this.lines = values;
		prints = new ArrayList<>();
	}

	/**
	 * Prints WITHOUT a newline.
	 * @param input What to print.
	 */
	@Override
	public void print(String input) {
		prints.add(input.trim()); // Trim trailing newline ("\n").
	}

	/**
	 * Prints WITH a newline.
	 * @param input What to print.
	 */
	@Override
	public void println(String input) {
		prints.add(input.trim()); // Trim trailing newline ("\n").
	}

	public ArrayList<String> getPrints() {
		return prints;
	}

	@Override
	public String readLine(String prompt) {

		print(prompt + " ");

		if (i < lines.size()) {
			String out = lines.get(i++);

			// This is formatted like in Python just so that it absolutely stands out.
			print("__INPUT_VALUE__=" + out);

			return out;
		} else {
			throw new RuntimeException("App tried to read from console, but came up empty. Add more lines to it?");
		}
	}
}
