package ohtu.domain;

import java.util.Date;

public class Video extends Bookmark {

	private String url;

	Video(int id, String title, Date addDate, String url) {
		super(id, title, addDate, 'V'); // V for video.
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

	public void setUrl(String url) {
		this.url = url;
	}
}
