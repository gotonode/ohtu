package ohtu.dao;

import ohtu.domain.Bookmark;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkDao implements ObjectDao<Bookmark, Integer> {

	// If possible, this class could return all results, when we don't want to get e.g. just the blog posts.

	@Override
	public List<Bookmark> findAll() throws SQLException, ParseException {
		return new ArrayList<>(); // An empty list.
	}

	@Override
	public Bookmark findById(Integer id) throws SQLException, ParseException {
		return null;
	}

	@Override
	public Bookmark findByTitle(String title) throws SQLException, ParseException {
		return null;
	}

	@Override
	public boolean create(Bookmark bookmark) throws SQLException, ParseException {
		return false;
	}


}
