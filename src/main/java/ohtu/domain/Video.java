package ohtu.domain;

import java.util.Date;

public class Video extends Bookmark {

	private String url;

	Video(int id, String title, Date addDate, char type, String url) {
		super(id, title, addDate, type);
		this.url = url;
	}

	@Override
	public String toString() {
		return "Video{" +
				"url='" + url + '\'' +
				'}';
	}

	public String getUrl() {
		return url;
	}
}
