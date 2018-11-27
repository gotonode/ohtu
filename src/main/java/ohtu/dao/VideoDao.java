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
     * @param database the database to be communicated with
    */
    public VideoDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Video> findAll() throws SQLException, ParseException {
        List<Video> videos = new ArrayList<>();
        String s = "SELECT * FROM bookmark, video WHERE bookmark.id = video.id";
        
        try (Connection conn = database.getConnection(); ResultSet rs = conn.prepareStatement(s).executeQuery()) {
            while (rs.next()) {
                videos.add(constructVideoFromResultSet(rs));
            }
        }
        
        return videos;
    }

    @Override
    public Video findById(Integer id) throws SQLException, ParseException {
        String s = "SELECT * FROM bookmark, video WHERE bookmark.id = ? AND bookmark.id = video.id";
        
        try (Connection conn = database.getConnection(); PreparedStatement stmt = conn.prepareStatement(s)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Video video = constructVideoFromResultSet(rs);
                rs.close();
                return video;
            }
            
            rs.close();
        }
        
        return null;
    }

    @Override
    public List<Video> findByTitle(String title) throws SQLException, ParseException {
        List<Video> videos = new ArrayList<>();
        String s = "SELECT * FROM bookmark, video WHERE bookmark.title = ? AND bookmark.id = video.id";
        
        try (Connection conn = database.getConnection(); PreparedStatement stmt = conn.prepareStatement(s)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Video video = constructVideoFromResultSet(rs);
                videos.add(video);
            }
            
            rs.close();
        }
        
        return videos;
    }

    @Override
    public Video create(Video video) throws SQLException, ParseException {
        String s1 = "INSERT INTO bookmark (title, type) VALUES (?, 'V')";
        String s2 = "INSERT INTO video (id, url) VALUES (?, ?)";
        
        try (Connection conn = database.getConnection(); PreparedStatement stmt1 = conn.prepareStatement(s1);
                PreparedStatement stmt2 = conn.prepareStatement(s2)) {
            stmt1.setString(1, video.getTitle());
            stmt1.execute();
            
            int id = getLatestId();
            if (id == -1) {
                return null; // Something went wrong when adding the new Bookmark.
            }
            
            stmt2.setInt(1, id);
            stmt2.setString(2, video.getUrl());
            stmt2.execute();
            
            Video added = findById(id);
            if (added.equals(video)) {
                return added;
            }
        }
        
        return null;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String s1 = "DELETE FROM bookmark WHERE id = ?";
        String s2 = "DELETE FROM blogpost WHERE id = ?";
        
        try (Connection conn = database.getConnection(); PreparedStatement stmt1 = conn.prepareStatement(s1);
                PreparedStatement stmt2 = conn.prepareStatement(s2)) {
            stmt1.setInt(1, id);
            stmt2.setInt(1, id);
            
            /* Blogpost is deleted only if the Bookmark was successfully deleted */ 
            if (stmt1.executeUpdate() == 1) {
                return stmt2.executeUpdate() == 1;
            }
        }
        
        return false;
    }

    @Override
    public boolean update(Video video) throws SQLException {
        String s1 = "UPDATE bookmark SET title = ? WHERE id = ?";
        String s2 = "UPDATE video SET url = ? WHERE id = ?";
        
        try (Connection conn = database.getConnection(); PreparedStatement stmt1 = conn.prepareStatement(s1);
                PreparedStatement stmt2 = conn.prepareStatement(s2)) {
            stmt1.setString(1, video.getTitle());
            stmt1.setInt(2, video.getId());
            stmt2.setString(1, video.getUrl());
            stmt2.setInt(2, video.getId());
            
            if (stmt1.executeUpdate() == 1) {
                return stmt2.executeUpdate() == 1;
            }
        }
        
        return false;
    }
    
    private Video constructVideoFromResultSet(ResultSet rs) throws SQLException, ParseException {
        Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("addDate")).getTime());
        Video video = new Video(rs.getInt("id"), rs.getString("title"), date, rs.getString("url"));
        
        return video;
    }
    
    private int getLatestId() throws SQLException{
        String s = "SELECT MAX(id) FROM bookmark";
        
        try (Connection conn = database.getConnection(); ResultSet rs = conn.prepareStatement(s).executeQuery()) {
            if (rs.next()) {
                return rs.getInt("MAX(id)");
            }
        }
        
        return -1;
    }
    
    public List<Video> findAllOrderByTitle() throws SQLException, ParseException {
        String s = "SELECT * FROM bookmark, video WHERE bookmark.id = video.id ORDER BY title";
        List<Video> videos = new ArrayList<>();
        
        try (Connection conn = database.getConnection(); ResultSet rs = conn.prepareStatement(s).executeQuery()) {
            while (rs.next()) {
                Video video = constructVideoFromResultSet(rs);
                videos.add(video);
            }
        }

        return videos;
    }
    
}
