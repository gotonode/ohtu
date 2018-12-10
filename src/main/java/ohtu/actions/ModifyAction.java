package ohtu.actions;

import ohtu.dao.BlogpostDao;
import ohtu.dao.BookDao;
import ohtu.dao.BookmarkDao;
import ohtu.dao.VideoDao;
import ohtu.database.Database;
import ohtu.domain.Blogpost;
import ohtu.domain.Book;
import ohtu.domain.Bookmark;
import ohtu.domain.Video;
import ohtu.io.IO;
import ohtu.main.Main;
import ohtu.tools.Validator;
import ohtu.ui.UiController;

import java.sql.SQLException;

public class ModifyAction extends Action {

	private UiController uiController;
	private Database database;
	private BookmarkDao bookmarkDao;
	private BlogpostDao blogpostDao;
	private VideoDao videoDao;
	private BookDao bookDao;

	public ModifyAction(IO io, UiController uiController, Database database, BookmarkDao bookmarkDao, BlogpostDao blogpostDao, VideoDao videoDao, BookDao bookDao) {
		super(io);
		this.uiController = uiController;
		this.database = database;
		this.bookmarkDao = bookmarkDao;
		this.blogpostDao = blogpostDao;
		this.videoDao = videoDao;
		this.bookDao = bookDao;
	}

	@Override
	public void act() {

		uiController.printEmptyLine();

		try {
			if (bookmarkDao.findAll().isEmpty()) {
				uiController.printNoBookmarks();
				uiController.printEmptyLine();
				return;
			}

		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
			return;
		}

		int id = uiController.askForIdToModify();

		// If the user tries to modify a Bookmark that he/she doesn't own, abort the modify operation.
		if (!database.userOwnsBookmarkWithId(id)) {
			uiController.printAccessDenied();
			return;
		}

		Bookmark bookmark;
		try {
			bookmark = bookmarkDao.findById(id);
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
			return;
		}

		if (bookmark == null) {
			uiController.printBookmarkNotFound();
			uiController.printEmptyLine();
			return;
		}

		super.getIo().println("Enter new values. Leave blank to use current value. You can't change the ID, date added or type.");

		uiController.printEmptyLine();

		super.getIo().println("Current title: " + bookmark.getTitle());
		String newTitle = uiController.askForString("New title: ", true);
		if (!newTitle.isEmpty()) {
			bookmark.setTitle(newTitle);
		}

		if (bookmark instanceof Blogpost) {
			modifyBlogpost((Blogpost) bookmark);
		} else if (bookmark instanceof Video) {
			modifyVideo((Video) bookmark);
		} else if (bookmark instanceof Book) {
			modifyBook((Book) bookmark);
		}

		boolean success = false;

		try {
			if (bookmark instanceof Blogpost) {
				success = blogpostDao.update((Blogpost) bookmark);
			} else if (bookmark instanceof Video) {
				success = videoDao.update((Video) bookmark);
			} else if (bookmark instanceof Book) {
				success = bookDao.update((Book) bookmark);
			}
		} catch (SQLException e) {
			Main.LOG.warning(e.getMessage());
			return;
		}


		if (success) {
			super.getIo().println("Successfully updated your bookmark.");
		} else {
			super.getIo().println("Couldn't update your bookmark. Please try again.");
		}

		uiController.printEmptyLine();
	}

	private void modifyBlogpost(Blogpost blogpost) {

		super.getIo().println("Current author: " + blogpost.getAuthor());
		String newAuthor = uiController.askForString("New author: ", true);
		if (!newAuthor.isEmpty()) {
			blogpost.setAuthor(newAuthor);
		}

		while (true) {
			super.getIo().println("Current URL: " + blogpost.getUrl());
			String newUrl = uiController.askForString("New URL: ", true).replaceAll("\\s", "");
			boolean isValidUrl = Validator.isValidUrl(newUrl);

			if (newUrl.isEmpty()) {
				break;
			}

			if (!isValidUrl) {
				uiController.printUrlNotValid();
				continue;
			}

			blogpost.setUrl(newUrl);

			break;
		}
	}

	private void modifyVideo(Video video) {

		// Code repetition here. Feel free to reduce it.
		while (true) {
			super.getIo().println("Current URL: " + video.getUrl());
			String newUrl = uiController.askForString("New URL: ", true).replaceAll("\\s", "");
			boolean isValidUrl = Validator.isValidUrl(newUrl);

			if (newUrl.isEmpty()) {
				break;
			}

			if (!isValidUrl) {
				uiController.printUrlNotValid();
				continue;
			}

			video.setUrl(newUrl);

			break;
		}
	}

	private void modifyBook(Book book) {

		// Code repetition here. Feel free to reduce it.
		super.getIo().println("Current author: " + book.getAuthor());
		String newAuthor = uiController.askForString("New author: ", true);
		if (!newAuthor.isEmpty()) {
			book.setAuthor(newAuthor);
		}

		super.getIo().println("Current ISBN: " + book.getAuthor());
		String newIsbn = uiController.askForString("New ISBN: ", true);
		if (!newIsbn.isEmpty()) {
			book.setIsbn(newIsbn);
		}
	}
}
