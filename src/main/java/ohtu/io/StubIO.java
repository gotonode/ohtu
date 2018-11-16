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

	@Override
	public void print(String input) {
		if (ohtu.main.Main.DEBUG) {
			System.out.print(input);
		}
		prints.add(input.trim()); // Trim trailing newline ("\n").
	}

	@Override
	public void println(String input) {
		if (ohtu.main.Main.DEBUG) {
			System.out.println(input);
		}
		prints.add(input.trim()); // Trim trailing newline ("\n").
	}

	public ArrayList<String> getPrints() {
		return prints;
	}

	@Override
	public String readLine(String prompt) {

		print(prompt + " ");

		if (i < lines.size()) {
			String output = lines.get(i++);

			if (ohtu.main.Main.DEBUG) {
				System.out.println(output);
			}

			return output;
		} else {
			throw new RuntimeException("App tried to read from console, but came up empty. Add more lines to it?");
		}
	}
}
