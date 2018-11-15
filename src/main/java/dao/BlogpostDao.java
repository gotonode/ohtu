package dao;

import database.Database;
import domain.Blogpost;

import java.sql.SQLException;
import java.util.List;

/**
 * A DAO object for the Blogposts. This class talks with the database and creates (or updates/deletes) Blogpost objects.
 */
public class BlogpostDao implements ObjectDao<Blogpost, Integer> {

	private final Database database;

	public BlogpostDao(Database database) {
		this.database = database;
	}

	// TODO: Implement findById, findByTitle, findAll etc.

	@Override
	public Blogpost findByTitle(String title) throws SQLException {
		return null;
	}

	@Override
	public List<Blogpost> findAll() throws SQLException {
		return null;
	}

	@Override
	public Blogpost findById(Integer id) throws SQLException {
		return null;
	}

}
