package ohtu.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * An interface which all DAO's must use.
 * @param <Bookmark> The specific type of Bookmark to be used.
 */
public interface ObjectDao<Bookmark, Integer> {

	List<Bookmark> findAll() throws SQLException;

	Bookmark findById(Integer id) throws SQLException;

	Bookmark findByTitle(String title) throws SQLException;

	// To be implemented later:

	//public void delete(Integer id) throws SQLException;
	//public void save(Bookmark bookmark) throws SQLException;
}
