package ohtu.dao;

import ohtu.database.Database;
import ohtu.domain.Video;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A DAO object for Video objects. VideoDao communicates with the database and
 * adds, edits and removes the Video objects saved in the database.
 */
public class VideoDao extends ObjectDaoTemplate<Video> {
    
    /**
     * Creates a new VideoDao object. The new VideoDao communicates with the
     * given database and adds, edits and removes the data concerning
     * Video-objects stored in the database.
     *
     * @param database the database to be communicated with
     */
    public VideoDao(Database database) {
        super(database);
    }

    @Override
    public Video create(Video video) throws SQLException, ParseException {
        Video added = null;
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO bookmark (title, type, user_id) VALUES (?, 'V', ?)");
        stmt.setString(1, video.getTitle());
        stmt.setInt(2, user_id);
        stmt.execute();

        int id = getLatestId();
        if (id != -1) { // Something went wrong when adding the new Bookmark.
            added = createVideo(video, id);
        }
        
        database.close(stmt, conn, null);
        return added;
    }
    
    private Video createVideo(Video video, int id) throws SQLException, ParseException {
        Video added = null;
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO video (id, url) VALUES (?, ?)");
        stmt.setInt(1, id);
        stmt.setString(2, video.getUrl());
        stmt.execute();

        Video newVideo = findById(id);
        if (newVideo.equals(video)) {
            added = newVideo;
        }
        
        database.close(stmt, conn, null);
        return added;
    }

    @Override
    public boolean update(Video video) throws SQLException {
        boolean updated = false;
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE bookmark SET title = ? WHERE id = ?");
        stmt.setString(1, video.getTitle());
        stmt.setInt(2, video.getId());
        
        if (stmt.executeUpdate() == 1) {  /* Video is updated only if the Bookmark was successfully updated */
            updated = updateVideo(video);
        }

        database.close(stmt, conn, null);
        return updated;
    }
    
    private boolean updateVideo(Video video) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE video SET url = ? WHERE id = ?");
        stmt.setString(1, video.getUrl());
        stmt.setInt(2, video.getId());
        boolean updated = stmt.executeUpdate() == 1;
        
        database.close(stmt, conn, null);
        return updated;
    }

    @Override
    public Video constructBookmarkFromResultSet(ResultSet rs) throws SQLException, ParseException {
        Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("addDate")).getTime());
        Video video = new Video(rs.getInt("id"), rs.getString("title"), date, rs.getString("url"));

        return video;
    }

    @Override
    protected PreparedStatement createStmtWhenFindAll(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, video WHERE bookmark.id = video.id "
                + "AND bookmark.user_id = ?");
        stmt.setInt(1, user_id);
        return stmt;
    }

    @Override
    protected PreparedStatement createStmtWhenDeleteFromCertainTable(Connection conn) throws SQLException {
        return conn.prepareStatement("DELETE FROM video WHERE id = ?");
    }

    @Override
    protected PreparedStatement createStmtWhenFindById(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, video WHERE bookmark.id = ? "
                + "AND video.id=bookmark.id AND bookmark.user_id = ?");
        stmt.setInt(2, user_id);
        return stmt;
    }

    @Override
    protected PreparedStatement createStmtWhenFindByTitle(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, video WHERE bookmark.title "
                + "LIKE ? AND bookmark.id = video.id AND bookmark.user_id = ?");
        stmt.setInt(2, user_id);
        return stmt;
    }

    @Override
    protected PreparedStatement createStmtWhenFindByUrl(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, video WHERE video.url LIKE ? "
                + "AND bookmark.id = video.id AND bookmark.user_id = ?");
        stmt.setInt(2, user_id);
        return stmt;
    }

    @Override
    protected PreparedStatement createStmtWhenFindAllOrderByTitle(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookmark, video WHERE bookmark.id = video.id "
                + "AND bookmark.user_id = ? ORDER BY title");
        stmt.setInt(1, user_id);
        return stmt;
    }
    
    
}
