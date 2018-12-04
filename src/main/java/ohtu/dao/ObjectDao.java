package ohtu.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * An interface which all DAO's must use.
 *
 * @param <Bookmark> The specific type of Bookmark to be used.
 */
public interface ObjectDao<Bookmark, Integer> {

    /**
     * Returns all of the Bookmarks, or Bookmarks of a specific type.
     * @return A list containing the results.
     * @throws SQLException Database failure.
     * @throws ParseException Parse failure.
     */
    List<Bookmark> findAll() throws SQLException, ParseException;

    /**
     * Finds a Bookmark by its unique ID.
     * @param id The ID, an integer that is 1 and above.
     * @return The Bookmark with the given ID.
     * @throws SQLException Database failure.
     * @throws ParseException Parse failure.
     */
    Bookmark findById(Integer id) throws SQLException, ParseException;

    /**
     * Finds a Bookmark with the given title.
     * @param title The title to look for.
     * @return The Bookmark with the title.
     * @throws SQLException Database failure.
     * @throws ParseException Parse failure.
     */
    List<Bookmark> findByTitle(String title) throws SQLException, ParseException;

    /**
     * Adds the Bookmark into the database. Ignores the Bookmark's ID and addDate, those are assigned by the database.
     * When creating a new Bookmark from user input (not from database), assign -1 as the ID and null as addDate.
     * @param bookmark The Bookmark in question. Cannot pass Bookmark here directly.
     * @return bookmark with id and addDate which were assigned by the database if creation succeeded, otherwise null
     * @throws SQLException Database failure.
     * @throws ParseException Parse failure.
     */
    Bookmark create(Bookmark bookmark) throws SQLException, ParseException;

    /**
     * Deletes the bookmark with given id
     * @param id.The ID, an integer that is 1 or above
     * @return true if the bookmark was successfully deleted, otherwise false
     * @throws SQLException Database failure
     */
    boolean delete(Integer id) throws SQLException;
    
    /**
     * updates the information of a bookmark which were save in the database
     * @param bookmark The bookmark with new information(title,author .etc)
     * @return true if the bookmark was successfully updated, otherwise false
     * @throws SQLException Database failure
     */
    boolean update(Bookmark bookmark) throws SQLException;
}
