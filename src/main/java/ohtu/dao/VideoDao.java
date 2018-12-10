package ohtu.dao;

import ohtu.database.Database;
import ohtu.domain.Video;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A DAO object for Video objects. VideoDao communicates with the database and
 * adds, edits and removes the Video objects saved in the database.
 */
public class VideoDao implements ObjectDao<Video, Integer> {

	private final Database database;

	/**
	 * Creates a new VideoDao object. The new VideoDao communicates with the given
	 * database and adds, edits and removes the data concerning Video-objects stored
	 * in the database.
	 *
	 * @param database the database to be communicated with
	 */
	public VideoDao(Database database) {
		this.database = database;
	}

	@Override
	public List<Video> findAll() throws SQLException, ParseException {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, video WHERE bookmark.id = video.id");
    
            List<Video> videos = createAndHandleResulSet(stmt);
            database.close(stmt, conn, null);

            return videos;
	}

	@Override
	public Video findById(Integer id) throws SQLException, ParseException {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, video WHERE bookmark.id = ? "
                    + "AND bookmark.id = video.id");
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Video video = constructVideoFromResultSet(rs);
                database.close(stmt, conn, rs);
                return video;
            }

            database.close(stmt, conn, rs);
            return null;
	}

	@Override
	public List<Video> findByTitle(String title) throws SQLException, ParseException {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, video WHERE bookmark.title "
                    + "LIKE ? AND bookmark.id = video.id");
            stmt.setString(1, "%" + title + "%");
            
            List<Video> videos = createAndHandleResulSet(stmt);
            
            database.close(stmt, conn,null);
            return videos;
	}

	@Override
	public Video create(Video video) throws SQLException, ParseException {
            Connection conn = database.getConnection();
            PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO bookmark (title, type, user_id) VALUES (?, 'V', 0)");
            stmt1.setString(1, video.getTitle());
            stmt1.execute();

            int id = getLatestId();
            if (id == -1) {
                database.close(stmt1, conn,null);
                return null; // Something went wrong when adding the new Bookmark.
            }
            
            PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO video (id, url) VALUES (?, ?)");
            stmt2.setInt(1, id);
            stmt2.setString(2, video.getUrl());
            stmt2.execute();

            Video added = findById(id);
            if (added.equals(video)) {
                return added;
            }

            return null;
	}

	@Override
	public boolean delete(Integer id) throws SQLException {
            Connection conn = database.getConnection();
            PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM bookmark WHERE id = ?");
            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM video WHERE id = ?");
            stmt1.setInt(1, id);
            stmt2.setInt(1, id);
            
            int updated = 0;
            if (stmt1.executeUpdate() == 1) { /* Video is deleted only if the Bookmark was successfully deleted */
                updated = stmt2.executeUpdate();
                stmt2.close();
            }
            
            database.close(stmt1, conn,null);
            return updated == 1;
	}

	@Override
	public boolean update(Video video) throws SQLException {
            Connection conn = database.getConnection();
            PreparedStatement stmt1 = conn.prepareStatement("UPDATE bookmark SET title = ? WHERE id = ?");
            PreparedStatement stmt2 = conn.prepareStatement("UPDATE video SET url = ? WHERE id = ?");
            stmt1.setString(1, video.getTitle());
            stmt1.setInt(2, video.getId());
            stmt2.setString(1, video.getUrl());
            stmt2.setInt(2, video.getId());
            
            int updated = 0;
            if (stmt1.executeUpdate() == 1) { /* Video is updated only if the Bookmark was successfully updated */
                updated = stmt2.executeUpdate();
                stmt2.close();
            }

            database.close(stmt1, conn,null);
            return updated == 1;
	}

	/**
	 * Constructs a Video object from a ResultSet. The fields of the created Video object match
	 * the corresponding data found in the ResultSet.
	 *
	 * @param rs the ResultSet containing the needed information
	 * @return A Video object constructed from the data in the given ResultSet
	 * @throws SQLException
	 * @throws ParseException
	 */
	private Video constructVideoFromResultSet(ResultSet rs) throws SQLException, ParseException {
            Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("addDate")).getTime());
            Video video = new Video(rs.getInt("id"), rs.getString("title"), date, rs.getString("url"));

            return video;
	}

	/**
	 * Returns the last id added to the database.
	 *
	 * @return latest used id or -1 if unable to retrieve id.
	 * @throws SQLException
	 */
	private int getLatestId() throws SQLException {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) FROM bookmark");
            ResultSet rs = stmt.executeQuery();
            
            int latest = -1;
            
            if (rs.next()) {
                latest = rs.getInt("MAX(id)");
            }
            
           database. close(stmt, conn, rs);
            return latest;
	}

	/**
	 * Retrieves all information of Video objects stored in the database and creates a list
	 * of corresponding Video objects ordered alphabetically by title.
	 *
	 * @return alphabetically ordered (by title) list of Video objects.
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<Video> findAllOrderByTitle() throws SQLException, ParseException {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM bookmark, video WHERE bookmark.id = video.id ORDER BY title");
            
            List<Video> videos = createAndHandleResulSet(stmt);
            
            database.close(stmt, conn,null);

            return videos;
	}

	/**
	 * Searches for Video objects that have an URL that matches the whole or partial
	 * url given as parameter.
	 *
	 * @param url the url/partial url to be searched for
	 * @return List of Videos matching the given url
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<Video> findByURL(String url) throws SQLException, ParseException {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, video WHERE video.url LIKE ? "
                    + "AND bookmark.id = video.id");
            stmt.setString(1, "%" + url + "%");
            
            List<Video> videos = createAndHandleResulSet(stmt);
            
            database.close(stmt, conn,null);
            
            return videos;
	}
        
        /**
         * Executes the PreparedStatement given as parameter and creates a list of Video objects from the data
         * that was recieved from the database as a ResultSet in response to executing the PreparedStatement.
         * 
         * @param stmt the Preparedstatement to be executed
         * @return List of Video object gotten as the result of the executed PreparedStatement
         * @throws SQLException
         * @throws ParseException 
         */
        private List<Video> createAndHandleResulSet(PreparedStatement stmt) throws SQLException, ParseException {
            List<Video> videos = new ArrayList<>();
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                    Video video = constructVideoFromResultSet(rs);
                    videos.add(video);
            }

            rs.close();
            return videos;
        }
        

}
