package ohtu.tools;

import ohtu.main.Main;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

	private HashGenerator() {
		throw new IllegalStateException("HashGenerator should not be instantiated.");
	}

	/**
	 * Returns a SHA-512 hashed String from the plaintext one.
	 *
	 * @param plaintextString The plaintext String, usually a password.
	 * @return The hashed String, or null if it didn't work.
	 */
	public static String getHashedString(final String plaintextString) {
		String output = null;
		final String salt = "7oZfqDwR9GTHWPDVdNJi1MZFRpRoUPTO"; // This is hard-coded, but will suffice.

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
			messageDigest.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = messageDigest.digest(plaintextString.getBytes(StandardCharsets.UTF_8));
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

			}
			output = stringBuilder.toString();

		} catch (NoSuchAlgorithmException e) {
			Main.LOG.warning(e.getMessage());
		}

		return output;
	}

}
