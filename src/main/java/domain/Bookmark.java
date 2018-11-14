package domain;

import java.util.Date;

/**
 * An abstract class used by Blogpost and others. Cannot be instantiated alone.
 */
public abstract class Bookmark {

	private int id;
	private String title;
	private Date addDate;
	private int type;
	// We'll add "isRead" property later.

	/**
	 * Create a new bookmark indirectly by calling the super.
	 * @param id ID for the object (from the database).
	 * @param title Title for the object.
	 * @param addDate When it was added to the database (convert this to Java's Date).
	 */
	Bookmark(int id, String title, Date addDate, int type) {
		this.id = id;
		this.title = title;
		this.addDate = addDate;
		this.type = type;
	}

	/**
	 * We can check if this instance of Bookmark is a Blogpost.
	 * @return True if it's a Blogpost, false otherwise.
	 */
	public boolean isBlogpost() {
		return this instanceof Blogpost;
	}

	// We'll add the rest later (video, article, book, podcast).

	public int getId() {
		return id;
	}

	public Date getAddDate() {
		return addDate;
	}

	public String getTitle() {
		return title;
	}

	public void setId(int id) {
		throw new IllegalStateException("This method should never be called?");
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
