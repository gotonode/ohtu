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
        List<Blogpost>blogposts=new ArrayList<>();
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn
                .prepareStatement("SELECT*FROM bookmark,blogpost WHERE bookmark.title=? AND bookmark.id=blogpost.id");
        stmt.setString(1, title);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Blogpost output = constructBlogpostFromResultSet(rs);
            //close(rs, stmt, conn);
            blogposts.add(output);
            
        }
        close(rs, stmt, conn);
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
        if(added.equals(blogpost)){
            return added;
        }
        return null;
    }

	@Override
	public boolean delete(Integer id) throws SQLException {
		return false;
	}

	@Override
	public boolean update(Blogpost blogpost) throws SQLException {
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
        close(rs, stmt, conn);
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
            close(rs, stmt, conn);
            return output;
        }
        close(rs, stmt, conn);
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
        PreparedStatement stmtBookmark = conn.prepareStatement("INSERT INTO bookmark(title,addDate,type) VALUES(?,date('now'),'B')");
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
            close(rs, stmt, conn);
            return id;
        }
        close(rs, stmt, conn);
        return -1;
    }

    //method to close connection to database,PreparesStatement and ResultSet
    private void close(ResultSet rs, PreparedStatement stmt, Connection conn) throws SQLException {
        rs.close();
        stmt.close();
        conn.close();
    }

}
