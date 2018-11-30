package ohtu.tools;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	private static String patternString = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	private Validator() {
		throw new IllegalStateException("Validator should not be instantiated.");
	}

	/**
	 * Check is the given URL is valid by running it though a Regex.
	 * @param url The URL to be checked.
	 * @return True if URL is valid, false otherwise.
	 */
	public static boolean isValidUrl(String url) {

		try {
			URL newUrl = new URL(url);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}

		/*
		Pattern pattern = Pattern.compile(patternString);

		Matcher matcher = pattern.matcher(url);

		boolean matchFound = matcher.matches();

		return matchFound;
		*/
	}
}
