package ohtu.tools;

import ohtu.domain.Blogpost;
import ohtu.domain.Book;
import ohtu.domain.Video;

import java.util.Date;

/**
 * A utility class to create new objects. Cannot be instantiated.
 */
public class BookmarkBuilder {

	private BookmarkBuilder() {
		throw new IllegalStateException("Builder should not be instantiated.");
	}

	/**
	 * Builds a new Blogpost.
	 * @param id A unique ID for the Bookmark.
	 * @param title Title for the Bookmark.
	 * @param author Who authored this Bookmark.
	 * @param url URL linking to a resource.
	 * @param date Date, when it was added to the database.
	 * @return The new Blogpost.
	 */
	public static Blogpost buildBlogpost(int id, String title, String author, String url, Date date) {
		return new Blogpost(id, title, date, author, url);
	}

	/**
	 * Builds a new Blogpost. Will place -1 for ID and null for addDate.
	 * @param title Title for the Bookmark.
	 * @param author Who authored this Bookmark.
	 * @param url URL linking to a resource.
	 * @return The new Blogpost.
	 */
	public static Blogpost buildBlogpostNonDatabase(String title, String author, String url) {
		return buildBlogpost(-1, title, url, author, null);
	}

	/**
	 * Builds a new Video.
	 * @param id A unique ID for the Bookmark.
	 * @param title Title for the Bookmark.
	 * @param url URL linking to a resource.
	 * @param date Date, when it was added to the database.
	 * @return The new Video.
	 */
	public static Video buildVideo(int id, String title, String url, Date date) {
		return new Video(id, title, date, url);
	}

	/**
	 * Builds a new Video. Will place -1 for ID and null for addDate.
	 * @param title Title for the Bookmark.
	 * @param url URL linking to a resource.
	 * @return The new Video.
	 */
	public static Video buildVideoNonDatabase(String title, String url) {
		return buildVideo(-1, title, url, null);
	}

	/**
	 * Builds a new Book.
	 * @param id A unique ID for the Bookmark.
	 * @param title Title for the Bookmark.
	 * @param date Date, when it was added to the database.
	 * @param isbn The ISBN for the book.
	 * @return The new Video.
	 */
	public static Book buildBook(int id, String title, String author, String isbn, Date date) {
		return new Book(id, title, date, author, isbn);
	}

	/**
	 * Builds a new Book. Will place -1 for ID and null for addDate.
	 * @param title Title for the Bookmark.
	 * @param isbn The ISBN for the book.
	 * @return The new Video.
	 */
	public static Book buildBookNonDatabase(String title, String author, String isbn) {
		return buildBook(-1, title, author, isbn, null);
	}
}
