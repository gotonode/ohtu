package ohtu.actions;

import ohtu.dao.BlogpostDao;
import ohtu.dao.BookDao;
import ohtu.dao.VideoDao;
import ohtu.domain.Blogpost;
import ohtu.domain.Book;
import ohtu.domain.Video;
import ohtu.io.IO;
import ohtu.main.Main;
import ohtu.tools.BookmarkBuilder;
import ohtu.ui.Color;
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
		super.getIo().println("Choose '" + Color.commandText('B') + "' for Blogpost, '" + Color.commandText('V') + "' for Video, '" + Color.commandText('K') + "' for Kook.");

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

	private void createVideo() {
		String[] values = uiController.askVideoData();
		String title = values[0];
		String url = values[1].replaceAll("\\s","");

		Date date = null;
		int id = -1;

		Video newVideo = BookmarkBuilder.buildVideo(id, title, url, date);
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

	private void createBlogpost() {
		String[] values = uiController.askBlogpostData();
		String title = values[0];
		String author = values[1];
		String url = values[2].replaceAll("\\s", "");

		Date date = null;
		int id = -1;

		Blogpost newBlogpost = BookmarkBuilder.buildBlogpost(id, title, author, url, date);
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
		String title = values[0];
		String author = values[1];
		String isbn = values[2];

		Date date = null;
		int id = -1;

		Book newBook = BookmarkBuilder.buildBook(id, title, author, isbn, date);
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
