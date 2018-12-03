package ohtu.tools;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A utility class to verify different things. Cannot be instantiated.
 */
public class Validator {

	private Validator() {
		throw new IllegalStateException("Validator should not be instantiated.");
	}

	/**
	 * Check is the given URL is valid.
	 * @param url The URL to be checked.
	 * @return True if URL is valid, false otherwise.
	 */
	public static boolean isValidUrl(String url) {

		try {
			URL test = new URL(url);
			// If the above won't fail, the URL is deemed valid.
			return true;
		} catch (MalformedURLException e) {
			// An exception occurred, so the URL is not valid.
			return false;
		}
	}
}
