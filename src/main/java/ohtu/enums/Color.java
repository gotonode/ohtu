package ohtu.enums;

public enum Color {
	Green("\u001b[32m"),
	Blue("\u001b[34m"),
	Magenta("\u001b[35m"),
	White("\u001b[37m"),
	Default("\u001b[0m");

	private final String value;

	Color(String value) {
		this.value = value;
	}

	public static String stringWithColor(String text, Color color) {
		return color + text + Default;
	}

	@Override
	public String toString() {
		return value;
	}

	public static String commandText(char command) {
		return stringWithColor(String.valueOf(command), Color.Magenta);
	}
}
