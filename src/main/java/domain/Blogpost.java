package domain;

import java.util.Date;

/**
 * A container for the Blogspot data.
 */
public class Blogpost extends Bookmark {

	private String author;
	private String url;

	public Blogpost(int id, String title, Date addDate, char type, String author, String url) {
		super(id, title, addDate, type);
		this.author = author;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getAuthor() {
		return author;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
