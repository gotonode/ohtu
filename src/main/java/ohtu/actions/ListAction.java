package ohtu.actions;

import ohtu.dao.BookmarkDao;
import ohtu.domain.Blogpost;
import ohtu.domain.Book;
import ohtu.domain.Bookmark;
import ohtu.domain.Video;
import ohtu.io.IO;
import ohtu.main.Main;
import ohtu.ui.Color;
import ohtu.ui.UiController;

import java.util.ArrayList;
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
				// Since Bookmarks may have unique fields, each must be handled separately.
				if (bookmark.isBlogpost()) {
					ouputBlogpost((Blogpost) bookmark);
				} else if (bookmark.isVideo()) {
					outputVideo((Video) bookmark);
				} else if (bookmark.isBook()) {
					outputBook((Book) bookmark);
				}
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

			// Loop through all of the Bookmarks and ask them to be printed to the console.
			assert bookmarks != null;
			for (Bookmark bookmark : bookmarks) {
				// Since Bookmarks may have unique fields, each must be handled separately.
				if (bookmark instanceof Blogpost) {
					ouputBlogpost((Blogpost) bookmark);
				} else if (bookmark instanceof Video) {
					outputVideo((Video) bookmark);
				} else if (bookmark instanceof Book) {
					outputBook((Book) bookmark);
				}
			}
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
		}
	}

	private void outputBook(Book book) {
		ArrayList<String> printableData = new ArrayList<>();
		printableData.add(book.getClass().getSimpleName());
		printableData.add(book.getAddDate().toString());
		printableData.add(book.getTitle());
		printableData.add(book.getAuthor());
		printableData.add(book.getIsbn());
		printableData.add(String.valueOf(book.getId()));

		uiController.printBook(printableData);
	}

	private void outputVideo(Video video) {
		ArrayList<String> printableData = new ArrayList<>();
		printableData.add(video.getClass().getSimpleName());
		printableData.add(video.getAddDate().toString());
		printableData.add(video.getTitle());
		printableData.add(video.getUrl());
		printableData.add(String.valueOf(video.getId()));

		uiController.printVideo(printableData);
	}

	private void ouputBlogpost(Blogpost blogpost) {
		ArrayList<String> printableData = new ArrayList<>();
		printableData.add(blogpost.getClass().getSimpleName());
		printableData.add(blogpost.getAddDate().toString());
		printableData.add(blogpost.getTitle());
		printableData.add(blogpost.getAuthor());
		printableData.add(blogpost.getUrl());
		printableData.add(String.valueOf(blogpost.getId()));

		uiController.printBlogpost(printableData);
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
