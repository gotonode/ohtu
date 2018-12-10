package ohtu.dao;

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
	public List<Blogpost> findByTitle(String title) throws SQLException, ParseException {
		List<Blogpost> blogposts = new ArrayList<>();
		Connection conn = database.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT*FROM bookmark,blogpost WHERE bookmark.title LIKE ? AND bookmark.id=blogpost.id");
		String pattern = "%" + title + "%";
		stmt.setString(1, pattern);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Blogpost output = constructBlogpostFromResultSet(rs);
			//close(rs, stmt, conn);
			blogposts.add(output);

		}
		database.close(stmt, conn, rs);
		return blogposts;
	}

	@Override
	public Blogpost create(Blogpost blogpost) throws SQLException, ParseException {
		createToBookmarkTable(blogpost.getTitle());
		int id = getLatestId();
		if (id == -1) {
			return null; //method createToBookmarkTable didn't work successfully and as a result blogpost cannot be added
		}
		createToBlogpostTable(id, blogpost.getAuthor(), blogpost.getUrl());

		// make sure that blogpost-object is added to database
		Blogpost added = findById(id);
		if (added.equals(blogpost)) {
			return added;
		}
		return null;
	}

	/**
	 * @param id
	 * @return true if id is found and deleted successfully
	 * @throws SQLException
	 */
	@Override
	public boolean delete(Integer id) throws SQLException {
		if (deleteFromBookmarkTable(id)) {
			return deleteFromBlogpostTable(id);
		}
		return false;
	}

	@Override
	public boolean update(Blogpost blogpost) throws SQLException {
		if (updateBookmark(blogpost.getTitle(), blogpost.getId())) {
			return updateBlogpost(blogpost.getAuthor(), blogpost.getUrl(), blogpost.getId());
		}
		return false;
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
		database.close(stmt, conn, rs);
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
			database.close(stmt, conn, rs);
			return output;
		}
		database.close(stmt, conn, rs);
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

	private void createToBookmarkTable(String title) throws SQLException {
		Connection conn = database.getConnection();
		// insert into bookmark-table
		PreparedStatement stmtBookmark = conn.prepareStatement("INSERT INTO bookmark(title,type, user_id) VALUES(?,'B', 0)");
		stmtBookmark.setString(1, title);
		stmtBookmark.execute();
		stmtBookmark.close();
		conn.close();
	}

	private void createToBlogpostTable(int id, String author, String url) throws SQLException {
		Connection conn = database.getConnection();
		PreparedStatement stmtBlogpost = conn.prepareStatement("INSERT INTO blogpost(id,author,url) VALUES(?,?,?)");

		stmtBlogpost.setInt(1, id);
		stmtBlogpost.setString(2, author);
		stmtBlogpost.setString(3, url);
		stmtBlogpost.execute();
		stmtBlogpost.close();
	}

	// get the id of the bookmark that was just added to database
	private int getLatestId() throws SQLException {
		Connection conn = database.getConnection();
		PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) from bookmark");
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			int id = rs.getInt("MAX(id)");
			database.close(stmt, conn, rs);
			return id;
		}
		database.close(stmt, conn, rs);
		return -1;
	}

	/**
	 * delete from bookmark table by id
	 *
	 * @param id
	 * @return true if id is found and deleted successfully
	 * @throws SQLException
	 */
	public boolean deleteFromBookmarkTable(int id) throws SQLException {
		Connection conn = database.getConnection();
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM bookmark where id=?");
		stmt.setInt(1, id);
		int success = stmt.executeUpdate();
		database.close(stmt, conn, null);
		return success == 1;
	}

	/**
	 * deleted from blogpost by id
	 *
	 * @param id
	 * @return true if id is found and deleted successfully
	 * @throws SQLException
	 */
	private boolean deleteFromBlogpostTable(int id) throws SQLException {
		Connection conn = database.getConnection();
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM blogpost where id=?");
		stmt.setInt(1, id);
		int success = stmt.executeUpdate();
		database.close(stmt, conn, null);
		return success == 1;
	}

	private boolean updateBookmark(String title, int id) throws SQLException {
		Connection conn = database.getConnection();
		PreparedStatement stmt = conn.prepareStatement("UPDATE bookmark SET title=? WHERE id=?");
		stmt.setString(1, title);
		stmt.setInt(2, id);
		int success = stmt.executeUpdate();
		database.close(stmt, conn, null);
		return success == 1;
	}

	private boolean updateBlogpost(String author, String url, int id) throws SQLException {
		Connection conn = database.getConnection();
		PreparedStatement stmt = conn.prepareStatement("UPDATE blogpost SET author=?,url=? WHERE id=?");
		stmt.setString(1, author);
		stmt.setString(2, url);
		stmt.setInt(3, id);
		int success = stmt.executeUpdate();
		database.close(stmt, conn, null);
		return success == 1;
	}
        
        /**
	 * Retrieves all information of Blogpost objects stored in the database and creates a list
	 * of corresponding Blogpost objects ordered alphabetically by title.
	 *
	 * @return alphabetically ordered (by title) list of Video objects.
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<Blogpost> findAllOrderByTitle() throws SQLException, ParseException {
		String s = "SELECT * FROM bookmark, blogpost WHERE bookmark.id = blogpost.id ORDER BY title";
		List<Blogpost> blogposts = new ArrayList<>();

		// Having the try-with-resources block only contain one command helps with test coverage.
		// For an example, only have database.getConnection inside of try(), and handle the rest later.
		// Remove these comments once done.
		try (Connection conn = database.getConnection()) {

			ResultSet rs = conn.prepareStatement(s).executeQuery();
			while (rs.next()) {
				Blogpost blogpost = constructBlogpostFromResultSet(rs);
				blogposts.add(blogpost);
			}
		}

		return blogposts;
	}

	/**
	 * Searches for Blogpost that have an URL that matches the whole or partial url given as parameter.
	 *
	 * @param url the url/partial url to be searched for
	 * @return List of Blogposts matching the given url
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<Blogpost> findByURL(String url) throws SQLException, ParseException {
		List<Blogpost> blogposts = new ArrayList<>();
		String s = "SELECT * FROM bookmark, blogpost WHERE blogpost.url LIKE ? "
				+ "AND bookmark.id = blogpost.id";

		try (Connection conn = database.getConnection(); PreparedStatement stmt = conn.prepareStatement(s)) {
			stmt.setString(1, "%" + url + "%");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Blogpost blogpost = constructBlogpostFromResultSet(rs);
				blogposts.add(blogpost);
			}

			rs.close();
		}

		return blogposts;
	}
}
