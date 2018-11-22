package ohtu.tools;

import ohtu.domain.Blogpost;

import java.util.Date;

public class Builder {

	public static Blogpost buildBlogpost(int id, String title, String author, String url, Date date) {
		Blogpost output = new Blogpost(id, title, date, author, url);
		return output;
	}
}
