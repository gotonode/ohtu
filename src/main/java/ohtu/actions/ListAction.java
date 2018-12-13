package ohtu.actions;

import ohtu.dao.BookmarkDao;
import ohtu.domain.Blogpost;
import ohtu.domain.Book;
import ohtu.domain.Bookmark;
import ohtu.domain.Video;
import ohtu.enums.Keys;
import ohtu.io.IO;
import ohtu.main.Main;
import ohtu.enums.Color;
import ohtu.ui.UiController;

import java.util.HashMap;
import java.util.List;

public class ListAction extends Action {

	private UiController uiController;
	private BookmarkDao bookmarkDao;

	private enum OrderBy {
		ID, TITLE
	}

	public ListAction(IO io, UiController uiController, BookmarkDao bookmarkDao) {
		super(io);
		this.uiController = uiController;
		this.bookmarkDao = bookmarkDao;
	}

	@Override
	public void act() {

		OrderBy orderBy = getOrderBy();

		uiController.printEmptyLine();
		super.getIo().println("Listing all bookmarks...");
		uiController.printEmptyLine();

		try {
			if (bookmarkDao.findAll().isEmpty()) {
				uiController.printNoBookmarks();
				uiController.printEmptyLine();
				return;
			}
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
		}

		try {
			List<Bookmark> bookmarks = null;
			if (orderBy == OrderBy.ID) {
				bookmarks = bookmarkDao.findAll();
			} else if (orderBy == OrderBy.TITLE) {
				bookmarks = bookmarkDao.findAllOrderByTitle();
			}

			// Loop through all of the Bookmarks and ask them to be printed to the console.
			assert bookmarks != null;
			for (Bookmark bookmark : bookmarks) {
				outputBookmark(bookmark);
			}
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
		}

		// No need to print an empty line here since the listed bookmarks have an empty line at the end.
	}

	public void search() {

		uiController.printEmptyLine();

		super.getIo().println("Would you like to search for bookmarks by title or by URL? Choose '"
				+ Color.commandText('T') + "' or '" + Color.commandText('U') + "'.");

		char c = uiController.askForCharacter(new char[]{'T', 'U'}, "Title or URL");

		String searchTerm = "";

		if (c == 'T') {
			searchTerm = uiController.askForString("Title to search for:", false);
		} else if (c == 'U') {
			searchTerm = uiController.askForString("URL to search for:", false);
		} else {
			throw new IllegalArgumentException("Illegal argument.");
		}

		try {
			List<Bookmark> bookmarks;
			if (c == 'T') {
				bookmarks = bookmarkDao.findByTitle(searchTerm);
			} else {
				bookmarks = bookmarkDao.findByURL(searchTerm);
			}

			if (bookmarks.isEmpty()) {
				super.getIo().println("No bookmarks found with those search terms.");
				uiController.printEmptyLine();
				return;
			}

			uiController.printEmptyLine();

			// Loop through all of the Bookmarks and ask them to be printed to the console.
			assert bookmarks != null;
			for (Bookmark bookmark : bookmarks) {
				outputBookmark(bookmark);
			}
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
		}
	}

	private void outputBookmark(Bookmark bookmark) {
		HashMap<Keys, String> printableData = new HashMap<>();

		printableData.put(Keys.ID, String.valueOf(bookmark.getId()));
		printableData.put(Keys.Class, bookmark.getClass().getSimpleName());
		printableData.put(Keys.Date, bookmark.getAddDate().toString());
		printableData.put(Keys.Title, bookmark.getTitle());

		if (bookmark.isBook()) {
			Book book = (Book)bookmark;
			printableData.put(Keys.Author, book.getAuthor());
			printableData.put(Keys.ISBN, book.getIsbn());
		} else if (bookmark.isBlogpost()) {
			Blogpost blogpost = (Blogpost)bookmark;
			printableData.put(Keys.Author, blogpost.getAuthor());
			printableData.put(Keys.URL, blogpost.getUrl());
		} else if (bookmark.isVideo()) {
			Video video = (Video)bookmark;
			printableData.put(Keys.URL, video.getUrl());
		}

		uiController.printBook(printableData);
	}

	private OrderBy getOrderBy() {
		uiController.printEmptyLine();

		super.getIo().println("How to sort your bookmarks? Choose '" + Color.commandText('I') + "' for ID, '" + Color.commandText('T') + "' for title.");

		char c = uiController.askForCharacter(new char[]{'I', 'T'}, "How to order");

		OrderBy orderBy = null;

		if (c == 'I') {
			orderBy = OrderBy.ID; // Strictly not necessary but here for clarity.
		} else if (c == 'T') {
			orderBy = OrderBy.TITLE;
		}

		return orderBy;
	}
}
