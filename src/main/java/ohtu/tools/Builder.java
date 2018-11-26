package ohtu.tools;

import ohtu.domain.Blogpost;
import ohtu.domain.Video;

import java.util.Date;

public class Builder {

	public static Blogpost buildBlogpost(int id, String title, String author, String url, Date date) {
		return new Blogpost(id, title, date, author, url);
	}

	public static Video buildVideo(int id, String title, String url, Date date) {
		return new Video(id, title, date, url);
	}
}
