package ohtu.dao;

import java.io.File;
import java.io.IOException;

import ohtu.database.Database;
import org.junit.rules.TemporaryFolder;

public class AbstractDaoTest {

	protected static Database database;
	static File databaseFile;

	protected static void initialize() throws IOException {
		TemporaryFolder tempFolder = new TemporaryFolder();
		tempFolder.create();

		// Assign a test database into the newly created temporary folder.
		databaseFile = new File(tempFolder.getRoot() + "/test.db");
		if (databaseFile.exists()) {
			databaseFile.delete();
		}
	}
}
