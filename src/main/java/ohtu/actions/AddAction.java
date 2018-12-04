package ohtu.actions;

import ohtu.dao.BlogpostDao;
import ohtu.dao.BookDao;
import ohtu.dao.BookmarkDao;
import ohtu.dao.VideoDao;
import ohtu.domain.Blogpost;
import ohtu.domain.Book;
import ohtu.domain.Video;
import ohtu.io.IO;
import ohtu.main.Main;
import ohtu.tools.Builder;
import ohtu.ui.UiController;

import java.util.Date;

public class AddAction extends Action {

	private UiController uiController;
	private BlogpostDao blogpostDao;
	private VideoDao videoDao;
	private BookDao bookDao;

	public AddAction(IO io, UiController uiController, BlogpostDao blogpostDao, VideoDao videoDao, BookDao bookDao) {
		super(io);
		this.uiController = uiController;
		this.blogpostDao = blogpostDao;
		this.videoDao = videoDao;
		this.bookDao = bookDao;
	}

	/**
	 * Asks the user for title, author and url, and creates a new Blogpost by using Builder.
	 * Then tries to add the new Blogpost into the database. Tells the user if the operation succeeded or not.
	 */
	@Override
	public void act() {

		uiController.printEmptyLine();

		super.getIo().println("What kind of a bookmark you'd like to create?");
		super.getIo().println("Choose 'B' for Blogpost, 'V' for video, 'K' for book.");

		char type = uiController.askForCharacter(new char[]{'B', 'V', 'K'}, "Bookmark type");

		switch (type) {
			case 'B':
				createBlogpost();
				break;
			case 'V':
				createVideo();
				break;
			case 'K':
				createBook();
				break;
			default:
				throw new RuntimeException("Got an illegal value.");
		}

		uiController.printEmptyLine();
	}

	// Contains code repetition, will be fixed later.
	private void createVideo() {
		String[] values = uiController.askVideoData();
		String title = values[0]; // Care should be taken so as not to mix these indices up.
		String url = values[1].replaceAll("\\s","");

		// Database will handle assigning correct ID to the object, and decides the addDate value.
		Date date = null; // This is here just as a reference that it is null.
		int id = -1; // When creating new Bookmarks from user input, assign -1 as the ID.

		Video newVideo = Builder.buildVideo(id, title, url, date);
		try {
			final Video success = videoDao.create(newVideo);
			if (success != null) {
				uiController.addSuccess(newVideo.getClass().getSimpleName());
			} else {
				uiController.addFailure();
			}
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
		}
	}

	// Contains code repetition, will be fixed later.
	private void createBlogpost() {
		String[] values = uiController.askBlogpostData();
		String title = values[0]; // Care should be taken so as not to mix these indices up.
		String author = values[1];
		String url = values[2].replaceAll("\\s", "");

		// Database will handle assigning correct ID to the object, and decides the addDate value.
		Date date = null; // This is here just as a reference that it is null.
		int id = -1; // When creating new Bookmarks from user input, assign -1 as the ID.

		Blogpost newBlogpost = Builder.buildBlogpost(id, title, author, url, date);
		try {
			final Blogpost success = blogpostDao.create(newBlogpost);
			if (success != null) {
				uiController.addSuccess(newBlogpost.getClass().getSimpleName());
			} else {
				uiController.addFailure();
			}
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
		}
	}

	private void createBook() {
		String[] values = uiController.askBookData();
		String title = values[0]; // Care should be taken so as not to mix these indices up.
		String author = values[1];
		String isbn = values[2];

		// Database will handle assigning correct ID to the object, and decides the addDate value.
		Date date = null; // This is here just as a reference that it is null.
		int id = -1; // When creating new Bookmarks from user input, assign -1 as the ID.

		Book newBook = Builder.buildBook(id, title, author, isbn, date);
		try {
			final Book success = bookDao.create(newBook);
			if (success != null) {
				uiController.addSuccess(newBook.getClass().getSimpleName());
			} else {
				uiController.addFailure();
			}
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
		}
	}
}
