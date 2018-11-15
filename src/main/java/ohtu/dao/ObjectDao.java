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

    List<Bookmark> findAll() throws SQLException, ParseException;

    Bookmark findById(Integer id) throws SQLException, ParseException;

    Bookmark findByTitle(String title) throws SQLException, ParseException;

    // To be implemented later:
    //public void delete(Integer id) throws SQLException;
    //public void save(Bookmark bookmark) throws SQLException;
}
