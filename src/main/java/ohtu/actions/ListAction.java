package ohtu.actions;

import ohtu.dao.BookmarkDao;
import ohtu.domain.Blogpost;
import ohtu.domain.Bookmark;
import ohtu.domain.Video;
import ohtu.io.IO;
import ohtu.main.Main;
import ohtu.ui.UiController;

import java.util.ArrayList;
import java.util.List;

public class ListAction extends Action {

	private UiController uiController;
	private BookmarkDao bookmarkDao;

	public ListAction(IO io, UiController uiController, BookmarkDao bookmarkDao) {
		super(io);
		this.uiController = uiController;
		this.bookmarkDao = bookmarkDao;
	}

	@Override
	public void act() {

		uiController.printEmptyLine();

		super.getIo().println("How to sort your bookmarks? Choose 'I' for ID, 'T' for title.");

		char c = uiController.askForCharacter(new char[]{'I', 'T'}, "How to order");

		if (c == 'I') {
			// Order by ID.
		} else if (c == 'T') {
			// Order by title.
		}

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
			List<Bookmark> bookmarks = bookmarkDao.findAll();
			for (Bookmark bookmark : bookmarks) {
				if (bookmark instanceof Blogpost) {
					Blogpost blogpost = (Blogpost) bookmark;

					// Create an ArrayList of Strings that contains the printable data IN ORDER.
					ArrayList<String> printableData = new ArrayList<>();
					printableData.add(blogpost.getClass().getSimpleName());
					printableData.add(blogpost.getAddDate().toString());
					printableData.add(blogpost.getTitle());
					printableData.add(blogpost.getAuthor());
					printableData.add(blogpost.getUrl());
					printableData.add(String.valueOf(blogpost.getId()));

					// Ask the controller to print the Bookmark's data to console.
					uiController.printBlogpost(printableData);
				} else if (bookmark instanceof Video) {

					Video video = (Video) bookmark;

					// Create an ArrayList of Strings that contains the printable data IN ORDER.
					ArrayList<String> printableData = new ArrayList<>();
					printableData.add(video.getClass().getSimpleName());
					printableData.add(video.getAddDate().toString());
					printableData.add(video.getTitle());
					printableData.add(video.getUrl());
					printableData.add(String.valueOf(video.getId()));

					// Ask the controller to print the Bookmark's data to console.
					uiController.printVideo(printableData);
				}
			}
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
		}

		// No need to print an empty line here since the listed bookmarks have an empty line at the end.
	}
}
