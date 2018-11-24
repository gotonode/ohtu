package ohtu.dao;

import ohtu.domain.Video;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class VideoDao implements ObjectDao<Video, Integer> {

	@Override
	public List<Video> findAll() throws SQLException, ParseException {
		return null;
	}

	@Override
	public Video findById(Integer id) throws SQLException, ParseException {
		return null;
	}

	@Override
	public List<Video> findByTitle(String title) throws SQLException, ParseException {
		return null;
	}

	@Override
	public Video create(Video video) throws SQLException, ParseException {
		return null;
	}

	@Override
	public boolean delete(Integer id) throws SQLException {
		return false;
	}

	@Override
	public boolean update(Video video) throws SQLException {
		return false;
	}
}
