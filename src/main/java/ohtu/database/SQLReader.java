
package ohtu.database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class SQLReader {
	private final String path;

	/**
	 * Creates a new SQLReader-object that can be utilised to read the contents of the
	 * SQL-script file located at the given path.
	 *
	 * @param path The path to the internal SQL file.
	 */
	SQLReader(String path) {
		this.path = path;
	}

	/**
	 * Reads the SQL-statements in the file that is found through the
	 * given path.
	 *
	 * @return List of SQL-statements ready to be executed.
	 */
	List<String> readSQLStatements() {
		String s = loadStreamData(path);

		String[] lines = s.split("\n");
		ArrayList<String> output = new ArrayList<>();
		for (String line : lines) {
			output.add(line.trim());
		}
		return output;
	}

	/**
	 * Loads data from an internal resource.
	 *
	 * @param internalPath The internal path to the resource.
	 * @return A String-object containing the loaded data. Has linefeeds ("\n").
	 */
	private String loadStreamData(String internalPath) {
		InputStream inputStream = getClass().getResourceAsStream(internalPath);
		return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
	}

}
