package dao;

import database.Database;
import domain.Blogpost;

import java.util.List;

/**
 * A DAO object for the Blogposts. This class talks with the database and creates (or updates/deletes) Blogpost objects.
 */
public class BlogpostDao {

	private final Database database;

	public BlogpostDao(Database database) {
		this.database = database;
	}

	// TODO: Implement findById, findByTitle, findAll etc.

	public Blogpost findById(int id) {
		return null;
	}

	public Blogpost findByTitle(String title) {
		return null;
	}

	public List<Blogpost> findAll() {
		return null;
	}

}
