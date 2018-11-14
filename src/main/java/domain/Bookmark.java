package domain;

import java.util.Date;

/**
 * An abstract class used by Blogpost and others. Cannot be instantiated alone.
 */
public abstract class Bookmark {

	private int id;
	private String title;
	private Date addDate;
	// We'll add "isRead" property later.

	// Actually we don't need a "type" property here since that is inferred from the database (1-to-1 relation).

	/**
	 * Create a new bookmark indirectly by calling the super.
	 * @param id ID for the object (from the database).
	 * @param title Title for the object.
	 * @param addDate When it was added to the database (convert this to Java's Date).
	 */
	Bookmark(int id, String title, Date addDate) {
		this.id = id;
		this.title = title;
		this.addDate = addDate;
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
}
