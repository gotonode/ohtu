package ohtu.dao;

import ohtu.domain.Bookmark;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import ohtu.database.Database;
import ohtu.domain.Blogpost;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A DAO object for the Blogposts. This class talks with the database and
 * creates (or updates/deletes) Blogpost objects.
 */
public class BlogpostDao implements ObjectDao<Blogpost, Integer> {

	private final Database database;

	public BlogpostDao(Database database) {
		this.database = database;
	}

	// TODO: Implement findById, findByTitle, findAll etc.
	@Override
	public Blogpost findByTitle(String title) throws SQLException, ParseException {
		Connection conn = database.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT*FROM bookmark,blogpost WHERE bookmark.title=? AND bookmark.id=blogpost.id");
		stmt.setString(1, title);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			Blogpost output = constructBlogpostFromResultSet(rs);
			rs.close();
			stmt.close();
			conn.close();
			return output;
		}
		return null;
	}

	@Override
	public List<Blogpost> findAll() throws SQLException, ParseException {
		Connection conn = database.getConnection();
		PreparedStatement stmt = conn.prepareStatement("SELECT*FROM bookmark,blogpost WHERE bookmark.id=blogpost.id");
		ResultSet rs = stmt.executeQuery();
		List<Blogpost> outputs = new ArrayList<>();
		while (rs.next()) {
			Blogpost blogpost = constructBlogpostFromResultSet(rs);
			outputs.add(blogpost);
		}
		rs.close();
		stmt.close();
		conn.close();
		return outputs;
	}

	@Override
	public Blogpost findById(Integer id) throws SQLException, ParseException {
		Connection conn = database.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT*FROM bookmark,blogpost WHERE bookmark.id=? AND bookmark.id=blogpost.id");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			Blogpost output = constructBlogpostFromResultSet(rs);
			rs.close();
			stmt.close();
			conn.close();
			return output;
		}
		return null;
	}

	private Blogpost constructBlogpostFromResultSet(ResultSet rs) throws SQLException, ParseException {
		String dateString = rs.getString("addDate");
		Date dateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		java.sql.Date date = new java.sql.Date(dateUtil.getTime());
		Blogpost blogpost = new Blogpost(rs.getInt("id"), rs.getString("title"), date, rs.getString("author"),
				rs.getString("url"));
		return blogpost;
	}

}
