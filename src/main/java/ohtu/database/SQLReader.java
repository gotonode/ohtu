
package ohtu.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SQLReader {
    private final String filepath;

    /**
     * Creates a new SQLReader-object that can be utilised to read the contents of the 
     * SQL-script file located at the given filepath.
     * @param filepath 
     */
    public SQLReader(String filepath) {
        this.filepath = filepath;
    }
    
    /**
     * Reads the SQL-statements in the file that is found through the
     * given filepath.
     * 
     * @return List of SQL-statements ready to be executed
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public List<String> readSQLStatements() throws IOException {
        if (!new File(filepath).exists()) {
            return backupCreateTableStatements();
        }
        
        BufferedReader reader = new BufferedReader(new FileReader(filepath));

        List<String> statements = new ArrayList<>();
        String statement = "";
        String nextline;

        while ((nextline = reader.readLine()) != null) {
            String line = nextline.trim();

            if (line.equals(("")) || line.startsWith("//") || line.startsWith("/*")) {
                continue;
            }

            statement += line + " "; // TODO: Use a StringBuilder here?

            if (line.endsWith(";")) {
                statements.add(statement);
                statement = "";
            }
        }
        
        return statements;
    }
    
    private List<String> backupCreateTableStatements() {
        List<String> statements = new ArrayList<>();
        
        statements.add("CREATE TABLE IF NOT EXISTS Bookmark (id INTEGER PRIMARY KEY, " +    
                "title varchar(200), addDate datetime default current_timestamp, type varchar(1));");
        statements.add("CREATE TABLE IF NOT EXISTS Blogpost (id integer NOT NULL, author varchar(100), " +
                "url varchar(400), PRIMARY KEY (id), FOREIGN KEY (id) REFERENCES Bookmark(id));");
        statements.add("CREATE TABLE IF NOT EXISTS Video (id integer NOT NULL, url varchar(400), " +
                "PRIMARY KEY (id), FOREIGN KEY (id) REFERENCES Bookmark(id));");
        statements.add("CREATE TABLE IF NOT EXISTS Book (id integer NOT NULL, author varchar(100), " +
                "isbn varchar(20), PRIMARY KEY (id), FOREIGN KEY (id) REFERENCES Bookmark(id));");
        
        return statements;
    }
    
}
